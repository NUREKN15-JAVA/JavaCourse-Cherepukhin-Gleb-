package ua.nure.kn155.cherepukhin.web.servlet;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.kn155.cherepukhin.db.DatabaseException;
import ua.nure.kn155.cherepukhin.logic.bean.User;

@WebServlet("/edit")
public class EditServlet extends AbstractUserManager {

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      User populatedUser = userDAO.getById(Long.valueOf(req.getParameter(ID_ATTRIBUTE)));
      req.setAttribute(USER_ATTRIBUTE, populatedUser);
      req.getRequestDispatcher(EDIT_PAGE_URI).forward(req, resp);
      return;
    } catch (DatabaseException e) {
      LOG.catching(e);
      req.setAttribute(ERROR_ATTRIBUTE, ERROR_SQL);
    } catch (NumberFormatException e) {
      LOG.catching(e);
      req.setAttribute(ERROR_ATTRIBUTE, ERROR_INCORRECT_TYPE);
    }
    req.getRequestDispatcher(BROWSE_ACTION).forward(req, resp);
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    User updatedUser = new User();
    updatedUser.setId(Long.parseLong(req.getParameter("id")));
    updatedUser.setFirstName(req.getParameter("firstName"));
    updatedUser.setLastName(req.getParameter("lastName"));
    try {
      updatedUser.setDateBirth(DATE_FORMAT.parse((String) req.getParameter("dateBirth")));
      userDAO.update(updatedUser);
      resp.sendRedirect(BROWSE_ACTION);
      return;
    } catch (ParseException e) {
      LOG.catching(e);
      req.setAttribute(ERROR_ATTRIBUTE, ERROR_INCORRECT_DATE);
    } catch (DatabaseException e) {
      LOG.catching(e);
      req.setAttribute(ERROR_ATTRIBUTE, ERROR_SQL);
    }
    req.getRequestDispatcher(ADD_PAGE_URI).forward(req, resp);
  }

}
