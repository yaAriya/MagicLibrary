package config;

import exceptions.DatabaseConfigException;

import java.sql.Connection;

public interface DatabaseConfig {
    Connection getConnection() throws DatabaseConfigException;

    void testConnection() throws DatabaseConfigException;

}
