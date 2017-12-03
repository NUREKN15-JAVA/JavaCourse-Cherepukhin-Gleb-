package ua.nure.kn155.cherepukhin.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;

import jade.wrapper.StaleProxyException;
import ua.nure.kn155.cherepukhin.agent.SearchAgent;
import ua.nure.kn155.cherepukhin.gui.panel.AddPanel;
import ua.nure.kn155.cherepukhin.gui.panel.BrowsePanel;
import ua.nure.kn155.cherepukhin.gui.panel.DetailsPanel;
import ua.nure.kn155.cherepukhin.gui.panel.EditPanel;
import ua.nure.kn155.cherepukhin.logic.bean.User;
import ua.nure.kn155.cherepukhin.logic.dao.DAOFactory2;
import ua.nure.kn155.cherepukhin.logic.dao.UserDAO;

public class MainFrame extends JFrame implements ActionListener {

  private static final String APP_TITLE = "User management";
  private static final int FRAME_WIDTH = 600;
  private static final int FRAME_HEIGHT = 480;

  private JPanel contentPanel;
  private BrowsePanel browsePanel;
  private AddPanel addPanel;
  private EditPanel editPanel;
  private DetailsPanel detailsPanel;

  private UserDAO userDAO;
  private SearchAgent agent;
  private SearchGui searchPanel;

  public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

  public MainFrame(DAOFactory2 daoFactory, SearchAgent agent) {
    super();
    this.agent = agent;
    this.addWindowListener(new MainFrameCloser(agent));
    userDAO = daoFactory.getUserDAO();
    initialize();
    showBrowsePanel();
  }

  public void initialize() {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    this.setTitle(APP_TITLE);
    this.setContentPane(getContentPane());
  }

  @Override
  public Container getContentPane() {
    if (contentPanel == null) {
      contentPanel = new JPanel();
      contentPanel.setLayout(new BorderLayout());
    }
    return contentPanel;
  }

  public BrowsePanel getBrowsePanel() {
    return (browsePanel == null) ? (browsePanel = new BrowsePanel(this)) : browsePanel;
  }

  public AddPanel getAddPanel() {
    return (addPanel == null) ? (addPanel = new AddPanel(this)) : addPanel;
  }

  public EditPanel getEditPanel() {
    return (editPanel == null) ? (editPanel = new EditPanel(this)) : editPanel;
  }

  public DetailsPanel getDetailsPanel() {
    return (detailsPanel == null) ? (detailsPanel = new DetailsPanel(this)) : detailsPanel;
  }

  public void showAddPanel() {
    showPanel(getAddPanel());
  }

  public void showDetailsPanel(User user) {
    getDetailsPanel().setEditData(user);
    showPanel(getDetailsPanel());
  }

  public void showEditPanel(User user) {
    getEditPanel().setEditData(user);
    showPanel(getEditPanel());
  }

  public void showBrowsePanel() {
    getBrowsePanel().reinitializeModel();
    showPanel(getBrowsePanel());
  }

  private void showPanel(JPanel panel) {
    getContentPane().removeAll();
    getContentPane().add(panel, BorderLayout.CENTER);
    getContentPane().repaint();
    panel.setVisible(true);
    panel.repaint();
  }

  public void showSearchPanel() {
    showPanel(getSearchPanel());
  }

  public JPanel getSearchPanel() {
    if (searchPanel == null) {
      searchPanel = new SearchGui(agent, this);
    }
    return searchPanel;
  }

  @Override
  public void actionPerformed(ActionEvent event) {

  }

  public UserDAO getUserDAO() {
    return userDAO;
  }

  public static void main(String[] args) {
    MainFrame frame = new MainFrame(DAOFactory2.getInstance(), null);
    frame.setResizable(false);
    frame.setVisible(true);
  }

  private class MainFrameCloser extends WindowAdapter {
    private SearchAgent agent;

    public MainFrameCloser(SearchAgent agent) {
      this.agent = agent;
    }

    @Override
    public void windowClosing(WindowEvent e) {
      try {
        agent.getContainerController().kill();
      } catch (StaleProxyException e1) {
        e1.printStackTrace();
        System.exit(-1);
      }
    }
  }

}
