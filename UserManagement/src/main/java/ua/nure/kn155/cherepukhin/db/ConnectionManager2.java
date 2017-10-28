package ua.nure.kn155.cherepukhin.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionManager2 implements IConnectionManager{

  private String url;
  private String user;
  private String password;
  private String driver;

  public ConnectionManager2(Properties properties) {
   this.url = properties.getProperty("db.url");
   this.user = properties.getProperty("db.user");
   this.password = properties.getProperty("db.password");
   this.driver = properties.getProperty("db.driver");
  }

  @Override
  public void close() throws Exception {
    
  }

  @Override
  public Connection getConnection() throws DatabaseException {
    try {
      Class.forName(driver);
      return DriverManager.getConnection(url, user, password);
    } catch (Exception ex) {
      throw new DatabaseException(ex);
    }
  }

}
