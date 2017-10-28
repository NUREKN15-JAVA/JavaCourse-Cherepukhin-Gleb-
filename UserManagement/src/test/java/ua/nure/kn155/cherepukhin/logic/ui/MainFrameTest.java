package ua.nure.kn155.cherepukhin.logic.ui;

import java.awt.Component;
import java.awt.Container;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import com.mockobjects.dynamic.Mock;
import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import ua.nure.kn155.cherepukhin.logic.bean.User;
import ua.nure.kn155.cherepukhin.logic.dao.DAOFactory2;
import ua.nure.kn155.cherepukhin.logic.dao.mock.MockDAOFactory;

public class MainFrameTest extends JFCTestCase {

  private Container mainFrame;
  private Mock mockUserDAO;
  private List<User> usersList;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    try {
    Properties properties = new Properties();
    properties.setProperty("dao.factory", MockDAOFactory.class.getName());
    DAOFactory2.init(properties);
    // TODO Class for getuserDao is not needed
    mockUserDAO = ((MockDAOFactory) DAOFactory2.getInstance().getUserDAO(null)).getMockUserDAO();
    User expectedUser = new User(1000L, "Ramzess", "VI", new Date());
    usersList = Collections.singletonList(expectedUser);
    mockUserDAO.expectAndReturn("findAll", usersList);
    setHelper(new JFCTestHelper());
    mainFrame = new MainFrame();
    } catch(Exception ex) {
      ex.printStackTrace();
    }
    mainFrame.setVisible(true);
  }

  @Override
  protected void tearDown() throws Exception {
    mockUserDAO.verify();
    mainFrame.setVisible(false);
    JFCTestHelper.cleanUp(this);
    super.tearDown();
  }

  private Component find(Class<?> componentClass, String name) {
    NamedComponentFinder finder = new NamedComponentFinder(componentClass, name);
    finder.setWait(0);
    Component component = finder.find(mainFrame, 0);
    assertNotNull(component);
    return component;
  }

  public void testBrowseControl() {
    find(JPanel.class, "browsePanel");
    JTable table = (JTable) find(JTable.class, "userTable");
    assertEquals(3, table.getColumnCount());
    assertEquals("ID", table.getColumnName(0));
    assertEquals("First Name", table.getColumnName(1));
    assertEquals("Last Name", table.getColumnName(2));
    // TODO extract constants, assert that table has {number} of rows
    find(JButton.class, "addButton");
    find(JButton.class, "editButton");
    find(JButton.class, "deleteButton");
    find(JButton.class, "detailsButton");
  }

  public void testAddUser() {
    Date date = new Date();
    String firstName = "John";
    String lastName = "Doe";
    User user = new User();
    user.setDateBirth(date);
    user.setFirstName(firstName);
    user.setLastName(lastName);

    User expectedUser = new User(1L, firstName, lastName, date);
    mockUserDAO.expectAndReturn("create", user, expectedUser);

    ArrayList<User> users = new ArrayList<>(usersList);
    users.add(expectedUser);
    mockUserDAO.expectAndReturn("findAll", users);

    JTable table = (JTable) find(JTable.class, "usertable");
    assertEquals(1, table.getRowCount());
    JButton addButton = (JButton) find(JButton.class, "addButton");
    getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

    find(JPanel.class, "addPanel");
    fillInputField(firstName, lastName, date);
    find(JButton.class, "cancelButton");
    find(JButton.class, "okButton");
    JButton okButton = (JButton) find(JButton.class, "addButton");
    getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

    find(JPanel.class, "browsePanel");
    // May be duplicating
    table = (JTable) find(JTable.class, "usertable");
    // TODO assert that table has {number} of rows
    mockUserDAO.verify();
  }

  private void fillInputField(String firstName, String lastName, Date date) {
    JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
    JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
    JTextField dateBirtheField = (JTextField) find(JTextField.class, "dateBirthField");

    getHelper().sendString(new StringEventData(this, firstNameField, firstName));
    getHelper().sendString(new StringEventData(this, lastNameField, lastName));

    String stringDate = DateFormat.getInstance().format(date);
    getHelper().sendString(new StringEventData(this, dateBirtheField, stringDate));
  }
}
