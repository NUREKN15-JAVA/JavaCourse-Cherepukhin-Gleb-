package ua.nure.kn155.cherepukhin.logic.dao.mock;

import com.mockobjects.dynamic.Mock;
import ua.nure.kn155.cherepukhin.logic.dao.DAOFactory2;
import ua.nure.kn155.cherepukhin.logic.dao.UserDAO;

public class MockDAOFactory extends DAOFactory2 {

  private Mock mockUserDAO;

  public MockDAOFactory() {
    mockUserDAO = new Mock(UserDAO.class);
  }

  public Mock getMockUserDAO() {
    return mockUserDAO;
  }
  
  @Override
  public synchronized UserDAO getUserDAO() {
    if (userDAO == null) {
      userDAO = new MockUserDAO();
    }
    return userDAO;
  }
}
