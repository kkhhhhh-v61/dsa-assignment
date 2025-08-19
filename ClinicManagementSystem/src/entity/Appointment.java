package entity;

import utility.Enum.Status;
import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
  private static int lastId = 0000;

  private String appointmentId;
  private String patientId;
  private String doctorId;
  private LocalDate appointmentDate;
  private LocalTime appointmentTime;
  private String reason;
  private Status status;

  public Appointment() {
    this.appointmentId = "A" + String.format("%04d", ++lastId);
    this.patientId = "";
    this.doctorId = "";
    this.appointmentDate = LocalDate.now();
    this.appointmentTime = LocalTime.now();
    this.reason = "";
    this.status = Status.Unknown;
  }

  public Appointment(String patientId, String doctorId, LocalDate appointmentDate, LocalTime appointmentTime,
      String reason, Status status) {
    this.appointmentId = "A" + String.format("%04d", ++lastId);
    this.patientId = patientId;
    this.doctorId = doctorId;
    this.appointmentDate = appointmentDate;
    this.appointmentTime = appointmentTime;
    this.reason = reason;
    this.status = status;
  }
  
  public String getAppointmentId() {
    return appointmentId;
  }

  public void setAppointmentId(String appointmentId) { /* Not recommended for use */
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

  public LocalDate getAppointmentDate() {
    return appointmentDate;
  }

  public void setAppointmentDate(LocalDate appointmentDate) {
    this.appointmentDate = appointmentDate;
  }

  public LocalTime getAppointmentTime() {
    return appointmentTime;
  }

  public void setAppointmentTime(LocalTime appointmentTime) {
    this.appointmentTime = appointmentTime;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public String toString() {
    return "Appointment ID: " + appointmentId + "\nPatient ID: " + patientId + "\nDoctor ID: " + doctorId + "\nAppointment Date: " + appointmentDate + "\nAppointment Time: " + appointmentTime + "\nReason: " + reason + "\nStatus: " + status;
  }

  public String toFileString() {
    return appointmentId + "|" + patientId + "|" + doctorId + "|" + appointmentDate + "|" + appointmentTime + "|" + reason + "|" + status;
  }
}
