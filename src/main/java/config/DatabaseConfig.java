package config;

import exceptions.DatabaseConfigException;
import exceptions.PropertyLoaderException;
import loader.PropertyLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class DatabaseConfig {
    private PropertyLoader propertyLoader;
    private static final Logger logger = LogManager.getLogger(DatabaseConfig.class);

    public void setPropertyLoader(PropertyLoader propertyLoader) {
        this.propertyLoader = propertyLoader;
    }

    public Connection getConnection() throws DatabaseConfigException {
        try {
            Connection connection = DriverManager.getConnection(propertyLoader.getDBUrl(), propertyLoader.getDBUsername(), propertyLoader.getDBPassword());
            logger.info("Getting connection was successful");
            return connection;
        } catch (SQLException | PropertyLoaderException e){
            logger.error("Getting the connection failed");
            throw new DatabaseConfigException(e);
        }
    }

    public void testConnection() throws DatabaseConfigException {
        try (Connection connection = DriverManager.getConnection(propertyLoader.getDBUrl(), propertyLoader.getDBUsername(), propertyLoader.getDBPassword())) {
            Statement statement = connection.createStatement();

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100) NOT NULL, email VARCHAR(255) UNIQUE NOT NULL, age INT NOT NULL)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS books (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100) NOT NULL, author VARCHAR(100) NOT NULL, page_number INT NOT NULL, user_id BIGINT)");
            logger.info("Testing connection was successful");
        } catch (SQLException | PropertyLoaderException e) {
            logger.error("Testing the connection failed");
            throw new DatabaseConfigException(e);
        }

    }
}
