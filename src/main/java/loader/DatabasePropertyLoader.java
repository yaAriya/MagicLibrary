package loader;

import exceptions.PropertyLoaderException;

import java.io.InputStream;
import java.util.Properties;

public class DatabasePropertyLoader {
    private static final String PROPERTY_FILE_PATH = "/databaseConfig.properties";
    private final Properties properties = new Properties();
    private static boolean initialized;

    static {

    }

    private void init() {
        try (InputStream input = DatabasePropertyLoader.class.getResourceAsStream(PROPERTY_FILE_PATH)) {
            if (!initialized && input != null) {
                properties.load(input);
                initialized = true;
            }
        } catch (Exception e) {
           throw new RuntimeException (e);
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
