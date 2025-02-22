package enity;

import java.util.Objects;

public class Book {
    private String bookName;
    private String author;
    private int pagesNumber;
    private User user;

    public Book (String bookName, String author, int pagesNumber, User user) {
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

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode(){
        return Objects.hash(bookName, author, pagesNumber, user);
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if ((obj == null) || !(obj instanceof Book)) return false;
        Book book = (Book) obj;
        return Objects.equals(bookName, book.bookName) && Objects.equals(author, book.author) && pagesNumber == book.pagesNumber && Objects.equals(user, book.user);
                //Objects.equals(user, book.user.getUserName()) && Objects.equals(user, book.user.getUserEmail()) && user == book.user.getUserID();
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }


    @Override
    public String toString(){
        return bookName + ", " + author + ", " + pagesNumber + ", " + user;
                //user.toString();
                //user.getUserName().toString() + ", " + user.getUserEmail().toString() + ", " + user.getUserID().toString;
    }
}
