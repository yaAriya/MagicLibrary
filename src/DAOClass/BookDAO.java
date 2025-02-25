package DAOClass;

import enity.Book;

import java.util.ArrayList;

import java.util.List;

public class BookDAO implements DAOBook {

    public static List<Book> initializeBooks() {
        List<Book> books = new ArrayList<>();
        return books;
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
        List<Book> books = initializeBooks();
        for(int i = 0; i< books.size(); i++){
            if(books.get(i).getBookID() == ID){
                return books.get(i);
            }
        }
        return null;
    }
}
