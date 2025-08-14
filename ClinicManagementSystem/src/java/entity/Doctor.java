package entity;

import utility.Enum.Gender;
import utility.Enum.Specialisation;

public class Doctor extends Person{
  private int lastID = 1000;

  private String doctorID;
  private Specialisation specialisation;
  private boolean isAvailable;

  public Doctor() {
  }

  public Doctor(String name, Gender gender, String dateOfBirth, String icNumber, String contactNumber, String address, Specialisation specialisation, boolean isAvailable) {
    super(name, gender, dateOfBirth, icNumber, contactNumber, address);
    this.doctorID = "D" + ++lastID;
    this.specialisation = specialisation;
    this.isAvailable = isAvailable;
  }

  public String getDoctorID() {
    return doctorID;
  }

  public void setDoctorID(String doctorID) {
    this.doctorID = doctorID;
  }

  public Specialisation getSpecialisation() {
    return specialisation;
  }

  public void setSpecialisation(Specialisation specialisation) {
    this.specialisation = specialisation;
  }

  public boolean isAvailable() {
    return isAvailable;
  }

  public void setAvailability(boolean isAvailable) {
    this.isAvailable = isAvailable;
  }

  public void toggleAvailability() {
    this.isAvailable = !this.isAvailable;
  }

  public String toString() {
    return String.format("Doctor ID: %s\nName: %s\nGender: %s\nDate of Birth: %s\nAge: %s\nIC Number: %s\nContact Number: %s\nAddress: %s\nDate Registered: %s\nSpecialisation: %s\nAvailability: %s",
        doctorID, getName(), getGender(), getDateOfBirth(), getAge(), getIcNumber(), getContactNumber(), getAddress(), getDateRegistered(), getSpecialisation(), isAvailable());
  }
}