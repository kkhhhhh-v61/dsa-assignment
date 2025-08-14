package entity;

import utility.Enum.Gender;
import utility.Enum.Specialisation;
import adt.ArrayList;

public class Doctor extends Person{
  private String doctorID;
  private Specialisation specialisation;
  private boolean isAvailable;
  private ArrayList<Schedule> schedules;

  private int lastID = 1000;

  public Doctor() {
  }

  public Doctor(String name, Gender gender, String dateOfBirth, String icNumber, String contactNumber, String address, Specialisation specialisation, boolean isAvailable, ArrayList<Schedule> schedules) {
    super(name, gender, dateOfBirth, icNumber, contactNumber, address);
    this.doctorID = "D" + ++lastID;
    this.specialisation = specialisation;
    this.isAvailable = isAvailable;
    this.schedules = schedules;
  }

  public String getDoctorID() {
    return doctorID;
  }

  // not recommended to use, included just in case
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

  public void toggleAvailability() {
    this.isAvailable = !this.isAvailable;
  }

  public String toString() {
    return String.format("Doctor ID: %s\nName: %s\nGender: %s\nDate of Birth: %s\nAge: %s\nIC Number: %s\nContact Number: %s\nAddress: %s\nDate Registered: %s\nSpecialisation: %s\nAvailability: %s\nSchedules: %s",
        doctorID, getName(), getGender(), getDateOfBirth(), getAge(), getIcNumber(), getContactNumber(), getAddress(), getDateRegistered(), getSpecialisation(), isAvailable() ? "Yes" : "No", schedules.toString());
  }
}