package gm.carlos.app.controller.bag;

import gm.carlos.app.model.Model;
import gm.carlos.app.model.entity.*;
import gm.carlos.app.model.entity.enums.ColorEnum;
import gm.carlos.app.model.entity.enums.Material;
import gm.carlos.app.model.entity.enums.ModelEnum;
import gm.carlos.app.model.entity.enums.Status;
import gm.carlos.app.util.Utilities;
import gm.carlos.app.view.bag.BagView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class BagController implements ActionListener{

    private BagView bagView;
    private Model model;
    private Bag currentbag;

    public BagController(BagView bagView, Model model) {
        this.bagView = bagView;
        this.model = model;
        addActionListener(this);
        reloadInformation();
        initComponent();
    }




    private void addActionListener(ActionListener actionListener) {
        bagView.btnSave.addActionListener(actionListener);
        bagView.btnUpdate.addActionListener(actionListener);
        bagView.btnClean.addActionListener(actionListener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String evt = e.getActionCommand();

        switch (evt){
            case "btnSave":{
                    save();
                break;
            }
            case "btnUpdate":{
                    update();
                break;
            }
            case "btnClean":{
                clearForm();
                break;
            }
            case "btnHome":{

                break;
            }
        }
    }

    private void update() {

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

        if(!validateForm(location,supplier,code,modelEnum,dateEntry,quantityN,priceN,weightN,material,colorEnum,status)){
            return;
        }

        int quantity = quantityN.intValue();
        int price = priceN.intValue();
        BigDecimal weight = BigDecimal.valueOf(weightN.doubleValue());

        if(currentbag != null){
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

            if(supplier != null){
                Supplier managedSupplier = model.getSupplierService().getByIdSupplier(supplier.getIdsupplier());

                bag.getSuppliers().add(managedSupplier);
                managedSupplier.getBags().add(bag);
            }

            if(location != null){
                LocationBag locationBag = new LocationBag();
                locationBag.setBag(bag);
                locationBag.setLocationBags(location);
                bag.getLocationBags().add(locationBag);
            }

            Bag savedBag = model.getBagService().save(bag);

            if(savedBag == null){
                Utilities.showInfoAlert("Bag saved successfully");
            }else{
                Utilities.showErrorAlert("There was a Error saving the Bag");
            }

            clearForm();
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

        if(!validateForm(location,supplier,code,modelEnum,dateEntry,quantityN,priceN,weightN,material,colorEnum,status)){
            return;
        }

        int quantity = quantityN.intValue();
        int price = priceN.intValue();
        BigDecimal weight = BigDecimal.valueOf(weightN.doubleValue());


        if(currentbag == null){

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

            if(supplier != null){
                Supplier managedSupplier = model.getSupplierService().getByIdSupplier(supplier.getIdsupplier());

                bag.getSuppliers().add(managedSupplier);
                managedSupplier.getBags().add(bag);
            }

            if(location != null){
                LocationBag locationBag = new LocationBag();
                locationBag.setBag(bag);
                locationBag.setLocationBags(location);
                bag.getLocationBags().add(locationBag);
            }

            Bag savedBag = model.getBagService().save(bag);

            if(savedBag == null){
                Utilities.showInfoAlert("Bag saved successfully");
            }else{
                Utilities.showErrorAlert("There was a Error saving the Bag");
            }

            clearForm();
        }else{
            Utilities.showErrorAlert("You must click On first Dasboard");
        }

    }

    public void clearForm() {
        bagView.txtCode.setText("");
        bagView.spQuantity.setValue(0);
        bagView.spPrice.setValue(0);
        bagView.spWeight.setValue(0.0);
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

        if(location == null){
            Utilities.showErrorAlert("Please Choose one Location");
            bagView.cbLocation.requestFocus();
            return false;
        }

        if(supplier == null){
            Utilities.showErrorAlert("Please Choose one Supplier");
            bagView.cbSupplier.requestFocus();
            return false;
        }

        if(code.isEmpty()){
            Utilities.showErrorAlert("Please type one code");
            bagView.txtCode.requestFocus();
            return false;
        }

        if(!code.matches("^PROD\\d+$")){
            Utilities.showErrorAlert("Invalid product code. Format: PROD001 (001–999)");
            bagView.txtCode.requestFocus();
            return false;
        }

        if(modelEnum == null){
            Utilities.showErrorAlert("Please Choose one Model");
            bagView.cbModel.requestFocus();
            return false;
        }

        if(dateEntry == null){
            Utilities.showErrorAlert("Please Choose one Date");
            bagView.dpDateEntry.requestFocus();
            return false;
        }

        if(quantity == null){
            Utilities.showErrorAlert("Please type one quantity");
            bagView.spQuantity.requestFocus();
            return false;
        }

        if(price == null){
            Utilities.showErrorAlert("Please type one price");
            bagView.spPrice.requestFocus();
            return false;
        }

        if(weight == null){
            Utilities.showErrorAlert("Please type one weight");
            bagView.spWeight.requestFocus();
            return false;
        }


        if(material == null){
            Utilities.showErrorAlert("Please Choose one material");
            bagView.cbMaterial.requestFocus();
            return false;
        }

        if(colorEnum == null){
            Utilities.showErrorAlert("Please Choose one color");
            bagView.cbColor.requestFocus();
            return false;
        }

        if(status == null){
            Utilities.showErrorAlert("Please Choose one status");
            bagView.cbStatus.requestFocus();
            return false;
        }

        return true;
    }

    public void initComponent(){
        clearForm();
    }

    private void reloadInformation() {
        List<Location> locationList = model.getLocationService().getAll();
        bagView.cbLocation.removeAllItems();

        for(Location location: locationList){
            bagView.cbLocation.addItem(location);
        }

        List<Supplier> supplierList = model.getSupplierService().getAllSupplier();
        bagView.cbSupplier.removeAllItems();
        for(Supplier supplier: supplierList){
            bagView.cbSupplier.addItem(supplier);
        }
    }

}
