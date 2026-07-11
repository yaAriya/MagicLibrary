package entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "books")
public class Book implements Cloneable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String author;
    private int pages;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Transient
    private long userId;

    public Book() {
    }

    public Book(long id, String name, String author, int pages) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.pages = pages;
    }

    public Book(long id, String name, String author, int pages, User user) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.pages = pages;
        this.user = user;
    }

    public Book(String name, String author, int pages) {
        this.name = name;
        this.author = author;
        this.pages = pages;
    }

    public Book(String name, String author, int pages, User user) {
        this.name = name;
        this.author = author;
        this.pages = pages;
        this.user = user;
    }

    public Book(long id, String name, String author, int pages, long userId) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.pages = pages;
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, author, pages, id, user);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if ((obj == null) || !(obj instanceof Book)) {
            return false;
        }
        Book book = (Book) obj;
        return Objects.equals(name, book.name) && Objects.equals(author, book.author) && pages == book.pages && id == book.id && Objects.equals(user, book.user);
    }

    @Override
    public Book clone() throws CloneNotSupportedException {
        Book clonedBook = (Book) super.clone();
        if (getUser() != null) {
            User clonedUser = new User(user.getId(), user.getName(), user.getEmail(), user.getAge(), user.getBooks());
            clonedBook.setUser(clonedUser);
        }
        return clonedBook;
    }

    @Override
    public String toString() {
        return id + ", " + name + ", " + author + ", " + pages + ", " + "(" + user + ")";
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
