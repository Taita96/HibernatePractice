package gm.carlos.app.model.data;

import gm.carlos.app.model.entity.Stock;
import gm.carlos.app.model.repository.IStockDAO;
import gm.carlos.app.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

/**
 * Implementación DAO para la entidad {@link Stock}.
 *
 * <p>Gestiona la persistencia del inventario asociado a las bolsas,
 * permitiendo realizar operaciones CRUD sobre la información de stock.</p>
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
public class StockDAO implements IStockDAO {


    /**
     * Guarda un nuevo registro de stock en la base de datos.
     *
     * @param stock entidad a persistir.
     * @return {@code true} si se guardó correctamente, {@code false} en caso de error.
     */
    @Override
    public boolean save(Stock stock) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(stock);
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
     * Actualiza un registro de stock existente.
     *
     * @param stock entidad modificada.
     * @return {@code true} si la actualización fue exitosa.
     */
    @Override
    public boolean update(Stock stock) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(stock);
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
     * Elimina físicamente un registro de stock.
     *
     * @param stock entidad a eliminar.
     * @return {@code true} si se eliminó correctamente.
     */
    @Override
    public boolean delete(Stock stock) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(stock);
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
     * Obtiene todos los registros de stock almacenados.
     *
     * @return lista de stock o {@code null} si ocurre un error.
     */
    @Override
    public List<Stock> getAll() {
        Session session = null;
        List<Stock> list = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("FROM Stock");
            list = (List<Stock>) query.getResultList();
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
     * Busca un registro de stock por su identificador.
     *
     * @param stockId ID del stock.
     * @return entidad encontrada o {@code null} si no existe.
     */
    @Override
    public Stock getById(int stockId) {
        Session session = null;
        Stock stock = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            stock = session.get(Stock.class, stockId);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return stock;
    }
}
