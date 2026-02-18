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
import java.util.function.Consumer;


public class IndexController implements ActionListener, ITransitionScreen {

    private View view;
    private Model model;

    private SupplierController supplierController;
    private LocationController locationController;
    private BagController bagController;
    private DashboardController dashboardController;

    public IndexController(View view, Model model) {
        this.view = view;
        this.model = model;
        addActionListener(this);
        setNavigationEnabled(false);
//        this.supplierController = new SupplierController(view.supplierView, model, this);
//        this.locationController = new LocationController(view.locationView, model, this);
//        this.bagController = new BagController(view.BAG_VIEW, model, this);
//        this.dashboardController = new DashboardController(view.DASHBOARD_VIEW, model, this);
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

    private void initCards() {

        view.panelCenter.removeAll();
        view.panelCenter.add(view.DASHBOARD_VIEW.dashboardView,"dashboardView");
        view.panelCenter.add(view.supplierView.SupplierCard,"supplierCard");
        view.panelCenter.add(view.locationView.locationCard,"locationCard");
        view.panelCenter.add(view.BAG_VIEW.bagCard,"bagCard");
        view.panelCenter.revalidate();
        view.panelCenter.repaint();
    }


    private void connected() {

        if (!HibernateUtil.isConnected()) {
            HibernateUtil.connect();

            view.itemConectar.setText("Desconectar");
            view.itemConectar.setActionCommand("Desconectar");
            setNavigationEnabled(true);
            initCards();
            // Crear controladores ahora que hay conexión
            this.supplierController = new SupplierController(view.supplierView, model, this);
            this.locationController = new LocationController(view.locationView, model, this);
            this.bagController = new BagController(view.BAG_VIEW, model, this);
            this.dashboardController = new DashboardController(view.DASHBOARD_VIEW, model, this);
        }
    }


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

    private void setNavigationEnabled(boolean enabled) {

        view.btnDashboard.setEnabled(enabled);
        view.btnWestBags.setEnabled(enabled);
        view.btnWestLocation.setEnabled(enabled);
        view.btnWestSuppliers.setEnabled(enabled);

    }


    @Override
    public void goToBag() {
        showCard("bagCard");
        bagController.initComponent();
    }

    @Override
    public void goToDashboard() {
        showCard("dashboardView");
        dashboardController.reloadTable();
    }

    @Override
    public void goToSupplier() {
        showCard("supplierCard");
    }

    @Override
    public void goToLocation() {
        showCard("locationCard");
    }

    @Override
    public void goToBagWithBag(Bag bag) {
        showCard("bagCard");
        bagController.loadBagForEdit(bag);
    }

    private void showCard(String cardName) {
        CardLayout card = (CardLayout) view.panelCenter.getLayout();
        card.show(view.panelCenter, cardName);
    }


}
