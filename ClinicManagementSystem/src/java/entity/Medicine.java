package entity;

import utility.Enum.MedicineCategory;

public class Medicine {
  private int lastID = 1000;

  private String medicineID;
  private String name;
  private MedicineCategory category;
  private String dosage;
  private int stockQuantity;
  private int reorderLevel;

  public Medicine() {
  }

  public Medicine(String name, MedicineCategory category, String dosage, int stockQuantity, int reorderLevel) {
    this.medicineID = "M" + ++lastID;
    this.name = name;
    this.category = category;
    this.dosage = dosage;
    this.stockQuantity = stockQuantity;
    this.reorderLevel = reorderLevel;
  }

  public String getMedicineID() {
    return medicineID;
  }

  // not recommended to use, included just in case
  public void setMedicineID(String medicineID) {
    this.medicineID = medicineID;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public MedicineCategory getMedicineCategory() {
    return category;
  }

  public void setMedicineCategory(MedicineCategory category) {
    this.category = category;
  }

  public String getDosage() {
    return dosage;
  }

  public void setDosage(String dosage) {
    this.dosage = dosage;
  }

  public int getStockQuantity() {
    return stockQuantity;
  }

  public void setStockQuantity(int stockQuantity) {
    this.stockQuantity = stockQuantity;
  }

  public int getReorderLevel() {
    return reorderLevel;
  }

  public void setReorderLevel(int reorderLevel) {
    this.reorderLevel = reorderLevel;
  }

  public String toString() {
    return String.format("Medicine ID: %s\nName: %s\nCategory: %s\nDosage: %s\nStock Quantity: %s\nReorder Level: %s",
        medicineID, name, category, dosage, stockQuantity, reorderLevel);
  }
}
