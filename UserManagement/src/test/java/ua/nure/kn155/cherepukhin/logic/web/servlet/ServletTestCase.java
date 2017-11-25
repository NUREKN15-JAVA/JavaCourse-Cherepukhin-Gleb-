package ua.nure.kn155.cherepukhin.logic.web.servlet;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.mockobjects.dynamic.Mock;

import ua.nure.kn155.cherepukhin.logic.bean.User;
import ua.nure.kn155.cherepukhin.logic.dao.DAOFactory2;
import ua.nure.kn155.cherepukhin.logic.dao.UserDAO;
import ua.nure.kn155.cherepukhin.logic.dao.mock.MockDAOFactory;
import ua.nure.kn155.cherepukhin.web.servlet.AbstractUserManager;

public class ServletTestCase<S extends AbstractUserManager> {

  private Class<S> servletClass;
  protected AbstractUserManager servletInstance;
  protected MockHttpServletRequest request;
  protected MockHttpServletResponse response;
  private static MockDAOFactory mockDAOFactory;
  protected Mock mockUserDAO;
  protected List<User> expectedUsers;

  // @BeforeClass
  public static void init() {
    Properties props = new Properties();
    props.setProperty(DAOFactory2.DAO_FACTORY_KEY,
        "ua.nure.kn155.cherepukhin.logic.dao.mock.MockDAOFactory");
    DAOFactory2.init(props);
    mockDAOFactory = (MockDAOFactory) DAOFactory2.getInstance();
  }

  // @Before
  public void setUp(Class<S> servletClass) throws Exception {
    mockUserDAO = mockDAOFactory.getMockUserDAO();
    servletInstance = servletClass.newInstance();
    request = new MockHttpServletRequest();
    response = new MockHttpServletResponse();
    servletInstance.setUserDAO((UserDAO) mockUserDAO.proxy());
    // set up collection
    User user = new User();
    user.setId(1L);
    user.setFirstName("John");
    user.setLastName("Doe");
    user.setDateBirth(new Date());
    expectedUsers = Collections.singletonList(user);

  }

  public ServletTestCase() {}

  public void testGet() throws Throwable {}

  public void testPost() throws Throwable {}

  public void testPut() throws Throwable {}

  public void testDelete() throws Throwable {}

  // @After
  public void tearDown() {
    mockUserDAO.verify();
  }
}
