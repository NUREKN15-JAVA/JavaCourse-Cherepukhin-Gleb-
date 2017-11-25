package ua.nure.kn155.cherepukhin.logic.web.servlet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ua.nure.kn155.cherepukhin.db.DatabaseException;
import ua.nure.kn155.cherepukhin.gui.MainFrame;
import ua.nure.kn155.cherepukhin.logic.bean.User;
import ua.nure.kn155.cherepukhin.web.servlet.AbstractUserManager;
import ua.nure.kn155.cherepukhin.web.servlet.EditServlet;

public class EditServletTest extends ServletTestCase<EditServlet> {

  @BeforeClass
  public static void init() {
    ServletTestCase.init();
  }

  @Test
  @Override
  public void testGet() throws Throwable {
    User updatedUser = expectedUsers.get(0);
    request.addParameter(AbstractUserManager.ID_ATTRIBUTE, updatedUser.getId().toString());
    mockUserDAO.expectAndReturn("getById", updatedUser.getId(), updatedUser);

    servletInstance.doGet(request, response);

    assertEquals(updatedUser, request.getAttribute("user"));
  }

  @Test
  public void testPostOnDateParseException() throws Throwable {
    request.addParameter("id", Long.valueOf(2L).toString());
    request.addParameter("firstName", "John");
    request.addParameter("lastName", "Dillinger");
    request.addParameter("dateBirth", "unparsable");
    
    servletInstance.doPost(request, response);

    Object errorAttribute = request.getAttribute(AbstractUserManager.ERROR_ATTRIBUTE);
    assertNotNull(errorAttribute);
    assertEquals(AbstractUserManager.ERROR_INCORRECT_DATE, errorAttribute);
  }

  @Test
  public void testPostOnDataBaseException() throws Throwable {
    User addedUser = new User();
    addedUser.setId(2L);
    addedUser.setFirstName("John");
    addedUser.setFirstName("Dillinger");
    addedUser.setDateBirth(MainFrame.DATE_FORMAT.parse("1934-07-09"));
    mockUserDAO.expectAndThrow("update", addedUser, new DatabaseException());
    request.addParameter("id", addedUser.getId().toString());
    request.addParameter("firstName", addedUser.getFirstName());
    request.addParameter("lastName", addedUser.getLastName());
    request.addParameter("dateBirth", MainFrame.DATE_FORMAT.format(addedUser.getDateBirth()));
    
    servletInstance.doPost(request, response);

    Object errorAttribute = request.getAttribute(AbstractUserManager.ERROR_ATTRIBUTE);
    assertNotNull(errorAttribute);
    assertEquals(AbstractUserManager.ERROR_SQL, errorAttribute);
  }

  @Before
  public void setUp() throws Exception {
    super.setUp(EditServlet.class);
  }

  @After
  public void tearDown() {
    super.tearDown();
  }

}
