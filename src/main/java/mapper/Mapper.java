package mapper;

import exceptions.MapperException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface Mapper<T> {
    T mapResultSetToObject(ResultSet resultSet) throws MapperException;

    T mapResultSetToObjectWithoutDependencies(ResultSet resultSet) throws SQLException;

    List<T> mapResultSetToObjects(ResultSet resultSet) throws MapperException;
}
