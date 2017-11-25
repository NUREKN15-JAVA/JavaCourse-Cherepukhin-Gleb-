package ua.nure.kn155.cherepukhin.web.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.kn155.cherepukhin.logic.dao.DAOFactory2;
import ua.nure.kn155.cherepukhin.logic.dao.UserDAO;

public class AbstractUserManager extends HttpServlet {

  public static final String ADD_PAGE_URI = "/WEB-INF/jsp/add.jsp";
  public static final String DETAILS_PAGE_URI = "/WEB-INF/jsp/details.jsp";
  public static final String EDIT_PAGE_URI = "/WEB-INF/jsp/edit.jsp";
  public static final String BROWSE_PAGE_URI = "/WEB-INF/jsp/browse.jsp";
  public static final String BROWSE_ACTION = "browse";

  public static final String ERROR_ATTRIBUTE = "error";
  public static final String ERROR_SQL = "Something wrong with DB!";
  public static final String ERROR_INCORRECT_DATE = "You have entered incorrect date!";
  public static final String ERROR_INCORRECT_TYPE =
      "Incorrect type of id or smth else, must be number!";

  public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
  protected static final String USERS_ATTRIBUTE = "users";
  protected static final String USER_ATTRIBUTE = "user";
  public static final String ID_ATTRIBUTE = "id";
  protected static final Logger LOG = LogManager.getLogger(AbstractUserManager.class);
  protected UserDAO userDAO;

  @Override
  public void init() throws ServletException {
    userDAO = DAOFactory2.getInstance().getUserDAO();
  }

  public void setUserDAO(UserDAO userDAO) {
    this.userDAO = userDAO;
  }

  @Override
  public void destroy() {
    try {
      DAOFactory2.getInstance().close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    throw new UnsupportedOperationException();
  }

}
