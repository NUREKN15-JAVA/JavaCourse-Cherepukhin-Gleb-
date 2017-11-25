package ua.nure.kn155.cherepukhin.logic.dao;

import java.io.IOException;
import java.util.Properties;

import ua.nure.kn155.cherepukhin.db.ConnectionManager2;
import ua.nure.kn155.cherepukhin.db.IConnectionManager;

public class DAOFactory2 {

  public static final String EMBEDDED_PROPERTIES ="config.properties";
  public static final String DAO_FACTORY_KEY = "dao.factory";
  private final static String userDAOimplKey = "user.dao";
  private static Properties properties;
  private static DAOFactory2 INSTANCE;
  protected UserDAO userDAO;

  public DAOFactory2() {}

  static {
    try {
      properties = new Properties();
      properties.load(
          Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static synchronized DAOFactory2 getInstance() {
    if (INSTANCE == null) {
      try {
        String className = properties.getProperty(DAO_FACTORY_KEY);
        if (className == null) {
          INSTANCE = new DAOFactory2();
        } else {
          System.out.println("4)");
          Class<?> factoryImplClass = Class.forName(className);
          INSTANCE = (DAOFactory2)factoryImplClass.newInstance();
        }
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

  public synchronized UserDAO getUserDAO() {
    try {
      if (userDAO == null) {
        Class<?> daoImplClass = Class.forName(properties.getProperty(userDAOimplKey));
        userDAO = (UserDAO) daoImplClass.newInstance();
        userDAO.setConnectionManager(getConnectionManager());
      }
      return userDAO;
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }
  
  public void close() throws Exception {
    userDAO.getConnectionManager().close();
  }
}
