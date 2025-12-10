package entity;

import java.util.Objects;

public class Book implements Cloneable {
    private long id;
    private String name;
    private String author;
    private int pagesNumber;
    private User user;

    public Book() {
    }

    public Book(long id, String name, String author, int pagesNumber) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.pagesNumber = pagesNumber;
    }

    public Book(long id, String name, String author, int pagesNumber, User user) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.pagesNumber = pagesNumber;
        this.user = user;
    }

    public Book(String name, String author, int pagesNumber) {
        this.name = name;
        this.author = author;
        this.pagesNumber = pagesNumber;
    }

    public Book(String name, String author, int pagesNumber, User user) {
        this.name = name;
        this.author = author;
        this.pagesNumber = pagesNumber;
        this.user = user;
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

    public int getPagesNumber() {
        return pagesNumber;
    }

    public void setPagesNumber(int pagesNumber) {
        this.pagesNumber = pagesNumber;
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

    @Override
    public int hashCode() {
        return Objects.hash(name, author, pagesNumber, id, user);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if ((obj == null) || !(obj instanceof Book)) {
            return false;
        }
        Book book = (Book) obj;
        return Objects.equals(name, book.name) && Objects.equals(author, book.author) && pagesNumber == book.pagesNumber && id == book.id && Objects.equals(user, book.user);
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
        return id + ", " + name + ", " + author + ", " + pagesNumber + ", " + "(" + user + ")";
    }
}
