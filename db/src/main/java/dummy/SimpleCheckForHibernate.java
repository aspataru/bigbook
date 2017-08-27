package dummy;

import da.FundDocument;
import da.Product;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by aspataru on 7/9/17.
 */
@Slf4j
public class SimpleCheckForHibernate {

    private static SessionFactory sessionFactory;

    public static void initSessionFactory() throws Exception {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);
            throw e;
        }
    }

    public void shouldInsertProducts() throws Exception {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(new FundDocument("DE0000000000", "My Fund"));
        session.save(new Product("DE0000000000", "DEFXXXXX", "My Fund", BigDecimal.valueOf(102.3)));
        session.getTransaction().commit();
        session.close();

        session = sessionFactory.openSession();
        List<FundDocument> result = session.createQuery("from FundDocument").list();
        for (FundDocument fundDocument : result) {
            log.info("Found fund document: {}", fundDocument);
        }
        session.close();
    }

}
