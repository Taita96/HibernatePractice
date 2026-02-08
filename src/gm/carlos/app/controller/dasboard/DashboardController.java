package gm.carlos.app.controller.dasboard;

import gm.carlos.app.model.Model;
import gm.carlos.app.model.entity.Bag;
import gm.carlos.app.model.entity.Location;
import gm.carlos.app.model.entity.LocationBag;
import gm.carlos.app.model.entity.Supplier;
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

    public DashboardController(DashboardView dashboardView, Model model) {
        this.dashboardView = dashboardView;
        this.model = model;
        addActionListener(this);
        addListSelectionListener(this);
        addTableModelListener(this);
        reloadTable();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String evt = e.getActionCommand();

        switch (evt) {
            case "btnRegister": {

                break;
            }
            case "btnUpdate": {

                break;
            }
            case "btnDelete": {

                break;
            }
        }
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

    private void reloadTable() {

        List<Bag> bagList = model.getBagService().getAllWithDetails();

        String[] columns = {"ID", "Code", "Status", "Model", "Material", "Color","Quantity","Price","Location Aisle", "Location Shelf", "Suppliers Name", "Suppliers Contact"};

        dashboardView.dtmTableDasboard.setRowCount(0);
        dashboardView.dtmTableDasboard.setColumnIdentifiers(columns);

        for(int i = 0; i < bagList.size(); i++){
            dashboardView.dtmTableDasboard.addRow(new Object[]{
                    bagList.get(i).getIdbag(),
                    bagList.get(i).getCode(),
                    bagList.get(i).getStatus(),
                    bagList.get(i).getModel(),
                    bagList.get(i).getTechnicalSheet().getMaterial(),
                    bagList.get(i).getTechnicalSheet().getColor(),
                    bagList.get(i).getStock().getQuantity(),
                    bagList.get(i).getStock().getPrice(),
                    bagList.get(i).getLocationBags().get(i).getLocationBags().getAisle(),
                    bagList.get(i).getLocationBags().get(i).getLocationBags().getShelf(),
                    bagList.get(i).getSuppliers().get(i).getName(),
                    bagList.get(i).getSuppliers().get(i).getContact(),
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
