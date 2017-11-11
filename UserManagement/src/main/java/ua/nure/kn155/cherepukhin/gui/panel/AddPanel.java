package ua.nure.kn155.cherepukhin.gui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ua.nure.kn155.cherepukhin.db.DatabaseException;
import ua.nure.kn155.cherepukhin.gui.MainFrame;
import ua.nure.kn155.cherepukhin.logic.bean.User;

public class AddPanel extends JPanel implements ActionListener {

  // JTextFields
  public static final String DATE_BIRTH_FIELD_NAME = "dateBirthField";
  public static final String LAST_NAME_FIELD_NAME = "lastNameField";
  public static final String FIRST_NAME_FIELD_NAME = "firstNameField";

  // Labels for fields
  public static final String BIRTH_DATE_LABEL = "Birth date";
  public static final String LAST_NAME_LABEL = "Last name";
  public static final String FIRST_NAME_LABEL = "First name";

  // Buttons
  private static final String CANCEL_BUTTON_TEXT = "Cancel";
  public static final String CANCEL_BUTTON_COMMAND = "cancel";
  public static final String CANCEL_BUTTON_NAME = "cancelButton";

  private static final String OK_BUTTON_TEXT = "Add";
  public static final String OK_BUTTON_COMMAND = "ok";
  public static final String OK_BUTTON_NAME = "okButton";

  // Panel
  public static final String ADD_PANEL_NAME = "addPanel";

  // Other
  public final static String NO_TEXT = "";// :))

  protected MainFrame parent;
  protected JPanel fieldPanel;
  protected JPanel buttonPanel;
  private JButton okButton;
  private JButton cancelButton;

  private JTextField firstNameField;
  private JTextField lastNameField;
  private JTextField dateBirthField;

  public AddPanel(MainFrame parent) {
    this.parent = parent;
    this.setName(ADD_PANEL_NAME);
    this.initialize();
  }

  public void initialize() {
    this.setLayout(new BorderLayout());
    this.add(getFieldPanel(), BorderLayout.NORTH);
    this.add(getButtonPanel(), BorderLayout.SOUTH);
  }

  public JPanel getFieldPanel() {
    if (fieldPanel == null) {
      fieldPanel = new JPanel();
      fieldPanel.setLayout(new GridLayout(3, 2));
      addLabeledField(fieldPanel, FIRST_NAME_LABEL, getFirstNameField());
      addLabeledField(fieldPanel, LAST_NAME_LABEL, getLastNameField());
      addLabeledField(fieldPanel, BIRTH_DATE_LABEL, getDateBirthField());
    }
    return fieldPanel;
  }

  public JPanel getButtonPanel() {
    if (buttonPanel == null) {
      buttonPanel = new JPanel();
      buttonPanel.add(getOkButton());
      buttonPanel.add(getCancelButton());
    }
    return buttonPanel;
  }

  public JButton getOkButton() {
    if (okButton == null) {
      okButton = new JButton();
      okButton.setText(OK_BUTTON_TEXT);
      okButton.setName(OK_BUTTON_NAME);
      okButton.setActionCommand(OK_BUTTON_COMMAND);
      okButton.addActionListener(this);
    }
    return okButton;
  }

  public JButton getCancelButton() {
    if (cancelButton == null) {
      cancelButton = new JButton();
      cancelButton.setText(CANCEL_BUTTON_TEXT);
      cancelButton.setName(CANCEL_BUTTON_NAME);
      cancelButton.setActionCommand(CANCEL_BUTTON_COMMAND);
      cancelButton.addActionListener(this);
    }
    return cancelButton;
  }

  public JTextField getFirstNameField() {
    if (firstNameField == null) {
      firstNameField = new JTextField();
      firstNameField.setName(FIRST_NAME_FIELD_NAME);
    }
    return firstNameField;
  }

  public JTextField getLastNameField() {
    if (lastNameField == null) {
      lastNameField = new JTextField();
      lastNameField.setName(LAST_NAME_FIELD_NAME);
    }
    return lastNameField;
  }

  public JTextField getDateBirthField() {
    if (dateBirthField == null) {
      dateBirthField = new JTextField();
      dateBirthField.setName(DATE_BIRTH_FIELD_NAME);
    }
    return dateBirthField;
  }

  @Override
  public void actionPerformed(ActionEvent event) {
    if (event.getActionCommand().equals(CANCEL_BUTTON_COMMAND)) {
      // Nothing to do
    } else if (event.getActionCommand().equals(OK_BUTTON_COMMAND)) {
      User user = new User();
      user.setFirstName(getFirstNameField().getText());
      user.setLastName(getLastNameField().getText());
      try {
        user.setDateBirth(parent.DATE_FORMAT.parse(getDateBirthField().getText()));
        parent.getUserDAO().create(user);
      } catch (ParseException exception) {
        JOptionPane.showMessageDialog(this, exception.getMessage(), "Error",
            JOptionPane.ERROR_MESSAGE);
        getDateBirthField().setBackground(Color.RED);
        return;
      } catch (DatabaseException exception) {

      }
    }
    parent.showBrowsePanel();
    parent.validate();
  }

  public void addLabeledField(JPanel panel, String labelText, JTextField textField) {
    JLabel label = new JLabel(labelText);
    label.setLabelFor(textField);
    panel.add(label);
    panel.add(textField);
  }

  public void clearFields() {
    getFirstNameField().setText(NO_TEXT);
    getLastNameField().setText(NO_TEXT);
    getDateBirthField().setText(NO_TEXT);
    getDateBirthField().setBackground(Color.WHITE);
  }
}
