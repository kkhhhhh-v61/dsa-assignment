package entity;

import utility.Enum.MedicineCategory;
import java.io.Serializable;

/**
 *
 * @author Lai Chee Kit
 */
public class Medicine implements Serializable {
  private static final long serialVersionUID = 1L;
  private static int lastId = 0;

  private String medicineId;
  private String name;
  private String description;
  private MedicineCategory category;
  private double price;
  private int stockQuantity;
  private int reorderLevel;

  public Medicine() {
    this.medicineId = "M" + String.format("%04d", ++lastId);
    this.name = "";
    this.description = "";
    this.category = MedicineCategory.Unknown;
    this.price = 0.0;
    this.stockQuantity = 0;
    this.reorderLevel = 0;
  }

  public Medicine(String name, String description, MedicineCategory category, double price, int stockQuantity,
      int reorderLevel) {
    this.medicineId = "M" + String.format("%04d", ++lastId);
    this.name = name;
    this.description = description;
    this.category = category;
    this.price = price;
    this.stockQuantity = stockQuantity;
    this.reorderLevel = reorderLevel;
  }
  
  public String getMedicineId() {
    return medicineId;
  }
  
  public void setMedicineId(String medicineId) { /* Not recommended for use */
    this.medicineId = medicineId;
  }

  public static void setLastId(int id) {
    lastId = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public MedicineCategory getCategory() {
    return category;
  }

  public void setCategory(MedicineCategory category) {
    this.category = category;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
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
    return "Medicine ID: " + medicineId + "\nName: " + name + "\nDescription: " + description + "\nCategory: " + category + "\nPrice: " + price + "\nStock Quantity: " + stockQuantity + "\nReorder Level: " + reorderLevel;
  }

  public String toFileString() {
    return medicineId + "|" + name + "|" + description + "|" + category + "|" + price + "|" + stockQuantity + "|" + reorderLevel;
  }
}
