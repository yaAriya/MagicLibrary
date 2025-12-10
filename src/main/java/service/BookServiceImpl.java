package service;

import dao.BookDao;
import entity.Book;
import entity.User;
import exceptions.*;
import validator.BookValidator;

import java.util.List;

public class BookServiceImpl implements BookService {
    private BookDao bookDao;
    private BookValidator bookValidator;
    private UserService userService;

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public void setBookValidator(BookValidator bookValidator) {
        this.bookValidator = bookValidator;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<Book> readAllBooks() throws BookServiceException {
        try {
            return bookDao.readAllBooks();
        } catch (ObjectInitializeException | BookDaoException e) {
            throw new BookServiceException(e);
        }
    }

    @Override
    public void add(Book book) throws BookServiceException {
        try {
            if (!bookValidator.validate(book) && bookDao.readAllBooks().contains(book)) {
                throw new InvalidEntityException("Параметры, введенные Вами некорректны");
            }
            for (Book tempBook : bookDao.readAllBooks()) {
                if (tempBook.getId() == book.getId()) {
                    throw new InvalidEntityException("Книга с таким айди уже существует");
                }
            }
            bookDao.add(book);
        } catch (InvalidEntityException | BookDaoException e) {
            throw new BookServiceException(e);
        }
    }

    @Override
    public Book read(long id) throws BookServiceException {
        try {
            if (bookDao.read(id) == null) {
                throw new EntityNotFoundException("Такой книги нет");
            }
            return bookDao.read(id);
        } catch (EntityNotFoundException | BookDaoException e) {
            throw new BookServiceException(e);
        }
    }

    @Override
    public void update(Book book) throws BookServiceException {
        try {
            Book oldBook = read(book.getId());
            if (!bookValidator.validate(book)) {
                throw new InvalidEntityException("Параметры, введенные Вами некорректны");
            } else if (oldBook.getUser() != null) {
                throw new InvalidEntityException("Вы не можете обновить уже арендованную книгу");
            } else if (book.getUser() != null) {
                throw new InvalidEntityException("Пользователь обновляемой книги должен отсутствовать");
            }
            bookDao.update(book);
        } catch (InvalidEntityException | BookDaoException e) {
            throw new BookServiceException(e);
        }
    }

    @Override
    public void delete(long id) throws BookServiceException {
        try {
            if (id < 0 && read(id).getUser() != null) {
                throw new InvalidEntityException("Cannot delete your book");
            }
            bookDao.delete(id);
        } catch (InvalidEntityException | BookDaoException e) {
            throw new BookServiceException(e);
        }
    }

    @Override
    public void rentBook(long userId, long bookId) throws BookServiceException {
        try {
            User readUser = userService.read(userId);
            Book readBook = read(bookId);
            if (readUser == null || readBook == null) {
                throw new InvalidEntityException("Пользователь или книга не найдены");
            } else if (readBook.getUser() != null || readUser.getBooks().contains(readBook)) {
                throw new InvalidEntityException("У книги уже есть пользователь или у пользователя уже арендована эта книга");
            }
            bookDao.rentBook(readUser, readBook);
        } catch (InvalidEntityException | BookDaoException e) {
            throw new BookServiceException(e);
        }
    }

    @Override
    public void returnBook(long userId, long bookId) throws BookServiceException {
        try {
            User readUser = userService.read(userId);
            Book readBook = read(bookId);
            if (readUser == null || readBook == null) {
                throw new InvalidEntityException("Пользователь или книга не могут быть пустыми");
            } else if (readBook.getUser() == null) {
                throw new InvalidEntityException("У книги нет пользователя или у пользователя не была арендована книга");
            }
            bookDao.returnBook(readUser, readBook);

            readUser.getBooks().remove(readBook);
            userService.update(readUser);

            readBook.setUser(null);
            update(readBook);
        } catch (InvalidEntityException | BookDaoException e) {
            throw new BookServiceException();
        }
    }
}
