package gm.carlos.app.model.data;

import gm.carlos.app.model.entity.Supplier;
import gm.carlos.app.model.repository.ISupplierDAO;
import gm.carlos.app.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

public class SupplierDAO implements ISupplierDAO {

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
