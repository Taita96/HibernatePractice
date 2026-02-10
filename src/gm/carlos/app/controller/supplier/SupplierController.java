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

public class SupplierController implements ActionListener, ListSelectionListener, TableModelListener {

    private SupplierView supplierView;
    private Model model;
    Supplier currentSupplier;

    private ITransitionScreen navigation;

    public SupplierController(SupplierView supplierView, Model model,ITransitionScreen navigation) {
        this.supplierView = supplierView;
        this.model = model;
        this.navigation = navigation;
        addActionListener(this);
        addListSelectionListener(this);
        addTableModelListener(this);
        reloadTable();
    }


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

    private void delete() {
        int row = supplierView.tableSuppliers.getSelectedRow();

        if (row < 0) {
            Utilities.showErrorAlert("Select a Supplier first");
            return;
        }

        model.getSupplierService().deleteSupplier(currentSupplier);
        cleanUI();
    }

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

    private void cleanForm() {
        supplierView.txtSupplierName.setText("");
        supplierView.txtSupplierContact.setText("");
    }

    public void cleanUI(){
        currentSupplier = null;
        reloadTable();
        cleanForm();
    }

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
