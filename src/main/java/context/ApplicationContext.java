package context;

import exceptions.ApplicationContextException;

public interface ApplicationContext {
    void initializeContext() throws ApplicationContextException;

    void register(String name, Object object);

    Object getBean(String objectName);
}
