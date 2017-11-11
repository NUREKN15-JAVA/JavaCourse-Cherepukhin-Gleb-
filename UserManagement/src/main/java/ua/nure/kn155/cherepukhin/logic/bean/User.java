package ua.nure.kn155.cherepukhin.logic.bean;

import java.util.Calendar;
import java.util.Date;

import ua.nure.kn155.cherepukhin.db.DatabaseException;
import ua.nure.kn155.cherepukhin.logic.dao.DAOFactory2;

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

  public int getAge() throws IllegalStateException {
    if(dateBirth == null) {
      throw new IllegalStateException("Birth date is not defined!");
    }
    Calendar currentDate = Calendar.getInstance();
    Calendar dateBirth = Calendar.getInstance();
    dateBirth.setTime(this.dateBirth);
    int years = currentDate.get(Calendar.YEAR) - dateBirth.get(Calendar.YEAR);
    if (years < 0) {
      throw new IllegalStateException("Age is negative!");
    }
    return years;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("User [id=");
    builder.append(id);
    builder.append(", firstName=");
    builder.append(firstName);
    builder.append(", lastName=");
    builder.append(lastName);
    builder.append(", dateBirth=");
    builder.append(dateBirth);
    builder.append("]");
    return builder.toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((dateBirth == null) ? 0 : dateBirth.hashCode());
    result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    User other = (User) obj;
    if (dateBirth == null) {
      if (other.dateBirth != null)
        return false;
    } else if (!dateBirth.equals(other.dateBirth))
      return false;
    if (firstName == null) {
      if (other.firstName != null)
        return false;
    } else if (!firstName.equals(other.firstName))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (lastName == null) {
      if (other.lastName != null)
        return false;
    } else if (!lastName.equals(other.lastName))
      return false;
    return true;
  }
  
}
