package ua.nure.kn155.cherepukhin.logic.web.servlet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mockobjects.dynamic.AnyConstraintMatcher;

import ua.nure.kn155.cherepukhin.db.DatabaseException;
import ua.nure.kn155.cherepukhin.gui.MainFrame;
import ua.nure.kn155.cherepukhin.logic.bean.User;
import ua.nure.kn155.cherepukhin.web.servlet.AbstractUserManager;
import ua.nure.kn155.cherepukhin.web.servlet.AddServlet;

public class AddServletTest extends ServletTestCase<AddServlet> {

  @BeforeClass
  public static void init() {
    ServletTestCase.init();
  }

  @Before
  public void setUp() throws Exception {
    super.setUp(AddServlet.class);
  }

  @Test
  @Override
  public void testPost() throws Throwable {
    User addedUser = new User();
    addedUser.setId(2L);
    addedUser.setFirstName("John");
    addedUser.setFirstName("Dillinger");
    addedUser.setDateBirth(MainFrame.DATE_FORMAT.parse("1934-07-09"));
    mockUserDAO.expectAndReturn("create", new AnyConstraintMatcher(), addedUser);
    request.addParameter("firstName", addedUser.getFirstName());
    request.addParameter("lastName", addedUser.getLastName());
    request.addParameter("dateBirth", MainFrame.DATE_FORMAT.format(addedUser.getDateBirth()));
    
    servletInstance.doPost(request, response);
  }

  @Test
  public void testPostOnDateParseException() throws Throwable {
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
    mockUserDAO.expectAndThrow("create", new AnyConstraintMatcher(), new DatabaseException());
    request.addParameter("firstName", addedUser.getFirstName());
    request.addParameter("lastName", addedUser.getLastName());
    request.addParameter("dateBirth", MainFrame.DATE_FORMAT.format(addedUser.getDateBirth()));
    
    servletInstance.doPost(request, response);
    
    Object errorAttribute = request.getAttribute(AbstractUserManager.ERROR_ATTRIBUTE);
    assertNotNull(errorAttribute);
    assertEquals(AbstractUserManager.ERROR_SQL, errorAttribute);
  }

  @After
  public void tearDown() {
    super.tearDown();
  }

}
