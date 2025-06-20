package service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.*;

import org.mockito.*;

import dao.BookDao;

import dao.BookDaoImpl;

import enity.Book;

import exceptions.InvalidEntityException;

import exceptions.ObjectInitializeException;

import java.util.ArrayList;

import java.util.List;

public class BookServiceTest {
    private BookService bookService;
    private Book testBook;
    private List<Book> testBooks;
    @Mock
    private BookDao bookDao;

    @BeforeEach
    void setUp() throws Exception{
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
    void readAllBooksTest( ) {
       // ObjectInitializeException thrown = assertThrows(ObjectInitializeException.class, ) {
            List<Book> actualBooks = bookService.readAllBooks();
            List<Book> expectedBooks = bookDao.readAllBooks();
            //Нужны примитивные типы! assertArrayEquals(expectedBooks,actualBooks);
       // }
    }


    @Test
    @Disabled
            //(expected = InvalidEntityException.class)
    void addTest(){
      bookService.add(testBook);
    }
}
