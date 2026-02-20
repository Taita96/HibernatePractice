package gm.carlos.app.model.service;

import gm.carlos.app.model.data.SupplierDAO;
import gm.carlos.app.model.entity.Supplier;

import java.util.List;

/**
 * Servicio que actúa como capa intermedia entre la aplicación y la persistencia de {@link Supplier}.
 *
 * <p>Proporciona métodos para gestionar la creación, actualización, eliminación
 * y consulta de objetos {@code Supplier}, delegando las operaciones al {@link SupplierDAO} correspondiente.</p>
 *
 * <p>Centraliza la lógica de negocio relacionada con los proveedores.</p>
 */
public class SupplierService {

    /** Instancia de {@link SupplierDAO} para operaciones de persistencia. */
    private SupplierDAO supplierDAO = new SupplierDAO();

    /**
     * Guarda un nuevo {@code Supplier} en la base de datos.
     *
     * @param supplier entidad {@code Supplier} a guardar.
     */
    public void saveSupplier(Supplier supplier){
        supplierDAO.save(supplier);
    }

    /**
     * Elimina un {@code Supplier} de la base de datos.
     *
     * @param supplier entidad {@code Supplier} a eliminar.
     */
    public void deleteSupplier(Supplier supplier){
        supplierDAO.delete(supplier);
    }

    /**
     * Actualiza un {@code Supplier} existente.
     *
     * @param supplier entidad {@code Supplier} modificada.
     */
    public void updateSupplier(Supplier supplier){
        supplierDAO.update(supplier);
    }

    /**
     * Obtiene todos los {@code Supplier} registrados.
     *
     * @return lista de {@code Supplier} o {@code null} si ocurre un error.
     */
    public List<Supplier> getAllSupplier(){
        return supplierDAO.getAll();
    }

    /**
     * Busca un {@code Supplier} por su identificador.
     *
     * @param supplierId ID del {@code Supplier}.
     * @return entidad {@code Supplier} encontrada o {@code null} si no existe.
     */
    public Supplier getByIdSupplier(int supplierId ){
        return supplierDAO.getById(supplierId);
    }

    /**
     * Busca un {@code Supplier} por nombre y contacto.
     *
     * @param name nombre del proveedor.
     * @param contact contacto del proveedor.
     * @return entidad {@code Supplier} encontrada o {@code null} si no existe.
     */
    public  Supplier findByNameAndContact(String name, String contact){
        return supplierDAO.findByNameAndContact(name,contact);
    }
}
