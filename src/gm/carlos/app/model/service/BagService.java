package gm.carlos.app.model.service;

import gm.carlos.app.model.data.BagDAO;
import gm.carlos.app.model.entity.Bag;

import java.util.List;

/**
 * Servicio que actúa como capa intermedia entre la aplicación y la persistencia de {@link Bag}.
 *
 * <p>Proporciona métodos para gestionar la creación, actualización, eliminación
 * y consulta de objetos {@code Bag}, delegando la operación al {@link BagDAO} correspondiente.</p>
 *
 * <p>Se encarga de centralizar la lógica de negocio y ofrecer una interfaz
 * limpia para las operaciones sobre {@code Bag}.</p>
 */
public class BagService {

    /** Instancia de {@link BagDAO} para operaciones de persistencia. */
    private BagDAO bagDAO = new BagDAO();

    /**
     * Guarda una nueva {@code Bag} en la base de datos.
     *
     * @param bag entidad {@code Bag} a guardar.
     * @return la {@code Bag} guardada, o {@code null} si ocurrió un error.
     */
    public Bag save(Bag bag) {
        return bagDAO.save(bag);
    }

    /**
     * Actualiza una {@code Bag} existente.
     *
     * @param bag entidad {@code Bag} modificada.
     * @return {@code true} si la actualización fue exitosa, {@code false} en caso de error.
     */
    public boolean update(Bag bag) {
        return bagDAO.update(bag);
    }

    /**
     * Elimina físicamente una {@code Bag} de la base de datos.
     *
     * @param bag entidad {@code Bag} a eliminar.
     * @return {@code true} si se eliminó correctamente, {@code false} en caso de error.
     */
    public boolean delete(Bag bag) {
        return bagDAO.delete(bag);
    }

    /**
     * Obtiene todas las {@code Bag} registradas.
     *
     * @return lista de {@code Bag} o {@code null} si ocurre un error.
     */
    public List<Bag> getAll() {
        return bagDAO.getAll();
    }

    /**
     * Busca una {@code Bag} por su identificador.
     *
     * @param bagId ID de la {@code Bag}.
     * @return entidad {@code Bag} encontrada o {@code null} si no existe.
     */
    public Bag getById(int bagId) {
        return bagDAO.getById(bagId);
    }

    /**
     * Obtiene todas las {@code Bag} con sus detalles relacionados
     * como stock, proveedores y hojas técnicas.
     *
     * @return lista de {@code Bag} con detalles o {@code null} si ocurre un error.
     */
    public List<Bag> getAllWithDetails(){
        return bagDAO.getAllWithDetails();
    }

    /**
     * Realiza una eliminación lógica (soft delete) de una {@code Bag} por su ID,
     * cambiando su estado a INACTIVE.
     *
     * @param bagId ID de la {@code Bag} a eliminar lógicamente.
     * @return {@code true} si la operación fue exitosa, {@code false} en caso contrario.
     */

    public boolean softDeleteById(int bagId){
        return bagDAO.softDeleteById(bagId);
    }

    /**
     * Verifica si existe una {@code Bag} con un código específico.
     *
     * @param code código de la {@code Bag} a buscar.
     * @return {@code true} si existe, {@code false} si no se encontró.
     */
    public boolean getByCode(String code){
        return bagDAO.getByCode(code);
    }

    /**
     * Obtiene el último código de {@code Bag} registrado en la base de datos.
     *
     * @return último código {@code String} o {@code null} si ocurre un error.
     */
    public String getLastCode(){
        return bagDAO.getLastCode();
    }
}
