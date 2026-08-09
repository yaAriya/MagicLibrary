package converter;

import entity.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class BookConverterImpl implements BookConverter {
    private static final Logger LOGGER = LogManager.getLogger(BookConverterImpl.class);
    private static final String PARAMETER = ",";

    @Override
    public Book convertLineToBook(String line) {
        String[] parameters = Arrays.stream(line.split(PARAMETER))
                .map(String::trim)
                .toArray(String[]::new);

        Book book = new Book();
        book.setId(Long.parseLong(parameters[0]));
        book.setName(parameters[1]);
        book.setAuthor(parameters[2]);
        book.setPages(Integer.parseInt(parameters[3]));

        if (parameters.length > 4) {
            book.setUserId(Integer.parseInt(parameters[4]));
        }

        LOGGER.info("Converting line to book completed successful");
        return book;
    }

    public String convertBookToLine(Book book) {
        String idToString = Long.toString(book.getId());
        String pagesNumberToString = Integer.toString(book.getPages());

        StringBuilder sb = new StringBuilder();
        sb.append(idToString).append(PARAMETER);
        sb.append(book.getName()).append(PARAMETER);
        sb.append(book.getAuthor()).append(PARAMETER);
        sb.append(pagesNumberToString);

        if (book.getUser() != null) {
            String userIdToString = Long.toString(book.getUser().getId());
            sb.append(PARAMETER).append(userIdToString);
        }
        LOGGER.info("Converting book to line completed successful");
        return sb.toString();
    }
}
