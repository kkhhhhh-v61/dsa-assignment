package client;

import adt.ArrayList;
import entity.Person;
import entity.Patient;
import entity.Doctor;
import entity.Schedule;
import entity.Medicine;
import entity.Appointment;
import entity.Consultation;
import entity.Treatment;
import entity.PrescribedMedicine;
import utility.Enum.Allergy;
import utility.Enum.BloodType;
import utility.Enum.DayOfWeek;
import utility.Enum.Gender;
import utility.Enum.MedicineCategory;
import utility.Enum.Specialization;
import utility.Enum.Status;
import control.PharmacyController;
import adt.ListInterface;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author khtan
 */
public class TestDriver {
  public static void main(String[] args) {
    // Test Pharmacy Management Module
    System.out.println("--- Testing Pharmacy Management Module ---");

    PharmacyController pharmacyController = new PharmacyController();

    // Clear existing medicines for a clean test environment
    ListInterface<Medicine> initialMedicines = pharmacyController.getAllMedicines();
    if (!initialMedicines.isEmpty()) {
        System.out.println("Clearing existing medicines for testing...");
        for (int i = initialMedicines.size(); i >= 1; i--) {
            pharmacyController.deleteMedicine(initialMedicines.getEntry(i).getMedicineId());
        }
    }

    // Test Case 1: Add New Medicines
    System.out.println("\n--- Test Case 1: Add New Medicines ---");
    Medicine med1 = new Medicine("Paracetamol", "Pain reliever", utility.Enum.MedicineCategory.Tablet, 5.50, 100, 20);
    Medicine med2 = new Medicine("Amoxicillin", "Antibiotic", utility.Enum.MedicineCategory.Capsule, 12.00, 50, 10);
    Medicine med3 = new Medicine("Vitamin C", "Supplement", utility.Enum.MedicineCategory.Tablet, 8.20, 200, 50);
    Medicine med4 = new Medicine("Ibuprofen", "Anti-inflammatory", utility.Enum.MedicineCategory.Tablet, 7.00, 15, 20); // Low stock for report

    System.out.println("Adding " + med1.getName() + ": " + pharmacyController.addMedicine(med1));
    System.out.println("Adding " + med2.getName() + ": " + pharmacyController.addMedicine(med2));
    System.out.println("Adding " + med3.getName() + ": " + pharmacyController.addMedicine(med3));
    System.out.println("Adding " + med4.getName() + ": " + pharmacyController.addMedicine(med4));
    System.out.println("Adding duplicate " + med1.getName() + ": " + pharmacyController.addMedicine(med1)); // Should fail
    
    // Test Case 1.1: Add Medicine with Invalid Stock Quantity
    System.out.println("\n--- Test Case 1.1: Add Medicine with Invalid Stock Quantity ---");
    Medicine medInvalidStock = new Medicine("Invalid Stock Med", "Test", utility.Enum.MedicineCategory.Tablet, 1.00, -10, 5);
    System.out.println("Adding " + medInvalidStock.getName() + " (invalid stock): " + pharmacyController.addMedicine(medInvalidStock)); // Should fail if Medicine constructor/setter validates

    // Test Case 2: View All Medicines
    System.out.println("\n--- Test Case 2: View All Medicines ---");
    ListInterface<Medicine> allMedicines = pharmacyController.getAllMedicines();
    if (!allMedicines.isEmpty()) {
        for (int i = 1; i <= allMedicines.size(); i++) {
            System.out.println(allMedicines.getEntry(i));
        }
    } else {
        System.out.println("No medicines found after adding.");
    }

    // Test Case 3: Get Medicine by ID
    System.out.println("\n--- Test Case 3: Get Medicine by ID ---");
    Medicine foundMed = pharmacyController.getMedicineById(med2.getMedicineId());
    System.out.println("Found Medicine (Amoxicillin): " + (foundMed != null ? foundMed.getName() : "Not Found"));
    Medicine notFoundMed = pharmacyController.getMedicineById("M9999");
    System.out.println("Found Medicine (M9999): " + (notFoundMed != null ? notFoundMed.getName() : "Not Found"));

    // Test Case 4: Update Medicine Details
    System.out.println("\n--- Test Case 4: Update Medicine Details ---");
    Medicine med1ToUpdate = pharmacyController.getMedicineById(med1.getMedicineId());
    if (med1ToUpdate != null) {
        med1ToUpdate.setName("Paracetamol (New Formula)");
        med1ToUpdate.setDescription("Improved pain relief");
        med1ToUpdate.setCategory(utility.Enum.MedicineCategory.Tablet);
        med1ToUpdate.setPrice(6.00);
        med1ToUpdate.setStockQuantity(120);
        med1ToUpdate.setReorderLevel(25);
        System.out.println("Updating " + med1.getName() + ": " + pharmacyController.updateMedicine(med1ToUpdate));
        System.out.println("Updated Medicine 1: " + pharmacyController.getMedicineById(med1.getMedicineId()));
    } else {
        System.out.println("Error: Medicine 1 not found for update.");
    }

    // Test Case 5: Dispense Medicines
    System.out.println("\n--- Test Case 5: Dispense Medicines ---");
    ListInterface<PrescribedMedicine> prescribedList = new ArrayList<>();
    prescribedList.add(new PrescribedMedicine(med1.getMedicineId(), "1 tablet", 2, 5, "After food")); // 10 units
    prescribedList.add(new PrescribedMedicine(med2.getMedicineId(), "1 capsule", 1, 7, "Before sleep")); // 7 units
    prescribedList.add(new PrescribedMedicine("M9999", "1 unit", 1, 1, "Invalid medicine")); // Invalid ID
    prescribedList.add(new PrescribedMedicine(med4.getMedicineId(), "1 tablet", 3, 10, "With water")); // 30 units (should cause insufficient stock)

    System.out.println("Dispensing medicines: " + pharmacyController.dispenseMedicines(prescribedList));
    System.out.println("Stock after dispensing for " + med1.getName() + ": " + pharmacyController.getMedicineById(med1.getMedicineId()).getStockQuantity());
    System.out.println("Stock after dispensing for " + med2.getName() + ": " + pharmacyController.getMedicineById(med2.getMedicineId()).getStockQuantity());
    System.out.println("Stock after dispensing for " + med4.getName() + ": " + pharmacyController.getMedicineById(med4.getMedicineId()).getStockQuantity()); // Should be unchanged

    // Test Case 5.1: Dispense Empty List
    System.out.println("\n--- Test Case 5.1: Dispense Empty List ---");
    ListInterface<PrescribedMedicine> emptyPrescribedList = new ArrayList<>();
    System.out.println("Dispensing empty list: " + pharmacyController.dispenseMedicines(emptyPrescribedList)); // Should return true (no operations, so no failures)

    // Test Case 5.2: Dispense All Insufficient Stock
    System.out.println("\n--- Test Case 5.2: Dispense All Insufficient Stock ---");
    ListInterface<PrescribedMedicine> allInsufficientList = new ArrayList<>();
    allInsufficientList.add(new PrescribedMedicine(med1.getMedicineId(), "1 tablet", 100, 100, "Large quantity")); // Exceeds stock
    allInsufficientList.add(new PrescribedMedicine(med2.getMedicineId(), "1 capsule", 100, 100, "Large quantity")); // Exceeds stock
    System.out.println("Dispensing all insufficient stock: " + pharmacyController.dispenseMedicines(allInsufficientList)); // Should return false
    System.out.println("Stock after all insufficient for " + med1.getName() + ": " + pharmacyController.getMedicineById(med1.getMedicineId()).getStockQuantity()); // Should be unchanged
    System.out.println("Stock after all insufficient for " + med2.getName() + ": " + pharmacyController.getMedicineById(med2.getMedicineId()).getStockQuantity()); // Should be unchanged

    // Test Case 6: Generate Low Stock Report
    System.out.println("\n--- Test Case 6: Generate Low Stock Report ---");
    System.out.println(pharmacyController.generateLowStockReport());

    // Test Case 7: Generate Medicines by Category Report
    System.out.println("\n--- Test Case 7: Generate Medicines by Category Report ---");
    System.out.println(pharmacyController.generateMedicineByCategoryReport());

    // Test Case 8: Delete Medicine
    System.out.println("\n--- Test Case 8: Delete Medicine ---");
    System.out.println("Deleting " + med3.getName() + ": " + pharmacyController.deleteMedicine(med3.getMedicineId()));
    System.out.println("Deleting non-existent medicine M9999: " + pharmacyController.deleteMedicine("M9999"));
    System.out.println("Medicines after deletion:");
    allMedicines = pharmacyController.getAllMedicines();
    if (!allMedicines.isEmpty()) {
        for (int i = 1; i <= allMedicines.size(); i++) {
            System.out.println(allMedicines.getEntry(i));
        }
    } else {
        System.out.println("No medicines found after deletion.");
    }
  }
}
