package dao;

import entity.Book;
import entity.User;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public class MySQLBasedBookDao implements BookDao {
    private final SessionFactory sessionFactory;

    public MySQLBasedBookDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Book> readAllBooks() {
        return sessionFactory.getCurrentSession()
                .createQuery("select distinct b from Book b left join fetch b.user", Book.class)
                .list();
    }

    @Override
    public void add(Book book) {
        sessionFactory.getCurrentSession().persist(book);
    }

    @Override
    public Book read(long id) {
        return sessionFactory.getCurrentSession()
                .createQuery("select distinct b from Book b left join fetch b.user where b.id = :id", Book.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public void update(Book book) {
        Book updateBook = sessionFactory.getCurrentSession().get(Book.class, book.getId());
        updateBook.setName(book.getName());
        updateBook.setAuthor(book.getAuthor());
        updateBook.setPages(book.getPages());
        updateBook.setUser(book.getUser());
    }

    @Override
    public void delete(long id) {
        Book readBook = sessionFactory.getCurrentSession().get(Book.class, id);
        sessionFactory.getCurrentSession().remove(readBook);
    }

    @Override
    public void rentBook(User user, Book book) {
        User mergedUser = sessionFactory.getCurrentSession().merge(user);
        Book mergedBook = sessionFactory.getCurrentSession().merge(book);
        mergedUser.addBook(mergedBook);
    }

    @Override
    public void returnBook(User user, Book book) {
        User mergedUser = sessionFactory.getCurrentSession().merge(user);
        Book mergedBook = sessionFactory.getCurrentSession().merge(book);
        mergedUser.removeBook(mergedBook);
    }
}

