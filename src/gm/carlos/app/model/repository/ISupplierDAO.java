package gm.carlos.app.model.repository;

import gm.carlos.app.model.entity.Supplier;

import java.util.List;

public interface ISupplierDAO {

    boolean save(Supplier supplier);
    boolean update(Supplier supplier);
    boolean delete(Supplier supplier);
    List<Supplier> getAll();
    Supplier getById(int supplierId);
}
