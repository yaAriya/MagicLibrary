package mapper;

import exceptions.MapperException;

import java.sql.ResultSet;

public interface Mapper<T> {
    T mapResultSetToObject(ResultSet resultSet) throws MapperException;

    T mapResultSetToObjectWithoutDependencies(ResultSet resultSet) throws MapperException;
}
