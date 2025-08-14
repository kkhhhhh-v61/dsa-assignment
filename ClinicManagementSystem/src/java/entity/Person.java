package entity;

import java.time.LocalDate;
import utility.Enum.Gender;
import utility.Format;

public class Person {
  protected String name;
  protected Gender gender;
  protected String dateOfBirth;
  protected String icNumber;
  protected String contactNumber;
  protected String address;
  protected String dateRegistered;

  public Person() {
    this.dateRegistered = Format.formatDate(LocalDate.now());
  }

  public Person(String name, Gender gender, String dateOfBirth, String icNumber, String contactNumber, String address) {
    this();
    this.name = name;
    this.gender = gender;
    this.dateOfBirth = dateOfBirth;
    this.icNumber = icNumber;
    this.contactNumber = contactNumber;
    this.address = address;
  }
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public String getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public int getAge() {
    return Format.getDifferenceInYears(LocalDate.now(), Format.parseDate(dateOfBirth));
  }

  public String getIcNumber() {
    return icNumber;
  }

  public void setIcNumber(String icNumber) {
    this.icNumber = icNumber;
  }

  public String getContactNumber() {
    return contactNumber;
  }

  public void setContactNumber(String contactNumber) {
    this.contactNumber = contactNumber;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getDateRegistered() {
    return dateRegistered;
  }

  public void setDateRegistered(String dateRegistered) {
    this.dateRegistered = dateRegistered;
  }
}
