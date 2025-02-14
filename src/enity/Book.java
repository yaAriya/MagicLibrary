package enity;

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








}
