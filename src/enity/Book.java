package enity;

import java.util.Objects;

public class Book {
    private String bookName;
    private String author;
    private int pagesNumber;
    private String userName;
    private String userEmail;
    private int userID;

    public Book (String bookName, String author, int pagesNumber, String userName, String userEmail, int userID){
        this.bookName = bookName;
        this.author = author;
        this.pagesNumber = pagesNumber;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userID = userID;
    }

    public String getBookName(){
        return  bookName;
    }

    public void setBookName(String bookName){
        this.bookName = bookName;
    }

    public String getAuthor(){
        return author;
    }

    public  void setAuthor(String author){
        this.author = author;
    }

    public int getPagesNumber(){
        return pagesNumber;
    }

    public void setPagesNumber(int pagesNumber){
        this.pagesNumber = pagesNumber;
    }

    public String getUserName(){
        return userName;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getUserEmail(){
        return  userEmail;
    }

    public void setUserEmail(String userEmail){
        this.userEmail = userEmail;
    }

    public int getUserID(){
        return userID;
    }

    public void setUserID(int userID){
        this.userID = userID;
    }

    @Override
    public int hashCode(){
        return Objects.hash(bookName, author, pagesNumber, userName, userEmail, userID);
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if ((obj == null) || !(obj instanceof Book)) return false;
        Book book = (Book) obj;
        return Objects.equals(bookName, book.bookName) && Objects.equals(author, book.author) && pagesNumber == book.pagesNumber && Objects.equals(userName, book.userName) && Objects.equals(userEmail, book.userEmail) && userID == book.userID;
    }


    @Override
    public String toString(){
        return bookName + ", " + author + ", " + pagesNumber + ", " + userName + ", " + userEmail + ", " + userID;
    }
}
