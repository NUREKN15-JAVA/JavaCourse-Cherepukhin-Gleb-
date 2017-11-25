package ua.nure.kn155.cherepukhin.logic.web.servlet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ua.nure.kn155.cherepukhin.db.DatabaseException;
import ua.nure.kn155.cherepukhin.logic.bean.User;
import ua.nure.kn155.cherepukhin.web.servlet.AbstractUserManager;
import ua.nure.kn155.cherepukhin.web.servlet.BrowseServlet;

public class BrowseServleTest extends ServletTestCase<BrowseServlet> {

  @BeforeClass
  public static void init() {
    ServletTestCase.init();
  }

  @Before
  public void setUp() throws Exception {
    super.setUp(BrowseServlet.class);
  }

  @Test
  @Override
  public void testGet() throws Exception {
    mockUserDAO.expectAndReturn("getAll", expectedUsers);

    servletInstance.doGet(request, response);

    List<User> actualUsers = (List<User>) request.getAttribute("users");
    assertEquals(expectedUsers, actualUsers);
  }

  @Test
  public void testBrowseOnDatabaseException() throws Exception {
    mockUserDAO.expectAndThrow("getAll", new DatabaseException());

    servletInstance.doGet(request, response);

    Object errorAttribute = request.getAttribute(AbstractUserManager.ERROR_ATTRIBUTE);
    assertNotNull(errorAttribute);
    assertEquals(AbstractUserManager.ERROR_SQL, errorAttribute);
  }

  @After
  public void tearDown() {
    super.tearDown();
  }

}
