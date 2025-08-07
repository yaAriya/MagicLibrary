package validator;

import entity.Book;

public class BookValidator implements Validator<Book> {
    private static BookValidator INSTANCE;

    public static BookValidator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BookValidator();
        }
        return INSTANCE;
    }

    @Override
    public boolean validate(Book book) {
        return book != null && validateId(book.getId()) && validateName(book.getName()) && validateAuthor(book.getAuthor()) && validatePageNumber(book.getPagesNumber());
    }

    private boolean validateId(long id) {
        return id >= 0;
    }

    private boolean validateName(String name) {
        return name != null;
    }

    private boolean validateAuthor(String author) {
        return author != null;
    }

    private boolean validatePageNumber(int pageNumber) {
        return pageNumber > 0;
    }
}
