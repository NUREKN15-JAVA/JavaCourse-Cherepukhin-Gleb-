package ua.nure.kn155.cherepukhin.web.servlet;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.kn155.cherepukhin.db.DatabaseException;
import ua.nure.kn155.cherepukhin.logic.bean.User;

@WebServlet("/add")
public class AddServlet extends AbstractUserManager {

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.getRequestDispatcher("WEB-INF/jsp/add.jsp").forward(req, resp);
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    User createdUser = new User();
    createdUser.setFirstName((String) req.getParameter("firstName"));
    createdUser.setLastName((String) req.getParameter("lastName"));
    try {
      createdUser.setDateBirth(DATE_FORMAT.parse((String) req.getParameter("dateBirth")));
      userDAO.create(createdUser);
      resp.sendRedirect(BROWSE_ACTION);
      return;
    } catch (ParseException e) {
      LOG.catching(e);
      req.setAttribute(ERROR_ATTRIBUTE, ERROR_INCORRECT_DATE); 
      return;
    } catch (DatabaseException e) {
      LOG.catching(e);
      req.setAttribute(ERROR_ATTRIBUTE, ERROR_SQL);
    }
    req.getRequestDispatcher(ADD_PAGE_URI).forward(req, resp);
  }

}
