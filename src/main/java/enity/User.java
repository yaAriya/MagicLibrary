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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String nameCopy = new String(name);
        //nameCopy.
        this.name = nameCopy;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        String emailCopy = email;
        this.email = emailCopy;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        int ageCopy = age;
        this.age = ageCopy ;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        long idCopy = id;
        this.id = idCopy;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        List<Book> booksCopy = new ArrayList<>();
        booksCopy.addAll(books);
        this.books = booksCopy;
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
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        List<Book> cloneBooks = new ArrayList<>();// ТАк нельзя
        for (int i = 0; i<books.size(); i++){
            cloneBooks.add(books.get(i));
            //cloneBooks.addAll(books);
        }
            List<Long> booksId = new ArrayList<>();
            for (int i = 0; i < cloneBooks.size(); i++) {
                booksId.add(cloneBooks.get(i).getId());
            }
            return id + ", " + name + ", " + email + ", " + age + ", " + booksId;
    }
}
