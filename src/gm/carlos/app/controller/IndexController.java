package gm.carlos.app.controller;

import gm.carlos.app.controller.bag.BagController;
import gm.carlos.app.controller.dasboard.DashboardController;
import gm.carlos.app.controller.location.LocationController;
import gm.carlos.app.controller.supplier.SupplierController;
import gm.carlos.app.model.Model;
import gm.carlos.app.model.entity.Bag;
import gm.carlos.app.model.entity.Location;
import gm.carlos.app.model.entity.Supplier;
import gm.carlos.app.model.repository.ITransitionScreen;
import gm.carlos.app.util.HibernateUtil;
import gm.carlos.app.util.Utilities;
import gm.carlos.app.view.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.function.Consumer;

/**
 * Controlador principal de la aplicación.
 *
 * <p>Actúa como punto central de coordinación entre la vista general {@link View},
 * el modelo {@link Model} y los controladores específicos de cada módulo
 * (Bags, Suppliers, Locations y Dashboard).</p>
 *
 * <p>Implementa el patrón Front Controller, encargándose de:
 * <ul>
 *     <li>Gestionar la conexión y desconexión con la base de datos mediante Hibernate.</li>
 *     <li>Inicializar los controladores secundarios.</li>
 *     <li>Controlar la navegación entre pantallas utilizando CardLayout.</li>
 *     <li>Evitar cambios de pantalla cuando existen ediciones en curso.</li>
 *     <li>Centralizar los eventos principales de la aplicación.</li>
 * </ul>
 * </p>
 *
 * <p>También implementa {@link ITransitionScreen} para desacoplar la navegación
 * de los controladores secundarios, evitando dependencias directas entre vistas.</p>
 *
 * <p>Este diseño garantiza una arquitectura MVC desacoplada y mantenible.</p>
 *
 * @author Carlos
 * @version 1.0
 */
public class IndexController implements ActionListener, ITransitionScreen, WindowListener {

    private View view;
    private Model model;

    private SupplierController supplierController;
    private LocationController locationController;
    private BagController bagController;
    private DashboardController dashboardController;

    /**
     * Inicializa el controlador principal del sistema.
     *
     * @param view vista principal de la aplicación
     * @param model modelo central con acceso a servicios
     */
    public IndexController(View view, Model model) {
        this.view = view;
        this.model = model;
        addActionListener(this);
        addWindowListener(this);
        setNavigationEnabled(false);
//        this.supplierController = new SupplierController(view.supplierView, model, this);
//        this.locationController = new LocationController(view.locationView, model, this);
//        this.bagController = new BagController(view.BAG_VIEW, model, this);
//        this.dashboardController = new DashboardController(view.DASHBOARD_VIEW, model, this);
    }

    private void addWindowListener(WindowListener e) {
        this.view.addWindowListener(e);
    }

    private void addActionListener(ActionListener actionListener) {
        view.btnDashboard.addActionListener(actionListener);
        view.btnWestBags.addActionListener(actionListener);
        view.btnWestLocation.addActionListener(actionListener);
        view.btnWestSuppliers.addActionListener(actionListener);
        view.itemConectar.addActionListener(actionListener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String evt = e.getActionCommand();

        switch (evt) {
            case "btnWestSuppliers": {
                navigateControl(n -> {
                    goToSupplier();
                });
                break;
            }
            case "btnWestLocation": {
                navigateControl(n -> {
                    goToLocation();
                });
                break;
            }
            case "btnWestBags": {
                navigateControl(n -> {
                    goToBag();
                });
                break;
            }
            case "btnDashboard": {
                navigateControl(n -> {
                    goToDashboard();
                });
                break;
            }
            case "Conectar": {
                connected();
                break;
            }
            case "Desconectar": {
                disconnected();
                break;
            }
        }
    }

    /**
     * Cierra la conexión con la base de datos y bloquea la navegación.
     */
    private void disconnected() {

        if (HibernateUtil.isConnected()) {

            HibernateUtil.disconnect();
            panelNoConnection();
            setNavigationEnabled(false);
            view.itemConectar.setText("Conectar");
            view.itemConectar.setActionCommand("Conectar");
            JOptionPane.showMessageDialog(null, "Database disconnected.");
        }
    }

    private void panelNoConnection(){
        view.panelCenter.removeAll();
        view.panelCenter.add(view.CONNECTION_VIEW.mainPanel,"CardConecction");
        view.panelCenter.revalidate();
        view.panelCenter.repaint();
    }

    /**
     * Registra todas las pantallas del sistema dentro del CardLayout central.
     */
    private void initCards() {

        view.panelCenter.removeAll();
        view.panelCenter.add(view.DASHBOARD_VIEW.dashboardView,"dashboardView");
        view.panelCenter.add(view.supplierView.SupplierCard,"supplierCard");
        view.panelCenter.add(view.locationView.locationCard,"locationCard");
        view.panelCenter.add(view.BAG_VIEW.bagCard,"bagCard");
        view.panelCenter.revalidate();
        view.panelCenter.repaint();
    }


    /**
     * Establece la conexión con la base de datos.
     *
     * <p>Al conectarse:
     * <ul>
     *     <li>Inicializa Hibernate.</li>
     *     <li>Habilita la navegación.</li>
     *     <li>Registra las vistas en el CardLayout.</li>
     *     <li>Crea los controladores secundarios.</li>
     * </ul>
     * </p>
     */
    private void connected() {

        if (!HibernateUtil.isConnected()) {
            HibernateUtil.connect();

            view.itemConectar.setText("Desconectar");
            view.itemConectar.setActionCommand("Desconectar");
            setNavigationEnabled(true);
            initCards();
            this.supplierController = new SupplierController(view.supplierView, model, this);
            this.locationController = new LocationController(view.locationView, model, this);
            this.bagController = new BagController(view.BAG_VIEW, model, this);
            this.dashboardController = new DashboardController(view.DASHBOARD_VIEW, model, this);
        }
    }


    /**
     * Controla los cambios de pantalla evitando pérdida de datos si el usuario
     * está editando información.
     *
     * @param accion acción de navegación solicitada
     */
    @Override
    public void navigateControl(Consumer<ITransitionScreen> accion) {

        if (!HibernateUtil.isConnected()) {
            JOptionPane.showMessageDialog(null, "You must connect to the database first.");
            return;
        }

        if (bagController.getCurrentbag() != null) {
            confirmNavigation(bagController.getCurrentbag(), accion);
        } else if (supplierController.getCurrentSupplier() != null) {
            confirmNavigation(supplierController.getCurrentSupplier(), accion);
        } else if (locationController.getCurrentLocation() != null) {
            confirmNavigation(locationController.getCurrentLocation(), accion);
        } else {
            goScreen(accion);
        }
    }

    @Override
    public void goScreen(Consumer<ITransitionScreen> accion) {
        accion.accept(this);
    }

    /**
     * Solicita confirmación al usuario antes de abandonar una edición en curso.
     *
     * @param object objeto actualmente en edición
     * @param accion acción de navegación a ejecutar si se confirma
     */
    @Override
    public void confirmNavigation(Object object, Consumer<ITransitionScreen> accion) {
        int option = 0;
        String title = "Alert";
        String message = "You're currently updating a Item, are you sure want to go to Home?";
        if (object != null) {

            if (object instanceof Bag) {
                option = Utilities.confirmMessage(message, title);
                if (option == JOptionPane.YES_NO_OPTION) {
                    bagController.clearForm();
                    goScreen(accion);
                }
            } else if (object instanceof Supplier) {
                option = Utilities.confirmMessage(message, title);
                if (option == JOptionPane.YES_NO_OPTION) {
                    supplierController.cleanUI();
                    goScreen(accion);
                }

            } else if (object instanceof Location) {
                option = Utilities.confirmMessage(message, title);
                if (option == JOptionPane.YES_NO_OPTION) {
                    locationController.cleanUI();
                    goScreen(accion);
                }
            }
        }
    }

    /**
     * Habilita o deshabilita los botones de navegación lateral.
     *
     * @param enabled estado de habilitación
     */
    private void setNavigationEnabled(boolean enabled) {

        view.btnDashboard.setEnabled(enabled);
        view.btnWestBags.setEnabled(enabled);
        view.btnWestLocation.setEnabled(enabled);
        view.btnWestSuppliers.setEnabled(enabled);

    }


    /**
     * Navega a la pantalla de gestión de bolsas.
     */
    @Override
    public void goToBag() {
        showCard("bagCard");
        bagController.initComponent();
    }

    /**
     * Muestra la pantalla principal (Dashboard) y actualiza los datos visibles.
     */
    @Override
    public void goToDashboard() {
        showCard("dashboardView");
        dashboardController.reloadTable();
    }
    /**
     * Navega a la pantalla de gestión de proveedores.
     */
    @Override
    public void goToSupplier() {
        showCard("supplierCard");
    }

    /**
     * Navega a la pantalla de gestión de ubicaciones.
     */
    @Override
    public void goToLocation() {
        showCard("locationCard");
    }

    /**
     * Abre la pantalla de edición cargando una bolsa específica.
     *
     * @param bag bolsa a editar
     */
    @Override
    public void goToBagWithBag(Bag bag) {
        showCard("bagCard");
        bagController.loadBagForEdit(bag);
    }

    /**
     * Cambia la vista visible dentro del CardLayout.
     *
     * @param cardName nombre de la tarjeta a mostrar
     */
    private void showCard(String cardName) {
        CardLayout card = (CardLayout) view.panelCenter.getLayout();
        card.show(view.panelCenter, cardName);
    }


    @Override
    public void windowOpened(WindowEvent e) {

    }

    /**
     * Controla el cierre de la aplicación solicitando confirmación al usuario
     * y liberando los recursos de Hibernate.
     */
    @Override
    public void windowClosing(WindowEvent e) {
        int option = Utilities.confirmMessage("Do you really want to exit the application?", "Exit Application");
        if (option == JOptionPane.YES_OPTION) {
            HibernateUtil.disconnect();
            System.exit(0);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
