package util;

import entity.Book;
import entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Properties props = new Properties();
            InputStream input = HibernateUtil.class
                    .getClassLoader()
                    .getResourceAsStream("hibernate.properties");

            if (input == null) {
                throw new RuntimeException("hibernate.properties not found");
            }

            props.load(input);

            return new Configuration()
                    .addProperties(props)
                    .addAnnotatedClass(Book.class)
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
