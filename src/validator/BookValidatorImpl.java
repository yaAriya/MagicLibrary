package validator;

import enity.Book;

public class BookValidatorImpl implements BookValidator {
    private static BookValidatorImpl instance;

    public static BookValidatorImpl getInstance() {
        if (instance == null) {
            instance = new BookValidatorImpl();
        }
        return instance;
    }

    @Override
    public boolean validate(Book book) {
        return book!= null && validateId(book.getId()) && validateName(book.getName()) && validateAuthor(book.getAuthor()) && validatePageNumber(book.getPagesNumber());
    }

    @Override
    public boolean validateId(long id) {
        return id >= 0;
    }

    @Override
    public boolean validateName(String name) {
        return name != null;
    }

    @Override
    public boolean validateAuthor(String author) {
        return author != null;
    }

    @Override
    public boolean validatePageNumber(int pageNumber) {
        return pageNumber > 0;
    }
}
