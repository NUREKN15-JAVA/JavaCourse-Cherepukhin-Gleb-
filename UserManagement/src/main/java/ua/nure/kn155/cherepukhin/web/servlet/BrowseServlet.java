package ua.nure.kn155.cherepukhin.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.kn155.cherepukhin.logic.dao.DAOFactory2;

@WebServlet("/browse")
public class BrowseServlet extends AbstractUserManager {

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      req.setAttribute(USERS_ATTRIBUTE, userDAO.getAll());
    } catch(Throwable t) {
      req.setAttribute(ERROR_ATTRIBUTE, ERROR_SQL);
    }
    req.getRequestDispatcher(BROWSE_PAGE_URI).forward(req, resp);
  }
  
  @Override
  public void destroy() {
    LOG.info("DESTROYING SERVLET");
    try {
      DAOFactory2.getInstance().close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
