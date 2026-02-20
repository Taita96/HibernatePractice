import gm.carlos.app.controller.IndexController;
import gm.carlos.app.model.Model;
import gm.carlos.app.util.HibernateUtil;
import gm.carlos.app.view.View;
import org.hibernate.HibernateException;
import org.hibernate.Metamodel;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.metamodel.EntityType;
import javax.swing.*;

public class Main {

    public static void main(final String[] args) throws Exception {
        try {
            SwingUtilities.invokeLater(() -> {
                Model model = new Model();
                View view = new View();

                new IndexController(view,model);
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.disconnect();
        }
    }
}