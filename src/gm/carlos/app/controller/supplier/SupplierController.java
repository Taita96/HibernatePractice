package gm.carlos.app.controller.supplier;

import gm.carlos.app.model.Model;
import gm.carlos.app.model.entity.Supplier;
import gm.carlos.app.model.repository.ITransitionScreen;
import gm.carlos.app.util.Utilities;
import gm.carlos.app.view.supplier.SupplierView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Controlador encargado de la gestión de proveedores (Supplier) dentro del sistema.
 *
 * <p>Forma parte de la capa de presentación en la arquitectura MVC, actuando como
 * intermediario entre {@link SupplierView} y los servicios accesibles desde {@link Model}.</p>
 *
 * <p>Este controlador permite:
 * <ul>
 *     <li>Registrar nuevos proveedores.</li>
 *     <li>Actualizar proveedores existentes.</li>
 *     <li>Eliminar proveedores.</li>
 *     <li>Validar los datos introducidos por el usuario.</li>
 *     <li>Editar datos directamente desde la tabla (inline editing).</li>
 *     <li>Gestionar la navegación entre pantallas.</li>
 * </ul>
 * </p>
 *
 * <p>No contiene acceso directo a la base de datos; todas las operaciones se delegan
 * al {@code SupplierService}, manteniendo la separación de responsabilidades.</p>
 *
 * @author Carlos
 * @version 1.0
 */
public class SupplierController implements ActionListener, ListSelectionListener, TableModelListener {

    private SupplierView supplierView;
    private Model model;
    Supplier currentSupplier;

    private ITransitionScreen navigation;

    /**
     * Inicializa el controlador de proveedores.
     * Registra los listeners de la interfaz y carga los datos iniciales.
     *
     * @param supplierView vista asociada a proveedores
     * @param model modelo central del sistema
     * @param navigation gestor de navegación entre pantallas
     */
    public SupplierController(SupplierView supplierView, Model model,ITransitionScreen navigation) {
        this.supplierView = supplierView;
        this.model = model;
        this.navigation = navigation;
        addActionListener(this);
        addListSelectionListener(this);
        addTableModelListener(this);
        reloadTable();
    }


    /**
     * Asocia los botones de la interfaz con el controlador.
     *
     * @param actionListener listener que gestionará los eventos de usuario
     */
    private void addActionListener(ActionListener actionListener) {
        supplierView.btnFormSupplierUpdate.addActionListener(actionListener);
        supplierView.btnFormSupplierSave.addActionListener(actionListener);
        supplierView.btnFormSupplierClean.addActionListener(actionListener);
        supplierView.btnTableSupplierDelete.addActionListener(actionListener);
        supplierView.btnTableGoHome.addActionListener(actionListener);
    }

    private void addListSelectionListener(ListSelectionListener e) {
        supplierView.tableSuppliers.setCellSelectionEnabled(true);
        ListSelectionModel tableAdminProducts = supplierView.tableSuppliers.getSelectionModel();
        tableAdminProducts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableAdminProducts.addListSelectionListener(e);
    }

    private void addTableModelListener(TableModelListener e) {
        supplierView.dtmTableSupplier.addTableModelListener(e);
    }


    /**
     * Gestiona las acciones lanzadas por los botones de la interfaz.
     * Ejecuta la operación correspondiente según el comando recibido.
     *
     * @param e evento de acción
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String evt = e.getActionCommand();

        switch (evt) {
            case "btnFormSupplierSave": {
                save();
                break;
            }
            case "btnFormSupplierUpdate": {
                updateForm();
                break;
            }
            case "btnFormSupplierUpdateClean": {
                cleanForm();
                break;
            }
            case "btnTableGoHome": {
                navigation.confirmNavigation(currentSupplier, n -> {navigation.goToDashboard();});
                break;
            }
            case "btnTableSupplierDelete": {
                delete();
                break;
            }
        }
    }

    public Supplier getCurrentSupplier() {
        return currentSupplier;
    }

    /**
     * Elimina el proveedor seleccionado del sistema.
     */
    private void delete() {
        int row = supplierView.tableSuppliers.getSelectedRow();

        if (row < 0) {
            Utilities.showErrorAlert("Select a Supplier first");
            return;
        }

        if (!model.getSupplierService().deleteSupplier(currentSupplier)) {
            Utilities.showWarningAlert(
                    "Cannot delete supplier. It is associated with one or more Bags."
            );
            return;
        }

//        model.getSupplierService().deleteSupplier(currentSupplier);
        cleanUI();
    }

    /**
     * Actualiza los datos del proveedor actualmente seleccionado utilizando
     * la información introducida en el formulario.
     */
    private void updateForm() {

        String name = supplierView.txtSupplierName.getText();
        String contact = supplierView.txtSupplierContact.getText();

        if (validateForm(name, contact)) {
            return;
        }

        if (currentSupplier != null) {
            currentSupplier.setName(name);
            currentSupplier.setContact(contact);
            model.getSupplierService().updateSupplier(currentSupplier);
            Utilities.showInfoAlert("Supplier has been Updated successfully.");
        }

        cleanUI();
    }

    /**
     * Guarda un nuevo proveedor en el sistema.
     *
     * <p>Solo se permite la inserción cuando no hay un proveedor seleccionado.</p>
     * <p>Los datos se validan antes de persistirse.</p>
     */
    private void save() {

        String name = supplierView.txtSupplierName.getText();
        String contact = supplierView.txtSupplierContact.getText();

        if (validateForm(name, contact)) {
            return;
        }

        if (currentSupplier == null) {
            Supplier supplier = new Supplier(name, contact);
            model.getSupplierService().saveSupplier(supplier);
            Utilities.showInfoAlert("Supplier has been saved successfully.");
            cleanUI();
        }else{
            Utilities.showWarningAlert("you should Click On Update Buttom.");
            supplierView.btnFormSupplierUpdate.requestFocus();
        }

    }

    /**
     * Valida los datos introducidos en el formulario.
     *
     * <ul>
     *     <li>El nombre no puede estar vacío.</li>
     *     <li>El contacto no puede estar vacío.</li>
     *     <li>El contacto debe tener formato de email válido.</li>
     * </ul>
     *
     * @param name nombre del proveedor
     * @param contact email o contacto del proveedor
     * @return true si existe algún error de validación, false si los datos son válidos
     */
    private boolean validateForm(String name, String contact) {
        if (name.isEmpty()) {
            Utilities.showErrorAlert("Please Fill Field Name");
            return true;
        }

        if (contact.isEmpty()) {
            Utilities.showErrorAlert("Please Fill Field Name");
            supplierView.txtSupplierName.requestFocus();
            return true;
        }

        if(Utilities.validateEmail(contact)){
            Utilities.showErrorAlert("Email incorrect Form.");
            supplierView.txtSupplierContact.requestFocus();
            return true;
        }

        return false;
    }

    /**
     * Limpia los campos del formulario de proveedor.
     */
    private void cleanForm() {
        supplierView.txtSupplierName.setText("");
        supplierView.txtSupplierContact.setText("");
    }

    /**
     * Restablece completamente la interfaz:
     * <ul>
     *     <li>Deselecciona el proveedor actual.</li>
     *     <li>Recarga la tabla de datos.</li>
     *     <li>Limpia el formulario.</li>
     * </ul>
     */
    public void cleanUI(){
        currentSupplier = null;
        reloadTable();
        cleanForm();
    }

    /**
     * Recarga la lista de proveedores desde la base de datos y actualiza la tabla visual.
     */
    private void reloadTable() {

        List<Supplier> supplierList = model.getSupplierService().getAllSupplier();

        String[] comlums = {"ID", "Name", "Contact"};
        supplierView.dtmTableSupplier.setRowCount(0);
        supplierView.dtmTableSupplier.setColumnIdentifiers(comlums);

        for (Supplier supplier : supplierList) {
            supplierView.dtmTableSupplier.addRow(new Object[]{
                    supplier.getIdsupplier(),
                    supplier.getName(),
                    supplier.getContact()
            });
        }
        Utilities.centerTable(supplierView.tableSuppliers);
    }

    /**
     * Se ejecuta cuando el usuario selecciona una fila de la tabla.
     * Carga los datos del proveedor en el formulario para permitir su edición.
     *
     * @param e evento de selección
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting() && !((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
            if (e.getSource().equals(supplierView.tableSuppliers.getSelectionModel())) {
                int row = supplierView.tableSuppliers.getSelectedRow();

                if (row < 0) {
                    Utilities.showErrorAlert("Select a Supplier first");
                    return;
                }

                int id = (int) supplierView.tableSuppliers.getValueAt(row, 0);
                System.out.println(id);

                String name = String.valueOf(supplierView.tableSuppliers.getValueAt(row, 1));
                String contact = String.valueOf(supplierView.tableSuppliers.getValueAt(row, 2));

                supplierView.txtSupplierName.setText(name);
                supplierView.txtSupplierContact.setText(contact);

                currentSupplier = new Supplier(name, contact);
                currentSupplier.setIdsupplier(id);

            } else if (e.getValueIsAdjusting() && ((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
                if (e.getSource().equals(supplierView.tableSuppliers.getSelectionModel())) {
                    cleanUI();
                }
            }

        }
    }

    /**
     * Detecta modificaciones directas realizadas sobre la tabla.
     *
     * <p>Cuando el usuario edita una celda:
     * <ul>
     *     <li>Se valida el nuevo valor.</li>
     *     <li>Se actualiza automáticamente el proveedor en la base de datos.</li>
     * </ul>
     * </p>
     *
     * @param e evento de modificación del modelo de tabla
     */
    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();

        if (row < 0 || column < 0) {
            return;
        }

        Object newValue = supplierView.tableSuppliers.getValueAt(row, column);
        int supplierId = (int) supplierView.tableSuppliers.getValueAt(row, 0);
        Supplier supplier = model.getSupplierService().getByIdSupplier(supplierId);

        switch (column) {
            case 1:
                supplier.setName(newValue.toString().trim());
                break;
            case 2:
                if (Utilities.validateEmail(newValue.toString())) {
                    Utilities.showErrorAlert("Email incorrect Form.");
                    cleanUI();
                    return;
                }
                supplier.setContact(newValue.toString().trim());
                break;
        }

        model.getSupplierService().updateSupplier(supplier);
        cleanUI();
        Utilities.showInfoAlert("Supplier has been Updated successfully.");
    }


}
