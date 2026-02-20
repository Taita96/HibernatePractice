package gm.carlos.app.util;

import gm.carlos.app.model.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


/**
 * Clase utilitaria encargada de gestionar el ciclo de vida de Hibernate.
 *
 * <p>Centraliza la configuración, inicialización y destrucción del
 * {@link SessionFactory}, garantizando que solo exista una única instancia
 * durante toda la ejecución de la aplicación (patrón Singleton).</p>
 *
 * <p>Responsabilidades principales:</p>
 * <ul>
 *     <li>Cargar la configuración desde el archivo <b>hibernate.cfg.xml</b>.</li>
 *     <li>Registrar todas las entidades anotadas del modelo.</li>
 *     <li>Crear y proporcionar acceso al {@link SessionFactory}.</li>
 *     <li>Cerrar correctamente los recursos de Hibernate al finalizar la aplicación.</li>
 * </ul>
 *
 * <p>Esta clase no puede ser instanciada.</p>
 *
 * @author Carlos
 * @since 1.0
 */
public class HibernateUtil {

    /** Instancia única de SessionFactory utilizada en toda la aplicación. */
    private static SessionFactory sessionFactory;

    /** Registro de servicios necesario para inicializar Hibernate. */

    private static StandardServiceRegistry registry;
    /**
     * Constructor privado para evitar la creación de instancias.
     */
    private HibernateUtil() {}


    /**
     * Inicializa Hibernate y crea el SessionFactory si aún no ha sido creado.
     *
     * <p>Este método realiza:</p>
     * <ol>
     *     <li>Carga del archivo de configuración <b>hibernate.cfg.xml</b>.</li>
     *     <li>Registro de las clases entidad anotadas.</li>
     *     <li>Construcción del {@link StandardServiceRegistry}.</li>
     *     <li>Creación del {@link SessionFactory}.</li>
     * </ol>
     *
     * <p>Si Hibernate ya está conectado, el método no realiza ninguna acción.</p>
     */
    public static void connect() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            return;
        }

        Configuration config = new Configuration().configure("hibernate.cfg.xml");

        config.addAnnotatedClass(Bag.class);
        config.addAnnotatedClass(Location.class);
        config.addAnnotatedClass(LocationBag.class);
        config.addAnnotatedClass(Stock.class);
        config.addAnnotatedClass(Supplier.class);
        config.addAnnotatedClass(TechnicalSheet.class);

        registry = new StandardServiceRegistryBuilder()
                .applySettings(config.getProperties())
                .build();

        sessionFactory = config.buildSessionFactory(registry);

        System.out.println("hibernate CONNECTED");
    }

    /**
     * Obtiene la instancia activa de SessionFactory.
     *
     * <p>Importante: debe haberse ejecutado previamente {@link #connect()}.</p>
     *
     * @return la instancia configurada de SessionFactory o {@code null} si no está inicializada.
     */
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            throw new IllegalStateException("Hibernate is NOT connected.");
        }
        return sessionFactory;
    }


    /**
     * Cierra los recursos de Hibernate y destruye el ServiceRegistry.
     *
     * <p>Debe llamarse al finalizar la aplicación para evitar fugas de memoria
     * o conexiones abiertas a la base de datos.</p>
     *
     * <p>Realiza de forma segura:</p>
     * <ul>
     *     <li>Cierre del SessionFactory.</li>
     *     <li>Destrucción del StandardServiceRegistry.</li>
     *     <li>Limpieza de referencias internas.</li>
     * </ul>
     */
    public static void disconnect() {

        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }

        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }

        sessionFactory = null;
        registry = null;

        System.out.println("Hibernate DISCONNECTED");
    }


    /**
     * Indica si Hibernate está actualmente inicializado y disponible.
     *
     * @return {@code true} si el SessionFactory existe y está abierto,
     *         {@code false} en caso contrario.
     */
    public static boolean isConnected() {
        return sessionFactory != null && !sessionFactory.isClosed();
    }
}
