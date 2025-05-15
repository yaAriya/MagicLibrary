package enity;

import java.util.ArrayList;
import java.util.List;

import java.util.Objects;

public class User {
    private long id;
    private String name;
    private String email;
    private int age;
    private List<Book> books;

    public User(){
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
        return Objects.hash(name, email, age, id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if ((obj == null) || !(obj instanceof User)) return false;
        User user = (User) obj;
        return id == user.id && Objects.equals(name, user.name) && Objects.equals(email, user.email) && age == user.age;
    }

    @Override
    public String toString() {
        List<Book> cloneBooks = books;
        List<Long> booksId = new ArrayList<>();
        for (int i = 0; i < cloneBooks.size(); i++) {
            booksId.add(cloneBooks.get(i).getId());
        }
        return id + ", " + name + ", " + email + ", " + age + ", " + booksId;
    }
}
