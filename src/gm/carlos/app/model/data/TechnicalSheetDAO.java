package gm.carlos.app.model.data;

import gm.carlos.app.model.entity.TechnicalSheet;
import gm.carlos.app.model.repository.ITechnicalSheetDAO;
import gm.carlos.app.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;


/**
 * Implementación DAO para la entidad {@link TechnicalSheet}.
 *
 * <p>Gestiona la persistencia de las fichas técnicas, permitiendo realizar
 * operaciones CRUD sobre la información de las mismas.</p>
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
public class TechnicalSheetDAO implements ITechnicalSheetDAO {

    /**
     * Guarda una nueva ficha técnica en la base de datos.
     *
     * @param technicalSheet entidad a persistir.
     * @return {@code true} si se guardó correctamente, {@code false} en caso de error.
     */
    @Override
    public boolean save(TechnicalSheet technicalSheet) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(technicalSheet);
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
     * Actualiza una ficha técnica existente.
     *
     * @param technicalSheet entidad modificada.
     * @return {@code true} si la actualización fue exitosa, {@code false} en caso de error.
     */
    @Override
    public boolean update(TechnicalSheet technicalSheet) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(technicalSheet);
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
     * Elimina físicamente una ficha técnica de la base de datos.
     *
     * @param technicalSheet entidad a eliminar.
     * @return {@code true} si se eliminó correctamente, {@code false} en caso de error.
     */
    @Override
    public boolean delete(TechnicalSheet technicalSheet) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(technicalSheet);
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
     * Obtiene todas las fichas técnicas registradas en la base de datos.
     *
     * @return lista de fichas técnicas o {@code null} si ocurre un error.
     */
    @Override
    public List<TechnicalSheet> getAll() {
        Session session = null;
        List<TechnicalSheet> list = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("FROM TechnicalSheet");
            list = (List<TechnicalSheet>) query.getResultList();
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
     * Busca una ficha técnica por su identificador.
     *
     * @param technicalSheetId ID de la ficha técnica.
     * @return entidad encontrada o {@code null} si no existe.
     */
    @Override
    public TechnicalSheet getById(int technicalSheetId) {
        Session session = null;
        TechnicalSheet technicalSheet = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            technicalSheet = session.get(TechnicalSheet.class, technicalSheetId);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return technicalSheet;
    }
}
