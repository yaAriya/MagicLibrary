package validator;

import enity.Book;

public interface BookValidator {
    boolean validate(Book book);

    boolean validateId(long id);

    boolean validateName(String name);

    boolean validateAuthor(String author);

    boolean validatePageNumber(int pageNumber);
}
