package dao;

import entity.Book;
import entity.User;
import exceptions.BookDaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import util.HibernateUtil;
import java.util.List;

@Repository
@Primary
public class MySQLBasedBookDao implements BookDao {
    private static final Logger LOGGER = LogManager.getLogger(MySQLBasedBookDao.class);

    @Override
    public List<Book> readAllBooks() throws BookDaoException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Book> books = session.createQuery("from Book", Book.class).list();
            LOGGER.info("Books reading completed successfully, found {} books", books.size());
            return books;
        } catch (Exception e) {
            LOGGER.error("Books reading failed", e);
            throw new BookDaoException(e);
        }
    }

    @Override
    public void add(Book book) throws BookDaoException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.persist(book);
            session.getTransaction().commit();
            LOGGER.info("Book adding completed successfully");
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            LOGGER.error("Book adding failed", e);
            throw new BookDaoException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public Book read(long id) throws BookDaoException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Book book = session.get(Book.class, id);
            LOGGER.info("Book reading completed successfully");
            return book;
        } catch (Exception e) {
            LOGGER.error("Book reading failed", e);
            throw new BookDaoException(e);
        }
    }

    @Override
    public void update(Book book) throws BookDaoException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Book updateBook = session.get(Book.class, book.getId());
            updateBook.setName(book.getName());
            updateBook.setAuthor(book.getAuthor());
            updateBook.setPages(book.getPages());
            updateBook.setUser(book.getUser());
            session.getTransaction().commit();
            LOGGER.info("Books updating completed successfully");
        } catch (Exception e) {
            if(session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            LOGGER.error("Book updating failed", e);
            throw new BookDaoException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(long id) throws BookDaoException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Book readBook = session.get(Book.class, id);
            session.remove(readBook);
            session.getTransaction().commit();
            LOGGER.info("Books deleting completed successfully");
        } catch (Exception e) {
            if(session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            LOGGER.error("Book deleting failed", e);
            throw new BookDaoException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public void rentBook(User user, Book book) throws BookDaoException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            User mergedUser = session.merge(user);
            Book mergedBook = session.merge(book);
            mergedUser.addBook(mergedBook);
            session.getTransaction().commit();
            LOGGER.info("Book renting compile successful");
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            LOGGER.error("Book renting failed", e);
            throw new BookDaoException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public void returnBook(User user, Book book) throws BookDaoException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            User mergedUser = session.merge(user);
            Book mergedBook = session.merge(book);
            mergedUser.removeBook(mergedBook);
            session.getTransaction().commit();
            LOGGER.info("Book returning compile successful");
        } catch (Exception e) {
            if(session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            LOGGER.error("Book returning failed", e);
            throw new BookDaoException(e);
        } finally {
            session.close();
        }
    }
}
