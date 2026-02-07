package gm.carlos.app.model;


import gm.carlos.app.model.service.SupplierService;

public class Model {

    private SupplierService supplierService;

    public Model(){
        supplierService = new SupplierService();
    }

    public SupplierService getSupplierService() {
        return supplierService;
    }

}
