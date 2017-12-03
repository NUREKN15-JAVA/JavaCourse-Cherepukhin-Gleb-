package ua.nure.kn155.cherepukhin.gui.panel;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ua.nure.kn155.cherepukhin.db.DatabaseException;
import ua.nure.kn155.cherepukhin.gui.MainFrame;
import ua.nure.kn155.cherepukhin.gui.UserTableModel;
import ua.nure.kn155.cherepukhin.logic.bean.User;

public class BrowsePanel extends JPanel implements ActionListener {

  // Panel&Table
  public static final String USER_TABLE_NAME = "userTable";
  public static final String LAST_NAME_COLUMN = "Last name";
  public static final String FIRST_NAME_COLUMN = "First name";
  public static final String ID_COLUMN = "ID";

  public static final String BROWSE_PANEL_NAME = "browsePanel";

  // Buttons
  public static final String DETAILS_BUTTON_TEXT = "Details";
  public static final String DETAILS_BUTTON_COMMAND = "details";
  public static final String DETAILS_BUTTON_NAME = "detailsButton";

  public static final String DELETE_BUTTON_NAME = "deleteButton";
  public static final String DELETE_BUTON_COMMAND = "delete";
  public static final String DELETE_BUTTON_TEXT = "Delete";

  public static final String EDIT_BUTTON_COMMAND = "edit";
  public static final String EDIT_BUTTON_NAME = "editButton";
  public static final String EDIT_BUTTON_TEXT = "Edit";

  public static final String ADD_BUTTON_NAME = "addButton";
  public static final String ADD_BUTTON_COMMAND = "add";
  public static final String ADD_BUTTON_TEXT = "Add";

  public static final String SEARCH_BUTTON_NAME = "searchButton";
  public static final String SEARCH_BUTTON_COMMAND = "search";
  public static final String SEARCH_BUTTON_TEXT = "Search";

  private MainFrame parent;
  private JScrollPane tablePanel;
  private JPanel buttonPanel;
  private JTable userTable;

  private JButton addButon;
  private JButton editButton;
  private JButton deleteButton;
  private JButton detailsButton;
  private JButton searchButton;

  private User selectedUser;

  public User getSelectedUser() {
    return selectedUser;
  }

  private final JOptionPane confirmationDialog =
      new JOptionPane("Do you want to delete selected person?", JOptionPane.QUESTION_MESSAGE,
          JOptionPane.YES_NO_OPTION);

  private final ListSelectionListener tableSelectionListener = new ListSelectionListener() {

    private int iSelectedIndex = -1;

    @Override
    public void valueChanged(ListSelectionEvent e) {
      String strSource = e.getSource().toString();
      int start = strSource.indexOf("={"), stop = strSource.length() - 1;
      if (start != -1) {
        try {
          this.iSelectedIndex = Integer.parseInt(strSource.substring(start + 2, stop));
        } catch (NumberFormatException exception) {
          return;
        }
        UserTableModel tableModel =
            (UserTableModel) parent.getBrowsePanel().getUserTable().getModel();
        BrowsePanel.this.selectedUser = tableModel.getUser(iSelectedIndex);
        System.out.println("SELECTED USER: : " + BrowsePanel.this.selectedUser);
      }
    }

  };

  public BrowsePanel(MainFrame frame) {
    this.parent = frame;
    this.initialize();
    this.setName(BROWSE_PANEL_NAME);
  }

  public void initialize() {
    this.setLayout(new BorderLayout());
    this.add(getTablePanel(), BorderLayout.CENTER);
    this.add(getButtonPanel(), BorderLayout.SOUTH);
  }

  public Container getTablePanel() {
    if (tablePanel == null) {
      tablePanel = new JScrollPane(getUserTable());
    }
    return tablePanel;
  }

  public JTable getUserTable() {
    UserTableModel dataTableModel;
    if (userTable == null) {
      userTable = new JTable();
      userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      userTable.setName(USER_TABLE_NAME);
      try {
        dataTableModel = new UserTableModel(parent.getUserDAO().getAll());
      } catch (DatabaseException e) {
        dataTableModel = new UserTableModel(Collections.emptyList());
        JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
      userTable.getSelectionModel().addListSelectionListener(tableSelectionListener);
      userTable.setModel(dataTableModel);
    }
    return userTable;
  }

  public Container getButtonPanel() {
    if (buttonPanel == null) {
      buttonPanel = new JPanel();
      buttonPanel.add(getAddButon());
      buttonPanel.add(getEditButton());
      buttonPanel.add(getDeleteButton());
      buttonPanel.add(getDetailsButton());
      buttonPanel.add(getSearchButton());
    }
    return buttonPanel;
  }

  public JButton getAddButon() {
    if (addButon == null) {
      addButon = new JButton(ADD_BUTTON_TEXT);
      addButon.setActionCommand(ADD_BUTTON_COMMAND);
      addButon.setName(ADD_BUTTON_NAME);
      addButon.addActionListener(this);
    }
    return addButon;
  }

  public JButton getEditButton() {
    if (editButton == null) {
      editButton = new JButton(EDIT_BUTTON_TEXT);
      editButton.setName(EDIT_BUTTON_NAME);
      editButton.setActionCommand(EDIT_BUTTON_COMMAND);
      editButton.addActionListener(this);
    }
    return editButton;
  }

  public JButton getDeleteButton() {
    if (deleteButton == null) {
      deleteButton = new JButton(DELETE_BUTTON_TEXT);
      deleteButton.setName(DELETE_BUTTON_NAME);
      deleteButton.setActionCommand(DELETE_BUTON_COMMAND);
      deleteButton.addActionListener(this);
    }
    return deleteButton;
  }

  public JButton getSearchButton() {
    if (searchButton == null) {
      searchButton = new JButton(SEARCH_BUTTON_TEXT);
      searchButton.setName(SEARCH_BUTTON_NAME);
      searchButton.setActionCommand(SEARCH_BUTTON_COMMAND);
      searchButton.addActionListener(this);
    }
    return deleteButton;
  }

  public JButton getDetailsButton() {
    if (detailsButton == null) {
      detailsButton = new JButton(DETAILS_BUTTON_TEXT);
      detailsButton.setName(DETAILS_BUTTON_NAME);
      detailsButton.setActionCommand(DETAILS_BUTTON_COMMAND);
      detailsButton.addActionListener(this);
    }
    return detailsButton;
  }

  @Override
  public void actionPerformed(ActionEvent event) {
    String actionCommand = event.getActionCommand();
    if (actionCommand.equals(ADD_BUTTON_COMMAND)) {
      parent.showAddPanel();
    } else if (actionCommand.equals(SEARCH_BUTTON_COMMAND)) {
      parent.showSearchPanel();
    } else if (actionCommand.equals(EDIT_BUTTON_COMMAND)) {
      parent.showEditPanel(selectedUser);
    } else if (actionCommand.equals(DETAILS_BUTTON_COMMAND)) {
      parent.showDetailsPanel(selectedUser);
    } else if (actionCommand.equals(DELETE_BUTON_COMMAND)) {
      try {
        int answer = JOptionPane.showConfirmDialog(this, "Delete selected user?", "Confirmation",
            JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.YES_OPTION) {
          parent.getUserDAO().delete(selectedUser);
          parent.showBrowsePanel();
        } else {
          // do nothing
        }
      } catch (DatabaseException e) {
        e.printStackTrace();
      }
    }
    parent.validate();
  }

  public void reinitializeModel() {
    try {
      getUserTable().setModel(new UserTableModel(parent.getUserDAO().getAll()));
    } catch (DatabaseException e) {
      e.printStackTrace();
    }
  }

}
