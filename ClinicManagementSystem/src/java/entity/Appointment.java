package entity;

import java.time.LocalDate;
import java.time.LocalTime;
import utility.Format;
import utility.Enum.AppointmentStatus;


public class Appointment {
  private int lastID = 1000;

  private String appointmentID;
  private String patientID;
  private String doctorID;
  private String reason;
  private String remark;
  private LocalDate appointmentDate;
  private LocalTime appointmentTime;
  private LocalDate registeredDate;
  private LocalTime registeredTime;
  private AppointmentStatus status;

  public Appointment() {
  }

  public Appointment(String patientID, String doctorID, String reason, String remark, LocalDate appointmentDate, LocalTime appointmentTime) {
    this.appointmentID = "A" + ++lastID;
    this.patientID = patientID;
    this.doctorID = doctorID;
    this.reason = reason;
    this.remark = remark;
    this.appointmentDate = appointmentDate;
    this.appointmentTime = appointmentTime;
    this.registeredDate = LocalDate.now();
    this.registeredTime = LocalTime.now();
    this.status = AppointmentStatus.Pending;
  }
  
  public String getAppointmentID() {
    return appointmentID;
  }

  // not recommended to use, included just in case
  public void setAppointmentID(String appointmentID) {
    this.appointmentID = appointmentID;
  }

  public String getPatientID() {
    return patientID;
  }

  public void setPatientID(String patientID) {
    this.patientID = patientID;
  }

  public String getDoctorID() {
    return doctorID;
  }

  public void setDoctorID(String doctorID) {
    this.doctorID = doctorID;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
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

  public LocalDate getRegisteredDate() {
    return registeredDate;
  }

  public void setRegisteredDate(LocalDate registeredDate) {
    this.registeredDate = registeredDate;
  }

  public LocalTime getRegisteredTime() {
    return registeredTime;
  }

  public void setRegisteredTime(LocalTime registeredTime) {
    this.registeredTime = registeredTime;
  }

  public AppointmentStatus getStatus() {
    return status;
  }

  public void setStatus(AppointmentStatus status) {
    this.status = status;
  }

  public String toString() {
    return String.format("Appointment ID: %s\nPatient ID: %s\nDoctor ID: %s\nReason: %s\nRemark: %s\nAppointment Date: %s\nAppointment Time: %s\nRegistered Date: %s\nRegistered Time: %s\nStatus: %s",
        appointmentID, patientID, doctorID, reason, remark, Format.formatDate(appointmentDate), Format.formatTime(appointmentTime), Format.formatDate(registeredDate), Format.formatTime(registeredTime), status);
  }
}
