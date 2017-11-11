package ua.nure.kn155.cherepukhin.gui.panel;

import java.awt.event.ActionEvent;
import java.text.ParseException;

import javax.swing.JOptionPane;

import ua.nure.kn155.cherepukhin.db.DatabaseException;
import ua.nure.kn155.cherepukhin.gui.MainFrame;
import ua.nure.kn155.cherepukhin.logic.bean.User;

public class EditPanel extends AddPanel{

  public static final String EDIT_PANEL_NAME = "editPanel";
  private Long id;
  
  public EditPanel(MainFrame parent) {
    super(parent);
    super.initialize();
    this.setName(EDIT_PANEL_NAME);
    super.getOkButton().setText("Update");
  }
  
  @Override
  public void actionPerformed(ActionEvent event) {
    if(event.getActionCommand().equals("ok")) {
      User user = new User();
      user.setId(id);
      user.setFirstName(getFirstNameField().getText());
      user.setLastName(getLastNameField().getText());
      try {
        user.setDateBirth(parent.DATE_FORMAT.parse(getDateBirthField().getText()));
        parent.getUserDAO().update(user);
      } catch (ParseException | DatabaseException exception) {
        JOptionPane.showMessageDialog(this, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        return;
      }    
    }
    parent.showBrowsePanel();
    parent.validate();
  }
  
  public void setEditData(User user) {
   this.id = user.getId();
   super.getFirstNameField().setText(user.getFirstName());
   super.getLastNameField().setText(user.getLastName());
   super.getDateBirthField().setText(user.getDateBirth().toString());
  }

  public Long getId() {
    return id;
  }
}
