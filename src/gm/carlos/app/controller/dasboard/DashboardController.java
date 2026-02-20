package gm.carlos.app.controller.dasboard;

import gm.carlos.app.model.Model;
import gm.carlos.app.model.entity.Bag;
import gm.carlos.app.model.entity.LocationBag;
import gm.carlos.app.model.entity.Supplier;
import gm.carlos.app.model.repository.ITransitionScreen;
import gm.carlos.app.util.Utilities;
import gm.carlos.app.view.dashboard.DashboardView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


/**
 * Controlador de la pantalla principal (Dashboard) de la aplicación.
 *
 * <p>Este controlador gestiona la visualización y administración de los bolsos registrados
 * en el sistema. Forma parte de la capa de presentación dentro del patrón MVC,
 * actuando como intermediario entre {@link DashboardView} y los servicios accesibles
 * a través de {@link Model}.</p>
 *
 * <p>Responsabilidades principales:
 * <ul>
 *     <li>Cargar y mostrar los bolsos en la tabla del dashboard.</li>
 *     <li>Gestionar acciones del usuario: registrar, actualizar y eliminar.</li>
 *     <li>Coordinar la navegación entre pantallas mediante {@link ITransitionScreen}.</li>
 *     <li>Refrescar la información mostrada tras cualquier operación.</li>
 * </ul>
 * </p>
 *
 * <p>Este controlador NO accede directamente a la base de datos; delega toda la
 * lógica de negocio al modelo y a la capa de servicios.</p>
 *
 * @author Carlos
 * @version 1.0
 */
public class DashboardController implements ActionListener, ListSelectionListener, TableModelListener {

    private DashboardView dashboardView;
    private Model model;
    private ITransitionScreen navigator;

    /**
     * Inicializa el controlador del Dashboard y registra los listeners necesarios
     * para interactuar con la interfaz gráfica.
     *
     * @param dashboardView vista principal que contiene la tabla y los botones de gestión
     * @param model modelo central que proporciona acceso a los servicios de negocio
     * @param navigator interfaz encargada de la navegación entre pantallas
     */
    public DashboardController(DashboardView dashboardView, Model model,ITransitionScreen navigator) {
        this.dashboardView = dashboardView;
        this.model = model;
        this.navigator = navigator;
        addActionListener(this);
        addListSelectionListener(this);
        addTableModelListener(this);
        reloadTable();
    }

    /**
     * Asocia los botones de la vista con el controlador para capturar las acciones del usuario.
     *
     * @param actionListener listener que manejará los eventos de los botones
     */
    private void addActionListener(ActionListener actionListener) {
        dashboardView.btnRegister.addActionListener(actionListener);
        dashboardView.btnUpdate.addActionListener(actionListener);
        dashboardView.btnDelete.addActionListener(actionListener);
    }

    /**
     * Configura el comportamiento de selección de la tabla del dashboard.
     * Permite seleccionar una única fila para operar sobre un bolso concreto.
     *
     * @param e listener que detecta cambios de selección
     */
    private void addListSelectionListener(ListSelectionListener e) {
        dashboardView.table.setCellSelectionEnabled(true);
        ListSelectionModel tableAdminProducts = dashboardView.table.getSelectionModel();
        tableAdminProducts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableAdminProducts.addListSelectionListener(e);
    }

    /**
     * Registra un listener sobre el modelo de la tabla para detectar cambios en los datos mostrados.
     *
     * @param e listener del modelo de tabla
     */
    private void addTableModelListener(TableModelListener e) {
        dashboardView.dtmTableDasboard.addTableModelListener(e);
    }

    /**
     * Gestiona las acciones ejecutadas por los botones del Dashboard.
     * Dependiendo del comando recibido realiza la navegación o la operación solicitada.
     *
     * @param e evento generado por la interacción del usuario
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String evt = e.getActionCommand();

        switch (evt) {
            case "btnRegister": {
                navigator.goToBag();
                break;
            }
            case "btnUpdate": {
                updateFromForm();
                break;
            }
            case "btnDelete": {
                    deleteBag();
                break;
            }
        }
    }

    /**
     * Elimina lógicamente (soft delete) el bolso seleccionado en la tabla.
     *
     * <p>Primero valida que exista una selección. Luego recupera el bolso desde el servicio
     * y ejecuta su eliminación lógica. Finalmente recarga la tabla.</p>
     */
    private void deleteBag() {

        int row = dashboardView.table.getSelectedRow();

        if (row < 0) {
            Utilities.showErrorAlert("Select a product first");
            return;
        }

        int bagId = (int) dashboardView.table.getValueAt(row, 0);

        Bag bag = model.getBagService().getById(bagId);

        if (bag == null) {
            Utilities.showErrorAlert("Bag not found");
            return;
        }

        model.getBagService().softDeleteById(bag.getIdbag());
        reloadTable();
    }

    /**
     * Envía el bolso seleccionado al formulario de edición.
     *
     * <p>No modifica datos directamente; simplemente navega a la pantalla de edición
     * pasando la entidad seleccionada.</p>
     */
    private void updateFromForm() {

        int row = dashboardView.table.getSelectedRow();
        if (row < 0) {
            Utilities.showErrorAlert("Select a product first");
            return;
        }

        int bagId = (int) dashboardView.table.getValueAt(row, 0);

        Bag bag = model.getBagService().getById(bagId);

        if (bag == null) {
            Utilities.showErrorAlert("Bag not found");
            return;
        }

        navigator.goToBagWithBag(bag);

    }

    /**
     * Recarga completamente la información mostrada en la tabla del Dashboard.
     *
     * <p>Obtiene todos los bolsos junto con sus relaciones (Stock, TechnicalSheet,
     * Location y Supplier) y los transforma en filas visibles para el usuario.</p>
     *
     * <p>Este método actúa como sincronizador entre la base de datos y la interfaz gráfica,
     * garantizando que siempre se muestre información actualizada.</p>
     */
    public void reloadTable() {

        List<Bag> bagList = model.getBagService().getAllWithDetails();

        String[] columns = {"ID", "Code", "Status", "Model", "Entry Date", "Material", "Color","Weight","Quantity","Price","Location Aisle", "Location Shelf", "Suppliers Name", "Suppliers Contact"};

        dashboardView.dtmTableDasboard.setRowCount(0);
        dashboardView.dtmTableDasboard.setColumnIdentifiers(columns);

        for(int i = 0; i < bagList.size(); i++){

            Bag bag = bagList.get(i);

            LocationBag lb = bag.getLocationBags().isEmpty() ? null : bag.getLocationBags().get(0);

            Supplier supplier = bag.getSuppliers().isEmpty() ? null : bag.getSuppliers().get(0);


            dashboardView.dtmTableDasboard.addRow(new Object[]{
                    bag.getIdbag(),
                    bag.getCode(),
                    bag.getStatus(),
                    bag.getModel(),
                    bag.getEntryDate(),
                    bag.getTechnicalSheet().getMaterial(),
                    bag.getTechnicalSheet().getColor(),
                    bag.getTechnicalSheet().getWeight(),
                    bag.getStock().getQuantity(),
                    bag.getStock().getPrice(),
                    lb != null ? lb.getLocationBags().getAisle() : "",
                    lb != null ? lb.getLocationBags().getShelf() : "",
                    supplier != null ? supplier.getName() : "",
                    supplier != null ? supplier.getContact() : ""
            });
        }

        Utilities.centerTable(dashboardView.table);
    }


    @Override
    public void valueChanged(ListSelectionEvent e) {

    }

    @Override
    public void tableChanged(TableModelEvent e) {

    }
}
