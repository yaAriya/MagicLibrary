package DAOClass;

import enity.Book;

import java.util.ArrayList;

import java.util.List;

public class BookDAOImplementation implements BookDAO {

    public Book initializeBook(String bookText) {

        String[] bookString = bookText.split(",");
        for (String word : bookString) {
            System.out.println(word);
        }

        Book book = new Book();
        book.setBookName(bookString[0]);
        book.setAuthor(bookString[1]);
        book.setPagesNumber(Integer.parseInt(bookString[2]));
        book.setBookID(Integer.parseInt(bookString[3]));
        book.setUser();

        Book bookObj = new Book(book.getBookName(),book.getAuthor(), book.getPagesNumber(), book.getPagesNumber(), book.getUser());
        return bookObj;
    }

    @Override
    public void add(Book book) {

    }

    @Override
    public Book upDate(Book book) {
        return null;
    }

    @Override
    public void delete(Book book) {

    }

    @Override
    public Book read(int ID) {
        /*BookDAOImplementation bookDAO = BookDAOImplementation();
        List<Book> books = bookDAO.initializeBooks();
        for(int i = 0; i< books.size(); i++){
            if(books.get(i).getBookID() == ID){
                return books.get(i);
            }
        }*/
        return null;

    }
}
