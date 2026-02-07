package gm.carlos.app.util;

import gm.carlos.app.model.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    private HibernateUtil() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration config = new Configuration().configure("hibernate.cfg.xml");
            config.addAnnotatedClass(Bag.class);
            config.addAnnotatedClass(Location.class);
            config.addAnnotatedClass(LocationBag.class);
            config.addAnnotatedClass(Stock.class);
            config.addAnnotatedClass(Supplier.class);
            config.addAnnotatedClass(TechnicalSheet.class);

            StandardServiceRegistry registry =
                    new StandardServiceRegistryBuilder()
                            .applySettings(config.getProperties())
                            .build();

            sessionFactory = config.buildSessionFactory(registry);
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null){
            sessionFactory.close();
        }

    }
}
