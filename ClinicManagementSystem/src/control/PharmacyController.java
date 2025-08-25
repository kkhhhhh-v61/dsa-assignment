package control;

import adt.ListInterface;
<<<<<<< Updated upstream
import adt.ArrayList;
import entity.Medicine;
import entity.PrescribedMedicine;
import utility.Enum.MedicineCategory;
import utility.DataManager; // Import DataManager

/**
 *
 * @author khtan (original author of existing entities)
 */
public class PharmacyController {

    private ListInterface<Medicine> medicineList;

    public PharmacyController() {
        medicineList = DataManager.loadMedicines(); // Load medicines from file
    }

    public ListInterface<Medicine> getAllMedicines() {
        return medicineList;
    }

    public Medicine getMedicineById(String medicineId) {
        for (int i = 0; i < medicineList.size(); i++) {
            Medicine medicine = medicineList.getEntry(i + 1);
            if (medicine.getMedicineId().equals(medicineId)) {
                return medicine;
            }
        }
        return null;
    }

    public boolean addMedicine(Medicine medicine) {
        if (getMedicineById(medicine.getMedicineId()) == null) {
            boolean added = medicineList.add(medicine);
            if (added) {
                DataManager.saveMedicines(medicineList); // Save after adding
            }
            return added;
        }
        return false; // Medicine with this ID already exists
    }

    public boolean updateMedicine(Medicine updatedMedicine) {
        for (int i = 0; i < medicineList.size(); i++) {
            Medicine medicine = medicineList.getEntry(i + 1);
            if (medicine.getMedicineId().equals(updatedMedicine.getMedicineId())) {
                medicineList.replace(i + 1, updatedMedicine);
                DataManager.saveMedicines(medicineList); // Save after updating
                return true;
            }
        }
        return false;
    }

    public boolean deleteMedicine(String medicineId) {
        for (int i = 0; i < medicineList.size(); i++) {
            Medicine medicine = medicineList.getEntry(i + 1);
            if (medicine.getMedicineId().equals(medicineId)) {
                boolean removed = medicineList.remove(i + 1);
                if (removed) {
                    DataManager.saveMedicines(medicineList); // Save after deleting
                }
                return removed;
            }
        }
        return false;
    }

    /**
     * Dispenses a list of prescribed medicines, updating stock quantities.
     * @param prescribedMedicines The list of medicines to dispense.
     * @return true if all medicines were successfully dispensed, false otherwise.
     */
    public boolean dispenseMedicines(ListInterface<PrescribedMedicine> prescribedMedicines) {
        boolean allDispensed = true;
        for (int i = 0; i < prescribedMedicines.size(); i++) {
            PrescribedMedicine pm = prescribedMedicines.getEntry(i + 1); // Corrected index
            Medicine medicine = getMedicineById(pm.getMedicineId());

            if (medicine != null) {
                // Assuming 1 unit of prescribed medicine corresponds to 1 unit of stock
                int requiredQuantity = pm.getTimesPerDay() * pm.getDuration(); // Simple calculation for required quantity
                if (medicine.getStockQuantity() >= requiredQuantity) {
                    medicine.setStockQuantity(medicine.getStockQuantity() - requiredQuantity);
                    updateMedicine(medicine); // Update the medicine in the list (which also saves)
                } else {
                    System.out.println("Insufficient stock for " + medicine.getName() + ". Required: " + requiredQuantity + ", Available: " + medicine.getStockQuantity());
                    allDispensed = false;
                }
            } else {
                System.out.println("Medicine with ID " + pm.getMedicineId() + " not found.");
                allDispensed = false;
            }
        }
        if (allDispensed) {
            DataManager.saveMedicines(medicineList); // Save after all dispensing operations are complete
        }
        return allDispensed;
    }

    /**
     * Generates a report of medicines with low stock (below reorder level).
     * @return A string containing the low stock report.
     */
    public String generateLowStockReport() {
        StringBuilder report = new StringBuilder();
        report.append("--- Low Stock Medicines Report ---\n");
        report.append(String.format("%-15s %-25s %-15s %-15s\n", "Medicine ID", "Name", "Stock", "Reorder Level"));
        report.append("------------------------------------------------------------------\n");

        boolean foundLowStock = false;
        for (int i = 0; i < medicineList.size(); i++) {
            Medicine medicine = medicineList.getEntry(i + 1);
            if (medicine.getStockQuantity() < medicine.getReorderLevel()) {
                report.append(String.format("%-15s %-25s %-15d %-15d\n",
                        medicine.getMedicineId(),
                        medicine.getName(),
                        medicine.getStockQuantity(),
                        medicine.getReorderLevel()));
                foundLowStock = true;
            }
        }

        if (!foundLowStock) {
            report.append("No medicines currently below reorder level.\n");
        }
        report.append("------------------------------------------------------------------\n");
        return report.toString();
    }

    /**
     * Generates a report of all medicines by category.
     * @return A string containing the medicine by category report.
     */
    public String generateMedicineByCategoryReport() {
        StringBuilder report = new StringBuilder();
        report.append("--- Medicines by Category Report ---\n");

        // Group medicines by category
        // This would ideally use a Map ADT if available and suitable for grouping
        // For now, iterate and print
        for (MedicineCategory category : MedicineCategory.values()) {
            if (category == MedicineCategory.Unknown) continue; // Skip unknown category

            report.append("\nCategory: ").append(category.name()).append("\n");
            report.append(String.format("%-15s %-25s %-10s %-10s\n", "ID", "Name", "Price", "Stock"));
            report.append("------------------------------------------------------------\n");
            boolean foundInCategory = false;
            for (int i = 0; i < medicineList.size(); i++) {
                Medicine medicine = medicineList.getEntry(i + 1);
                if (medicine.getCategory() == category) {
                    report.append(String.format("%-15s %-25s %-10.2f %-10d\n",
                            medicine.getMedicineId(),
                            medicine.getName(),
                            medicine.getPrice(),
                            medicine.getStockQuantity()));
                    foundInCategory = true;
                }
            }
            if (!foundInCategory) {
                report.append("No medicines in this category.\n");
            }
        }
        report.append("------------------------------------------------------------\n");
        return report.toString();
=======
import dao.MedicineDAO;
import entity.Medicine;
import entity.PrescribedMedicine;
import utility.Enum.MedicineCategory;
import utility.MessageUI;
import utility.Validation;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PharmacyController implements Serializable {
    private MedicineDAO medicineDAO;

    public PharmacyController() {
        medicineDAO = new MedicineDAO();
    }

    public ListInterface<Medicine> getAllMedicines() {
        return medicineDAO.getAllMedicines();
    }

    public Medicine getMedicineById(String medicineId) {
        return medicineDAO.getMedicineById(medicineId);
    }

    public boolean addMedicine(Medicine medicine) {
        return medicineDAO.addMedicine(medicine);
    }

    public boolean updateMedicine(Medicine updatedMedicine) {
        return medicineDAO.updateMedicine(updatedMedicine);
    }

    public boolean deleteMedicine(String medicineId) {
        return medicineDAO.deleteMedicine(medicineId);
    }

    public boolean dispenseMedicines(ListInterface<PrescribedMedicine> prescribedMedicines) {
        boolean success = true;
        for (int i = 0; i < prescribedMedicines.size(); i++) {
            PrescribedMedicine pm = prescribedMedicines.get(i + 1);
            Medicine medicine = medicineDAO.getMedicineById(pm.getMedicine().getMedicineId());

            if (medicine != null && medicine.getStockQuantity() >= pm.getQuantity()) {
                medicine.setStockQuantity(medicine.getStockQuantity() - pm.getQuantity());
                medicineDAO.updateMedicine(medicine);
            } else {
                success = false;
                MessageUI.displayErrorMessage("Failed to dispense " + pm.getMedicine().getName() + ". Insufficient stock or medicine not found.");
            }
        }
        return success;
    }

    public ListInterface<Medicine> generateLowStockReport() {
        ListInterface<Medicine> lowStockMedicines = new adt.ArrayList<>();
        ListInterface<Medicine> allMedicines = medicineDAO.getAllMedicines();

        for (int i = 0; i < allMedicines.size(); i++) {
            Medicine medicine = allMedicines.get(i + 1);
            if (medicine.getStockQuantity() <= medicine.getReorderLevel()) {
                lowStockMedicines.add(medicine);
            }
        }
        return lowStockMedicines;
    }

    public Map<MedicineCategory, ListInterface<Medicine>> generateMedicineByCategoryReport() {
        Map<MedicineCategory, ListInterface<Medicine>> report = new HashMap<>();
        ListInterface<Medicine> allMedicines = medicineDAO.getAllMedicines();

        for (MedicineCategory category : MedicineCategory.values()) {
            report.put(category, new adt.ArrayList<>());
        }

        for (int i = 0; i < allMedicines.size(); i++) {
            Medicine medicine = allMedicines.get(i + 1);
            report.get(medicine.getCategory()).add(medicine);
        }
        return report;
>>>>>>> Stashed changes
    }
}
