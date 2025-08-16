package entity;

import utility.Enum.Gender;
import java.time.LocalDate;
import java.time.Period;

public class Person {
  protected String icNumber;
  protected String name;
  protected Gender gender;
  protected LocalDate birthDate;
  protected int age;
  protected String contactNumber;
  protected String email;
  protected String address;
  protected LocalDate registrationDate;

  public Person() {
    this.icNumber = "";
    this.name = "";
    this.gender = Gender.Unknown;
    this.birthDate = LocalDate.now();
    this.contactNumber = "";
    this.email = "";
    this.address = "";
    this.registrationDate = LocalDate.now();
    this.age = calculateAge();
  }

  public Person(String icNumber, String name, Gender gender, LocalDate birthDate, String contactNumber, String email,
      String address, LocalDate registrationDate) {
    this.icNumber = icNumber;
    this.name = name;
    this.gender = gender;
    this.birthDate = birthDate;
    this.contactNumber = contactNumber;
    this.email = email;
    this.address = address;
    this.registrationDate = registrationDate == null ? LocalDate.now() : registrationDate;
    this.age = calculateAge();
  }

  // --- Getters and Setters ---
  public String getIcNumber() {
    return icNumber;
  }

  public void setIcNumber(String icNumber) {
    this.icNumber = icNumber;
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

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
    this.age = calculateAge();
  }

  public int getAge() {
    // Always return the current calculated age
    return calculateAge();
  }
  
  public int calculateAge() {
    if (this.birthDate == null) {
      return 0;
    }
    return Period.between(this.birthDate, LocalDate.now()).getYears();
  }

  public String getContactNumber() {
    return contactNumber;
  }

  public void setContactNumber(String contactNumber) {
    this.contactNumber = contactNumber;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public LocalDate getRegistrationDate() {
    return registrationDate;
  }

  public void setRegistrationDate(LocalDate registrationDate) {
    this.registrationDate = registrationDate;
  }

  @Override
  public String toString() {
    return "IC Number: " + icNumber + "\nName: " + name + "\nGender: " + gender + "\nBirth Date: " + birthDate + "\nAge: " + getAge() + "\nContact Number: " + contactNumber + "\nEmail: " + email + "\nAddress: " + address + "\nRegistration Date: " + registrationDate;
  }
  
  /**
   * Corrected toFileString to only include Person fields for saving.
   */
  public String toFileString() {
    return icNumber + "|" + name + "|" + gender + "|" + birthDate + "|" + contactNumber + "|" + email + "|" + address + "|" + registrationDate;
  }
}