package validator;

import enity.Book;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
public class BookValidatorTest {
    private BookValidator bookValidator;
    private Book book;
    private Book testBook;

    @Test
    public void setUp() throws Exception{
        bookValidator = BookValidator.getInstance();
        book = new Book();
        testBook = new Book();
        testBook.setName("Вафся");
        testBook.setId(0);
        testBook.setAuthor("хз");
        testBook.setPagesNumber(1342);
        testBook.getUser();
    }
   /* @*//*Test
    public void validateTest() {
        boolean result = bookValidator.validate(testBook);
        assertTrue(result);
    }

    @Test
    public void validateIfThereIsNoInformationAboutBookTest(){
        boolean result = bookValidator.validate(book);
        assertFalse(result, "О книге нет данных");
    }*/
}