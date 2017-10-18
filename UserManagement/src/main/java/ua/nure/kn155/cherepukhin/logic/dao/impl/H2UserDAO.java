package ua.nure.kn155.cherepukhin.logic.dao.impl;

import java.sql.Connection;
import java.util.List;
import ua.nure.kn155.cherepukhin.logic.bean.User;
import ua.nure.kn155.cherepukhin.logic.dao.AbstractDAO;
import ua.nure.kn155.cherepukhin.logic.dao.DatabaseException;

public class H2UserDAO extends AbstractDAO<Long, User>{

  public H2UserDAO(Connection connection) {
    super(connection);
  }

  @Override
  public List<User> getAll() throws DatabaseException {
    return null;
  }

  @Override
  public User getById(Long id) throws DatabaseException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public User create(User entity) throws DatabaseException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean update(User entity) throws DatabaseException {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean delete(User entity) throws DatabaseException {
    // TODO Auto-generated method stub
    return false;
  }




}
