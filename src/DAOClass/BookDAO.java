package DAOClass;

import enity.Book;

import java.util.ArrayList;

import java.util.List;

public class BookDAO implements DAOBook {

    public static List<Book> initializeBooks() {
        List<Book> books = new ArrayList<>();

        Book firstBook = new Book("Pride and produce", "Jane Austen", 352, 145, null);
                //352, "Elsa", "Elisa@gmail.com", 245);
        Book secondBook = new Book("Harry Potter", "J. K. Rowling", 302, 326, null);// Вызываем дао юзер а тот вызовет юзера
                //"Petr", "Petya@gmail.com", 356);
        Book thirdBook = new Book("The Mysterious Island", "Jules Verne", 597, 467, null);
                //"Nick", "NickNick@gmail.com", 134);

        books.add(firstBook);
        books.add(secondBook);
        books.add(thirdBook);


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
