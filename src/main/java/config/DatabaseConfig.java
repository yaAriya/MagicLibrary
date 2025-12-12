package config;

import exceptions.DatabaseConfigException;
import exceptions.PropertyLoaderException;
import loader.PropertyLoader;

import java.sql.*;

public class DatabaseConfig {
    private PropertyLoader propertyLoader;

    public void setPropertyLoader(PropertyLoader propertyLoader) {
        this.propertyLoader = propertyLoader;
    }

    public Connection getConnection() throws DatabaseConfigException {
        try {
            return DriverManager.getConnection(propertyLoader.getDBUrl(), propertyLoader.getDBUsername(), propertyLoader.getDBPassword());
        } catch (SQLException | PropertyLoaderException e){
            throw new DatabaseConfigException(e);
        }
    }

    public void testConnection() throws DatabaseConfigException {
        try (Connection connection = DriverManager.getConnection(propertyLoader.getDBUrl(), propertyLoader.getDBUsername(), propertyLoader.getDBPassword())) {
            Statement statement = connection.createStatement();

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100) NOT NULL, email VARCHAR(255) UNIQUE NOT NULL, age INT NOT NULL)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS books (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100) NOT NULL, author VARCHAR(100) NOT NULL, page_number INT NOT NULL, user_id BIGINT)");
            System.out.println("Подключение прошло успешно");

        } catch (SQLException | PropertyLoaderException e) {
            System.out.println("Подключение провалено");
            throw new DatabaseConfigException(e);
        }

    }
}
