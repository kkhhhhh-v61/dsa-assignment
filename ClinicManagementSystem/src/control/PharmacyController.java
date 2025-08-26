package control;

import adt.ListInterface;
import dao.MedicineDAO;
import entity.Medicine;
import entity.PrescribedMedicine;
import utility.Enum.MedicineCategory;
import utility.MessageUI;

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
    }
}
