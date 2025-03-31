package validator;

import enity.Book;

public class BookValidatorImpl implements BookValidator {
    private static BookValidatorImpl instance;

    public static BookValidatorImpl getInstance(){
        if(instance == null){
            instance = new BookValidatorImpl();
        }
        return instance;
    }
    @Override
    public boolean validate(Book book) {
        return book != null;
    }
}
