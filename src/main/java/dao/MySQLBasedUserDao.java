package dao;

import entity.User;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public class MySQLBasedUserDao implements UserDao {
    SessionFactory sessionFactory;

    public MySQLBasedUserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<User> readAllUsers() {
        return sessionFactory.getCurrentSession()
                .createQuery("select distinct u from User u left join fetch u.books", User.class)
                .list();
    }

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().persist(user);
    }

    @Override
    public User read(long id) {
        return sessionFactory.getCurrentSession()
                .createQuery("select u from User u left join fetch u.books where u.id = :id", User.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public void update(User user) {
        User updateUser = sessionFactory.getCurrentSession().get(User.class, user.getId());
        updateUser.setName(user.getName());
        updateUser.setEmail(user.getEmail());
        updateUser.setAge(user.getAge());
        updateUser.setBooks(user.getBooks());
    }

    @Override
    public void delete(long id) {
        User readUser = sessionFactory.getCurrentSession().get(User.class, id);
        sessionFactory.getCurrentSession().remove(readUser);
    }
}
