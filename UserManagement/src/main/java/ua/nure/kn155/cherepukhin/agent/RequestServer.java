package ua.nure.kn155.cherepukhin.agent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import ua.nure.kn155.cherepukhin.db.DatabaseException;
import ua.nure.kn155.cherepukhin.logic.bean.User;
import ua.nure.kn155.cherepukhin.logic.dao.DAOFactory2;

public class RequestServer extends CyclicBehaviour {

  @Override
  public void action() {
    ACLMessage message = myAgent.receive();
    if (message != null) {
      if (message.getPerformative() == ACLMessage.REQUEST) {
        myAgent.send(createReply(message));
      } else if (message.getPerformative() == ACLMessage.INFORM) {
        Collection users = parseMessage(message);
        ((SearchAgent) myAgent).showUsers(users, message.getSender().getName());
      }
    } else {
      block();
    }
  }

  private Collection<User> parseMessage(ACLMessage message) {
    Collection<User> users = new LinkedList<>();
    String content = message.getContent();
    StringTokenizer tokenizer = new StringTokenizer(content, ";");
    if (content != null && !content.equals("")) {
      while (tokenizer.hasMoreTokens()) {
        String serializedUser = tokenizer.nextToken();
        StringTokenizer userAttrTokenizer = new StringTokenizer(serializedUser, ",");
        
        User user = new User();
        user.setId(new Long(userAttrTokenizer.nextToken()));
        user.setFirstName(userAttrTokenizer.nextToken());
        user.setLastName(userAttrTokenizer.nextToken());
        
        users.add(user);
      }
    }
    return users;
  }

  private ACLMessage createReply(ACLMessage message) {
    ACLMessage reply = message.createReply();
    String content = message.getContent();
    StringTokenizer tokenizer = new StringTokenizer(content, " ");
    String firstName = tokenizer.nextToken();
    String lastName = tokenizer.nextToken();
    Collection<User> users = null;
    try {
      users = DAOFactory2.getInstance().getUserDAO().find(firstName, lastName);
      StringBuffer buffer = new StringBuffer();
      for (Iterator<User> it = users.iterator(); it.hasNext();) {
        User user = it.next();
        buffer.append(user.getId()).append(",");
        buffer.append(user.getFirstName()).append(",");
        buffer.append(user.getLastName()).append(";");
      }
      reply.setContent(buffer.toString());
    } catch (DatabaseException e) {
      users = new ArrayList<>(0);
      e.printStackTrace();
    }
    return reply;
  }

}
