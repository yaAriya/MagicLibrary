package loader;

import exceptions.PropertyLoaderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {
    private static final Logger logger = LogManager.getLogger();
    private static final Properties properties = new Properties();
    private static boolean initialized;

    private static void init() throws PropertyLoaderException {
        try (InputStream input = PropertyLoader.class.getResourceAsStream("/databaseConfig.properties")) {
            if (!initialized && input != null) {
                properties.load(input);
                initialized = true;
            }
        } catch (Exception e) {
            throw new PropertyLoaderException(e);
        }
    }

    public String getDBUsername() throws PropertyLoaderException {
        init();
        return properties.getProperty("database.username");
    }

    public String getDBPassword() throws PropertyLoaderException {
        init();
        return properties.getProperty("database.password");
    }

    public String getDBUrl() throws PropertyLoaderException {
        init();
        return properties.getProperty("database.url");
    }
}
