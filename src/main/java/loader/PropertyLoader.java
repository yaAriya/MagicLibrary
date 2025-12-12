package loader;

import exceptions.PropertyLoaderException;

import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {
    private static final Properties properties = new Properties();
    private static boolean initialized;

    private static void init() throws PropertyLoaderException {
        try (InputStream input = PropertyLoader.class.getResourceAsStream("/Log4j2.properties")) {
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
