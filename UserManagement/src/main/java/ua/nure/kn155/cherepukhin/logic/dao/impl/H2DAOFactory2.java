package ua.nure.kn155.cherepukhin.logic.dao.impl;

import ua.nure.kn155.cherepukhin.logic.dao.DAOFactory2;
import ua.nure.kn155.cherepukhin.logic.dao.UserDAO;

public class H2DAOFactory2 extends DAOFactory2{

  private UserDAO userDAO;
  
  @Override
  public UserDAO getUserDAO(Class<?> daoImplClass) {
    try {
      if (userDAO == null) {
        this.userDAO = (UserDAO) daoImplClass.newInstance();
        userDAO.setConnectionManager(getConnectionManager());
      }
      return userDAO;
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }
}
