package ua.nure.kn155.cherepukhin.gui.panel;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

import ua.nure.kn155.cherepukhin.gui.MainFrame;
import ua.nure.kn155.cherepukhin.logic.bean.User;

public class DetailsPanel extends AddPanel {

  //JTextField&Label
  public static final String ID_FIELD_NAME = "idField";
  public static final String ID_LABEL = "Id";
  
  //Panel
  public static final String DETAILS_PANEL_NAME = "detailsPanel";
  
  
  private JTextField idField;

  public DetailsPanel(MainFrame parent) {
    super(parent);
    this.setName(DETAILS_PANEL_NAME);
    getOkButton().setText("Ok");
    getDateBirthField().setEditable(false);
    getFirstNameField().setEditable(false);
    getLastNameField().setEditable(false);
    getIdField().setEditable(false);
  }

  @Override
  public JPanel getFieldPanel() {
    if (super.fieldPanel == null) {
      super.fieldPanel = new JPanel();
      super.fieldPanel.setLayout(new GridLayout(4, 2));
      addLabeledField(super.fieldPanel, ID_LABEL, this.getIdField());
      addLabeledField(super.fieldPanel, AddPanel.FIRST_NAME_LABEL, super.getFirstNameField());
      addLabeledField(super.fieldPanel, AddPanel.LAST_NAME_LABEL, super.getLastNameField());
      addLabeledField(super.fieldPanel, AddPanel.BIRTH_DATE_LABEL, super.getDateBirthField());
    }
    return fieldPanel;
  }

  public JTextField getIdField() {
    if (idField == null) {
      idField = new JTextField();
      idField.setName(ID_FIELD_NAME);
    }
    return idField;
  }

  @Override
  public JPanel getButtonPanel() {
    if (buttonPanel == null) {
      buttonPanel = new JPanel();
      buttonPanel.add(getOkButton());
    }
    return buttonPanel;
  }
  
  public void setEditData(User user) {
    this.getIdField().setText(user.getId().toString());
    super.getFirstNameField().setText(user.getFirstName());
    super.getLastNameField().setText(user.getLastName());
    super.getDateBirthField().setText(user.getDateBirth().toString()); 
   }
}
