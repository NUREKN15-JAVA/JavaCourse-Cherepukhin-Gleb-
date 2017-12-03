package ua.nure.kn155.cherepukhin.logic.ui;

import java.awt.Component;
import java.awt.Container;
import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.eventdata.JTableMouseEventData;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.AbstractButtonFinder;
import junit.extensions.jfcunit.finder.DialogFinder;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import ua.nure.kn155.cherepukhin.db.DatabaseException;
import ua.nure.kn155.cherepukhin.gui.MainFrame;
import ua.nure.kn155.cherepukhin.gui.UserTableModel;
import ua.nure.kn155.cherepukhin.gui.panel.AddPanel;
import ua.nure.kn155.cherepukhin.gui.panel.BrowsePanel;
import ua.nure.kn155.cherepukhin.gui.panel.DetailsPanel;
import ua.nure.kn155.cherepukhin.gui.panel.EditPanel;
import ua.nure.kn155.cherepukhin.logic.bean.User;
import ua.nure.kn155.cherepukhin.logic.dao.DAOFactory2;
import ua.nure.kn155.cherepukhin.logic.dao.mock.MockDAOFactory;
import ua.nure.kn155.cherepukhin.logic.dao.mock.MockUserDAO;

public class MainFrameTest extends JFCTestCase {

  private final static Logger LOG = LogManager.getLogger(MainFrameTest.class);
  private static final String EMPTY_FIELD_VALUE = "";
  private Container mainFrame;
  private MockUserDAO mockUserDAO;
  private List<User> usersList;
  private final static long NO_MATTER_ID = 999L;
  private final static int TABLE_INITIAL_LENGTH = 1;
  private static final int TABLE_COLUMN_NUMBER = 3;
  private final User theOnlyUser = new User(NO_MATTER_ID, "Ramzess", "VI", new Date());

  private final static String YES_BUTTON_DIALOG = "Yes";
  private final static String DIALOG_TITLE = "Confirm";

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    try {
      Properties properties = new Properties();
      properties.setProperty("dao.factory", MockDAOFactory.class.getName());
      properties.setProperty("user.dao", MockUserDAO.class.getName());
      usersList = Collections.singletonList(theOnlyUser);
      try{
      DAOFactory2.init(properties);
      } catch(Throwable t){LOG.catching(t);}
      mockUserDAO = (MockUserDAO) DAOFactory2.getInstance().getUserDAO();
      mockUserDAO.setUpValues(usersList);

      setHelper(new JFCTestHelper());
      mainFrame = new MainFrame(DAOFactory2.getInstance(),null);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    mainFrame.setVisible(true);
  }

  @Override
  protected void tearDown() throws Exception {
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

  @Test
  public void testBrowseControl() {
    find(JPanel.class, BrowsePanel.BROWSE_PANEL_NAME);
    JTable table = (JTable) find(JTable.class, BrowsePanel.USER_TABLE_NAME);
    assertEquals(TABLE_COLUMN_NUMBER, table.getColumnCount());
    assertEquals(BrowsePanel.ID_COLUMN, table.getColumnName(0));
    assertEquals(BrowsePanel.FIRST_NAME_COLUMN, table.getColumnName(1));
    assertEquals(BrowsePanel.LAST_NAME_COLUMN, table.getColumnName(2));
    assertEquals(TABLE_INITIAL_LENGTH, table.getRowCount());
    find(JButton.class, BrowsePanel.ADD_BUTTON_NAME);
    find(JButton.class, BrowsePanel.EDIT_BUTTON_NAME);
    find(JButton.class, BrowsePanel.DELETE_BUTTON_NAME);
    find(JButton.class, BrowsePanel.DETAILS_BUTTON_NAME);
  }

  @Test
  public void testAddUser() throws DatabaseException, ParseException {
    Date date = MainFrame.DATE_FORMAT.parse(MainFrame.DATE_FORMAT.format(new Date()));
    String firstName = "John";
    String lastName = "Doe";

    User expectedUser = new User(1L, firstName, lastName, date);

    JButton addButton = (JButton) find(JButton.class, BrowsePanel.ADD_BUTTON_NAME);
    JTable table = (JTable) find(JTable.class, BrowsePanel.USER_TABLE_NAME);
    assertEquals(1, table.getRowCount());

    getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

    find(JPanel.class, AddPanel.ADD_PANEL_NAME);
    find(JButton.class, AddPanel.OK_BUTTON_NAME);
    find(JButton.class, AddPanel.CANCEL_BUTTON_NAME);
    fillInputField(firstName, lastName, date);

    JButton okButton = (JButton) find(JButton.class, AddPanel.OK_BUTTON_NAME);
    getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

    find(JPanel.class, BrowsePanel.BROWSE_PANEL_NAME);

    table = (JTable) find(JTable.class, BrowsePanel.USER_TABLE_NAME);
    assertEquals(2, table.getRowCount());
    assertTrue(((UserTableModel) table.getModel()).getListOfUsers().contains(expectedUser));
  }

  @Test
  public void testCancelAddUser() throws DatabaseException, ParseException {
    Date date = MainFrame.DATE_FORMAT.parse(MainFrame.DATE_FORMAT.format(new Date()));
    String firstName = "John";
    String lastName = "Doe";

    User expectedUser = new User(1L, firstName, lastName, date);

    JButton addButton = (JButton) find(JButton.class, BrowsePanel.ADD_BUTTON_NAME);
    JTable table = (JTable) find(JTable.class, BrowsePanel.USER_TABLE_NAME);
    assertEquals(1, table.getRowCount());

    getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

    find(JPanel.class, AddPanel.ADD_PANEL_NAME);
    find(JButton.class, AddPanel.OK_BUTTON_NAME);
    find(JButton.class, AddPanel.CANCEL_BUTTON_NAME);
    fillInputField(firstName, lastName, date);

    JButton cancelButton = (JButton) find(JButton.class, AddPanel.CANCEL_BUTTON_NAME);
    getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));

    find(JPanel.class, BrowsePanel.BROWSE_PANEL_NAME);

    table = (JTable) find(JTable.class, BrowsePanel.USER_TABLE_NAME);
    assertEquals(1, table.getRowCount());
    assertFalse(((UserTableModel) table.getModel()).getListOfUsers().contains(expectedUser));
  }

  @Test
  public void testDeleteUser() {
    JTable table = (JTable) find(JTable.class, BrowsePanel.USER_TABLE_NAME);
    getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
    int selectedRow = table.getSelectedRow();
    assertTrue(selectedRow != -1);

    JButton deleteButton = (JButton) find(JButton.class, BrowsePanel.DELETE_BUTTON_NAME);
    getHelper().enterClickAndLeave(new MouseEventData(this, deleteButton));
    modalDialogButtonClick(DIALOG_TITLE, YES_BUTTON_DIALOG);

    table = (JTable) find(JTable.class, BrowsePanel.USER_TABLE_NAME);
    assertEquals(0, table.getRowCount());
  }

  @Test
  public void testUpdateUser() throws ParseException {
    JTable table = (JTable) find(JTable.class, BrowsePanel.USER_TABLE_NAME);
    BrowsePanel browsePanel = (BrowsePanel) find(BrowsePanel.class, BrowsePanel.BROWSE_PANEL_NAME);
    getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
    int selectedRow = table.getSelectedRow();
    assertTrue(selectedRow != -1);
    assertEquals(theOnlyUser, browsePanel.getSelectedUser());

    JButton editButton = (JButton) find(JButton.class, BrowsePanel.EDIT_BUTTON_NAME);
    getHelper().enterClickAndLeave(new MouseEventData(this, editButton));

    find(JPanel.class, EditPanel.EDIT_PANEL_NAME);
    find(JButton.class, EditPanel.OK_BUTTON_NAME);
    find(JButton.class, EditPanel.CANCEL_BUTTON_NAME);

    EditPanel editPanel = (EditPanel) find(JPanel.class, EditPanel.EDIT_PANEL_NAME);
    assertEquals(NO_MATTER_ID, editPanel.getId().longValue());

    User expectedUser = new User(NO_MATTER_ID, "Amenhotep", "II",
        MainFrame.DATE_FORMAT.parse(MainFrame.DATE_FORMAT.format(new Date())));
    fillInputField(expectedUser.getFirstName(), expectedUser.getLastName(),
        expectedUser.getDateBirth());
    JButton okButton = (JButton) find(JButton.class, EditPanel.OK_BUTTON_NAME);
    getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

    table = (JTable) find(JTable.class, BrowsePanel.USER_TABLE_NAME);
    assertEquals(1, table.getRowCount());
    assertTrue(((UserTableModel) table.getModel()).getListOfUsers().contains(expectedUser));
  }

  @Test
  public void testCancelOnUpdateUser() throws ParseException {
    JTable table = (JTable) find(JTable.class, BrowsePanel.USER_TABLE_NAME);
    BrowsePanel browsePanel = (BrowsePanel) find(BrowsePanel.class, BrowsePanel.BROWSE_PANEL_NAME);
    getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
    int selectedRow = table.getSelectedRow();
    assertTrue(selectedRow != -1);
    assertEquals(theOnlyUser, browsePanel.getSelectedUser());

    JButton editButton = (JButton) find(JButton.class, BrowsePanel.EDIT_BUTTON_NAME);
    getHelper().enterClickAndLeave(new MouseEventData(this, editButton));

    find(JPanel.class, EditPanel.EDIT_PANEL_NAME);
    find(JButton.class, EditPanel.OK_BUTTON_NAME);
    find(JButton.class, EditPanel.CANCEL_BUTTON_NAME);

    EditPanel editPanel = (EditPanel) find(JPanel.class, EditPanel.EDIT_PANEL_NAME);
    assertEquals(NO_MATTER_ID, editPanel.getId().longValue());

    User expectedUser = new User(NO_MATTER_ID, "Amenhotep", "II",
        MainFrame.DATE_FORMAT.parse(MainFrame.DATE_FORMAT.format(new Date())));
    fillInputField(expectedUser.getFirstName(), expectedUser.getLastName(),
        expectedUser.getDateBirth());
    JButton cancelButton = (JButton) find(JButton.class, EditPanel.CANCEL_BUTTON_NAME);
    getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));

    table = (JTable) find(JTable.class, BrowsePanel.USER_TABLE_NAME);
    assertEquals(1, table.getRowCount());
    assertFalse(((UserTableModel) table.getModel()).getListOfUsers().contains(expectedUser));
  }

  @Test
  public void testDetailsofUser() {
    JTable table = (JTable) find(JTable.class, BrowsePanel.USER_TABLE_NAME);
    BrowsePanel browsePanel = (BrowsePanel) find(BrowsePanel.class, BrowsePanel.BROWSE_PANEL_NAME);
    getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
    int selectedRow = table.getSelectedRow();
    assertTrue(selectedRow != -1);
    assertEquals(theOnlyUser, browsePanel.getSelectedUser());

    JButton detailsButton = (JButton) find(JButton.class, BrowsePanel.DETAILS_BUTTON_NAME);
    getHelper().enterClickAndLeave(new MouseEventData(this, detailsButton));

    find(DetailsPanel.class, DetailsPanel.DETAILS_PANEL_NAME);
    JTextField idField = (JTextField) find(JTextField.class, DetailsPanel.ID_FIELD_NAME);
    JTextField firstNameField =
        (JTextField) find(JTextField.class, DetailsPanel.FIRST_NAME_FIELD_NAME);
    JTextField lastNameField =
        (JTextField) find(JTextField.class, DetailsPanel.LAST_NAME_FIELD_NAME);
    JTextField birthDateField =
        (JTextField) find(JTextField.class, DetailsPanel.DATE_BIRTH_FIELD_NAME);

    assertEquals(theOnlyUser.getId().toString(), idField.getText());
    assertEquals(theOnlyUser.getFirstName().toString(), firstNameField.getText());
    assertEquals(theOnlyUser.getLastName().toString(), lastNameField.getText());
    assertEquals(theOnlyUser.getDateBirth().toString(), birthDateField.getText());
  }

  private void fillInputField(String firstName, String lastName, Date date) {
    JTextField firstNameField = (JTextField) find(JTextField.class, AddPanel.FIRST_NAME_FIELD_NAME);
    JTextField lastNameField = (JTextField) find(JTextField.class, AddPanel.LAST_NAME_FIELD_NAME);
    JTextField dateBirtheField =
        (JTextField) find(JTextField.class, AddPanel.DATE_BIRTH_FIELD_NAME);

    firstNameField.setText(EMPTY_FIELD_VALUE);
    lastNameField.setText(EMPTY_FIELD_VALUE);
    dateBirtheField.setText(EMPTY_FIELD_VALUE);

    getHelper().sendString(new StringEventData(this, firstNameField, firstName));
    getHelper().sendString(new StringEventData(this, lastNameField, lastName));
    String stringDate = MainFrame.DATE_FORMAT.format(date);
    getHelper().sendString(new StringEventData(this, dateBirtheField, stringDate));
  }

  private void modalDialogButtonClick(String title, String button) {
    Component dialog = new DialogFinder(title).find();
    AbstractButtonFinder buttonFinder = new AbstractButtonFinder(button);
    List<JButton> buttonsOfDialog = buttonFinder.findAll((JDialog) dialog);
    assertEquals(1, buttonsOfDialog.size());
    JButton btn = (JButton) buttonsOfDialog.get(0);
    getHelper().enterClickAndLeave(new MouseEventData(this, btn));
  }
}
