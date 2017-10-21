package ua.nure.kn155.cherepukhin.logic.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;

import ua.nure.kn155.cherepukhin.logic.bean.User;
import ua.nure.kn155.cherepukhin.logic.dao.config.DBUnitConfig;
import ua.nure.kn155.cherepukhin.logic.dao.impl.H2UserDAO;

public class H2UserDAOTest extends DBUnitConfig<User> {

  public H2UserDAO userDAO;

  public H2UserDAOTest(String name) {
    super(name);
    userDAO = new H2UserDAO(connectionManager);
  }

  @Override
  public void setUp() throws Exception {
    super.setUp();
    beforeData = new FlatXmlDataSetBuilder().build(Thread.currentThread().getContextClassLoader()
        .getResourceAsStream("entities/user-data.xml"));
    tester.setDataSet(beforeData);
    tester.onSetup();
  }

  @Test
  @Override
  public void testCreate() throws Exception {
    // given
    User user = new User();
    user.setFirstName("John");
    user.setLastName("Doe");
    user.setDateBirth(new SimpleDateFormat("dd.MM.yyyy").parse("13.08.1970"));

    // when
    User createdUser = userDAO.create(user);

    IDataSet expectedData = new FlatXmlDataSetBuilder().build(Thread.currentThread()
        .getContextClassLoader().getResourceAsStream("entities/user-data-create.xml"));
    IDataSet actualData = tester.getConnection().createDataSet();

    // then
    assertNotNull(createdUser);
    assertNotNull(createdUser.getId());
    Assertion.assertEquals(expectedData, actualData);
  }

  @Test
  @Override
  public void testDelete() throws SQLException, Exception {
    // given
    User userToDelete = new User();
    userToDelete.setId(4L);
    userToDelete.setFirstName("d");
    userToDelete.setLastName("e");
    userToDelete.setDateBirth(new SimpleDateFormat("dd.MM.yyyy").parse("24.09.1997"));

    IDataSet expectedData = new FlatXmlDataSetBuilder().build(Thread.currentThread()
        .getContextClassLoader().getResourceAsStream("entities/user-data-delete.xml"));
    // when
    boolean resultOfDeleteOperation = userDAO.delete(userToDelete);

    // then
    assertTrue(resultOfDeleteOperation);
    IDataSet actualData = tester.getConnection().createDataSet();
    Assertion.assertEquals(expectedData, actualData);
  }

  @Test
  @Override
  public void testUpdate() throws SQLException, Exception {
    // given
    User userToDelete = new User();
    userToDelete.setId(1L);
    userToDelete.setFirstName("aaa");
    userToDelete.setLastName("bbb");
    userToDelete.setDateBirth(new SimpleDateFormat("dd.MM.yyyy").parse("24.09.2015"));

    IDataSet expectedData = new FlatXmlDataSetBuilder().build(Thread.currentThread()
        .getContextClassLoader().getResourceAsStream("entities/user-data-update.xml"));

    // when
    boolean resultOfupdateOperation = userDAO.update(userToDelete);

    // then
    assertEquals(true, resultOfupdateOperation);
    IDataSet actualData = tester.getConnection().createDataSet();
    Assertion.assertEquals(expectedData, actualData);

  }

  @Test
  @Override
  public void testGetAll() throws Exception {
    // given-when
    List<User> fetchedUsers = userDAO.getAll();

    IDataSet expectedData = new FlatXmlDataSetBuilder().build(Thread.currentThread()
        .getContextClassLoader().getResourceAsStream("entities/user-data.xml"));
    IDataSet actualData = buildDataSetOfList(fetchedUsers, User.class);

    // then
    Assertion.assertEquals(expectedData, actualData);
    assertEquals(fetchedUsers.size(), expectedData.getTable("USER").getRowCount());
  }

  @Test
  @Override
  public void testGetByKey() throws Exception {
    // given-when
    User fetchedUser = userDAO.getById(1L);

    IDataSet expectedData = new FlatXmlDataSetBuilder().build(Thread.currentThread()
        .getContextClassLoader().getResourceAsStream("entities/user-data-selectkey.xml"));
    IDataSet actualData = buildDataSetOfList(Arrays.asList(fetchedUser), User.class);
    // then
    Assertion.assertEquals(expectedData, actualData);
  }

}
