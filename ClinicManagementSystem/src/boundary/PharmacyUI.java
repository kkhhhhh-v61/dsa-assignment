package boundary;

import control.PharmacyController;
import entity.Medicine;
import entity.PrescribedMedicine;
import utility.Enum.MedicineCategory;
import utility.MessageUI;
import utility.Validation;
import adt.ArrayList;
import adt.ListInterface;

import java.util.Scanner;

/**
 *
 * @author khtan (original author of existing UIs)
 */
public class PharmacyUI {

    private PharmacyController pharmacyController;
    private Scanner scanner;

    public PharmacyUI(PharmacyController pharmacyController) {
        this.pharmacyController = pharmacyController;
        this.scanner = new Scanner(System.in);
    }

    public void displayPharmacyMenu() {
        int choice;
        do {
            System.out.println("\n--- Pharmacy Management Module ---");
            System.out.println("1. Add New Medicine");
            System.out.println("2. Update Medicine Details");
            System.out.println("3. Delete Medicine");
            System.out.println("4. Dispense Medicines");
            System.out.println("5. View All Medicines");
            System.out.println("6. Generate Low Stock Report");
            System.out.println("7. Generate Medicines by Category Report");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = Validation.getIntegerInput();

            switch (choice) {
                case 1:
                    addNewMedicine();
                    break;
                case 2:
                    updateMedicineDetails();
                    break;
                case 3:
                    deleteMedicine();
                    break;
                case 4:
                    dispenseMedicines();
                    break;
                case 5:
                    viewAllMedicines();
                    break;
                case 6:
                    generateLowStockReport();
                    break;
                case 7:
                    generateMedicineByCategoryReport();
                    break;
                case 0:
                    MessageUI.displayExitMessage("Returning to Main Menu...");
                    break;
                default:
                    MessageUI.displayInvalidChoice();
            }
        } while (choice != 0);
    }

    private void addNewMedicine() {
        System.out.println("\n--- Add New Medicine ---");
        System.out.print("Enter Medicine Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Description: ");
        String description = scanner.nextLine();

        MedicineCategory category = selectMedicineCategory();

        System.out.print("Enter Price: ");
        double price = Validation.getDoubleInput();
        System.out.print("Enter Stock Quantity: ");
        int stockQuantity = Validation.getIntegerInput();
        System.out.print("Enter Reorder Level: ");
        int reorderLevel = Validation.getIntegerInput();

        Medicine newMedicine = new Medicine(name, description, category, price, stockQuantity, reorderLevel);

        if (pharmacyController.addMedicine(newMedicine)) {
            MessageUI.displaySuccessMessage("Medicine added successfully: " + newMedicine.getMedicineId());
        } else {
            MessageUI.displayErrorMessage("Failed to add medicine. Medicine ID might already exist.");
        }
    }

    private void updateMedicineDetails() {
        System.out.println("\n--- Update Medicine Details ---");
        System.out.print("Enter Medicine ID to update: ");
        String medicineId = scanner.nextLine();

        Medicine existingMedicine = pharmacyController.getMedicineById(medicineId);
        if (existingMedicine == null) {
            MessageUI.displayErrorMessage("Medicine not found.");
            return;
        }

        System.out.println("Current Details for " + existingMedicine.getName() + ":");
        System.out.println(existingMedicine);

        System.out.print("Enter New Name (leave blank to keep current: " + existingMedicine.getName() + "): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            existingMedicine.setName(name);
        }

        System.out.print("Enter New Description (leave blank to keep current: " + existingMedicine.getDescription() + "): ");
        String description = scanner.nextLine();
        if (!description.isEmpty()) {
            existingMedicine.setDescription(description);
        }

        System.out.print("Change Category? (y/n, current: " + existingMedicine.getCategory().name() + "): ");
        String changeCategory = scanner.nextLine();
        if (changeCategory.equalsIgnoreCase("y")) {
            existingMedicine.setCategory(selectMedicineCategory());
        }

        System.out.print("Enter New Price (0 to keep current: " + existingMedicine.getPrice() + "): ");
        double price = Validation.getDoubleInput();
        if (price != 0) {
            existingMedicine.setPrice(price);
        }

        System.out.print("Enter New Stock Quantity (0 to keep current: " + existingMedicine.getStockQuantity() + "): ");
        int stockQuantity = Validation.getIntegerInput();
        if (stockQuantity != 0) {
            existingMedicine.setStockQuantity(stockQuantity);
        }

        System.out.print("Enter New Reorder Level (0 to keep current: " + existingMedicine.getReorderLevel() + "): ");
        int reorderLevel = Validation.getIntegerInput();
        if (reorderLevel != 0) {
            existingMedicine.setReorderLevel(reorderLevel);
        }

        if (pharmacyController.updateMedicine(existingMedicine)) {
            MessageUI.displaySuccessMessage("Medicine updated successfully.");
        } else {
            MessageUI.displayErrorMessage("Failed to update medicine.");
        }
    }

    private void deleteMedicine() {
        System.out.println("\n--- Delete Medicine ---");
        System.out.print("Enter Medicine ID to delete: ");
        String medicineId = scanner.nextLine();

        if (pharmacyController.deleteMedicine(medicineId)) {
            MessageUI.displaySuccessMessage("Medicine deleted successfully.");
        } else {
            MessageUI.displayErrorMessage("Failed to delete medicine. Medicine not found.");
        }
    }

    private void dispenseMedicines() {
        System.out.println("\n--- Dispense Medicines ---");
        System.out.println("This function simulates dispensing medicines based on a prescription.");
        System.out.println("Enter prescribed medicines one by one. Type 'done' when finished.");

        ListInterface<PrescribedMedicine> prescribedMedicines = new ArrayList<>();
        String input;
        do {
            System.out.print("Enter Prescribed Medicine ID (or 'done' to finish): ");
            input = scanner.nextLine();
            if (!input.equalsIgnoreCase("done")) {
                String medicineId = input;
                Medicine medicine = pharmacyController.getMedicineById(medicineId);
                if (medicine == null) {
                    MessageUI.displayErrorMessage("Medicine with ID " + medicineId + " not found in stock. Please check ID.");
                    continue;
                }
                System.out.print("Enter Dosage for " + medicine.getName() + ": ");
                String dosage = scanner.nextLine();
                System.out.print("Enter Times Per Day: ");
                int timesPerDay = Validation.getIntegerInput();
                System.out.print("Enter Duration (in days): ");
                int duration = Validation.getIntegerInput();
                System.out.print("Enter Remarks (e.g., 'Take after food'): ");
                String remarks = scanner.nextLine();

                prescribedMedicines.add(new PrescribedMedicine(medicineId, dosage, timesPerDay, duration, remarks));
                MessageUI.displaySuccessMessage("Prescribed medicine added to list for dispensing.");
            }
        } while (!input.equalsIgnoreCase("done"));

        if (prescribedMedicines.size() > 0) {
            if (pharmacyController.dispenseMedicines(prescribedMedicines)) {
                MessageUI.displaySuccessMessage("All prescribed medicines dispensed successfully.");
            } else {
                MessageUI.displayErrorMessage("Some medicines could not be dispensed due to insufficient stock or invalid IDs. Check console for details.");
            }
        } else {
            System.out.println("No medicines entered for dispensing.");
        }
    }

    private void viewAllMedicines() {
        System.out.println("\n--- All Medicines in Stock ---");
        ListInterface<Medicine> medicines = pharmacyController.getAllMedicines();
        if (medicines.isEmpty()) {
            System.out.println("No medicines currently in stock.");
            return;
        }

        System.out.printf("%-15s %-25s %-20s %-10s %-10s %-15s %-15s\n",
                "ID", "Name", "Category", "Price", "Stock", "Reorder Level", "Description");
        System.out.println("-------------------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < medicines.size(); i++) {
            Medicine m = medicines.getEntry(i);
            System.out.printf("%-15s %-25s %-20s %-10.2f %-10d %-15d %-15s\n",
                    m.getMedicineId(),
                    m.getName(),
                    m.getCategory().name(),
                    m.getPrice(),
                    m.getStockQuantity(),
                    m.getReorderLevel(),
                    m.getDescription().length() > 12 ? m.getDescription().substring(0, 9) + "..." : m.getDescription());
        }
        System.out.println("-------------------------------------------------------------------------------------------------------------------");
    }

    private void generateLowStockReport() {
        System.out.println(pharmacyController.generateLowStockReport());
    }

    private void generateMedicineByCategoryReport() {
        System.out.println(pharmacyController.generateMedicineByCategoryReport());
    }

    private MedicineCategory selectMedicineCategory() {
        MedicineCategory[] categories = MedicineCategory.values();
        int choice;
        do {
            System.out.println("Select Medicine Category:");
            for (int i = 0; i < categories.length; i++) {
                if (categories[i] != MedicineCategory.Unknown) { // Skip Unknown category for selection
                    System.out.println((i + 1) + ". " + categories[i].name());
                }
            }
            System.out.print("Enter category number: ");
            choice = Validation.getIntegerInput();
            if (choice >= 1 && choice <= categories.length && categories[choice - 1] != MedicineCategory.Unknown) {
                return categories[choice - 1];
            } else {
                MessageUI.displayInvalidChoice();
            }
        } while (true);
    }
}
