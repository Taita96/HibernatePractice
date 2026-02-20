package gm.carlos.app.model.service;

import gm.carlos.app.model.data.StockDAO;
import gm.carlos.app.model.entity.Stock;

import java.util.List;

/**
 * Servicio que actúa como capa intermedia entre la aplicación y la persistencia de {@link Stock}.
 *
 * <p>Proporciona métodos para gestionar la creación, actualización, eliminación
 * y consulta de objetos {@code Stock}, delegando las operaciones al {@link StockDAO} correspondiente.</p>
 *
 * <p>Centraliza la lógica de negocio relacionada con los registros de inventario.</p>
 */
public class StockService {

    /** Instancia de {@link StockDAO} para operaciones de persistencia. */
    private StockDAO stockDAO = new StockDAO();

    /**
     * Guarda un nuevo {@code Stock} en la base de datos.
     *
     * @param stock entidad {@code Stock} a guardar.
     * @return {@code true} si se guarda correctamente, {@code false} en caso contrario.
     */
    public boolean save(Stock stock) {
        return stockDAO.save(stock);
    }

    /**
     * Actualiza un {@code Stock} existente.
     *
     * @param stock entidad {@code Stock} modificada.
     * @return {@code true} si se actualiza correctamente, {@code false} en caso contrario.
     */
    public boolean update(Stock stock) {
        return stockDAO.update(stock);
    }

    /**
     * Elimina un {@code Stock} de la base de datos.
     *
     * @param stock entidad {@code Stock} a eliminar.
     * @return {@code true} si se elimina correctamente, {@code false} en caso contrario.
     */
    public boolean delete(Stock stock) {
        return stockDAO.delete(stock);
    }

    /**
     * Obtiene todos los {@code Stock} registrados.
     *
     * @return lista de {@code Stock} o {@code null} si ocurre un error.
     */
    public List<Stock> getAll() {
        return stockDAO.getAll();
    }

    /**
     * Busca un {@code Stock} por su identificador.
     *
     * @param stockId ID del {@code Stock}.
     * @return entidad {@code Stock} encontrada o {@code null} si no existe.
     */

    public Stock getById(int stockId) {
        return stockDAO.getById(stockId);
    }

}
