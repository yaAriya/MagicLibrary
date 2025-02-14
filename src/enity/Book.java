package enity;

public class Book {
    private String bookName;
    private String author;
    private int pagesNumber;
    private String userName;
    private String userEmail;
    private String userID;

    public Book (String bookName, String author, int pagesNumber, String userName, String userEmail, String userID){
        this.bookName = bookName;
        this.author = author;
        this.pagesNumber = pagesNumber;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userID = userID;
    }
}
