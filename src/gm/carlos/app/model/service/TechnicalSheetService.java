package gm.carlos.app.model.service;

import gm.carlos.app.model.data.TechnicalSheetDAO;
import gm.carlos.app.model.entity.TechnicalSheet;

import java.util.List;

/**
 * Servicio que actúa como capa intermedia entre la aplicación y la persistencia de {@link TechnicalSheet}.
 *
 * <p>Proporciona métodos para gestionar la creación, actualización, eliminación
 * y consulta de objetos {@code TechnicalSheet}, delegando las operaciones al {@link TechnicalSheetDAO} correspondiente.</p>
 *
 * <p>Centraliza la lógica de negocio relacionada con las fichas técnicas.</p>
 */
public class TechnicalSheetService {

    /** Instancia de {@link TechnicalSheetDAO} para operaciones de persistencia. */
    private TechnicalSheetDAO technicalSheetDAO = new TechnicalSheetDAO();

    /**
     * Guarda una nueva {@code TechnicalSheet} en la base de datos.
     *
     * @param technicalSheet entidad {@code TechnicalSheet} a guardar.
     * @return {@code true} si se guardó correctamente, {@code false} en caso contrario.
     */
    public boolean save(TechnicalSheet technicalSheet) {
        return technicalSheetDAO.save(technicalSheet);
    }

    /**
     * Actualiza una {@code TechnicalSheet} existente.
     *
     * @param technicalSheet entidad {@code TechnicalSheet} modificada.
     * @return {@code true} si se actualizó correctamente, {@code false} en caso contrario.
     */
    public boolean update(TechnicalSheet technicalSheet) {
        return technicalSheetDAO.update(technicalSheet);
    }

    /**
     * Elimina una {@code TechnicalSheet} de la base de datos.
     *
     * @param technicalSheet entidad {@code TechnicalSheet} a eliminar.
     * @return {@code true} si se eliminó correctamente, {@code false} en caso contrario.
     */
    public boolean delete(TechnicalSheet technicalSheet) {
        return technicalSheetDAO.delete(technicalSheet);
    }

    /**
     * Obtiene todas las {@code TechnicalSheet} registradas.
     *
     * @return lista de {@code TechnicalSheet} o {@code null} si ocurre un error.
     */
    public List<TechnicalSheet> getAll() {
        return technicalSheetDAO.getAll();
    }

    /**
     * Busca una {@code TechnicalSheet} por su identificador.
     *
     * @param technicalSheetId ID de la {@code TechnicalSheet}.
     * @return entidad {@code TechnicalSheet} encontrada o {@code null} si no existe.
     */
    public TechnicalSheet getById(int technicalSheetId) {
        return technicalSheetDAO.getById(technicalSheetId);
    }
}
