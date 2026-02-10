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

public class DashboardController implements ActionListener, ListSelectionListener, TableModelListener {

    private DashboardView dashboardView;
    private Model model;
    private ITransitionScreen navigator;

    public DashboardController(DashboardView dashboardView, Model model,ITransitionScreen navigator) {
        this.dashboardView = dashboardView;
        this.model = model;
        this.navigator = navigator;
        addActionListener(this);
        addListSelectionListener(this);
        addTableModelListener(this);
        reloadTable();
    }


    private void addActionListener(ActionListener actionListener) {
        dashboardView.btnRegister.addActionListener(actionListener);
        dashboardView.btnUpdate.addActionListener(actionListener);
        dashboardView.btnDelete.addActionListener(actionListener);
    }

    private void addListSelectionListener(ListSelectionListener e) {
        dashboardView.table.setCellSelectionEnabled(true);
        ListSelectionModel tableAdminProducts = dashboardView.table.getSelectionModel();
        tableAdminProducts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableAdminProducts.addListSelectionListener(e);
    }

    private void addTableModelListener(TableModelListener e) {
        dashboardView.dtmTableDasboard.addTableModelListener(e);
    }

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
    }

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
