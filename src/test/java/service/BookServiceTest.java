package service;

import dao.BookDao;
import dao.BookDaoImpl;
import entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

public class BookServiceTest {
    private BookService bookService;
    private Book testBook;
    private List<Book> testBooks;
    @Mock
    private BookDao bookDao;

    @BeforeEach
    void setUp() throws Exception {
        bookService = BookServiceImpl.getInstance();
        bookDao = BookDaoImpl.getInstance();

        testBook = new Book();
        testBook.setName("lskvk");
        testBook.setId(1);
        testBook.setAuthor(";slmv");
        testBook.setPagesNumber(245);
        testBook.setUser(null);

        testBooks = new ArrayList<>();

    }


    @Test
    @Disabled
        //ObjectInitializeException.class(ex)
    void readAllBooksTest() {
        // ObjectInitializeException thrown = assertThrows(ObjectInitializeException.class, ) {
        List<Book> actualBooks = bookService.readAllBooks();
        List<Book> expectedBooks = bookDao.readAllBooks();
        //Нужны примитивные типы! assertArrayEquals(expectedBooks,actualBooks);
        // }
    }


    @Test
    @Disabled
        //(expected = InvalidEntityException.class)
    void addTest() {
        bookService.add(testBook);
    }
}
