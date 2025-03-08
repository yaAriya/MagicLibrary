package enity;

import java.util.List;

import java.util.Objects;

public class User {
    private int userId;
    private String userName;
    private String userEmail;
    private int userAge;
    private List<Book> books;

    public User(){
    }

    public User(int userId, String userName, String userEmail, int userAge){
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userAge = userAge;
    }

    public User (int userId, String userName, String userEmail, int userAge, List<Book> books){
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userAge = userAge;
        this.books = books;
    }

    public String getUserName(){
        return userName;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getUserEmail(){
        return userEmail;
    }

    public void setUserEmail(String userEmail){
        this.userEmail = userEmail;
    }

    public int getUserAge(){
        return userAge;
    }

    public void setUserAge(int userAge){
        this.userAge = userAge;
    }

    public int getUserId(){
        return userId;
    }

    public void setUserId(int userID) {
        this.userId = userID;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books){
        this.books = books;
    }

    @Override
    public int hashCode(){
        return Objects.hash(userName, userEmail, userAge, userId);
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if ((obj == null) || !(obj instanceof User)) return false;
        User user = (User) obj;
        return userId == user.userId && Objects.equals(userName, user.userName) && Objects.equals(userEmail, user.userEmail) && userAge == user.userAge;
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }

    @Override
    public String toString(){
        return userId + ", " + userName + ", " + userEmail + ", " + userAge;
    }
}
