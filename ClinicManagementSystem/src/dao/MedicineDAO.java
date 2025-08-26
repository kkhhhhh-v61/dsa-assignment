package dao;

import adt.ArrayList;
import adt.ListInterface;
import entity.Medicine;
import utility.DataManager;
import java.io.Serializable;
import utility.Enum.MedicineCategory; // Import MedicineCategory

public class MedicineDAO implements Serializable {
    private ListInterface<Medicine> medicineList;
    private static final String FILE_PATH = "data/medicines.dat";

    public MedicineDAO() {
        medicineList = loadMedicines();
        if (medicineList == null) {
            medicineList = new ArrayList<>();
            // Add some sample data if the file is new
            addMedicine(new Medicine("Paracetamol", "Pain Relief", MedicineCategory.Other, 2.50, 100, 50));
            addMedicine(new Medicine("Amoxicillin", "Antibiotic", MedicineCategory.Other, 10.00, 50, 20));
            addMedicine(new Medicine("Ibuprofen", "Anti-inflammatory", MedicineCategory.Other, 3.75, 75, 30));
            saveMedicines();
        } else {
            // Initialize lastId in Medicine class based on existing data
            int maxId = 0;
            for (int i = 0; i < medicineList.size(); i++) {
                Medicine medicine = medicineList.get(i + 1);
                String idStr = medicine.getMedicineId().substring(1); // Remove 'M' prefix
                try {
                    int id = Integer.parseInt(idStr);
                    if (id > maxId) {
                        maxId = id;
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing medicine ID: " + medicine.getMedicineId());
                }
            }
            Medicine.setLastId(maxId);
        }
    }

    public ListInterface<Medicine> getAllMedicines() {
        return medicineList;
    }

    public Medicine getMedicineById(String medicineId) {
        for (int i = 0; i < medicineList.size(); i++) {
            Medicine medicine = medicineList.get(i + 1);
            if (medicine.getMedicineId().equals(medicineId)) {
                return medicine;
            }
        }
        return null;
    }

    public boolean addMedicine(Medicine medicine) {
        if (getMedicineById(medicine.getMedicineId()) == null) {
            medicineList.add(medicine);
            saveMedicines();
            return true;
        }
        return false;
    }

    public boolean updateMedicine(Medicine updatedMedicine) {
        for (int i = 0; i < medicineList.size(); i++) {
            Medicine medicine = medicineList.get(i + 1);
            if (medicine.getMedicineId().equals(updatedMedicine.getMedicineId())) {
                medicineList.replace(i + 1, updatedMedicine);
                saveMedicines();
                return true;
            }
        }
        return false;
    }

    public boolean deleteMedicine(String medicineId) {
        for (int i = 0; i < medicineList.size(); i++) {
            Medicine medicine = medicineList.get(i + 1);
            if (medicine.getMedicineId().equals(medicineId)) {
                medicineList.remove(i + 1);
                saveMedicines();
                return true;
            }
        }
        return false;
    }

    private void saveMedicines() {
        DataManager.saveToFile((ArrayList<Medicine>) medicineList, FILE_PATH);
    }

    @SuppressWarnings("unchecked")
    private ListInterface<Medicine> loadMedicines() {
        return (ListInterface<Medicine>) DataManager.readFromFile(FILE_PATH);
    }
}
