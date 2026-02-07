package gm.carlos.app.model.service;

import gm.carlos.app.model.data.SupplierDAO;
import gm.carlos.app.model.entity.Supplier;

import java.util.List;

public class SupplierService {

    private SupplierDAO supplierDAO = new SupplierDAO();

    public void saveSupplier(Supplier supplier){
        supplierDAO.save(supplier);
    }

    public void deleteSupplier(Supplier supplier){
        supplierDAO.delete(supplier);
    }
    public void updateSupplier(Supplier supplier){
        supplierDAO.update(supplier);
    }

    public List<Supplier> getAllSupplier(){
        return supplierDAO.getAll();
    }

    public Supplier getByIdSupplier(int supplierId ){
        return supplierDAO.getById(supplierId);
    }
}
