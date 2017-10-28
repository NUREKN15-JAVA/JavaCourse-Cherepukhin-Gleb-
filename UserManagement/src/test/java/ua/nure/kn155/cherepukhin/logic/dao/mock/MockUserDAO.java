package ua.nure.kn155.cherepukhin.logic.dao.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ua.nure.kn155.cherepukhin.db.DatabaseException;
import ua.nure.kn155.cherepukhin.db.IConnectionManager;
import ua.nure.kn155.cherepukhin.logic.bean.User;
import ua.nure.kn155.cherepukhin.logic.dao.UserDAO;

public class MockUserDAO implements UserDAO {

  private Map<Long, User> users = new HashMap<>();
  private long id = 0;

  @Override
  public List<User> getAll() throws DatabaseException {
    return new ArrayList<User>(users.values());
  }

  @Override
  public User getById(Long id) throws DatabaseException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public User create(User entity) throws DatabaseException {
    Long currentId = new Long(++id);
    entity.setId(currentId);
    users.put(currentId, entity);
    return entity;
  }

  @Override
  public boolean update(User entity) throws DatabaseException {
    Long currentId = entity.getId();
    users.remove(currentId);
    users.put(currentId, entity);
    return true;
  }

  @Override
  public boolean delete(User entity) throws DatabaseException {
    users.remove(entity.getId());
    return true;
  }

  @Override
  public void setConnectionManager(IConnectionManager connectionManager) {
    // TODO Auto-generated method stub

  }
}
