package mapper;

import entity.Book;
import entity.User;
import exceptions.MapperException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class BookMapper implements Mapper<Book> {
    private static BookMapper INSTANCE;

    public static BookMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BookMapper();
        }
        return INSTANCE;
    }

    private BookMapper() {
    }

    @Override
    public Book mapRSToObject(ResultSet resultSet) throws MapperException {
        try {
            Book mappedBook = new Book();
            long id = resultSet.getLong("id");//Без джоина, но с джоином то будет ьук айди, поэтому вызывать маппер другой не вариант
            mappedBook.setId(id);
            String name = resultSet.getString("name");
            mappedBook.setName(name);
            String author = resultSet.getString("author");
            mappedBook.setAuthor(author);
            int pageNumber = resultSet.getInt("page_number");
            mappedBook.setPagesNumber(pageNumber);
            long userId = resultSet.getLong("user_id");
            mappedBook.setUser(UserMapper.getInstance().mapRSToObject(resultSet));
            return mappedBook;
        } catch (SQLException e) {
            throw new MapperException(e);
        }
    }

    @Override
    public void mapObjectToStatement(PreparedStatement preparedStatement, Book book) throws MapperException {
        try {
            preparedStatement.setString(1, book.getName());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setInt(3, book.getPagesNumber());
            if (book.getUser() != null) {
                preparedStatement.setLong(4, book.getUser().getId());
            } else {
                preparedStatement.setLong(4, Types.BIGINT);
            }
        } catch (SQLException e){
            throw new MapperException(e);
        }
        }

        @Override
        public void mapObjectIdToStatement (PreparedStatement preparedStatement,long id) throws MapperException {
            try{
                preparedStatement.setLong(1, id);
            } catch (SQLException e){
                throw new MapperException(e);
            }
        }

        @Override
        public void mapUpdateObjectToStatement (PreparedStatement preparedStatement, Book book) throws MapperException {
        try {
            preparedStatement.setString(1, book.getName());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setLong(3, book.getPagesNumber());
            if (book.getUser() != null) {
                preparedStatement.setLong(4, book.getUser().getId());
            } else {
                preparedStatement.setLong(4, Types.BIGINT);
            }
            preparedStatement.setLong(5, book.getId());
        } catch (SQLException e){
            throw new MapperException(e);
        }

        }
    }
