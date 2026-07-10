package converter;

import dao.FileBasedUserDao;
import entity.Book;
import entity.User;
import exceptions.ConverterException;
import exceptions.UserDaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.UserService;

import java.util.Arrays;

public class BookConverterImpl implements BookConverter {
    private static final Logger LOGGER = LogManager.getLogger(BookConverterImpl.class);
    private static final String PARAMETER = ",";
    private UserService userService;
    private FileBasedUserDao userDao;

    @Override
    public Book convertLineToBook(String line) throws ConverterException {
        try {
            String[] parameters = Arrays.stream(line.split(PARAMETER))
                    .map(String::trim)
                    .toArray(String[]::new);

            Book book = new Book();
            book.setId(Long.parseLong(parameters[0]));
            book.setName(parameters[1]);
            book.setAuthor(parameters[2]);
            book.setPages(Integer.parseInt(parameters[3]));

            if (parameters.length > 4) {
                book.setUser(userService.read(Long.parseLong(parameters[4])));

                User updateUser = book.getUser();
                userDao.addBookToUser(updateUser, book);
            }
            LOGGER.info("Converting line to book completed successful");
            return book;
        } catch (UserDaoException e) {
            LOGGER.error("Converting line to book failed");
            throw new ConverterException(e);
        }
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

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setUserDao(FileBasedUserDao userDao) {
        this.userDao = userDao;
    }
}
