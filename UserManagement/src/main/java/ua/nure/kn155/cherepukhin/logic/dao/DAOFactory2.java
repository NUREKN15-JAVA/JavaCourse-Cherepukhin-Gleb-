package ua.nure.kn155.cherepukhin.logic.dao;

import java.io.IOException;
import java.util.Properties;
import ua.nure.kn155.cherepukhin.db.ConnectionManager2;
import ua.nure.kn155.cherepukhin.db.IConnectionManager;

public abstract class DAOFactory2 {

  private final static String userDAOKey = "user.dao";
  private final static String DAOFactoryKey = "dao.factory";
  private static Properties properties;
  private static DAOFactory2 INSTANCE;
  
  public DAOFactory2() {}
  
  static {
    try {
      properties = new Properties();
      properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  public static DAOFactory2 getInstance() {
    if(INSTANCE == null) {
      try {
        Class<?> factoryClass = Class.forName(properties.getProperty(DAOFactoryKey));
        INSTANCE = (DAOFactory2)factoryClass.newInstance();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    return INSTANCE;
  }
  
  public static void init(Properties _properties) {
    properties = _properties;
    INSTANCE = null;
  }
  
  protected IConnectionManager getConnectionManager() {
    return new ConnectionManager2(properties);
  }
  
  public abstract UserDAO getUserDAO(Class<?> daoImplClass);
}
