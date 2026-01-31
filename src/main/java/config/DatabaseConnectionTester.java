package config;

import exceptions.DatabaseConfigException;
import exceptions.DatabaseConnectionTesterException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionTester {
    private static final Logger LOGGER = LogManager.getLogger(DatabaseConnectionTester.class);
    private DatabaseConfig databaseConfig;

    public void validateTestConnection() throws DatabaseConnectionTesterException {
        try (Connection connection = databaseConfig.getConnection()) {
            if (!connection.isValid(5)) {
                LOGGER.warn("Connection not valid");
            }
            LOGGER.info("Connection valid");
            databaseConfig.testConnection();
        } catch (SQLException | DatabaseConfigException e) {
            LOGGER.error("Testing the connection failed");
            throw new DatabaseConnectionTesterException(e);
        }
    }

    public void setDatabaseConfig(DatabaseConfigImpl databaseConfigImpl) {
        this.databaseConfig = databaseConfigImpl;
    }
}
