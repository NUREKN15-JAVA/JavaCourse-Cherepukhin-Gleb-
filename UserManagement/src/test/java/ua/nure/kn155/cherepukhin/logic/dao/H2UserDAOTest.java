package ua.nure.kn155.cherepukhin.logic.dao;

import java.util.Date;
import ua.nure.kn155.cherepukhin.logic.bean.User;
import ua.nure.kn155.cherepukhin.logic.dao.config.DBUnitConfig;
import ua.nure.kn155.cherepukhin.logic.dao.impl.H2UserDAO;

public class H2UserDAOTest extends DBUnitConfig {

  public H2UserDAO userDAO;

  @Override
  protected void setUp() throws Exception {
    userDAO = new H2UserDAO(null);
  }

  @Override
  public void testCreate() {
    try {
      User user = new User();
      user.setFirstName("John");
      user.setLastName("Doe");
      user.setDateBirth(new Date());
      User createdUser = userDAO.create(user);
      assertNotNull(createdUser);
      assertNotNull(createdUser.getId());
    } catch (DatabaseException ex) {
      ex.printStackTrace();
      fail();
    }
  }

  @Override
  public void testDelete() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void testUpdate() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void testGetAll() {
    // TODO Auto-generated method stub
    
  }

}
