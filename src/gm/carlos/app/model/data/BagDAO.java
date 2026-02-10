package gm.carlos.app.model.data;

import gm.carlos.app.model.entity.*;
import gm.carlos.app.model.entity.enums.Status;
import gm.carlos.app.model.repository.IBagDAO;
import gm.carlos.app.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

public class BagDAO implements IBagDAO {

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
