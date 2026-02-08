package gm.carlos.app.model;


import gm.carlos.app.model.service.*;

public class Model {

    private SupplierService supplierService;
    private LocationService locationService;
    private BagService bagService;
    private StockService stockService;
    private TechnicalSheetService technicalSheetService;
    private LocationBagService locationBagService;

    public Model(){
        supplierService = new SupplierService();
        locationService = new LocationService();
        bagService = new BagService();
        stockService = new StockService();
        technicalSheetService = new TechnicalSheetService();
        locationBagService = new LocationBagService();
    }

    public SupplierService getSupplierService() {
        return supplierService;
    }

    public LocationService getLocationService() {
        return locationService;
    }

    public BagService getBagService() {
        return bagService;
    }

    public StockService getStockService() {
        return stockService;
    }

    public TechnicalSheetService getTechnicalSheetService() {
        return technicalSheetService;
    }

    public LocationBagService getLocationBagService() {
        return locationBagService;
    }

}
