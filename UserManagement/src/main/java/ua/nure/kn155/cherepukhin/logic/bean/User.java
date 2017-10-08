package ua.nure.kn155.cherepukhin.logic.bean;

import java.util.Date;

public class User {

  private Long id;
  private String firstName;
  private String lastName;
  private Date dateBirth;

  public User() {}

  public User(Long id, String firstName, String lastName, Date dateBirth) {
    super();
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.dateBirth = dateBirth;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Date getDateBirth() {
    return dateBirth;
  }

  public void setDateBirth(Date dateBirth) {
    this.dateBirth = dateBirth;
  }

  public String getFullName() throws IllegalStateException {
    if (firstName == null || lastName == null)
      throw new IllegalStateException("first/last name must be NOT null");
    StringBuilder fullName = new StringBuilder();
    fullName.append(firstName).append(", ").append(lastName);
    return fullName.toString();
  }

}
