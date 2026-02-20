package gm.carlos.app.model.data;

import gm.carlos.app.model.entity.LocationBag;
import gm.carlos.app.model.repository.ILocationBagDAO;
import gm.carlos.app.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

/**
 * Implementación DAO para la entidad {@link LocationBag}.
 *
 * <p>Se encarga de gestionar la persistencia de la relación entre ubicaciones
 * y bolsas, realizando operaciones CRUD mediante Hibernate.</p>
 *
 * <p>Esta clase controla manualmente:</p>
 * <ul>
 *     <li>Apertura y cierre de sesiones.</li>
 *     <li>Manejo de transacciones.</li>
 *     <li>Rollback automático en caso de error.</li>
 * </ul>
 *
 * <p>Forma parte de la capa de acceso a datos (Data Layer).</p>
 */
public class LocationBagDAO implements ILocationBagDAO {

    /**
     * Guarda una nueva relación Location-Bag en la base de datos.
     *
     * @param locationBag entidad a persistir.
     * @return {@code true} si se guardó correctamente, {@code false} si ocurrió un error.
     */
    @Override
    public boolean save(LocationBag locationBag) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(locationBag);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * Actualiza una relación existente entre Location y Bag.
     *
     * @param locationBag entidad modificada.
     * @return {@code true} si la actualización fue exitosa.
     */
    @Override
    public boolean update(LocationBag locationBag) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(locationBag);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * Elimina físicamente una relación Location-Bag.
     *
     * @param locationBag entidad a eliminar.
     * @return {@code true} si se eliminó correctamente.
     */
    @Override
    public boolean delete(LocationBag locationBag) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(locationBag);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * Obtiene todas las relaciones Location-Bag registradas.
     *
     * @return lista de relaciones o {@code null} si ocurre un error.
     */
    @Override
    public List<LocationBag> getAll() {
        Session session = null;
        List<LocationBag> list = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("FROM LocationBag");
            list = (List<LocationBag>) query.getResultList();
            return list;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    /**
     * Busca una relación específica por su identificador.
     *
     * @param locationBagId ID de la relación.
     * @return entidad encontrada o {@code null} si no existe.
     */
    @Override
    public LocationBag getById(int locationBagId) {
        Session session = null;
        LocationBag locationBag = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            locationBag = session.get(LocationBag.class, locationBagId);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return locationBag;
    }
}
