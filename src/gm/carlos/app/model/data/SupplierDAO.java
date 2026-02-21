package gm.carlos.app.model.data;

import gm.carlos.app.model.entity.Supplier;
import gm.carlos.app.model.repository.ISupplierDAO;
import gm.carlos.app.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;


/**
 * Implementación DAO para la entidad {@link Supplier}.
 *
 * <p>Gestiona la persistencia de los proveedores, permitiendo realizar
 * operaciones CRUD sobre la información de los mismos.</p>
 *
 * <p>Esta clase utiliza Hibernate para:</p>
 * <ul>
 *     <li>Abrir y cerrar sesiones de base de datos.</li>
 *     <li>Controlar manualmente las transacciones.</li>
 *     <li>Realizar rollback en caso de error.</li>
 *     <li>Ejecutar consultas HQL.</li>
 * </ul>
 *
 * <p>Pertenece a la capa de acceso a datos (DAO Layer).</p>
 */
public class SupplierDAO implements ISupplierDAO {


    /**
     * Guarda un nuevo proveedor en la base de datos.
     *
     * @param supplier entidad a persistir.
     * @return {@code true} si se guardó correctamente, {@code false} en caso de error.
     */
    @Override
    public boolean save(Supplier supplier) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(supplier);
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
     * Actualiza un proveedor existente.
     *
     * @param supplier entidad modificada.
     * @return {@code true} si la actualización fue exitosa.
     */
    @Override
    public boolean update(Supplier supplier) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(supplier);
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
     * Elimina físicamente un proveedor de la base de datos.
     *
     * @param supplier entidad a eliminar.
     * @return {@code true} si se eliminó correctamente.
     */
    @Override
    public boolean delete(Supplier supplier) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(supplier);
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
     * Comprueba si un proveedor tiene al menos una bolsa asociada.
     *
     * @param supplierId ID del proveedor.
     * @return {@code true} si tiene bolsas asociadas, {@code false} si no.
     */
    @Override
    public boolean hasBags(int supplierId) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            String hql = "SELECT COUNT(b) " +
                    "FROM Bag b JOIN b.suppliers s " +
                    "WHERE s.idsupplier = :id";

            Long count = session.createQuery(hql, Long.class)
                    .setParameter("id", supplierId)
                    .uniqueResult();

            return count != null && count > 0;

        } catch (HibernateException e) {
            e.printStackTrace();
            return true; // por seguridad, bloquea borrado ante error
        } finally {
            if (session != null) session.close();
        }
    }

    /**
     * Obtiene todos los proveedores registrados en la base de datos.
     *
     * @return lista de proveedores o {@code null} si ocurre un error.
     */
    @Override
    public List<Supplier> getAll() {
        Session session = null;
        List<Supplier> list = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("FROM Supplier");
            list = (List<Supplier>) query.getResultList();
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
     * Busca un proveedor por su identificador y carga sus bolsas asociadas.
     *
     * @param id ID del proveedor.
     * @return entidad encontrada o {@code null} si no existe.
     */
    @Override
    public Supplier getById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Supplier supplier = null;
        try {

            String hql = "SELECT s " +
                    "FROM Supplier s " +
                    "JOIN FETCH s.bags " +
                    "WHERE s.idsupplier = :id";

            supplier = session.createQuery(hql, Supplier.class)
                    .setParameter("id", id)
                    .uniqueResult();
            return supplier;
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
     * Busca un proveedor por nombre y contacto.
     *
     * <p>Realiza una búsqueda parcial usando LIKE y fuerza la carga de las bolsas asociadas.</p>
     *
     * @param name nombre o parte del nombre del proveedor.
     * @param contact contacto o parte del contacto del proveedor.
     * @return proveedor encontrado o {@code null} si no existe.
     */
    @Override
    public Supplier findByNameAndContact(String name, String contact) {
        Session session = null;
        Supplier supplier = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            String hql = "SELECT DISTINCT s " +
                    "FROM Supplier s " +
                    "Where s.name like :name " +
                    "AND s.contact like :contact";

            supplier = session.createQuery(hql, Supplier.class)
                    .setParameter("name", "%" + name + "%")
                    .setParameter("contact", "%" + contact + "%")
                    .uniqueResult();

            supplier.getBags().size();
            return supplier;
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
