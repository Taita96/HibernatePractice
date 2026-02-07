package gm.carlos.app.model;


import gm.carlos.app.model.service.LocationService;
import gm.carlos.app.model.service.SupplierService;

public class Model {

    private SupplierService supplierService;
    private LocationService locationService;

    public Model(){
        supplierService = new SupplierService();
        locationService = new LocationService();
    }

    public SupplierService getSupplierService() {
        return supplierService;
    }

    public LocationService getLocationService() {
        return locationService;
    }

}
