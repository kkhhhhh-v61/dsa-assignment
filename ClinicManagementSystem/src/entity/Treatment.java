package entity;

import adt.ArrayList;
import dao.ConsultationDAO;
import java.io.Serializable;
import utility.Enum.Status;

/**
 *
 * @author Tan Kai Hao
 */
public class Treatment implements Serializable{
  private static final long serialVersionUID = 1L;
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

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Treatment ID: ").append(treatmentId).append("\n");
    sb.append("Consultation ID: ").append(consultationId).append("\n");
    sb.append("Patient ID: ").append(patientId).append("\n");
    sb.append("Doctor ID: ").append(doctorId).append("\n");
    
    // Get consultation details
    Consultation consultation = new ConsultationDAO().getConsultationById(consultationId);
    if (consultation != null) {
        sb.append("Consultation Date: ").append(consultation.getConsultationDate()).append("\n");
        sb.append("Consultation Time: ").append(consultation.getConsuLocalTime()).append("\n");
    }

    sb.append("Notes: ").append(notes).append("\n");
    sb.append("Prescribed Medicines:\n");

    if (medicines == null || medicines.isEmpty()) {
        sb.append("  None\n");
    } else {
        for (int i = 1; i <= medicines.size(); i++) {
            sb.append("  ").append(i).append(". ").append(medicines.get(i)).append("\n");
        }
    }

    return sb.toString();
  }

  
  

  public String toFileString() {
    return treatmentId + "|" + consultationId + "|" + doctorId + "|" + patientId + "|" + notes + "|" + status;
  }
}
