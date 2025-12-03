package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConfig {
    private static final String user = "root";
    private static final String password = "qwertyuiop[]12";
    private static final String url = "jdbc:mysql://127.0.0.1:3306/librarytest";


    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public void testConnection() {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            Statement statement = connection.createStatement();

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100) NOT NULL, email VARCHAR(255) UNIQUE NOT NULL, age INT NOT NULL)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS books (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100) NOT NULL, author VARCHAR(100) NOT NULL, page_number INT NOT NULL, user_id BIGINT)");
            System.out.println("Подключение прошло успешно");

        } catch (SQLException e) {
            System.out.println("Подключение провалено");
            throw new RuntimeException(e);
        }

    }
}
