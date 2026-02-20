package gm.carlos.app.model.data;

import gm.carlos.app.model.entity.Location;
import gm.carlos.app.model.repository.ILocationDAO;
import gm.carlos.app.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

/**
 * Implementación DAO para la entidad {@link Location}.
 *
 * <p>Gestiona todas las operaciones de persistencia relacionadas con las ubicaciones
 * físicas del almacén (pasillo, estantería, etc.) utilizando Hibernate.</p>
 *
 * <p>Esta clase se encarga de:</p>
 * <ul>
 *     <li>Administrar sesiones de base de datos.</li>
 *     <li>Controlar transacciones manualmente.</li>
 *     <li>Aplicar rollback en caso de error.</li>
 *     <li>Ejecutar consultas HQL.</li>
 * </ul>
 *
 * <p>Forma parte de la capa de acceso a datos siguiendo el patrón DAO.</p>
 */
public class LocationDAO implements ILocationDAO {


    /**
     * Guarda una nueva ubicación en la base de datos.
     *
     * @param location entidad a persistir.
     * @return {@code true} si se guardó correctamente, {@code false} en caso de error.
     */
    @Override
    public boolean save(Location location) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(location);
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
     * Actualiza una ubicación existente.
     *
     * @param location entidad modificada.
     * @return {@code true} si la actualización fue exitosa.
     */
    @Override
    public boolean update(Location location) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(location);
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
     * Elimina físicamente una ubicación de la base de datos.
     *
     * @param location entidad a eliminar.
     * @return {@code true} si se eliminó correctamente.
     */
    @Override
    public boolean delete(Location location) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(location);
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
     * Obtiene todas las ubicaciones registradas.
     *
     * @return lista de ubicaciones o {@code null} si ocurre un error.
     */
    @Override
    public List<Location> getAll() {
        Session session = null;
        List<Location> list = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("FROM Location");
            list = (List<Location>) query.getResultList();
            return list;
        } catch (HibernateException e) {
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();

        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    /**
     * Busca una ubicación por su identificador.
     *
     * @param locationId ID de la ubicación.
     * @return la ubicación encontrada o {@code null} si no existe.
     */
    @Override
    public Location getById(int locationId) {
        Session session = null;
        Location location = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            location = session.get(Location.class, locationId);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return location;
    }

    /**
     * Busca una ubicación por pasillo y estantería.
     *
     * <p>Permite localizar posiciones físicas dentro del almacén usando coincidencias parciales.</p>
     *
     * @param aisle  pasillo a buscar.
     * @param shelf  estantería a buscar.
     * @return ubicación encontrada o {@code null} si no existe coincidencia.
     */
    @Override
    public Location findByAisleAndShelf(String aisle, String shelf) {
        Session session = null;
        Location location = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            String hql = "SELECT DISTINCT l " +
                    "FROM Location l " +
                    "Where l.aisle like :aisle " +
                    "AND l.shelf like :shelf";

            location = session.createQuery(hql, Location.class)
                    .setParameter("aisle", "%" + aisle + "%")
                    .setParameter("shelf", "%" + shelf + "%")
                    .uniqueResult();

            return location;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }
}
