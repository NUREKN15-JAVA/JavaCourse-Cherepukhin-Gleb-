package ua.nure.kn155.cherepukhin.logic.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;
import ua.nure.kn155.cherepukhin.logic.bean.User;
//TODO *(1) add specs
public abstract class AbstractDAO<K extends Serializable, E> {

  protected Connection connection;

  public AbstractDAO(Connection connection) {
    this.connection = connection;
  }

  public abstract List<E> getAll() throws DatabaseException;

  public abstract E getById(K id) throws DatabaseException;

  /**
   * 
   * @param entity to add in DB, see impl package for further info
   * id must be null.
   * @return user with assigned id
   * @exception DatabaseException if any trouble appears during operation
   */
  public abstract User create(E entity) throws DatabaseException;

  public abstract boolean update(E entity) throws DatabaseException;

  public abstract boolean delete(E entity) throws DatabaseException;

}
