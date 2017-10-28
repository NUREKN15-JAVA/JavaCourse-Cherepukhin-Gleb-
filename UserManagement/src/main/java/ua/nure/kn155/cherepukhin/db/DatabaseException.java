package ua.nure.kn155.cherepukhin.db;

import java.sql.SQLException;

public class DatabaseException extends Exception {

  public DatabaseException() {
  }

  public DatabaseException(Exception ex) {
    super(ex);
  }

}
