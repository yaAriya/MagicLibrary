package DAOClass;

import enity.Book;

import java.util.ArrayList;

import java.util.List;

public class BookDAO {
    public List<Book> initializeIssuedBooks(){
        List<Book> issuedBooks = new ArrayList<>();

        Book firstBook = new Book("Pride and produce", "Jane Austen", 352, "Elsa", "Elisa@gmail.com", 245);
        Book secondBook = new Book("Harry Potter", "J. K. Rowling", 302, "Petr", "Petya@gmail.com", 356);
        Book thirdBook = new Book("The Mysterious Island", "Jules Verne", 597, "Nick", "NickNick@gmail.com", 134);

        issuedBooks.add(firstBook);
        issuedBooks.add(secondBook);
        issuedBooks.add(thirdBook);

        return issuedBooks;
    }

    public List<Book> initializeBooksOnShelves(){
        List<Book> booksOnShelves = new ArrayList<>();


    }
}
