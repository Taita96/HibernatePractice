package gm.carlos.app.model.data;

import gm.carlos.app.model.entity.*;
import gm.carlos.app.model.entity.enums.Status;
import gm.carlos.app.model.repository.IBagDAO;
import gm.carlos.app.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

/**
 * Implementación de {@link IBagDAO} utilizando Hibernate para el acceso a datos.
 *
 * <p>Esta clase se encarga de realizar todas las operaciones de persistencia
 * relacionadas con la entidad {@link Bag}, gestionando manualmente:</p>
 * <ul>
 *     <li>Apertura y cierre de sesiones.</li>
 *     <li>Control de transacciones.</li>
 *     <li>Rollback en caso de error.</li>
 *     <li>Ejecución de consultas HQL.</li>
 * </ul>
 *
 * <p>Sigue el patrón DAO, separando la lógica de base de datos de la lógica de negocio.</p>
 */
public class BagDAO implements IBagDAO {


    /**
     * Guarda una nueva bolsa en la base de datos.
     *
     * @param bag entidad a persistir.
     * @return la entidad guardada o {@code null} si ocurre un error.
     */
    @Override
    public Bag save(Bag bag) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(bag);
            session.getTransaction().commit();
            return bag;
        } catch (HibernateException e) {
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * Actualiza una bolsa existente.
     *
     * @param bag entidad modificada.
     * @return {@code true} si la actualización fue correcta.
     */
    @Override
    public boolean update(Bag bag) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(bag);
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
     * Elimina físicamente una bolsa de la base de datos.
     *
     * @param bag entidad a eliminar.
     * @return {@code true} si se eliminó correctamente.
     */
    @Override
    public boolean delete(Bag bag) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(bag);
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
     * Obtiene todas las bolsas registradas.
     *
     * @return lista de bolsas o {@code null} si ocurre un error.
     */
    @Override
    public List<Bag> getAll() {
        Session session = null;
        List<Bag> list = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("FROM Bag");
            list = (List<Bag>) query.getResultList();
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
     * Busca una bolsa por su ID.
     *
     * <p>Fuerza la inicialización de relaciones para evitar problemas de Lazy Loading.</p>
     *
     * @param bagId identificador de la bolsa.
     * @return entidad encontrada o {@code null}.
     */
    @Override
    public Bag getById(int bagId) {
        Session session = null;
        Bag bag = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            bag = session.get(Bag.class, bagId);
            bag.getSuppliers().size();
            bag.getLocationBags().size();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return bag;
    }

    /**
     * Verifica si existe una bolsa cuyo código coincida (búsqueda parcial).
     *
     * @param code código a buscar.
     * @return {@code true} si existe coincidencia.
     */
    @Override
    public boolean getByCode(String code) {
        Session session = null;
        Bag bag = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            String hql = "SELECT DISTINCT b " +
                    "FROM Bag b " +
                    "Where b.code like :newCode ";

            bag = session.createQuery(hql, Bag.class)
            .setParameter("newCode","%" +code+"%")
                    .uniqueResult();;

            if(bag != null){
                return true;
            }

        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return false;
    }

    /**
     * Realiza un borrado lógico cambiando el estado a INACTIVE.
     *
     * @param bagId identificador de la bolsa.
     * @return {@code true} si se actualizó al menos un registro.
     */
    @Override
    public boolean softDeleteById(int bagId) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            String hql = "UPDATE Bag b " +
                    "SET b.status = :newStatus " +
                    "WHERE b.idbag = :bagId";

            Query query = session.createQuery(hql);
            query.setParameter("newStatus", Status.INACTIVE.toString());
            query.setParameter("bagId", bagId);

            int rows = query.executeUpdate();

            session.getTransaction().commit();

            return rows > 0;

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
     * Obtiene todas las bolsas activas junto con sus relaciones principales.
     *
     * <p>Usa JOIN FETCH para evitar múltiples consultas (problema N+1).</p>
     *
     * @return lista de bolsas con detalles completos.
     */
    @Override
    public List<Bag> getAllWithDetails() {
        Session session = null;
        List<Bag> list = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();


            String hql = "SELECT DISTINCT b " +
                    "FROM Bag b " +
                    "JOIN FETCH b.locationBags " +
                    "JOIN FETCH b.stock " +
                    "JOIN FETCH b.technicalSheet " +
                    "WHERE b.status = 'ACTIVE'";

            Query query = session.createQuery(hql, Bag.class);

            list = query.getResultList();

            for (Bag b : list) {
                b.getSuppliers().size();
            }

            return list;

        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * Obtiene el último código registrado (ordenado por ID descendente).
     *
     * <p>Se usa normalmente para generar el siguiente código incremental.</p>
     *
     * @return último código o {@code null} si no existen registros.
     */
    @Override
    public String getLastCode() {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            String hql = "SELECT b.code " +
                    "FROM Bag b " +
                    "ORDER BY b.idbag DESC";

            return session.createQuery(hql, String.class)
                    .setMaxResults(1)
                    .uniqueResult();

        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }


}
