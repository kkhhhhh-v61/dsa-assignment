package entity;

import java.time.LocalDate;
import java.time.LocalTime;

import adt.ArrayList;

public class Consultation {
  private int lastID = 1000;

  private String consultationID;
  private String patientID;
  private String doctorID;
  private LocalDate consultationDate;
  private LocalTime consultationTime;
  private ArrayList<String> symptoms;
  private ArrayList<String> diagnosis;
  private String remark;

  public Consultation() {
  }

  public Consultation(String patientID, String doctorID, LocalDate consultationDate, LocalTime consultationTime,
      ArrayList<String> symptoms, ArrayList<String> diagnosis, String remark) {
    this.consultationID = "C" + ++lastID;
    this.patientID = patientID;
    this.doctorID = doctorID;
    this.consultationDate = consultationDate;
    this.consultationTime = consultationTime;
    this.symptoms = symptoms;
    this.diagnosis = diagnosis;
    this.remark = remark;
  }

  public String getConsultationID() {
    return consultationID;
  }

  // not recommended to use, included just in case
  public void setConsultationID(String consultationID) {
    this.consultationID = consultationID;
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

  public LocalDate getConsultationDate() {
    return consultationDate;
  }

  public void setConsultationDate(LocalDate consultationDate) {
    this.consultationDate = consultationDate;
  }

  public LocalTime getConsultationTime() {
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

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String toString() {
    return String.format("Consultation ID: %s\nPatient ID: %s\nDoctor ID: %s\nConsultation Date: %s\nConsultation Time: %s\nSymptoms: %s\nDiagnosis: %s\nRemark: %s",
        consultationID, patientID, doctorID, consultationDate, consultationTime, symptoms, diagnosis, remark);
  }
}

