package entity;

import adt.ArrayList;
import utility.Enum.Status;

/**
 *
 * @author Tan Kai Hao
 */
public class Treatment {
  private static int lastId = 0;

  private String treatmentId;
  private String consultationId;
  private String doctorId;
  private String patientId;
  private ArrayList<PrescribedMedicine> medicines;
  private String notes;
  private Status status;

  public Treatment() {
    this.treatmentId = "T" + String.format("%04d", ++lastId);
    this.consultationId = "";
    this.doctorId = "";
    this.patientId = "";
    this.medicines = new ArrayList<>();
    this.notes = "";
    this.status = Status.Unknown;
  }

  public Treatment(String consultationId, String doctorId, String patientId, ArrayList<PrescribedMedicine> medicines,
      String notes, Status status) {
    this.treatmentId = "T" + String.format("%04d", ++lastId);
    this.consultationId = consultationId;
    this.doctorId = doctorId;
    this.patientId = patientId;
    this.medicines = medicines;
    this.notes = notes;
    this.status = status;
  }

  public String getTreatmentId() {
    return treatmentId;
  }

  public void setTreatmentId(String treatmentId) { /* Not recommended for use */
    this.treatmentId = treatmentId;
  }

  public String getConsultationId() {
    return consultationId;
  }

  public void setConsultationId(String consultationId) {
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

  public ArrayList<PrescribedMedicine> getMedicines() {
    return medicines;
  }

  public void setMedicines(ArrayList<PrescribedMedicine> medicines) {
    this.medicines = medicines;
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
    return "Treatment ID: " + treatmentId + "\nConsultation ID: " + consultationId + "\nDoctor ID: " + doctorId + "\nPatient ID: " + patientId + "\nNotes: " + notes + "\nStatus: " + status;
  }

  public String toFileString() {
    return treatmentId + "|" + consultationId + "|" + doctorId + "|" + patientId + "|" + notes + "|" + status;
  }
}
