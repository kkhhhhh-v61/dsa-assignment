package entity;

<<<<<<< Updated upstream
public class PrescribedMedicine {
  private String medicineId;
=======
import java.io.Serializable;

/**
 *
 
 */
public class PrescribedMedicine implements Serializable {
  private Medicine medicine;
  private int quantity;
>>>>>>> Stashed changes
  private String dosage;
  private int timesPerDay;
  private int duration;
  private String remarks;

  public PrescribedMedicine() {
    this.medicine = null;
    this.quantity = 0;
    this.dosage = "";
    this.timesPerDay = 0;
    this.duration = 0;
    this.remarks = "";
  }

  public PrescribedMedicine(Medicine medicine, int quantity, String dosage, int timesPerDay, int duration, String remarks) {
    this.medicine = medicine;
    this.quantity = quantity;
    this.dosage = dosage;
    this.timesPerDay = timesPerDay;
    this.duration = duration;
    this.remarks = remarks;
  }

  public Medicine getMedicine() {
    return medicine;
  }

  public void setMedicine(Medicine medicine) {
    this.medicine = medicine;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
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
    return "Medicine: " + medicine.getName() + " (ID: " + medicine.getMedicineId() + ")" +
           "\nQuantity: " + quantity +
           "\nDosage: " + dosage +
           "\nTimes Per Day: " + timesPerDay +
           "\nDuration: " + duration +
           "\nRemarks: " + remarks;
  }

  public String toFileString() {
    return medicine.getMedicineId() + "|" + quantity + "|" + dosage + "|" + timesPerDay + "|" + duration + "|" + remarks;
  }
}
