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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * Controlador encargado de la gestión de Bolsos (Bag) dentro de la aplicación.
 *
 * <p>Esta clase forma parte de la capa de presentación siguiendo el patrón MVC.
 * Actúa como intermediaria entre la vista {@link BagView} y la lógica de negocio
 * proporcionada por {@link Model}.</p>
 *
 * <p>Sus responsabilidades principales son:
 * <ul>
 *     <li>Escuchar los eventos generados por la interfaz gráfica.</li>
 *     <li>Validar los datos introducidos por el usuario.</li>
 *     <li>Crear, actualizar o eliminar entidades {@link Bag}.</li>
 *     <li>Sincronizar información relacionada como {@link Stock}, {@link TechnicalSheet},
 *     {@link Supplier} y {@link Location}.</li>
 *     <li>Gestionar la navegación entre pantallas mediante {@link ITransitionScreen}.</li>
 * </ul>
 * </p>
 *
 * <p>Este controlador NO contiene lógica de persistencia directa; delega todas
 * las operaciones al modelo y a la capa de servicios.</p>
 *
 * @author Carlos
 * @version 1.0
 */
public class BagController implements ActionListener {

    private BagView bagView;
    private Model model;
    private Bag currentbag;

    private ITransitionScreen navigation;

    /**
     * Constructor del controlador de bolsos.
     *
     * @param bagView    vista asociada a la gestión de bolsos
     * @param model      modelo central que proporciona acceso a los servicios
     * @param navigation interfaz utilizada para realizar cambios de pantalla
     */
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


    /**
     * Gestiona los eventos lanzados por los botones de la vista.
     * Dependiendo de la acción ejecuta guardar, actualizar, limpiar o navegar.
     *
     * @param e evento de acción generado por el usuario
     */
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

                navigation.confirmNavigation(currentbag, n -> {
                    navigation.goToDashboard();
                });

                break;
            }
        }
    }

    public Bag getCurrentbag() {
        return currentbag;
    }


    /**
     * Carga un bolso existente en el formulario para su edición.
     * Rellena todos los campos de la interfaz con los datos actuales.
     *
     * @param bag bolso seleccionado para modificar
     */
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


    /**
     * Actualiza el bolso actualmente seleccionado con la información introducida
     * en el formulario.
     *
     * <p>También actualiza sus relaciones:
     * <ul>
     *     <li>{@link Stock}</li>
     *     <li>{@link TechnicalSheet}</li>
     *     <li>{@link Supplier}</li>
     *     <li>{@link Location}</li>
     * </ul>
     * </p>
     *
     * <p>Regla de negocio: si la cantidad de stock es 0, el bolso se elimina automáticamente.</p>
     */
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

    /**
     * Recoge los datos del formulario, los valida y crea un nuevo registro de {@link Bag}.
     *
     * <p>Si los datos no son válidos se cancela la operación.
     * Si el guardado es correcto, se limpia el formulario y se vuelve al Dashboard.</p>
     */
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
                navigation.goToDashboard();
            } else {
                Utilities.showErrorAlert("There was a Error saving the Bag");
            }

        } else {
            Utilities.showErrorAlert("You must click On first Dashboard");
        }

    }

    /**
     * Limpia todos los campos del formulario y reinicia el estado del controlador.
     * Se utiliza después de guardar, actualizar o cuando el usuario desea comenzar
     * un nuevo registro.
     */
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


    /**
     * Valida que todos los campos del formulario tengan valores correctos.
     *
     * @return true si la validación es correcta, false si existe algún error
     */
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

    /**
     * Comprueba que el código del bolso no esté repetido en la base de datos.
     *
     * @param code       código introducido por el usuario
     * @param bags       lista de bolsos existentes
     * @param currentbag bolso en edición (se ignora en la comprobación)
     * @return true si el código es único, false si ya existe
     */
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

    /**
     * Inicializa el estado del formulario al cargar la pantalla.
     */
    public void initComponent() {
        clearForm();
    }

    /**
     * Recarga la información de Localizaciones y Proveedores desde la base de datos
     * y actualiza los ComboBox de la vista.
     *
     * <p>Garantiza que el usuario siempre trabaje con datos actualizados.</p>
     */
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
