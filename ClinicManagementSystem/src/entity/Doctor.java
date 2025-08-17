package entity;

import adt.ArrayList;
import utility.Enum.Gender;
import utility.Enum.Specialization;
import java.time.LocalDate;

/**
 *
 * @author khtan
 */
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
    this.doctorId = "D" + String.format("%04d", ++lastId);
    this.specialization = Specialization.Unknown;
    this.yearsOfExperience = calculateYearsOfExperience();
    this.dutySchedule = new ArrayList<>();
    this.appointmentRecords = new ArrayList<>();
    this.consultationRecords = new ArrayList<>();
    this.treatmentRecords = new ArrayList<>();
  }
  
  public Doctor(String icNumber, String name, Gender gender, LocalDate birthDate, String contactNumber, String email,
      String address, LocalDate registrationDate, Specialization specialization, ArrayList<String> dutySchedule, ArrayList<String> appointmentRecords, ArrayList<String> consultationRecords, ArrayList<String> treatmentRecords) {
    super(icNumber, name, gender, birthDate, contactNumber, email, address, registrationDate);
    this.doctorId = "D" + String.format("%04d", ++lastId);
    this.specialization = specialization;
    this.yearsOfExperience = calculateYearsOfExperience();
    this.dutySchedule = dutySchedule;
    this.appointmentRecords = appointmentRecords;
    this.consultationRecords = consultationRecords;
    this.treatmentRecords = treatmentRecords;
  }
  
  public String getDoctorId() {
    return doctorId;
  }
  
  public void setDoctorId(String doctorId) { /* Not recommended for use */
    this.doctorId = doctorId;
  }

  public Specialization getSpecialization() {
    return specialization;
  }

  public void setSpecialization(Specialization specialization) {
    this.specialization = specialization;
  }

  public int getYearsOfExperience() {
    return yearsOfExperience;
  }

  public void setYearsOfExperience(int yearsOfExperience) {
    this.yearsOfExperience = yearsOfExperience;
  }

  public int calculateYearsOfExperience() {
    int years = LocalDate.now().getYear() - this.registrationDate.getYear();
    if (LocalDate.now().getMonthValue() <= this.registrationDate.getMonthValue()) {
      if (LocalDate.now().getDayOfMonth() < this.registrationDate.getDayOfMonth()) {
        years--;
      }
    }
    return years;
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

  public String toString() {
    return "Doctor ID: " + doctorId + "\nSpecialization: " + specialization + "\nYears of Experience: " + yearsOfExperience + "\nDuty Schedule: " + dutySchedule + "\nAppointment Records: " + appointmentRecords + "\nConsultation Records: " + consultationRecords + "\nTreatment Records: " + treatmentRecords;
  }

  public String toFileString() {
    return doctorId + "|" + specialization + "|" + yearsOfExperience + "|" + dutySchedule + "|" + appointmentRecords + "|" + consultationRecords + "|" + treatmentRecords;
  }

  public String toStringComplete() {
    return "Doctor ID: " + doctorId + "\n" + super.toString() + "\nSpecialization: " + specialization + "\nYears of Experience: " + yearsOfExperience + "\nDuty Schedule: " + dutySchedule + "\nAppointment Records: " + appointmentRecords + "\nConsultation Records: " + consultationRecords + "\nTreatment Records: " + treatmentRecords;
  }

  public String toFileStringComplete() {
    return doctorId + "|" + super.toFileString() + "|" + specialization + "|" + yearsOfExperience + "|" + dutySchedule + "|" + appointmentRecords + "|" + consultationRecords + "|" + treatmentRecords;
  }
}
