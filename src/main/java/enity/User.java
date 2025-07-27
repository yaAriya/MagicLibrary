package enity;

import java.util.ArrayList;
import java.util.List;

import java.util.Objects;

public class User implements Cloneable {
    private long id;
    private String name;
    private String email;
    private int age;
    private List<Book> books;

    public User() {
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
    public User clone() throws CloneNotSupportedException {
        if (getBooks().size() == 0) {
            User clonedUser = new User();
            clonedUser.setId(this.id);
            clonedUser.setName(this.name);
            clonedUser.setAge(this.age);
            clonedUser.setEmail(this.email);
            clonedUser.setBooks(new ArrayList<>());

            return clonedUser;
        } else {
            User clonedUser = new User();// У юзера всегда будет лист с книгами, другой вопрос пустой ли он?
            clonedUser.setId(this.id);
            clonedUser.setName(this.name);
            clonedUser.setAge(this.age);
            clonedUser.setEmail(this.email);
            List<Book> clonedBooks = new ArrayList<>();

            for (Book book : getBooks()) {
                clonedBooks.add(book.clone());
            }
            clonedUser.setBooks(clonedBooks);

            return clonedUser;
        }
    }

    @Override
    public String toString() {
        List<Long> booksId = new ArrayList<>();

        for (Book book : getBooks()) {
            booksId.add(book.getId());
        }
        return id + ", " + name + ", " + email + ", " + age + ", " + booksId;
    }


}
