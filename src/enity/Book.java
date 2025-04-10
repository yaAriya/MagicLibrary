package enity;

import java.util.Objects;

public class Book {
    private long id;
    private String bookName;
    private String author;
    private int pagesNumber;
    private User user;

    public Book() {
    }

    public Book(long id, String bookName, String author, int pagesNumber) {
        this.id = id;
        this.bookName = bookName;
        this.author = author;
        this.pagesNumber = pagesNumber;

    }

    public Book(long id, String bookName, String author, int pagesNumber, User user) {
        this.id = id;
        this.bookName = bookName;
        this.author = author;
        this.pagesNumber = pagesNumber;
        this.user = user;

        /*if(getUser() != null){

        }
        if (bookName == null){
         //   new Exception("Your parameter is/are incorrect ");
        } проверка на налл */
    }


    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
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
        return Objects.hash(bookName, author, pagesNumber, id, user);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if ((obj == null) || !(obj instanceof Book)) return false;
        Book book = (Book) obj;
        return Objects.equals(bookName, book.bookName) && Objects.equals(author, book.author) && pagesNumber == book.pagesNumber && id == book.id && Objects.equals(user, book.user);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }


    @Override
    public String toString() {
        return id + ", " + bookName + ", " + author + ", " + pagesNumber + ", " + user;
    }
}
