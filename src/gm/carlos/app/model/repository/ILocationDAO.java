package gm.carlos.app.model.repository;

import gm.carlos.app.model.entity.Location;

import java.util.List;

/**
 * Interfaz DAO para la entidad {@link Location}.
 *
 * <p>Define las operaciones CRUD y de búsqueda que deben implementarse
 * para gestionar la persistencia de los objetos {@code Location}.</p>
 *
 * <p>Las implementaciones de esta interfaz serán responsables de interactuar
 * con la base de datos mediante Hibernate.</p>
 */
public interface ILocationDAO {

    /**
     * Guarda una nueva ubicación en la base de datos.
     *
     * @param location entidad a persistir.
     * @return {@code true} si se guardó correctamente, {@code false} en caso de error.
     */
    boolean save(Location location);

    /**
     * Actualiza una ubicación existente en la base de datos.
     *
     * @param location entidad modificada.
     * @return {@code true} si la actualización fue exitosa, {@code false} en caso de error.
     */
    boolean update(Location location);

    /**
     * Elimina físicamente una ubicación de la base de datos.
     *
     * @param location entidad a eliminar.
     * @return {@code true} si se eliminó correctamente, {@code false} en caso de error.
     */
    boolean delete(Location location);

    /**
     * Obtiene todas las ubicaciones registradas en la base de datos.
     *
     * @return lista de {@code Location} o {@code null} si ocurre un error.
     */
    List<Location> getAll();

    /**
     * Busca una ubicación por su identificador.
     *
     * @param locationId ID de la ubicación.
     * @return entidad encontrada o {@code null} si no existe.
     */
    Location getById(int locationId);

    /**
     * Busca una ubicación por pasillo y estante.
     *
     * <p>Realiza una búsqueda parcial usando LIKE para ambos parámetros.</p>
     *
     * @param aisle pasillo o parte del pasillo.
     * @param shelf estante o parte del estante.
     * @return ubicación encontrada o {@code null} si no existe.
     */
    Location findByAisleAndShelf(String aisle, String shelf);
}
