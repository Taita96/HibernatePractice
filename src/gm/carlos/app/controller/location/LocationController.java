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

/**
 * Controlador encargado de la gestión de ubicaciones (Location) dentro del sistema.
 *
 * <p>Forma parte de la capa de presentación en el patrón MVC y actúa como intermediario
 * entre {@link LocationView} y los servicios proporcionados por {@link Model}.</p>
 *
 * <p>Responsabilidades principales:
 * <ul>
 *     <li>Registrar nuevas ubicaciones.</li>
 *     <li>Actualizar ubicaciones existentes.</li>
 *     <li>Eliminar ubicaciones.</li>
 *     <li>Sincronizar los datos mostrados en la tabla con la base de datos.</li>
 *     <li>Gestionar la navegación hacia otras pantallas.</li>
 * </ul>
 * </p>
 *
 * <p>No contiene lógica de negocio ni acceso directo a la base de datos;
 * todas las operaciones se delegan a {@code LocationService}.</p>
 *
 * @author Carlos
 * @version 1.0
 */
public class LocationController implements ActionListener, ListSelectionListener, TableModelListener {

    private LocationView locationView;
    private Model model;
    Location currentLocation;

    private ITransitionScreen navigation;

    /**
     * Inicializa el controlador de ubicaciones.
     * Registra los listeners necesarios y carga los datos iniciales en la tabla.
     *
     * @param locationView vista asociada a la gestión de ubicaciones
     * @param model modelo central con acceso a los servicios
     * @param navigation interfaz de navegación entre pantallas
     */
    public LocationController(LocationView locationView, Model model,ITransitionScreen navigation) {
        this.locationView = locationView;
        this.model = model;
        this.navigation = navigation;
        addActionListener(this);
        addListSelectionListener(this);
        addTableModelListener(this);
        reloadTable();
    }

    /**
     * Asocia los botones del formulario y de la tabla con el controlador.
     *
     * @param actionListener listener que gestionará las acciones del usuario
     */
    private void addActionListener(ActionListener actionListener) {
        locationView.btnFormClean.addActionListener(actionListener);
        locationView.btnFormSave.addActionListener(actionListener);
        locationView.btnFormUpdate.addActionListener(actionListener);
        locationView.btnTableDelete.addActionListener(actionListener);
        locationView.btnTableGoHome.addActionListener(actionListener);
    }

    /**
     * Configura el comportamiento de selección de la tabla.
     * Permite seleccionar una única ubicación para editarla o eliminarla.
     *
     * @param e listener de selección
     */
    private void addListSelectionListener(ListSelectionListener e) {
        locationView.tableLocation.setCellSelectionEnabled(true);
        ListSelectionModel tableAdminProducts = locationView.tableLocation.getSelectionModel();
        tableAdminProducts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableAdminProducts.addListSelectionListener(e);
    }

    /**
     * Registra un listener sobre el modelo de la tabla para detectar ediciones directas.
     *
     * @param e listener del modelo de tabla
     */
    private void addTableModelListener(TableModelListener e) {
        locationView.dtmTableLocation.addTableModelListener(e);
    }


    /**
     * Gestiona las acciones generadas por los botones de la interfaz.
     * Determina qué operación ejecutar según el comando recibido.
     *
     * @param e evento de acción del usuario
     */
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

    /**
     * Elimina la ubicación seleccionada de la base de datos.
     */
    private void delete() {
        int row = locationView.tableLocation.getSelectedRow();

        if (row < 0) {
            Utilities.showErrorAlert("Select a Supplier first");
            return;
        }

        if (!model.getLocationService().delete(currentLocation)) {
            Utilities.showWarningAlert(
                    "Cannot delete location. It is associated with one or more Bags."
            );
            return;
        }

//        model.getLocationService().delete(currentLocation);
        cleanUI();
    }

    /**
     * Actualiza la ubicación actualmente seleccionada con los datos del formulario.
     */
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

    /**
     * Guarda una nueva ubicación en el sistema.
     *
     * <p>Valida los campos del formulario antes de crear la entidad.</p>
     * <p>Solo permite guardar cuando no existe una ubicación seleccionada.</p>
     */
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

    /**
     * Valida que los campos obligatorios del formulario no estén vacíos.
     *
     * @param Aisle pasillo de la ubicación
     * @param Shelf estantería de la ubicación
     * @return true si hay errores de validación, false si es válido
     */
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

    /**
     * Limpia los campos del formulario.
     */
    private void cleanForm() {

        locationView.txtShelf.setText("");
        locationView.txtAisle.setText("");
    }

    /**
     * Restablece completamente la interfaz:
     * <ul>
     *     <li>Deselecciona la ubicación actual.</li>
     *     <li>Recarga la tabla.</li>
     *     <li>Limpia el formulario.</li>
     * </ul>
     */
    public void cleanUI(){
        currentLocation = null;
        reloadTable();
        cleanForm();
    }

    /**
     * Recarga todas las ubicaciones desde la base de datos y las muestra en la tabla.
     *
     * <p>Este método mantiene sincronizada la interfaz gráfica con el estado real
     * de los datos almacenados.</p>
     */
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

    /**
     * Se ejecuta cuando el usuario selecciona una fila de la tabla.
     * Carga los datos de la ubicación seleccionada en el formulario para su edición.
     *
     * @param e evento de cambio de selección
     */
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

    /**
     * Permite la edición directa en la tabla (inline editing).
     * Cuando una celda cambia, actualiza automáticamente la entidad en la base de datos.
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
