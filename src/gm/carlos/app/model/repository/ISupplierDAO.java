package gm.carlos.app.model.repository;

import gm.carlos.app.model.entity.Supplier;

import java.util.List;

/**
 * Interfaz DAO para la entidad {@link Supplier}.
 *
 * <p>Define las operaciones CRUD y de búsqueda que deben implementarse
 * para gestionar la persistencia de los objetos {@code Supplier}.</p>
 *
 * <p>Las implementaciones de esta interfaz serán responsables de interactuar
 * con la base de datos mediante Hibernate.</p>
 */
public interface ISupplierDAO {

    /**
     * Guarda un nuevo {@code Supplier} en la base de datos.
     *
     * @param supplier entidad a persistir.
     * @return {@code true} si se guardó correctamente, {@code false} en caso de error.
     */
    boolean save(Supplier supplier);

    /**
     * Actualiza un {@code Supplier} existente en la base de datos.
     *
     * @param supplier entidad modificada.
     * @return {@code true} si la actualización fue exitosa, {@code false} en caso de error.
     */
    boolean update(Supplier supplier);

    /**
     * Elimina físicamente un {@code Supplier} de la base de datos.
     *
     * @param supplier entidad a eliminar.
     * @return {@code true} si se eliminó correctamente, {@code false} en caso de error.
     */
    boolean delete(Supplier supplier);

    /**
     * Obtiene todos los {@code Supplier} registrados en la base de datos.
     *
     * @return lista de {@code Supplier} o {@code null} si ocurre un error.
     */
    List<Supplier> getAll();

    /**
     * Busca un {@code Supplier} por su identificador.
     *
     * @param supplierId ID del {@code Supplier}.
     * @return entidad encontrada o {@code null} si no existe.
     */
    Supplier getById(int supplierId);

    /**
     * Busca un {@code Supplier} por nombre y contacto.
     *
     * <p>Realiza una búsqueda parcial usando LIKE para ambos parámetros.</p>
     *
     * @param name nombre o parte del nombre del proveedor.
     * @param contact contacto o parte del contacto del proveedor.
     * @return proveedor encontrado o {@code null} si no existe.
     */
    Supplier findByNameAndContact(String name, String contact);


    boolean hasBags(int supplierId);
}
