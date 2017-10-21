package ua.nure.kn155.cherepukhin.db;

import java.sql.Connection;


public interface IConnectionManager extends AutoCloseable{

  Connection getConnection() throws DatabaseException;

}
