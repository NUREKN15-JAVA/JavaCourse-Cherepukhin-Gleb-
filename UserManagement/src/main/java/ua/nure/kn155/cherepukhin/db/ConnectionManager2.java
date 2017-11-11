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
   parseDBURL(properties.getProperty("db.url"),properties.getProperty("db.file.extension"));
   this.user = properties.getProperty("db.login");
   this.password = properties.getProperty("db.password");
   this.driver = properties.getProperty("db.driver");
  }

  @Override
  public void close() throws Exception {}

  @Override
  public Connection getConnection() throws DatabaseException {
    try {
      Class.forName("org.h2.Driver");
      return DriverManager.getConnection(url, user, password);
    } catch (Exception ex) {
      throw new DatabaseException(ex);
    }
  }
  
  private  void parseDBURL(String dbURL, String fileExt) {
    if(dbURL.startsWith("jdbc:")) {
      this.url = dbURL;
    } else {
      this.url = "jdbc:h2:" + Thread.currentThread().getContextClassLoader().getResource(dbURL+fileExt).getPath().replace(fileExt, "");
      System.out.println(this.url);
    }
  }

}
