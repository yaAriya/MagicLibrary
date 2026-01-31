package context;

public interface ApplicationContext {
    void initializeContext();

    void register(Object object);

    Object getBeans(String objectName);
}
