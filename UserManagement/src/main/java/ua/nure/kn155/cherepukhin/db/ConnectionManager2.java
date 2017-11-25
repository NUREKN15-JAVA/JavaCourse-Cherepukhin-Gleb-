package ua.nure.kn155.cherepukhin.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import ua.nure.kn155.cherepukhin.logic.dao.DAOFactory2;

public class ConnectionManager2 implements IConnectionManager {

  private final List<Connection> connenctions = new ArrayList<>();
  private static final String EXTERNAL_PREFIX = "external:";
  private static final String FILE_PREFIX = "file:";
  private String url;
  private String user;
  private String password;
  private String driver;

  public ConnectionManager2(Properties properties) {
    parseDBURL(properties.getProperty("db.url"), properties.getProperty("db.file.extension"));
    this.user = properties.getProperty("db.login");
    this.password = properties.getProperty("db.password");
    this.driver = properties.getProperty("db.driver");
  }

  @Override
  public void close() throws Exception {
    connenctions.forEach(conection -> {
      try {
        conection.close();
      } catch (SQLException e) {
      }
    });
  }

  @Override
  public Connection getConnection() throws DatabaseException {
    try {
      Class.forName("org.h2.Driver");
      return DriverManager.getConnection(url, user, password);
    } catch (Exception ex) {
      throw new DatabaseException(ex);
    }
  }

  private void parseDBURL(String dbURL, String fileExt) {
    System.out.println(dbURL + " :: " + fileExt);
    if (dbURL.startsWith(EXTERNAL_PREFIX)) {
      String rootUrl = Thread.currentThread().getContextClassLoader()
          .getResource(DAOFactory2.EMBEDDED_PROPERTIES).getPath();
      rootUrl = rootUrl.substring(0, rootUrl.lastIndexOf("/")).replace(FILE_PREFIX, "");
      rootUrl = rootUrl.substring(0, rootUrl.lastIndexOf("/"));
      this.url = "jdbc:h2:" + rootUrl + "/" + dbURL.replace(EXTERNAL_PREFIX, "");
      System.out.println(this.url);
    } else if (dbURL.startsWith("jdbc:")) {
      this.url = dbURL;
    } else {
      this.url = "jdbc:h2:" + Thread.currentThread().getContextClassLoader()
          .getResource(dbURL + fileExt).getPath().replace(fileExt, "");
      System.out.println(this.url);
    }
  }

}
