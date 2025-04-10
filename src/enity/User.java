package enity;

import java.util.List;

import java.util.Objects;

public class User {
    private long id;
    private String userName;
    private String userEmail;
    private int userAge;
    private List<Book> books;

    public User() {
    }

    public User(long id, String userName, String userEmail, int userAge) {
        this.id = id;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userAge = userAge;
    }

    public User(long id, String userName, String userEmail, int userAge, List<Book> books) {
        this.id = id;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userAge = userAge;
        this.books = books;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
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
        return Objects.hash(userName, userEmail, userAge, id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if ((obj == null) || !(obj instanceof User)) return false;
        User user = (User) obj;
        return id == user.id && Objects.equals(userName, user.userName) && Objects.equals(userEmail, user.userEmail) && userAge == user.userAge;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return id + ", " + userName + ", " + userEmail + ", " + userAge;
    }
}
