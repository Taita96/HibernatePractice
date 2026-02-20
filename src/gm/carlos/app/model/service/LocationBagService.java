package gm.carlos.app.model.service;

import gm.carlos.app.model.data.LocationBagDAO;
import gm.carlos.app.model.entity.LocationBag;

import java.util.List;

/**
 * Servicio que actúa como capa intermedia entre la aplicación y la persistencia de {@link LocationBag}.
 *
 * <p>Proporciona métodos para gestionar la creación, actualización, eliminación
 * y consulta de objetos {@code LocationBag}, delegando las operaciones al {@link LocationBagDAO} correspondiente.</p>
 *
 * <p>Centraliza la lógica de negocio relacionada con las asociaciones de {@code Bag} y {@code Location}.</p>
 */
public class LocationBagService {

    /** Instancia de {@link LocationBagDAO} para operaciones de persistencia. */
    private LocationBagDAO locationBagDAO = new LocationBagDAO();

    /**
     * Guarda un nuevo {@code LocationBag} en la base de datos.
     *
     * @param locationBag entidad {@code LocationBag} a guardar.
     * @return {@code true} si se guardó correctamente, {@code false} en caso de error.
     */
    public boolean save(LocationBag locationBag) {
        return locationBagDAO.save(locationBag);
    }

    /**
     * Actualiza un {@code LocationBag} existente.
     *
     * @param locationBag entidad {@code LocationBag} modificada.
     * @return {@code true} si la actualización fue exitosa, {@code false} en caso de error.
     */
    public boolean update(LocationBag locationBag) {
        return locationBagDAO.update(locationBag);
    }

    /**
     * Elimina físicamente un {@code LocationBag} de la base de datos.
     *
     * @param locationBag entidad {@code LocationBag} a eliminar.
     * @return {@code true} si se eliminó correctamente, {@code false} en caso de error.
     */
    public boolean delete(LocationBag locationBag) {
        return locationBagDAO.delete(locationBag);
    }

    /**
     * Obtiene todos los {@code LocationBag} registrados.
     *
     * @return lista de {@code LocationBag} o {@code null} si ocurre un error.
     */
    public List<LocationBag> getAll() {
        return locationBagDAO.getAll();
    }

    /**
     * Busca un {@code LocationBag} por su identificador.
     *
     * @param locationBagid ID del {@code LocationBag}.
     * @return entidad {@code LocationBag} encontrada o {@code null} si no existe.
     */
    public LocationBag getById(int locationBagid) {
        return locationBagDAO.getById(locationBagid);
    }

}
