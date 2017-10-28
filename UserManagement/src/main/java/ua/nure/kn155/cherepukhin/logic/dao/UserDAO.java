package ua.nure.kn155.cherepukhin.logic.dao;

import ua.nure.kn155.cherepukhin.db.IConnectionManager;
import ua.nure.kn155.cherepukhin.logic.bean.User;

public interface UserDAO extends AbstractDAO<Long, User>{
  
  void setConnectionManager(IConnectionManager connectionManager);
}
