package entity;

import adt.ArrayList;
import utility.Enum.Gender;

public class Patient extends Person {
  private String patientID;
  private ArrayList<String> allergies;

  private int lastID = 1000;

  public Patient() {
  }
  
  public Patient(String name, Gender gender, String dateOfBirth, String icNumber, String contactNumber, String address, ArrayList<String> allergies) {
    super(name, gender, dateOfBirth, icNumber, contactNumber, address);
    this.patientID = "P" + ++lastID;
    this.allergies = allergies;
  }
  
  public String getPatientID() {
    return patientID;
  }

  // not recommended to use, included just in case
  public void setPatientID(String patientID) {
    this.patientID = patientID;
  }

  public String toString() {
    return String.format("Patient ID: %s\nName: %s\nGender: %s\nDate of Birth: %s\nAge: %s\nIC Number: %s\nContact Number: %s\nAddress: %s\nDate Registered: %s\nAllergies: %s",
        patientID, getName(), getGender(), getDateOfBirth(), getAge(), getIcNumber(), getContactNumber(), getAddress(), getDateRegistered(), allergies.toString());
  }
}