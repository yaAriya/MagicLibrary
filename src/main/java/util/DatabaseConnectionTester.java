package util;

import config.DatabaseConfig;
import exceptions.DatabaseConfigException;
import exceptions.DatabaseConnectionTesterException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionTester {
    private DatabaseConfig databaseConfig;
    private static final Logger logger = LogManager.getLogger();

    public void setDatabaseConfig(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    public void validateTestConnection() throws DatabaseConnectionTesterException {
        try (Connection connection = databaseConfig.getConnection()) {
            if (!connection.isValid(5)) {
                logger.warn("Connection not valid");
            }
            logger.info("Connection valid");
            databaseConfig.testConnection();
        } catch (SQLException | DatabaseConfigException e) {
            logger.error("Testing the connection failed");
            throw new DatabaseConnectionTesterException(e);
        }
    }

}
