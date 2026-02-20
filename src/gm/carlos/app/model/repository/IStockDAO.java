package gm.carlos.app.model.repository;

import gm.carlos.app.model.entity.Stock;

import java.util.List;

/**
 * Interfaz DAO para la entidad {@link Stock}.
 *
 * <p>Define las operaciones CRUD básicas que deben implementarse
 * para gestionar la persistencia de los objetos {@code Stock}.</p>
 *
 * <p>Las implementaciones de esta interfaz deben utilizar
 * Hibernate u otra tecnología de persistencia para interactuar con la base de datos.</p>
 */
public interface IStockDAO {

    /**
     * Guarda un nuevo {@code Stock} en la base de datos.
     *
     * @param stock entidad a persistir.
     * @return {@code true} si se guardó correctamente, {@code false} en caso de error.
     */
    boolean save(Stock stock);

    /**
     * Actualiza un {@code Stock} existente en la base de datos.
     *
     * @param stock entidad modificada.
     * @return {@code true} si la actualización fue exitosa, {@code false} en caso de error.
     */
    boolean update(Stock stock);

    /**
     * Elimina físicamente un {@code Stock} de la base de datos.
     *
     * @param stock entidad a eliminar.
     * @return {@code true} si se eliminó correctamente, {@code false} en caso de error.
     */
    boolean delete(Stock stock);

    /**
     * Obtiene todos los {@code Stock} registrados en la base de datos.
     *
     * @return lista de {@code Stock} o {@code null} si ocurre un error.
     */
    List<Stock> getAll();

    /**
     * Busca un {@code Stock} por su identificador.
     *
     * @param stockId ID del {@code Stock}.
     * @return entidad encontrada o {@code null} si no existe.
     */
    Stock getById(int stockId);
}
