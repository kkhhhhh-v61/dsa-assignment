package control;

import adt.ListInterface;
import dao.MedicineDAO;
import dao.DispenseRecordDAO; // Import DispenseRecordDAO
import entity.Medicine;
import entity.DispenseRecord; // Import DispenseRecord
import utility.Enum.MedicineCategory;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Lai Chee Kit
 */
public class PharmacyController implements Serializable {
    private MedicineDAO medicineDAO;
    private DispenseRecordDAO dispenseRecordDAO; // Add DispenseRecordDAO

    public PharmacyController() {
        medicineDAO = new MedicineDAO();
        dispenseRecordDAO = new DispenseRecordDAO(); // Initialize DispenseRecordDAO
    }

    public ListInterface<Medicine> getAllMedicines() {
        medicineDAO.reload(); // Ensure latest data is loaded
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

    public ListInterface<DispenseRecord> getAllDispenseRecords() {
        dispenseRecordDAO.reload(); // Ensure latest data is loaded
        return dispenseRecordDAO.getAllDispenseRecords();
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
    }
}
