package gm.carlos.app.controller.bag;

import gm.carlos.app.model.Model;
import gm.carlos.app.model.entity.*;
import gm.carlos.app.model.entity.enums.ColorEnum;
import gm.carlos.app.model.entity.enums.Material;
import gm.carlos.app.model.entity.enums.ModelEnum;
import gm.carlos.app.model.entity.enums.Status;
import gm.carlos.app.model.repository.ITransitionScreen;
import gm.carlos.app.util.Utilities;
import gm.carlos.app.view.bag.BagView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class BagController implements ActionListener {

    private BagView bagView;
    private Model model;
    private Bag currentbag;

    private ITransitionScreen navigation;

    public BagController(BagView bagView, Model model, ITransitionScreen navigation) {
        this.bagView = bagView;
        this.model = model;
        this.navigation = navigation;
        addActionListener(this);
        reloadInformation();
        initComponent();
    }


    private void addActionListener(ActionListener actionListener) {
        bagView.btnSave.addActionListener(actionListener);
        bagView.btnUpdate.addActionListener(actionListener);
        bagView.btnClean.addActionListener(actionListener);
        bagView.btnHome.addActionListener(actionListener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String evt = e.getActionCommand();

        switch (evt) {
            case "btnSave": {
                save();
                break;
            }
            case "btnUpdate": {
                update();
                break;
            }
            case "btnClean": {
                clearForm();
                break;
            }
            case "btnHome": {

//                if(currentbag != null){
//                    int option = Utilities.confirmMessage("You're currently updating a Bag, are you sure want to go to Home?","Alert");
//
//                    if(option == JOptionPane.YES_NO_OPTION){
//                        clearForm();
//                        navigation.goToDashboard();
//                    }
//                }

                navigation.confirmNavigation(currentbag,n -> {navigation.goToDashboard();});

                break;
            }
        }
    }

    public Bag getCurrentbag() {
        return currentbag;
    }

    public void loadBagForEdit(Bag bag) {
        this.currentbag = bag;

        bagView.txtCode.setText(bag.getCode());
        bagView.cbModel.setSelectedItem(ModelEnum.valueOf(bag.getModel()));
        bagView.cbStatus.setSelectedItem(Status.valueOf(bag.getStatus()));
        bagView.dpDateEntry.setDate(bag.getEntryDate().toLocalDate());

        bagView.spQuantity.setValue(bag.getStock().getQuantity());
        bagView.spPrice.setValue(bag.getStock().getPrice());
        bagView.spWeight.setValue(bag.getTechnicalSheet().getWeight());

        bagView.cbMaterial.setSelectedItem(
                Material.valueOf(bag.getTechnicalSheet().getMaterial())
        );
        bagView.cbColor.setSelectedItem(
                ColorEnum.valueOf(bag.getTechnicalSheet().getColor())
        );

        if (!bag.getSuppliers().isEmpty()) {
            bagView.cbSupplier.setSelectedItem(bag.getSuppliers().get(0));
        }

        if (!bag.getLocationBags().isEmpty()) {
            bagView.cbLocation.setSelectedItem(
                    bag.getLocationBags().get(0).getLocationBags()
            );
        }
    }


    private void update() {

        List<Bag> bagList = model.getBagService().getAll();

        if (currentbag == null) {
            Utilities.showErrorAlert("No bag selected for update");
            return;
        }

        Location location = (Location) bagView.cbLocation.getSelectedItem();
        Supplier supplier = (Supplier) bagView.cbSupplier.getSelectedItem();
        String code = bagView.txtCode.getText();
        ModelEnum modelEnum = (ModelEnum) bagView.cbModel.getSelectedItem();
        LocalDate dateEntry = bagView.dpDateEntry.getDate();
        Number quantityN = (Number) bagView.spQuantity.getValue();
        Number priceN = (Number) bagView.spPrice.getValue();
        Number weightN = (Number) bagView.spWeight.getValue();
        Material material = (Material) bagView.cbMaterial.getSelectedItem();
        ColorEnum colorEnum = (ColorEnum) bagView.cbColor.getSelectedItem();
        Status status = (Status) bagView.cbStatus.getSelectedItem();

        if (!validateForm(location, supplier, code, modelEnum, dateEntry, quantityN, priceN, weightN, material, colorEnum, status)) {
            return;
        }

        if (!isCodeUnique(code, bagList, currentbag)) {
            Utilities.showErrorAlert(code + " already exists");
            return;
        }
        int quantity = quantityN.intValue();
        int price = priceN.intValue();
        BigDecimal weight = BigDecimal.valueOf(weightN.doubleValue());

        //Datos Bolso
        currentbag.setCode(code);
        currentbag.setModel(modelEnum.toString());
        currentbag.setStatus(status.toString());
        currentbag.setEntryDate(Date.valueOf(dateEntry));
        //Datos Stock
        currentbag.getStock().setQuantity(quantity);
        currentbag.getStock().setPrice(price);
        //Datos TechnicalSheet
        currentbag.getTechnicalSheet().setMaterial(material.toString());
        currentbag.getTechnicalSheet().setColor(colorEnum.toString());
        currentbag.getTechnicalSheet().setWeight(weight);
        //Datos Suppliers
        currentbag.getSuppliers().clear();
        Supplier managedSupplier = model.getSupplierService().getByIdSupplier(supplier.getIdsupplier());
        currentbag.getSuppliers().add(managedSupplier);

        //Datos Location
        currentbag.getLocationBags().clear();
        LocationBag locationBag = new LocationBag();
        locationBag.setBag(currentbag);
        locationBag.setLocationBags(location);
        currentbag.getLocationBags().add(locationBag);

        boolean updated = model.getBagService().update(currentbag);

        if (updated) {

            if (currentbag.getStock().getQuantity() == 0) {
                model.getBagService().delete(currentbag);
                Utilities.showInfoAlert("The bag has been deleted. There are no more bags");
                clearForm();
                reloadInformation();
                navigation.goToDashboard();
                return;
            }
            Utilities.showInfoAlert("Bag updated successfully");
            clearForm();
            reloadInformation();
            navigation.goToDashboard();
        } else {
            Utilities.showErrorAlert("Error updating bag");
        }

    }

    private void save() {

        Location location = (Location) bagView.cbLocation.getSelectedItem();
        Supplier supplier = (Supplier) bagView.cbSupplier.getSelectedItem();
        String code = bagView.txtCode.getText();
        ModelEnum modelEnum = (ModelEnum) bagView.cbModel.getSelectedItem();
        LocalDate dateEntry = bagView.dpDateEntry.getDate();
        Number quantityN = (Number) bagView.spQuantity.getValue();
        Number priceN = (Number) bagView.spPrice.getValue();
        Number weightN = (Number) bagView.spWeight.getValue();
        Material material = (Material) bagView.cbMaterial.getSelectedItem();
        ColorEnum colorEnum = (ColorEnum) bagView.cbColor.getSelectedItem();
        Status status = (Status) bagView.cbStatus.getSelectedItem();

        if (!validateForm(location, supplier, code, modelEnum, dateEntry, quantityN, priceN, weightN, material, colorEnum, status)) {
            return;
        }

        if (model.getBagService().getByCode(code)) {
            Utilities.showErrorAlert(code + " already exists");
            return;
        }

        int quantity = quantityN.intValue();
        int price = priceN.intValue();
        BigDecimal weight = BigDecimal.valueOf(weightN.doubleValue());


        if (currentbag == null) {

            Bag bag = new Bag();
            bag.setCode(code);
            bag.setModel(modelEnum.toString());
            bag.setStatus(status.toString());
            bag.setEntryDate(Date.valueOf(dateEntry));

            Stock stock = new Stock();
            stock.setQuantity(quantity);
            stock.setPrice(price);
            bag.setStock(stock);

            TechnicalSheet technicalSheet = new TechnicalSheet();
            technicalSheet.setMaterial(material.toString());
            technicalSheet.setColor(colorEnum.toString());
            technicalSheet.setWeight(weight);
            bag.setTechnicalSheet(technicalSheet);

            if (supplier != null) {
                Supplier managedSupplier = model.getSupplierService().findByNameAndContact(supplier.getName(), supplier.getContact());
                if (managedSupplier == null) {
                    System.err.println(managedSupplier);
                }
                bag.getSuppliers().add(managedSupplier);
                managedSupplier.getBags().add(bag);
            }


            if (location != null) {
                LocationBag locationBag = new LocationBag();
                locationBag.setBag(bag);
                locationBag.setLocationBags(location);
                bag.getLocationBags().add(locationBag);
            }

            Bag savedBag = model.getBagService().save(bag);

            if (savedBag != null) {
                Utilities.showInfoAlert("Bag saved successfully");
                clearForm();
                reloadInformation();
                ;
                navigation.goToDashboard();
            } else {
                Utilities.showErrorAlert("There was a Error saving the Bag");
            }

        } else {
            Utilities.showErrorAlert("You must click On first Dashboard");
        }

    }

    public void clearForm() {
        bagView.txtLastCode.setText(model.getBagService().getLastCode());
        bagView.txtCode.setText("");
        bagView.spQuantity.setValue(1);
        bagView.spPrice.setValue(1.0);
        bagView.spWeight.setValue(1.0);
        bagView.cbLocation.setSelectedIndex(-1);
        bagView.cbSupplier.setSelectedIndex(-1);
        bagView.cbColor.setSelectedIndex(-1);
        bagView.cbMaterial.setSelectedIndex(-1);
        bagView.cbModel.setSelectedIndex(-1);
        bagView.cbStatus.setSelectedIndex(-1);
        bagView.dpDateEntry.setDate(LocalDate.now());
        currentbag = null;
    }


    private boolean validateForm(Location location, Supplier supplier, String code, ModelEnum modelEnum,
                                 LocalDate dateEntry, Number quantity, Number price, Number weight,
                                 Material material, ColorEnum colorEnum, Status status) {

        if (location == null) {
            Utilities.showErrorAlert("Please Choose one Location");
            bagView.cbLocation.requestFocus();
            return false;
        }

        if (supplier == null) {
            Utilities.showErrorAlert("Please Choose one Supplier");
            bagView.cbSupplier.requestFocus();
            return false;
        }

        if (code.isEmpty()) {
            Utilities.showErrorAlert("Please type one code");
            bagView.txtCode.requestFocus();
            return false;
        }

        if (!code.matches("^PROD\\d+$")) {
            Utilities.showErrorAlert("Invalid product code. Format: PROD001 (001–999)");
            bagView.txtCode.requestFocus();
            return false;
        }

        if (modelEnum == null) {
            Utilities.showErrorAlert("Please Choose one Model");
            bagView.cbModel.requestFocus();
            return false;
        }

        if (dateEntry == null) {
            Utilities.showErrorAlert("Please Choose one Date");
            bagView.dpDateEntry.requestFocus();
            return false;
        }

        if (quantity == null) {
            Utilities.showErrorAlert("Please type one quantity");
            bagView.spQuantity.requestFocus();
            return false;
        }

        if (price == null) {
            Utilities.showErrorAlert("Please type one price");
            bagView.spPrice.requestFocus();
            return false;
        }

        if (weight == null) {
            Utilities.showErrorAlert("Please type one weight");
            bagView.spWeight.requestFocus();
            return false;
        }


        if (material == null) {
            Utilities.showErrorAlert("Please Choose one material");
            bagView.cbMaterial.requestFocus();
            return false;
        }

        if (colorEnum == null) {
            Utilities.showErrorAlert("Please Choose one color");
            bagView.cbColor.requestFocus();
            return false;
        }

        if (status == null) {
            Utilities.showErrorAlert("Please Choose one status");
            bagView.cbStatus.requestFocus();
            return false;
        }

        return true;
    }

    private boolean isCodeUnique(String code, List<Bag> bags, Bag currentbag) {
        for (Bag bag : bags) {
            if (currentbag != null && bag.getIdbag().equals(currentbag.getIdbag())) {
                continue;
            }

            if (bag.getCode().equalsIgnoreCase(code)) {
                return false;
            }
        }
        return true;
    }

    public void initComponent() {
        clearForm();
    }

    private void reloadInformation() {
        List<Location> locationList = model.getLocationService().getAll();
        bagView.cbLocation.removeAllItems();

        for (Location location : locationList) {
            bagView.cbLocation.addItem(location);
        }

        List<Supplier> supplierList = model.getSupplierService().getAllSupplier();
        bagView.cbSupplier.removeAllItems();
        for (Supplier supplier : supplierList) {
            bagView.cbSupplier.addItem(supplier);
        }
    }


}
