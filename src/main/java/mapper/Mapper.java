package mapper;

import exceptions.MapperException;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Mapper<T> {
    T mapResultSetToObject(ResultSet resultSet) throws MapperException;

    T mapResultSetToObjectWithoutDependencies(ResultSet resultSet) throws SQLException;
}
