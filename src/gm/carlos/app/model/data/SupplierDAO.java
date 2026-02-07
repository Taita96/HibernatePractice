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
            Query query = session.createQuery("FROM supplier");
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
}
