package applicationContext;

import exceptions.ApplicationContextException;

public interface ApplicationContext {
    void initializeContext() throws ApplicationContextException;

    void register(Object object);

    Object getInstance(String className) throws ApplicationContextException;
}
