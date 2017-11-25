package ua.nure.kn155.cherepukhin.logic.web.servlet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ua.nure.kn155.cherepukhin.db.DatabaseException;
import ua.nure.kn155.cherepukhin.logic.bean.User;
import ua.nure.kn155.cherepukhin.web.servlet.AbstractUserManager;
import ua.nure.kn155.cherepukhin.web.servlet.DetailsServlet;

public class DetailsServletTest extends ServletTestCase<DetailsServlet> {

  @BeforeClass
  public static void init() {
    ServletTestCase.init();
  }
  
  @Before
  public void setUp() throws Exception {
    super.setUp(DetailsServlet.class);
  }

  @Test
  @Override
  public void testGet() throws Throwable {
    User fetchedUser = expectedUsers.get(0);
    request.addParameter(AbstractUserManager.ID_ATTRIBUTE, fetchedUser.getId().toString());
    mockUserDAO.expectAndReturn("getById", fetchedUser.getId(), fetchedUser);
    
    servletInstance.doGet(request, response);
    
    assertEquals(fetchedUser,request.getAttribute("user"));
  }
  
  @Test
  public void testGetOnDataBaseException() throws Throwable {
    User fetchedUser = expectedUsers.get(0);
    mockUserDAO.expectAndThrow("getById", fetchedUser.getId(), new DatabaseException());
    request.addParameter(AbstractUserManager.ID_ATTRIBUTE, fetchedUser.getId().toString());

    servletInstance.doGet(request, response);

    Object errorAttribute = request.getAttribute(AbstractUserManager.ERROR_ATTRIBUTE);
    assertNotNull(errorAttribute);
    assertEquals(AbstractUserManager.ERROR_SQL, errorAttribute);
  }
  
  @Test
  public void testGetOnNumberFormatExceptino() throws Throwable {
    request.addParameter(AbstractUserManager.ID_ATTRIBUTE, "unparsable");

    servletInstance.doGet(request, response);

    Object errorAttribute = request.getAttribute(AbstractUserManager.ERROR_ATTRIBUTE);
    assertNotNull(errorAttribute);
    assertEquals(AbstractUserManager.ERROR_INCORRECT_TYPE, errorAttribute);
  }

  @After
  public void tearDown() {
    super.tearDown();
  }

}
