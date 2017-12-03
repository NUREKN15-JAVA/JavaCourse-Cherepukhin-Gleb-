package ua.nure.kn155.cherepukhin.agent;

import ua.nure.kn155.cherepukhin.db.DatabaseException;

public class SearchException extends Exception {

  public SearchException(DatabaseException e) {
    super(e);
  }

}
