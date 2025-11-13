package invoker;

import entity.Book;
import entity.User;
import exceptions.BookServiceException;
import org.junit.jupiter.api.*;
import org.mockito.*;
import printer.PrinterImpl;
import service.BookServiceImpl;
import service.UserServiceImpl;

public class MainTest {
    @Mock
    private UserServiceImpl userServiceMock;
    @Mock
    private BookServiceImpl bookServiceMock;
    @Mock
    private PrinterImpl printerMock;

        @BeforeEach
        void setUp() {
            Main main = new Main();
            Book book = new Book();
            User user = new User();
            userServiceMock = UserServiceImpl.getInstance();
            bookServiceMock = BookServiceImpl.getInstance();
            printerMock = PrinterImpl.getInstance();
        }

       /* @Test
        void mainTest() {
            Main.main(new String[0]);
            Mockito.verify(userServiceMock).initializeCash();
            Mockito.verify(bookServiceMock).initializeCash();
        }*/

        /*@Test
        void throwBookServiceException(){
            Book book = new Book();
            BookServiceException bookServiceException = Assertions.assertThrowsExactly(BookServiceException.class, () -> {
                book.throwsBookServiceException();
            });
        }*/

       /* @Test
        void throwUserServiceException(){

        }*/


}
