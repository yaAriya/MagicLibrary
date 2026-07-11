package dao;

import entity.User;
import exceptions.UserDaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import util.HibernateUtil;

import java.util.List;

@Repository
@Primary
public class MySQLBasedUserDao implements UserDao {
    private static final Logger LOGGER = LogManager.getLogger(MySQLBasedUserDao.class);

    @Override
    public List<User> readAllUsers() throws UserDaoException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<User> users = session.createQuery("from User", User.class).list();
            LOGGER.info("Users reading completed successfully, found {} books", users.size());
            return users;
        } catch (Exception e) {
            LOGGER.error("Users reading failed", e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public void add(User user) throws UserDaoException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
            LOGGER.info("User adding completed successfully");
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            LOGGER.error("User adding failed", e);
            throw new UserDaoException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public User read(long id) throws UserDaoException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(User.class, id);
        } catch (Exception e) {
            LOGGER.error("User reading failed", e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public void update(User user) throws UserDaoException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            User updateUser = session.get(User.class, user.getId());
            updateUser.setName(user.getName());
            updateUser.setEmail(user.getEmail());
            updateUser.setAge(user.getAge());
            updateUser.setBooks(user.getBooks());
            session.getTransaction().commit();
            LOGGER.info("Users updating completed successfully");
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            LOGGER.error("User updating failed", e);
            throw new UserDaoException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(long id) throws UserDaoException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            User readUser = session.get(User.class, id);
            session.remove(readUser);
            session.getTransaction().commit();
            LOGGER.info("Users deleting completed successfully");
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            LOGGER.error("User deleting failed", e);
            throw new UserDaoException(e);
        } finally {
            session.close();
        }
    }
}
