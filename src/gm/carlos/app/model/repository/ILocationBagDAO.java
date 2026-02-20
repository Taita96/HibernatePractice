package gm.carlos.app.model.repository;

import gm.carlos.app.model.entity.LocationBag;

import java.util.List;

/**
 * Interfaz DAO para la entidad {@link LocationBag}.
 *
 * <p>Define las operaciones CRUD básicas que deben implementarse
 * para gestionar la persistencia de los objetos {@code LocationBag}.</p>
 *
 * <p>Las implementaciones de esta interfaz serán responsables de interactuar
 * con la base de datos mediante Hibernate.</p>
 */
public interface ILocationBagDAO {

    /**
     * Guarda un nuevo {@code LocationBag} en la base de datos.
     *
     * @param locationBag entidad a persistir.
     * @return {@code true} si se guardó correctamente, {@code false} en caso de error.
     */
    boolean save(LocationBag locationBag);

    /**
     * Actualiza un {@code LocationBag} existente en la base de datos.
     *
     * @param locationBag entidad modificada.
     * @return {@code true} si la actualización fue exitosa, {@code false} en caso de error.
     */
    boolean update(LocationBag locationBag);

    /**
     * Elimina físicamente un {@code LocationBag} de la base de datos.
     *
     * @param locationBag entidad a eliminar.
     * @return {@code true} si se eliminó correctamente, {@code false} en caso de error.
     */
    boolean delete(LocationBag locationBag);

    /**
     * Obtiene todos los {@code LocationBag} registrados en la base de datos.
     *
     * @return lista de {@code LocationBag} o {@code null} si ocurre un error.
     */
    List<LocationBag> getAll();

    /**
     * Busca un {@code LocationBag} por su identificador.
     *
     * @param locationBagId ID del {@code LocationBag}.
     * @return entidad encontrada o {@code null} si no existe.
     */
    LocationBag getById(int locationBagId);
}
