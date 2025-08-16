package entity;

import adt.ArrayList;
import utility.Enum.Gender;
import utility.Enum.Specialization;
import java.time.LocalDate;
import java.time.Period;

public class Doctor extends Person {
  private static int lastId = 0;

  private String doctorId;
  private Specialization specialization;
  private int yearsOfExperience;
  private ArrayList<String> dutySchedule;
  private ArrayList<String> appointmentRecords;
  private ArrayList<String> consultationRecords;
  private ArrayList<String> treatmentRecords;

  public Doctor() {
    super();
    this.doctorId = "D" + String.format("%04d", ++lastId);
    this.specialization = Specialization.Unknown;
    this.dutySchedule = new ArrayList<>();
    this.appointmentRecords = new ArrayList<>();
    this.consultationRecords = new ArrayList<>();
    this.treatmentRecords = new ArrayList<>();
    this.yearsOfExperience = calculateYearsOfExperience();
  }
  
  public Doctor(String icNumber, String name, Gender gender, LocalDate birthDate, String contactNumber, String email,
      String address, LocalDate registrationDate, Specialization specialization, ArrayList<String> dutySchedule, ArrayList<String> appointmentRecords, ArrayList<String> consultationRecords, ArrayList<String> treatmentRecords) {
    super(icNumber, name, gender, birthDate, contactNumber, email, address, registrationDate);
    this.doctorId = "D" + String.format("%04d", ++lastId);
    this.specialization = specialization;
    this.dutySchedule = dutySchedule;
    this.appointmentRecords = appointmentRecords;
    this.consultationRecords = consultationRecords;
    this.treatmentRecords = treatmentRecords;
    this.yearsOfExperience = calculateYearsOfExperience();
  }

  // --- Getters and Setters ---
  public String getDoctorId() {
    return doctorId;
  }

  public Specialization getSpecialization() {
    return specialization;
  }

  public void setSpecialization(Specialization specialization) {
    this.specialization = specialization;
  }

  public int getYearsOfExperience() {
    // Always return the current calculated experience
    return calculateYearsOfExperience();
  }
  
  public int calculateYearsOfExperience() {
      if (super.registrationDate == null) {
          return 0;
      }
      return Period.between(super.registrationDate, LocalDate.now()).getYears();
  }

  public ArrayList<String> getDutySchedule() {
    return dutySchedule;
  }

  public void setDutySchedule(ArrayList<String> dutySchedule) {
    this.dutySchedule = dutySchedule;
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

  @Override
  public String toString() {
    return "Doctor ID: " + doctorId + "\nSpecialization: " + specialization + "\nYears of Experience: " + getYearsOfExperience();
  }

  /**
   * Corrected toFileString to only include Doctor-specific fields for saving.
   * It should NOT include the ArrayLists for this simple save format.
   */
  public String toFileString() {
    // This method is now designed to be combined with the Person's toFileString
    return doctorId + "|" + super.toFileString() + "|" + specialization;
  }

  public String toStringComplete() {
    return "Doctor ID: " + doctorId + "\n" + super.toString() + "\nSpecialization: " + specialization + "\nYears of Experience: " + getYearsOfExperience();
  }
}