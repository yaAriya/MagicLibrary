package mapper;

import entity.User;
import exceptions.MapperException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface Mapper<T> {
    T mapRSToObject(ResultSet resultSet) throws MapperException;
    void mapObjectToStatement(PreparedStatement preparedStatement,T t) throws MapperException;
    void mapObjectIdToStatement(PreparedStatement preparedStatement, long id) throws MapperException;
    void mapUpdateObjectToStatement(PreparedStatement preparedStatement, T t) throws MapperException;
}
