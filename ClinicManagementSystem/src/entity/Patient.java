package entity;

import adt.ArrayList;
import utility.Enum.BloodType;
import utility.Enum.Gender;
import java.time.LocalDate;

/**
 *
 * @author Tan Kai Hao
 */
public class Patient extends Person {
  private static final long serialVersionUID = 1L;
  private static int lastId = 0;

  private String patientId;
  private BloodType bloodType;
  private ArrayList<String> appointmentRecords;
  private ArrayList<String> consultationRecords;
  private ArrayList<String> treatmentRecords;

  public Patient() {
    this.patientId = "P" + String.format("%04d", ++lastId);
    this.bloodType = BloodType.Unknown;
    this.appointmentRecords = new ArrayList<>();
    this.consultationRecords = new ArrayList<>();
    this.treatmentRecords = new ArrayList<>();
  }

  public Patient(String icNumber, String name, Gender gender, LocalDate birthDate, String contactNumber, String email,
      String address, LocalDate registrationDate, BloodType bloodType, ArrayList<String> appointmentRecords,
      ArrayList<String> consultationRecords, ArrayList<String> treatmentRecords) {
    super(icNumber, name, gender, birthDate, contactNumber, email, address, registrationDate);
    this.patientId = "P" + String.format("%04d", ++lastId);
    this.bloodType = bloodType;
    this.appointmentRecords = appointmentRecords;
    this.consultationRecords = consultationRecords;
    this.treatmentRecords = treatmentRecords;
  }
  
  public static void setLastId(int lastId) {
    Patient.lastId = lastId;
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
    return patientId + "|" + super.toFileString() + "|" + bloodType;
  }
}
