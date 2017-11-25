package ua.nure.kn155.cherepukhin.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.kn155.cherepukhin.db.DatabaseException;
import ua.nure.kn155.cherepukhin.logic.bean.User;

@WebServlet("/delete")
public class DeleteServlet extends AbstractUserManager {

  

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    User deletedUser = new User();
    try {
      deletedUser.setId(Long.valueOf(req.getParameter(ID_ATTRIBUTE)));
      userDAO.delete(deletedUser);
    } catch (DatabaseException e) {
      LOG.catching(e);
      req.setAttribute(ERROR_ATTRIBUTE, ERROR_SQL);
    } catch(NumberFormatException e) {
      LOG.catching(e);
      req.setAttribute(ERROR_ATTRIBUTE, ERROR_INCORRECT_TYPE);
    }
    req.getRequestDispatcher(BROWSE_ACTION).forward(req, resp);;
  }

}
