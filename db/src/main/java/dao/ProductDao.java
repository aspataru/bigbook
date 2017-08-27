package dao;

import da.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Created by aspataru on 7/13/17.
 */
public class ProductDao {

    private final SessionFactory sessionFactory;
    // private Transaction currentTransaction;

    public ProductDao() {
        StandardServiceRegistry standardRegistry =
                new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metaData =
                new MetadataSources(standardRegistry).getMetadataBuilder().build();
        sessionFactory = metaData.getSessionFactoryBuilder().build();
    }

    public void persist(Product product) {
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();
        try {
            currentSession.save(product);
        } finally {
            transaction.commit();
            currentSession.close();
        }
    }

    public Product findByIsin(String isin) {
        Session currentSession = sessionFactory.openSession();
        Product product;
        try {
            product = currentSession.get(Product.class, isin);
        } finally {
            currentSession.close();
        }
        return product;
    }

//    public void update(Product entity) {
//        getSessionFactory().update(entity);
//    }

//    public void delete(Product entity) {
//        getSessionFactory().delete(entity);
//    }
//
//    @SuppressWarnings("unchecked")
//    public List<Product> findAll() {
//        List<Product> books = (List<Product>) getSessionFactory().createQuery("from Product").list();
//        return books;
//    }
//
//    public void deleteAll() {
//        List<Product> entityList = findAll();
//        for (Product entity : entityList) {
//            delete(entity);
//        }
//    }
}
