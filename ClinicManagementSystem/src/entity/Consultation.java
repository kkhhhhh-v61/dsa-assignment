package entity;

import adt.ArrayList;
import java.io.Serializable;
import utility.Enum.Status;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author Tan Kai Hao
 */
public class Consultation implements Serializable{
  private static final long serialVersionUID = 1L;
  private static int lastId = 0;

  private String consultationId;
  private String appointmentId; 
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
    this.appointmentId = "";
    this.patientId = "";
    this.doctorId = "";
    this.consultationDate = LocalDate.now();
    this.consultationTime = LocalTime.now();
    this.symptoms = new ArrayList<>();
    this.diagnosis = new ArrayList<>();
    this.notes = "";
    this.status = Status.Unknown;
  }

  public Consultation(String appointmentId, String doctorId, String patientId, LocalDate consultationDate, LocalTime consultationTime,
      ArrayList<String> symptoms, ArrayList<String> diagnosis, String notes, Status status) {
    this.consultationId = "C" + String.format("%04d", ++lastId);
    this.appointmentId = appointmentId;
    this.doctorId = doctorId;
    this.patientId = patientId;
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

  public String getAppointmentId() { 
    return appointmentId; 
  }
  public void setAppointmentId(String appointmentId) { 
    this.appointmentId = appointmentId; 
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
    return "Consultation ID: " + consultationId + "\nAppointment ID: " + appointmentId + "\nPatient ID: " + patientId + "\nDoctor ID: " + doctorId + "\nConsultation Date: " + consultationDate + "\nConsultation Time: " + consultationTime + "\nSymptoms: " + formatList(symptoms) + "\nDiagnosis: " + formatList(diagnosis) + "\nNotes: " + notes + "\nStatus: " + status;
  }

  public String toFileString() {
    return consultationId + "|" + appointmentId + "|" + patientId + "|" + doctorId + "|" + consultationDate + "|" + consultationTime + "|" + formatList(symptoms) + "|" + formatList(diagnosis) + "|" + notes + "|" + status;
  }
  
  private String formatList(adt.ArrayList<String> list) {
    if (list == null || list.isEmpty()) {
        return "[]";
    }
    StringBuilder sb = new StringBuilder("[");
    for (int i = 1; i <= list.size(); i++) {   // ✅ 1-based indexing
        sb.append(list.get(i));
        if (i < list.size()) {
            sb.append(", ");
        }
    }
    sb.append("]");
    return sb.toString();
}
}
