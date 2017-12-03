package ua.nure.kn155.cherepukhin.logic.dao;

import java.util.Collection;

import ua.nure.kn155.cherepukhin.db.DatabaseException;
import ua.nure.kn155.cherepukhin.db.IConnectionManager;
import ua.nure.kn155.cherepukhin.logic.bean.User;

public interface UserDAO extends AbstractDAO<Long, User>{
  
  void setConnectionManager(IConnectionManager connectionManager);

  IConnectionManager getConnectionManager();
  
  Collection<User> find(String firstNme,String lastName) throws DatabaseException;
}
