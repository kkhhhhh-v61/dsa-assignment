package entity;

import adt.ArrayList;
import utility.Enum.Allergy;
import utility.Enum.BloodType;
import utility.Enum.Gender;
import java.time.LocalDate;

/**
 *
 * @author khtan
 */
public class Patient extends Person {
  private static int lastId = 0;

  private String patientId;
  private BloodType bloodType;
  private ArrayList<Allergy> allergies;
  private ArrayList<String> appointmentRecords;
  private ArrayList<String> consultationRecords;
  private ArrayList<String> treatmentRecords;

  public Patient() {
    this.patientId = "P" + String.format("%04d", ++lastId);
    this.bloodType = BloodType.Unknown;
    this.allergies = new ArrayList<>();
    this.appointmentRecords = new ArrayList<>();
    this.consultationRecords = new ArrayList<>();
    this.treatmentRecords = new ArrayList<>();
  }

  public Patient(String icNumber, String name, Gender gender, LocalDate birthDate, String contactNumber, String email,
      String address, LocalDate registrationDate, BloodType bloodType, ArrayList<Allergy> allergies, ArrayList<String> appointmentRecords,
      ArrayList<String> consultationRecords, ArrayList<String> treatmentRecords) {
    super(icNumber, name, gender, birthDate, contactNumber, email, address, registrationDate);
    this.patientId = "P" + String.format("%04d", ++lastId);
    this.bloodType = bloodType;
    this.allergies = allergies;
    this.appointmentRecords = appointmentRecords;
    this.consultationRecords = consultationRecords;
    this.treatmentRecords = treatmentRecords;
  }
  
  public String getPatientId() {
    return patientId;
  }

  public void setPatientId(String patientId) { /* Not recommended for use */
    this.patientId = patientId;
  }

  public BloodType getBloodType() {
    return bloodType;
  }

  public void setBloodType(BloodType bloodType) {
    this.bloodType = bloodType;
  }

  public ArrayList<Allergy> getAllergies() {
    return allergies;
  }

  public void setAllergies(ArrayList<Allergy> allergies) {
    this.allergies = allergies;
  }

  public ArrayList<String> getAppointmentRecords() {
    return appointmentRecords;
  }

  public void setAppointmentRecords(ArrayList<String> appointmentRecords) {
    this.appointmentRecords = appointmentRecords;
  }

  public ArrayList<String> getConsultationRecords() {
    return consultationRecords;
  }

  public void setConsultationRecords(ArrayList<String> consultationRecords) {
    this.consultationRecords = consultationRecords;
  }

  public ArrayList<String> getTreatmentRecords() {
    return treatmentRecords;
  }

  public void setTreatmentRecords(ArrayList<String> treatmentRecords) {
    this.treatmentRecords = treatmentRecords;
  }

  public String toString() {
    return "Patient ID: " + patientId + "\n" + super.toString();
  }

  public String toFileString() {
    return patientId + "|" + super.toFileString();
  }
  
  public String toStringComplete() {
    return "Patient ID: " + patientId + "\n" + super.toString() + "\nBlood Type: " + bloodType + "\nAllergies: " + allergies
        + "\nAppointment Records: " + appointmentRecords + "\nConsultation Records: " + consultationRecords
        + "\nTreatment Records: " + treatmentRecords;
  }
  
  public String toFileStringComplete() {
    return patientId + "|" + super.toFileString() + "|" + bloodType + "|" + allergies + "|" + appointmentRecords + "|"
        + consultationRecords + "|"
        + treatmentRecords;
  }
  
  public String toStringListLeft() {
    return String.format("| %-5s | %14s | %-35s | %6s | %10s | %3s | %-14s |",
        patientId, icNumber, name, gender, birthDate, age, contactNumber);
  }
  
  public String toStringListRight() {
    return String.format("| %-5s | %-35s | %-45s | %10s | %-2s |",
        patientId, email, address, registrationDate, bloodType);
  }
}
