package entity;

/**
 *
 * @author Tan Kai Hao
 */
public class PrescribedMedicine {
  private String medicineId;
  private String dosage;
  private int timesPerDay;
  private int duration;
  private String remarks;

  public PrescribedMedicine() {
    this.medicineId = "";
    this.dosage = "";
    this.timesPerDay = 0;
    this.duration = 0;
    this.remarks = "";
  }

  public PrescribedMedicine(String medicineId, String dosage, int timesPerDay, int duration, String remarks) {
    this.medicineId = medicineId;
    this.dosage = dosage;
    this.timesPerDay = timesPerDay;
    this.duration = duration;
    this.remarks = remarks;
  }

  public String getMedicineId() {
    return medicineId;
  }

  public void setMedicineId(String medicineId) {
    this.medicineId = medicineId;
  }

  public String getDosage() {
    return dosage;
  }

  public void setDosage(String dosage) {
    this.dosage = dosage;
  }

  public int getTimesPerDay() {
    return timesPerDay;
  }

  public void setTimesPerDay(int timesPerDay) {
    this.timesPerDay = timesPerDay;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  public String toString() {
    return "Medicine ID: " + medicineId + "\nDosage: " + dosage + "\nTimes Per Day: " + timesPerDay + "\nDuration: " + duration + "\nRemarks: " + remarks;
  }

  public String toFileString() {
    return medicineId + "|" + dosage + "|" + timesPerDay + "|" + duration + "|" + remarks;
  }
}
