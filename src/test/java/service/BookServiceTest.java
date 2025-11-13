package service;

import dao.BookDao;
import dao.BookDaoImpl;
import entity.Book;
import org.junit.jupiter.api.*;
import org.mockito.*;

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
        testBook.setName("lina");
        testBook.setId(1);
        testBook.setAuthor("elena G");
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
    void additionTest() {
        bookService.add(testBook);
    }
}
