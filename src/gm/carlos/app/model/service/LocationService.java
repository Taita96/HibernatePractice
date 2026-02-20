package gm.carlos.app.model.service;

import gm.carlos.app.model.data.LocationDAO;
import gm.carlos.app.model.entity.Location;

import java.util.List;

/**
 * Servicio que actúa como capa intermedia entre la aplicación y la persistencia de {@link Location}.
 *
 * <p>Proporciona métodos para gestionar la creación, actualización, eliminación
 * y consulta de objetos {@code Location}, delegando las operaciones al {@link LocationDAO} correspondiente.</p>
 *
 * <p>Centraliza la lógica de negocio relacionada con las ubicaciones de almacenamiento.</p>
 */
public class LocationService {

    /** Instancia de {@link LocationDAO} para operaciones de persistencia. */
    private LocationDAO locationDAO = new LocationDAO();

    /**
     * Guarda una nueva {@code Location} en la base de datos.
     *
     * @param location entidad {@code Location} a guardar.
     */
    public void save(Location location){
        locationDAO.save(location);
    }

    /**
     * Elimina una {@code Location} de la base de datos.
     *
     * @param location entidad {@code Location} a eliminar.
     */
    public void delete(Location location){
        locationDAO.delete(location);
    }

    /**
     * Actualiza una {@code Location} existente.
     *
     * @param location entidad {@code Location} modificada.
     */
    public void update(Location location){
        locationDAO.update(location);
    }

    /**
     * Obtiene todas las {@code Location} registradas.
     *
     * @return lista de {@code Location} o {@code null} si ocurre un error.
     */
    public List<Location> getAll(){
        return locationDAO.getAll();
    }

    /**
     * Busca una {@code Location} por su identificador.
     *
     * @param supplierId ID de la {@code Location}.
     * @return entidad {@code Location} encontrada o {@code null} si no existe.
     */
    public Location getById(int supplierId ){
        return locationDAO.getById(supplierId);
    }

    /**
     * Busca una {@code Location} por pasillo (aisle) y estantería (shelf).
     *
     * @param aisle pasillo de la ubicación.
     * @param shelf estantería de la ubicación.
     * @return entidad {@code Location} encontrada o {@code null} si no existe.
     */
    public Location findByAisleAndShelf(String aisle, String shelf){
        return locationDAO.findByAisleAndShelf(aisle,shelf);
    }
}
