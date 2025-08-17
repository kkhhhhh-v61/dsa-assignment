package entity;

import adt.ArrayList;
import utility.Enum.Status;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author khtan
 */
public class Consultation {
  private static int lastId = 0;

  private String consultationId;
  private String patientId;
  private String doctorId;
  private LocalDate consultationDate;
  private LocalTime consultationTime;
  private ArrayList<String> symptoms;
  private ArrayList<String> diagnosis;
  private String notes;
  private Status status;

  public Consultation() {
    this.consultationId = "C" + String.format("%04d", ++lastId);
    this.patientId = "";
    this.doctorId = "";
    this.consultationDate = LocalDate.now();
    this.consultationTime = LocalTime.now();
    this.symptoms = new ArrayList<>();
    this.diagnosis = new ArrayList<>();
    this.notes = "";
    this.status = Status.Unknown;
  }

  public Consultation(String doctorId, String patientId, LocalDate consultationDate, LocalTime consultationTime,
      ArrayList<String> symptoms, ArrayList<String> diagnosis, String notes, Status status) {
    this.consultationId = "C" + String.format("%04d", ++lastId);
    this.patientId = patientId;
    this.doctorId = doctorId;
    this.consultationDate = consultationDate;
    this.consultationTime = consultationTime;
    this.symptoms = symptoms;
    this.diagnosis = diagnosis;
    this.notes = notes;
    this.status = status;
  }

  public String getConsultationId() {
    return consultationId;
  }

  public void setConsultationId(String consultationId) { /* Not recommended for use */
    this.consultationId = consultationId;
  }

  public String getPatientId() {
    return patientId;
  }

  public void setPatientId(String patientId) {
    this.patientId = patientId;
  }

  public String getDoctorId() {
    return doctorId;
  }

  public void setDoctorId(String doctorId) {
    this.doctorId = doctorId;
  }

  public LocalDate getConsultationDate() {
    return consultationDate;
  }

  public void setConsultationDate(LocalDate consultationDate) {
    this.consultationDate = consultationDate;
  }

  public LocalTime getConsuLocalTime() {
    return consultationTime;
  }

  public void setConsultationTime(LocalTime consultationTime) {
    this.consultationTime = consultationTime;
  }

  public ArrayList<String> getSymptoms() {
    return symptoms;
  }

  public void setSymptoms(ArrayList<String> symptoms) {
    this.symptoms = symptoms;
  }

  public ArrayList<String> getDiagnosis() {
    return diagnosis;
  }

  public void setDiagnosis(ArrayList<String> diagnosis) {
    this.diagnosis = diagnosis;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public String toString() {
    return "Consultation ID: " + consultationId + "\nPatient ID: " + patientId + "\nDoctor ID: " + doctorId + "\nConsultation Date: " + consultationDate + "\nConsultation Time: " + consultationTime + "\nSymptoms: " + symptoms + "\nDiagnosis: " + diagnosis + "\nNotes: " + notes + "\nStatus: " + status;
  }

  public String toFileString() {
    return consultationId + "|" + patientId + "|" + doctorId + "|" + consultationDate + "|" + consultationTime + "|" + symptoms + "|" + diagnosis + "|" + notes + "|" + status;
  }
}
