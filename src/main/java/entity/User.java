package entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private int age;
    @OneToMany(mappedBy = "user")
    private List<Book> books;

    public void addBook(Book book){
        this.getBooks().add(book);
        book.setUser(this);
    }

    public void removeBook(Book book) {
        this.getBooks().remove(book);
        book.setUser(null);
    }

    public User() {
        this.books = new ArrayList<>();
    }

    public User(long id, String name, String email, int age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.books = new ArrayList<>();
    }

    public User(long id, String name, String userEmail, int userAge, List<Book> books) {
        this.id = id;
        this.name = name;
        this.email = userEmail;
        this.age = userAge;
        this.books = books;
    }

    public User(String name, String email, int age, List<Book> books) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.books = books;
    }

    public User(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.books = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if ((obj == null) || !(obj instanceof User)) {
            return false;
        }
        User user = (User) obj;
        return id == user.id;
    }

    @Override
    public User clone() throws CloneNotSupportedException {
        User clonedUser = (User) super.clone();
        if (!getBooks().isEmpty()) {
            List<Book> clonedBooks = new ArrayList<>();

            for (Book book : getBooks()) {
                clonedBooks.add(book.clone());
            }
            clonedUser.setBooks(clonedBooks);
        }
        return clonedUser;
    }

    @Override
    public String toString() {
        return id + ", " + name + ", " + email + ", " + age;
    }
}
