package entity;

import utility.Enum.Status;
import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
  private static int lastId = 0000;

  private String appointmentId;
  private Patient patient;
  private Doctor doctor;
  private LocalDate appointmentDate;
  private LocalTime appointmentTime;
  private String reason;
  private Status status;

  public Appointment() {
    this.appointmentId = "A" + String.format("%04d", ++lastId);
    this.patient = null;
    this.doctor = null;
    this.appointmentDate = LocalDate.now();
    this.appointmentTime = LocalTime.now();
    this.reason = "";
    this.status = Status.Unknown;
  }

  public Appointment(Patient patient, Doctor doctor, LocalDate appointmentDate, LocalTime appointmentTime,
      String reason, Status status) {
    this.appointmentId = "A" + String.format("%04d", ++lastId);
    this.patient = patient;
    this.doctor = doctor;
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

  public Patient getPatient() {
    return patient;
  }

  public void setPatient(Patient patient) {
    this.patient = patient;
  }

  public Doctor getDoctor() {
    return doctor;
  }

  public void setDoctor(Doctor doctor) {
    this.doctor = doctor;
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
    return "Appointment ID: " + appointmentId + "\nPatient ID: " + patient.getPatientId() + "\nDoctor ID: " + doctor.getDoctorId() + "\nAppointment Date: " + appointmentDate + "\nAppointment Time: " + appointmentTime + "\nReason: " + reason + "\nStatus: " + status;
  }

  public String toFileString() {
    return appointmentId + "|" + patient.getPatientId() + "|" + doctor.getDoctorId() + "|" + appointmentDate + "|" + appointmentTime + "|" + reason + "|" + status;
  }
}
