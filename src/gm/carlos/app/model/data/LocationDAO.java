package gm.carlos.app.model.data;

import gm.carlos.app.model.entity.Bag;
import gm.carlos.app.model.entity.Location;
import gm.carlos.app.model.entity.Supplier;
import gm.carlos.app.model.repository.ILocationDAO;
import gm.carlos.app.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

public class LocationDAO implements ILocationDAO {

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
