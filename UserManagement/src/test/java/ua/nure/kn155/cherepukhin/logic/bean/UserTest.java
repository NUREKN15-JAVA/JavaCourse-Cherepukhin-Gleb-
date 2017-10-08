package ua.nure.kn155.cherepukhin.logic.bean;

import static org.junit.Assert.assertEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UserTest {

  private User user;
  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Before
  public void setUp() throws Exception {
    user = new User();
  }

  @Test
  public void shouldReturnFullName() {
    // given
    user.setFirstName("a");
    user.setLastName("b");
    // when-then
    assertEquals("a, b", user.getFullName());
  }

  @Test
  public void shouldThrowExceptionOnFirstNameAbsent() {
    // given
    user.setLastName("b");
    // when-then
    expectedException.expect(IllegalStateException.class);
    user.getFullName();
  }

  @Test
  public void shouldThrowExceptionOnLastNameAbsent() {
    // given
    user.setFirstName("a");
    // when-then
    expectedException.expect(IllegalStateException.class);
    user.getFullName();
  }

  @After
  public void tearDown() throws Exception {
    // nothing to clean
  }
}
