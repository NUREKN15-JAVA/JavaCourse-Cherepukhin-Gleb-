package ua.nure.kn155.cherepukhin.logic.dao.impl;

import ua.nure.kn155.cherepukhin.db.IConnectionManager;
import ua.nure.kn155.cherepukhin.logic.dao.DAOFactory;
import ua.nure.kn155.cherepukhin.logic.dao.UserDAO;

public class H2DAOFactory implements DAOFactory {

  private UserDAO userDAO;

  public H2DAOFactory(IConnectionManager connectionManager) {}

  @Override
  public UserDAO getUserDAO(Class<?> daoImplClass) {
    return null;
  }

  @Override
  public UserDAO getUserDAO() {
    return null;
  }
}
