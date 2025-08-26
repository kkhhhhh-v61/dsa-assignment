package boundary;

import adt.ListInterface;
import control.PharmacyController;
import entity.Medicine;
import entity.PrescribedMedicine;
import utility.Enum.MedicineCategory;
import utility.MessageUI;
import utility.Validation;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class PharmacyUI {
    private PharmacyController pharmacyController;
    private Scanner scanner;

    public PharmacyUI() {
        pharmacyController = new PharmacyController();
        scanner = new Scanner(System.in);
    }

    public void displayPharmacyMenu() {
        int choice;
        do {
            System.out.println("\n--- Pharmacy Management Module ---");
            System.out.println("1. Add New Medicine");
            System.out.println("2. Update Medicine Details");
            System.out.println("3. Delete Medicine");
            System.out.println("4. View All Medicines");
            System.out.println("5. Dispense Medicines");
            System.out.println("6. Generate Low Stock Report");
            System.out.println("7. Generate Medicine by Category Report");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
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
                        viewAllMedicines();
                        break;
                    case 5:
                        dispenseMedicines();
                        break;
                    case 6:
                        generateLowStockReport();
                        break;
                    case 7:
                        generateMedicineByCategoryReport();
                        break;
                    case 0:
                        MessageUI.displayMessage("Returning to Main Menu...");
                        break;
                    default:
                        MessageUI.displayErrorMessage("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                MessageUI.displayErrorMessage("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
                choice = -1; // Set to an invalid choice to continue the loop
            }
        } while (choice != 0);
    }

    public void addNewMedicine() {
        System.out.println("\n--- Add New Medicine ---");
        System.out.println("Enter '0' to return to Pharmacy Menu.");

        System.out.print("Enter Medicine Name: ");
        String name = scanner.nextLine();
        if (name.equals("0")) return;

        System.out.print("Enter Medicine Description: ");
        String description = scanner.nextLine();
        if (description.equals("0")) return;

        MedicineCategory category = selectMedicineCategory();
        if (category == null) return; // User exited from category selection

        double price = Validation.getValidDoubleInput(scanner, "Enter Medicine Price (0 to exit): ");
        if (price == 0) return;

        int stockQuantity = Validation.getValidIntegerInput(scanner, "Enter Stock Quantity (0 to exit): ");
        if (stockQuantity == 0) return;

        int reorderLevel = Validation.getValidIntegerInput(scanner, "Enter Reorder Level (0 to exit): ");
        if (reorderLevel == 0) return;

        Medicine newMedicine = new Medicine(name, description, category, price, stockQuantity, reorderLevel);
        if (pharmacyController.addMedicine(newMedicine)) {
            MessageUI.displaySuccessMessage("Medicine added successfully! ID: " + newMedicine.getMedicineId());
        } else {
            MessageUI.displayErrorMessage("Failed to add medicine. Medicine ID might already exist.");
        }
    }

    public void updateMedicineDetails() {
        System.out.println("\n--- Update Medicine Details ---");
        System.out.println("Enter '0' to return to Pharmacy Menu.");

        System.out.print("Enter Medicine ID to update: ");
        String medicineId = scanner.nextLine();
        if (medicineId.equals("0")) return;

        Medicine existingMedicine = pharmacyController.getMedicineById(medicineId);

        if (existingMedicine == null) {
            MessageUI.displayErrorMessage("Medicine with ID " + medicineId + " not found.");
            return;
        }

        System.out.println("Current Details for " + existingMedicine.getName() + ":");
        System.out.println(existingMedicine);

        System.out.print("Enter New Medicine Name (leave blank to keep current, or '0' to exit: " + existingMedicine.getName() + "): ");
        String name = scanner.nextLine();
        if (name.equals("0")) return;
        if (!name.isEmpty()) {
            existingMedicine.setName(name);
        }

        System.out.print("Enter New Medicine Description (leave blank to keep current, or '0' to exit: " + existingMedicine.getDescription() + "): ");
        String description = scanner.nextLine();
        if (description.equals("0")) return;
        if (!description.isEmpty()) {
            existingMedicine.setDescription(description);
        }

        System.out.print("Update Medicine Category? (y/n, or '0' to exit): ");
        String updateCategoryChoice = scanner.nextLine();
        if (updateCategoryChoice.equals("0")) return;
        if (updateCategoryChoice.equalsIgnoreCase("y")) {
            MedicineCategory category = selectMedicineCategory();
            if (category == null) return; // User exited from category selection
            existingMedicine.setCategory(category);
        }

        double price = Validation.getValidDoubleInput(scanner, "Enter New Medicine Price (current: " + existingMedicine.getPrice() + ", enter 0 to keep current or exit): ");
        if (price == 0) {
            System.out.print("Keep current price? (y/n): ");
            if (scanner.nextLine().equalsIgnoreCase("n")) {
                return; // User chose to exit
            }
        } else {
            existingMedicine.setPrice(price);
        }

        int stockQuantity = Validation.getValidIntegerInput(scanner, "Enter New Stock Quantity (current: " + existingMedicine.getStockQuantity() + ", enter 0 to keep current or exit): ");
        if (stockQuantity == 0) {
            System.out.print("Keep current stock quantity? (y/n): ");
            if (scanner.nextLine().equalsIgnoreCase("n")) {
                return; // User chose to exit
            }
        } else {
            existingMedicine.setStockQuantity(stockQuantity);
        }

        int reorderLevel = Validation.getValidIntegerInput(scanner, "Enter New Reorder Level (current: " + existingMedicine.getReorderLevel() + ", enter 0 to keep current or exit): ");
        if (reorderLevel == 0) {
            System.out.print("Keep current reorder level? (y/n): ");
            if (scanner.nextLine().equalsIgnoreCase("n")) {
                return; // User chose to exit
            }
        } else {
            existingMedicine.setReorderLevel(reorderLevel);
        }

        if (pharmacyController.updateMedicine(existingMedicine)) {
            MessageUI.displaySuccessMessage("Medicine details updated successfully!");
        } else {
            MessageUI.displayErrorMessage("Failed to update medicine details.");
        }
    }

    public void deleteMedicine() {
        System.out.println("\n--- Delete Medicine ---");
        System.out.println("Enter '0' to return to Pharmacy Menu.");
        System.out.print("Enter Medicine ID to delete: ");
        String medicineId = scanner.nextLine();
        if (medicineId.equals("0")) return;

        if (pharmacyController.deleteMedicine(medicineId)) {
            MessageUI.displaySuccessMessage("Medicine " + medicineId + " deleted successfully!");
        } else {
            MessageUI.displayErrorMessage("Failed to delete medicine. Medicine with ID " + medicineId + " not found.");
        }
    }

    public void dispenseMedicines() {
        System.out.println("\n--- Dispense Medicines ---");
        System.out.println("Enter '0' to return to Pharmacy Menu at any prompt.");
        ListInterface<PrescribedMedicine> prescribedMedicines = new adt.ArrayList<>();
        String continueDispensing;

        do {
            System.out.print("Enter Medicine ID to dispense: ");
            String medicineId = scanner.nextLine();
            if (medicineId.equals("0")) return;

            Medicine medicine = pharmacyController.getMedicineById(medicineId);

            if (medicine == null) {
                MessageUI.displayErrorMessage("Medicine with ID " + medicineId + " not found.");
            } else {
                System.out.println("Medicine found: " + medicine.getName() + " (Stock: " + medicine.getStockQuantity() + ")");
                int quantity = Validation.getValidIntegerInput(scanner, "Enter quantity to dispense (0 to exit): ");
                if (quantity == 0) return;

                if (quantity > 0 && medicine.getStockQuantity() >= quantity) {
                    System.out.print("Enter Dosage (0 to exit): ");
                    String dosage = scanner.nextLine();
                    if (dosage.equals("0")) return;

                    int timesPerDay = Validation.getValidIntegerInput(scanner, "Enter Times Per Day (0 to exit): ");
                    if (timesPerDay == 0) return;

                    int duration = Validation.getValidIntegerInput(scanner, "Enter Duration (days, 0 to exit): ");
                    if (duration == 0) return;

                    System.out.print("Enter Remarks (0 to exit): ");
                    String remarks = scanner.nextLine();
                    if (remarks.equals("0")) return;

                    prescribedMedicines.add(new PrescribedMedicine(medicine, quantity, dosage, timesPerDay, duration, remarks));
                    MessageUI.displaySuccessMessage("Medicine added to dispense list.");
                } else {
                    MessageUI.displayErrorMessage("Invalid quantity or insufficient stock for " + medicine.getName() + ".");
                }
            }

            System.out.print("Dispense another medicine? (y/n, or '0' to exit): ");
            continueDispensing = scanner.nextLine();
            if (continueDispensing.equals("0")) return;
        } while (continueDispensing.equalsIgnoreCase("y"));

        if (!prescribedMedicines.isEmpty()) {
            if (pharmacyController.dispenseMedicines(prescribedMedicines)) {
                MessageUI.displaySuccessMessage("All prescribed medicines dispensed successfully!");
            } else {
                MessageUI.displayErrorMessage("Some medicines could not be dispensed. Check error messages above.");
            }
        } else {
            MessageUI.displayMessage("No medicines selected for dispensing.");
        }
    }

    public void viewAllMedicines() {
        System.out.println("\n--- All Medicines ---");
        System.out.println("Enter '0' to return to Pharmacy Menu.");
        ListInterface<Medicine> medicines = pharmacyController.getAllMedicines();

        if (medicines.isEmpty()) {
            MessageUI.displayMessage("No medicines available.");
            System.out.print("Press Enter to return to Pharmacy Menu...");
            scanner.nextLine(); // Wait for user to acknowledge
            return;
        }

        System.out.printf("%-10s %-25s %-20s %-10s %-10s %-15s %-15s\n",
                "ID", "Name", "Category", "Price", "Stock", "Reorder Level", "Description");
        System.out.println("-----------------------------------------------------------------------------------------------------------------");

        for (int i = 0; i < medicines.size(); i++) {
            Medicine medicine = medicines.get(i + 1);
            System.out.printf("%-10s %-25s %-20s %-10.2f %-10d %-15d %-15s\n",
                    medicine.getMedicineId(),
                    medicine.getName(),
                    medicine.getCategory(),
                    medicine.getPrice(),
                    medicine.getStockQuantity(),
                    medicine.getReorderLevel(),
                    medicine.getDescription());
        }
        System.out.print("Press Enter to return to Pharmacy Menu...");
        scanner.nextLine(); // Wait for user to acknowledge
    }

    public void generateLowStockReport() {
        System.out.println("\n--- Low Stock Report ---");
        System.out.println("Enter '0' to return to Pharmacy Menu.");
        ListInterface<Medicine> lowStockMedicines = pharmacyController.generateLowStockReport();

        if (lowStockMedicines.isEmpty()) {
            MessageUI.displayMessage("No medicines are currently in low stock.");
            System.out.print("Press Enter to return to Pharmacy Menu...");
            scanner.nextLine(); // Wait for user to acknowledge
            return;
        }

        System.out.printf("%-10s %-25s %-20s %-10s %-10s %-15s\n",
                "ID", "Name", "Category", "Price", "Stock", "Reorder Level");
        System.out.println("-------------------------------------------------------------------------------------------------");

        for (int i = 0; i < lowStockMedicines.size(); i++) {
            Medicine medicine = lowStockMedicines.get(i + 1);
            System.out.printf("%-10s %-25s %-20s %-10.2f %-10d %-15d\n",
                    medicine.getMedicineId(),
                    medicine.getName(),
                    medicine.getCategory(),
                    medicine.getPrice(),
                    medicine.getStockQuantity(),
                    medicine.getReorderLevel());
        }
        System.out.print("Press Enter to return to Pharmacy Menu...");
        scanner.nextLine(); // Wait for user to acknowledge
    }

    public void generateMedicineByCategoryReport() {
        System.out.println("\n--- Medicine by Category Report ---");
        System.out.println("Enter '0' to return to Pharmacy Menu.");
        Map<MedicineCategory, ListInterface<Medicine>> report = pharmacyController.generateMedicineByCategoryReport();

        if (report.isEmpty()) {
            MessageUI.displayMessage("No medicines available to generate report.");
            System.out.print("Press Enter to return to Pharmacy Menu...");
            scanner.nextLine(); // Wait for user to acknowledge
            return;
        }

        for (Map.Entry<MedicineCategory, ListInterface<Medicine>> entry : report.entrySet()) {
            MedicineCategory category = entry.getKey();
            ListInterface<Medicine> medicines = entry.getValue();

            System.out.println("\nCategory: " + category + " (" + medicines.size() + " medicines)");
            System.out.println("-------------------------------------------------------------------------------------------------");

            if (medicines.isEmpty()) {
                System.out.println("  No medicines in this category.");
            } else {
                System.out.printf("  %-10s %-25s %-20s %-10s %-10s %-15s\n",
                        "ID", "Name", "Description", "Price", "Stock", "Reorder Level");
                System.out.println("  -------------------------------------------------------------------------------------------------");
                for (int i = 0; i < medicines.size(); i++) {
                    Medicine medicine = medicines.get(i + 1);
                    System.out.printf("  %-10s %-25s %-20s %-10.2f %-10d %-15d\n",
                            medicine.getMedicineId(),
                            medicine.getName(),
                            medicine.getDescription(),
                            medicine.getPrice(),
                            medicine.getStockQuantity(),
                            medicine.getReorderLevel());
                }
            }
        }
        System.out.print("Press Enter to return to Pharmacy Menu...");
        scanner.nextLine(); // Wait for user to acknowledge
    }

    public MedicineCategory selectMedicineCategory() {
        MedicineCategory[] categories = MedicineCategory.values();
        int choice;
        do {
            System.out.println("\nSelect Medicine Category (0 to exit):");
            for (int i = 0; i < categories.length; i++) {
                System.out.println((i + 1) + ". " + categories[i]);
            }
            System.out.print("Enter choice: ");
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (choice == 0) {
                    return null; // User chose to exit
                } else if (choice >= 1 && choice <= categories.length) {
                    return categories[choice - 1];
                } else {
                    MessageUI.displayErrorMessage("Invalid category choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                MessageUI.displayErrorMessage("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
                choice = -1; // Set to an invalid choice to continue the loop
            }
        } while (true);
    }
}
