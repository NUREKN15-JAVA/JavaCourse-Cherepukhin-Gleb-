package ua.nure.kn155.cherepukhin.logic.dao.config;

import org.dbunit.DBTestCase;
import org.dbunit.dataset.IDataSet;

public abstract class DBUnitConfig extends DBTestCase{

  @Override
  protected IDataSet getDataSet() throws Exception {
    // TODO Auto-generated method stub
    return null;
  }
  
  public abstract void testCreate();
  
  public abstract void testDelete();
  
  public abstract void testUpdate();
  
  public abstract void testGetAll();

}
