package ua.nure.kn155.cherepukhin.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ua.nure.kn155.cherepukhin.gui.panel.BrowsePanel;
import ua.nure.kn155.cherepukhin.logic.bean.User;

public class UserTableModel extends AbstractTableModel {


  private List<User> listOfUsers;
  private final String[] COLUMN_NAMES =
      {BrowsePanel.ID_COLUMN, BrowsePanel.FIRST_NAME_COLUMN, BrowsePanel.LAST_NAME_COLUMN};
  private Class<?>[] COLUMN_CLASSES = {Long.class, String.class, String.class};


  public UserTableModel(List<User> listOfUsers) {
    this.listOfUsers = new ArrayList<>(listOfUsers);
  }

  @Override
  public int getColumnCount() {
    return COLUMN_NAMES.length;
  }

  @Override
  public int getRowCount() {
    return listOfUsers.size();
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    User user = (User) listOfUsers.get(rowIndex);
    switch (columnIndex) {
      case 0:
        return user.getId();
      case 1:
        return user.getFirstName();
      case 2:
        return user.getLastName();
    }
    return null;
  }

  @Override
  public Class<?> getColumnClass(int columnIndex) {
    return COLUMN_CLASSES[columnIndex];
  }

  @Override
  public String getColumnName(int columnIndex) {
    return COLUMN_NAMES[columnIndex];
  }

  public User getUser(final int selectedRow) {
    return listOfUsers.get(selectedRow);
  }

  public List<User> getListOfUsers() {
    return listOfUsers;
  }
  
}
