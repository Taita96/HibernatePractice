package gm.carlos.app.controller.location;

import gm.carlos.app.model.Model;
import gm.carlos.app.model.entity.Location;
import gm.carlos.app.model.repository.ITransitionScreen;
import gm.carlos.app.util.Utilities;
import gm.carlos.app.view.location.LocationView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LocationController implements ActionListener, ListSelectionListener, TableModelListener {

    private LocationView locationView;
    private Model model;
    Location currentLocation;

    private ITransitionScreen navigation;

    public LocationController(LocationView locationView, Model model,ITransitionScreen navigation) {
        this.locationView = locationView;
        this.model = model;
        this.navigation = navigation;
        addActionListener(this);
        addListSelectionListener(this);
        addTableModelListener(this);
        reloadTable();
    }
    private void addActionListener(ActionListener actionListener) {
        locationView.btnFormClean.addActionListener(actionListener);
        locationView.btnFormSave.addActionListener(actionListener);
        locationView.btnFormUpdate.addActionListener(actionListener);
        locationView.btnTableDelete.addActionListener(actionListener);
        locationView.btnTableGoHome.addActionListener(actionListener);
    }

    private void addListSelectionListener(ListSelectionListener e) {
        locationView.tableLocation.setCellSelectionEnabled(true);
        ListSelectionModel tableAdminProducts = locationView.tableLocation.getSelectionModel();
        tableAdminProducts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableAdminProducts.addListSelectionListener(e);
    }

    private void addTableModelListener(TableModelListener e) {
        locationView.dtmTableLocation.addTableModelListener(e);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String evt = e.getActionCommand();

        switch (evt) {
            case "btnFormSave": {
                save();
                break;
            }
            case "btnFormUpdate": {
                updateForm();
                break;
            }
            case "btnFormClean": {
                cleanForm();
                break;
            }
            case "btnTableGoHome": {
                navigation.confirmNavigation(currentLocation, n -> {navigation.goToDashboard();});
                break;
            }
            case "btnTableDelete": {
                delete();
                break;
            }
        }
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    private void delete() {
        int row = locationView.tableLocation.getSelectedRow();

        if (row < 0) {
            Utilities.showErrorAlert("Select a Supplier first");
            return;
        }

        model.getLocationService().delete(currentLocation);
        cleanUI();
    }

    private void updateForm() {

        String Aisle = locationView.txtAisle.getText();
        String Shelf = locationView.txtShelf.getText();

        if (validateForm(Aisle, Shelf)) {
            return;
        }

        if (currentLocation != null) {
            currentLocation.setAisle(Aisle);
            currentLocation.setShelf(Shelf);
            model.getLocationService().update(currentLocation);
            Utilities.showInfoAlert("Location has been Updated successfully.");
        }

        cleanUI();
    }

    private void save() {

        String Aisle = locationView.txtAisle.getText();
        String Shelf = locationView.txtShelf.getText();

        if (validateForm(Aisle, Shelf)) {
            return;
        }

        if (currentLocation == null) {
            Location location = new Location(Aisle, Shelf);
            model.getLocationService().save(location);
            Utilities.showInfoAlert("Location has been saved successfully.");
            cleanUI();
        }else{
            Utilities.showWarningAlert("you should Click On Update Buttom.");
            locationView.btnFormUpdate.requestFocus();
        }

    }

    private boolean validateForm(String Aisle, String Shelf) {
        if (Aisle.isEmpty()) {
            Utilities.showErrorAlert("Please Fill Field Aisle");
            locationView.txtAisle.requestFocus();
            return true;
        }

        if (Shelf.isEmpty()) {
            Utilities.showErrorAlert("Please Fill Field Shelf");
            locationView.txtShelf.requestFocus();
            return true;
        }

        return false;
    }

    private void cleanForm() {

        locationView.txtShelf.setText("");
        locationView.txtAisle.setText("");
    }

    public void cleanUI(){
        currentLocation = null;
        reloadTable();
        cleanForm();
    }

    private void reloadTable() {

        List<Location> locationList = model.getLocationService().getAll();

        String[] comlums = {"ID", "Aisle", "Shelf"};
        locationView.dtmTableLocation.setRowCount(0);
        locationView.dtmTableLocation.setColumnIdentifiers(comlums);

        for (Location location : locationList) {
            locationView.dtmTableLocation.addRow(new Object[]{
                    location.getIdlocation(),
                    location.getAisle(),
                    location.getShelf()
            });
        }
        Utilities.centerTable(locationView.tableLocation);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting() && !((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
            if (e.getSource().equals(locationView.tableLocation.getSelectionModel())) {
                int row = locationView.tableLocation.getSelectedRow();

                if (row < 0) {
                    Utilities.showErrorAlert("Select a location first");
                    return;
                }

                int id = (int) locationView.tableLocation.getValueAt(row, 0);
                System.out.println(id);

                String Aisle = String.valueOf(locationView.tableLocation.getValueAt(row, 1));
                String Shelf = String.valueOf(locationView.tableLocation.getValueAt(row, 2));

                locationView.txtAisle.setText(Aisle);
                locationView.txtShelf.setText(Shelf);

                currentLocation = new Location(Aisle, Shelf);
                currentLocation.setIdlocation(id);

            } else if (e.getValueIsAdjusting() && ((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
                if (e.getSource().equals(locationView.tableLocation.getSelectionModel())) {
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

        Object newValue = locationView.tableLocation.getValueAt(row, column);
        int supplierId = (int) locationView.tableLocation.getValueAt(row, 0);
        Location location = model.getLocationService().getById(supplierId);

        switch (column) {
            case 1:
                location.setAisle(newValue.toString().trim());
                break;
            case 2:
                location.setShelf(newValue.toString().trim());
                break;
        }

        model.getLocationService().update(location);
        cleanUI();
        Utilities.showInfoAlert("Location has been Updated successfully.");
    }
}
