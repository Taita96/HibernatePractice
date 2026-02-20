package gm.carlos.app.model.repository;

import gm.carlos.app.model.entity.Bag;

import java.util.List;

/**
 * Interfaz DAO para la gestión de la entidad {@link Bag}.
 *
 * <p>Define las operaciones de acceso a datos relacionadas con las bolsas
 * (Bag), desacoplando la lógica de persistencia del resto de la aplicación.</p>
 *
 * <p>Las implementaciones de esta interfaz serán responsables de interactuar
 * con la base de datos mediante Hibernate.</p>
 */
public interface IBagDAO {

    /**
     * Guarda una nueva bolsa en la base de datos.
     *
     * @param bag objeto Bag a persistir.
     * @return la bolsa guardada con sus datos actualizados (por ejemplo, ID generado).
     */
    Bag save(Bag bag);

    /**
     * Guarda una nueva bolsa en la base de datos.
     *
     * @param bag objeto Bag a persistir.
     * @return la bolsa guardada con sus datos actualizados (por ejemplo, ID generado).
     */
    boolean update(Bag bag);


    /**
     * Elimina físicamente una bolsa de la base de datos.
     *
     * @param bag bolsa a eliminar.
     * @return {@code true} si se eliminó correctamente, {@code false} si ocurrió algún error.
     */
    boolean delete(Bag bag);

    /**
     * Obtiene todas las bolsas registradas.
     *
     * @return lista con todas las bolsas.
     */
    List<Bag> getAll();

    /**
     * Busca una bolsa por su identificador.
     *
     * @param bagId identificador único de la bolsa.
     * @return la bolsa encontrada o {@code null} si no existe.
     */
    Bag getById(int bagId);


    List<Bag> getAllWithDetails();


    boolean softDeleteById(int bagId);


    boolean getByCode(String code);


    String getLastCode();

}
