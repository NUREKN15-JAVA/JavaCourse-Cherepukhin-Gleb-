package ua.nure.kn155.cherepukhin.agent;

import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import ua.nure.kn155.cherepukhin.db.DatabaseException;
import ua.nure.kn155.cherepukhin.gui.MainFrame;
import ua.nure.kn155.cherepukhin.gui.SearchGui;
import ua.nure.kn155.cherepukhin.logic.bean.User;
import ua.nure.kn155.cherepukhin.logic.dao.DAOFactory2;
import ua.nure.kn155.cherepukhin.logic.dao.UserDAO;

public class SearchAgent extends Agent {

  private static final Logger LOG = LogManager.getLogger(SearchAgent.class);
  private final UserDAO userDAO = DAOFactory2.getInstance().getUserDAO();
  private AID[] aids;
  
  private MainFrame mainFrame;

  @Override
  protected void setup() {
    mainFrame = new MainFrame(DAOFactory2.getInstance(), this);
    mainFrame.setResizable(false);
    mainFrame.setVisible(true);
    
    ServiceDescription serviceDescription = new ServiceDescription();
    serviceDescription.setName("JADE Search");
    serviceDescription.setType("searching");

    DFAgentDescription description = new DFAgentDescription();
    description.setName(getAID());
    description.addServices(serviceDescription);

    try {
      DFService.register(this, description);
    } catch (FIPAException e) {
      LOG.catching(e);
    }

    addBehaviour(new RequestServer());
    addBehaviour(new TickerBehaviour(this, 60000) {

      @Override
      protected void onTick() {
          DFAgentDescription agentDescription = new DFAgentDescription();
          ServiceDescription serviceDescription = new ServiceDescription();
          serviceDescription.setType("searching");
          agentDescription.addServices(serviceDescription);
          DFAgentDescription[] descriptions;
          try {
              descriptions = DFService.search(myAgent, agentDescription);
          } catch (FIPAException e) {
              e.printStackTrace();
              descriptions = new DFAgentDescription[0];
          }
          aids = new AID[descriptions.length];
          for (int i = 0; i < descriptions.length; i++) {
              aids[i] = descriptions[i].getName();
          }

      }
  });
    LOG.info("AGENT {} STARTED WORKING\n NOW IS [IDLE]", getAID());
  }

  public void search(String firstName, String lastName) throws SearchException {
    try {
      Collection<User> users = DAOFactory2.getInstance().getUserDAO().find(firstName, lastName);
      if (users.size() > 0) {
        showUsers(users, this.getName());
      } else {
        addBehaviour(new SearchRequestBehaviour(aids, firstName, lastName));
      }
    } catch (DatabaseException e) {
      LOG.throwing(new SearchException(e));
    }
  }

  public void showUsers(Collection<User> find, String name) {
    ((SearchGui) mainFrame.getSearchPanel()).addUsers(find);
  }

  @Override
  protected void takeDown() {
    try {
      DFService.deregister(this);
    } catch (FIPAException e) {
      LOG.catching(e);
    }
    LOG.info("AGENT {} FINISHED WORKING", getAID());
  }

}
