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
import ua.nure.kn155.cherepukhin.web.servlet.DeleteServlet;

public class DeleteServletTest extends ServletTestCase<DeleteServlet> {

  @BeforeClass
  public static void init() {
    ServletTestCase.init();
  }

  @Before
  public void setUp() throws Exception {
    super.setUp(DeleteServlet.class);
  }

  @Test
  @Override
  public void testGet() throws Throwable {
    Long deletedUserId = 2L;
    User user = new User();
    user.setId(deletedUserId);
    mockUserDAO.expectAndReturn("delete", user, true);
    request.addParameter(AbstractUserManager.ID_ATTRIBUTE, deletedUserId.toString());

    servletInstance.doGet(request, response);
  }

  @Test
  public void testGetOnDataBaseException() throws Throwable {
    Long deletedUserId = 2L;
    User user = new User();
    user.setId(deletedUserId);
    mockUserDAO.expectAndThrow("delete", user, new DatabaseException());
    request.addParameter(AbstractUserManager.ID_ATTRIBUTE, deletedUserId.toString());

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
