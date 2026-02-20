package gm.carlos.app.model.repository;

import gm.carlos.app.model.entity.TechnicalSheet;

import java.util.List;

/**
 * Interfaz DAO para la entidad {@link TechnicalSheet}.
 *
 * <p>Define las operaciones CRUD básicas que deben implementarse
 * para gestionar la persistencia de los objetos {@code TechnicalSheet}.</p>
 *
 * <p>Las implementaciones de esta interfaz serán responsables de interactuar
 * con la base de datos mediante Hibernate.</p>
 */
public interface ITechnicalSheetDAO {

    /**
     * Guarda una nueva {@code TechnicalSheet} en la base de datos.
     *
     * @param technicalSheet entidad a persistir.
     * @return {@code true} si se guardó correctamente, {@code false} en caso de error.
     */
    boolean save(TechnicalSheet technicalSheet);

    /**
     * Actualiza una {@code TechnicalSheet} existente en la base de datos.
     *
     * @param technicalSheet entidad modificada.
     * @return {@code true} si la actualización fue exitosa, {@code false} en caso de error.
     */
    boolean update(TechnicalSheet technicalSheet);

    /**
     * Elimina físicamente una {@code TechnicalSheet} de la base de datos.
     *
     * @param technicalSheet entidad a eliminar.
     * @return {@code true} si se eliminó correctamente, {@code false} en caso de error.
     */
    boolean delete(TechnicalSheet technicalSheet);

    /**
     * Obtiene todas las {@code TechnicalSheet} registradas en la base de datos.
     *
     * @return lista de {@code TechnicalSheet} o {@code null} si ocurre un error.
     */
    List<TechnicalSheet> getAll();

    /**
     * Busca una {@code TechnicalSheet} por su identificador.
     *
     * @param technicalSheetId ID de la {@code TechnicalSheet}.
     * @return entidad encontrada o {@code null} si no existe.
     */
    TechnicalSheet getById(int technicalSheetId);

}
