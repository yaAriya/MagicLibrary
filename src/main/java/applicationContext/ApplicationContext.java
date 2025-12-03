package applicationContext;

public interface ApplicationContext {
    void initializeContext();

    void register(Object object);
}
