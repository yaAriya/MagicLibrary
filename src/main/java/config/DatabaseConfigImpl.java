package config;

import exceptions.DatabaseConfigException;
import exceptions.PropertyLoaderException;
import loader.DatabasePropertyLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConfigImpl implements DatabaseConfig {
    private static final Logger LOGGER = LogManager.getLogger(DatabaseConfigImpl.class);
    private DatabasePropertyLoader databasePropertyLoader;

    public Connection getConnection() throws DatabaseConfigException {
        try {
            Connection connection = DriverManager.getConnection(databasePropertyLoader.getDBUrl(), databasePropertyLoader.getDBUsername(), databasePropertyLoader.getDBPassword());
            LOGGER.info("Getting connection was successful");
            return connection;
        } catch (SQLException | PropertyLoaderException e) {
            LOGGER.error("Getting the connection failed");
            throw new DatabaseConfigException(e);
        }
    }

    public void testConnection() throws DatabaseConfigException {
        try (Connection connection = DriverManager.getConnection(databasePropertyLoader.getDBUrl(), databasePropertyLoader.getDBUsername(), databasePropertyLoader.getDBPassword())) {
            Statement statement = connection.createStatement();

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100) NOT NULL, email VARCHAR(255) UNIQUE NOT NULL, age INT NOT NULL)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS books (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100) NOT NULL, author VARCHAR(100) NOT NULL, page_number INT NOT NULL, user_id BIGINT)");
            LOGGER.info("Testing connection was successful");
        } catch (SQLException | PropertyLoaderException e) {
            LOGGER.error("Testing the connection failed");
            throw new DatabaseConfigException(e);
        }
    }

    public void setPropertyLoader(DatabasePropertyLoader databasePropertyLoader) {
        this.databasePropertyLoader = databasePropertyLoader;
    }
}
