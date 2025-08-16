package entity;

import utility.Enum.Gender;
import java.time.LocalDate;

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
    this.age = calculateAge();
    this.contactNumber = "";
    this.email = "";
    this.address = "";
    this.registrationDate = LocalDate.now();
  }

  public Person(String icNumber, String name, Gender gender, LocalDate birthDate, String contactNumber, String email,
      String address, LocalDate registrationDate) {
    this.icNumber = icNumber;
    this.name = name;
    this.gender = gender;
    this.birthDate = birthDate;
    this.age = calculateAge();
    this.contactNumber = contactNumber;
    this.email = email;
    this.address = address;
    this.registrationDate = registrationDate == null ? LocalDate.now() : registrationDate;
  }

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
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }
  
  public int calculateAge() {
    int age = LocalDate.now().getYear() - this.birthDate.getYear();
    if (LocalDate.now().getMonthValue() <= this.birthDate.getMonthValue()) {
      if (LocalDate.now().getDayOfMonth() < this.birthDate.getDayOfMonth()) {
        if(age > 0) age--;
      }
    }
    return age;
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

  public String toString() {
    return "IC Number: " + icNumber + "\nName: " + name + "\nGender: " + gender + "\nBirth Date: " + birthDate + "\nAge: " + age + "\nContact Number: " + contactNumber + "\nEmail: " + email + "\nAddress: " + address + "\nRegistration Date: " + registrationDate;
  }
  
  public String toFileString() {
    return icNumber + "|" + name + "|" + gender + "|" + birthDate + "|" + age + "|" + contactNumber + "|" + email + "|" + address + "|" + registrationDate;
  }
}
