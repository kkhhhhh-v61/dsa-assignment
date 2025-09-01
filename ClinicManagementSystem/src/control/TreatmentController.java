package control;
// Author - Jason Tye
import dao.TreatmentDAO;
import dao.ConsultationDAO;
import dao.MedicineDAO;
import dao.DispenseRecordDAO; 
import entity.Treatment;
import entity.Consultation;
import entity.PrescribedMedicine;
import entity.Medicine;
import entity.DispenseRecord; 
import adt.ArrayList;
import utility.Enum.Status;
import java.time.LocalDate;

public class TreatmentController {
    private TreatmentDAO treatmentDAO;
    private ConsultationDAO consultationDAO;
    private MedicineDAO medicineDAO;
    private DispenseRecordDAO dispenseRecordDAO; 

    public TreatmentController() {
        this.treatmentDAO = new TreatmentDAO();
        this.consultationDAO = new ConsultationDAO();
        this.medicineDAO = new MedicineDAO();
        this.dispenseRecordDAO = new DispenseRecordDAO(); 
    }

    // ===== Add Teratment =====
    public boolean addTreatment(String consultationId, ArrayList<PrescribedMedicine> meds, String notes) {
        Consultation c = consultationDAO.getConsultationById(consultationId);
        if (c == null) {
            System.out.println("Consultation not found.");
            return false;
        }

        for (int i = 1; i <= meds.size(); i++) {
            PrescribedMedicine pm = meds.get(i);
            Medicine med = medicineDAO.getMedicineById(pm.getMedicine().getMedicineId());

            if (med == null) {
                System.out.println("Medicine " + pm.getMedicine().getMedicineId() + " not found.");
                return false;
            }

            if (pm.getQuantity() <= med.getStockQuantity()) {
		med.setStockQuantity(med.getStockQuantity() - pm.getQuantity());
            	medicineDAO.updateMedicine(med);
            	pm.setMedicine(med); 
            } else {
		System.out.println("Not enough stock for " + med.getName() + ". Available: " + med.getStockQuantity());
                return false;
	    }
        }

        int lastId = treatmentDAO.getLastId();
        String newId = String.format("T%04d", lastId + 1);

        Treatment t = new Treatment(consultationId, c.getDoctorId(), c.getPatientId(), meds, notes, Status.Unknown);
        t.setTreatmentId(newId);

        treatmentDAO.addTreatment(t);
        
        for (int i = 1; i <= meds.size(); i++) {
            PrescribedMedicine pm = meds.get(i);
            // Fetch the latest medicine object from DAO after stock update
            Medicine latestMed = medicineDAO.getMedicineById(pm.getMedicine().getMedicineId()); 
            DispenseRecord dr = new DispenseRecord(latestMed, pm.getQuantity(), newId, c.getPatientId(), c.getDoctorId());
            dispenseRecordDAO.addDispenseRecord(dr);
        }
        
        treatmentDAO.saveAll();
        return true;
    }

	// ===== View Treatment By ID =====
    public Treatment viewTreatmentById(String id) {
        return treatmentDAO.getTreatmentById(id);
    }

	// ===== View Treatment By Patient ID =====
    public ArrayList<Treatment> viewTreatmentsByPatientId(String patientId) {
        return treatmentDAO.getTreatmentsByUserId(patientId);
    }

	// ===== View All Treatment =====
    public ArrayList<Treatment> viewAllTreatments() {
        treatmentDAO.reload();
        return treatmentDAO.getAllTreatments();
    }

	// ===== Edit Treatment =====
    public boolean editTreatment(Treatment treatment) {
        Treatment old = treatmentDAO.getTreatmentById(treatment.getTreatmentId());
        if (old != null) {
            ArrayList<PrescribedMedicine> oldMeds = old.getMedicines();
            for (int i = 1; i <= oldMeds.size(); i++) {
                PrescribedMedicine pm = oldMeds.get(i);
                Medicine med = medicineDAO.getMedicineById(pm.getMedicine().getMedicineId());
                if (med != null) {
                    med.setStockQuantity(med.getStockQuantity() + pm.getQuantity());
                    medicineDAO.updateMedicine(med);
                }
            }
            ArrayList<DispenseRecord> oldDispenseRecords = (ArrayList<DispenseRecord>) dispenseRecordDAO.getDispenseRecordsByTreatmentId(old.getTreatmentId());
            for (int i = 1; i <= oldDispenseRecords.size(); i++) {
                dispenseRecordDAO.deleteDispenseRecord(oldDispenseRecords.get(i).getDispenseId());
            }
        }

        ArrayList<PrescribedMedicine> newMeds = treatment.getMedicines();
        Consultation c = consultationDAO.getConsultationById(treatment.getConsultationId());
        if (c == null) {
            System.out.println("Consultation not found for treatment " + treatment.getTreatmentId());
            return false;
        }

        for (int i = 1; i <= newMeds.size(); i++) {
            PrescribedMedicine pm = newMeds.get(i);
            Medicine med = medicineDAO.getMedicineById(pm.getMedicine().getMedicineId());

            if (med == null) {
                System.out.println("Medicine " + pm.getMedicine().getMedicineId() + " not found.");
                return false;
            }

            if (pm.getQuantity() > med.getStockQuantity()) {
                System.out.println("Not enough stock for " + med.getName() + ". Available: " + med.getStockQuantity());
                return false;
            }
        }

        for (int i = 1; i <= newMeds.size(); i++) {
            PrescribedMedicine pm = newMeds.get(i);
            Medicine med = medicineDAO.getMedicineById(pm.getMedicine().getMedicineId());
            med.setStockQuantity(med.getStockQuantity() - pm.getQuantity());
            medicineDAO.updateMedicine(med);
            pm.setMedicine(med);
            
            Medicine latestMed = medicineDAO.getMedicineById(pm.getMedicine().getMedicineId());
            DispenseRecord dr = new DispenseRecord(latestMed, pm.getQuantity(), treatment.getTreatmentId(), c.getPatientId(), c.getDoctorId());
            dispenseRecordDAO.addDispenseRecord(dr);
        }

        treatmentDAO.updateTreatment(treatment);
        treatmentDAO.saveAll();
        return true;
    }

	// ===== Delete Treatment =====
    public void deleteTreatment(String id) {
        Treatment t = treatmentDAO.getTreatmentById(id);
        if (t != null) {
            ArrayList<PrescribedMedicine> meds = t.getMedicines();
            for (int i = 1; i <= meds.size(); i++) {
                PrescribedMedicine pm = meds.get(i);
                Medicine med = medicineDAO.getMedicineById(pm.getMedicine().getMedicineId());
                if (med != null) {
                    med.setStockQuantity(med.getStockQuantity() + pm.getQuantity());
                    medicineDAO.updateMedicine(med);
                }
            }
            ArrayList<DispenseRecord> dispenseRecordsToDelete = (ArrayList<DispenseRecord>) dispenseRecordDAO.getDispenseRecordsByTreatmentId(id);
            for (int i = 1; i <= dispenseRecordsToDelete.size(); i++) {
                dispenseRecordDAO.deleteDispenseRecord(dispenseRecordsToDelete.get(i).getDispenseId());
            }
            treatmentDAO.deleteTreatment(id);
            treatmentDAO.saveAll();
        }
    }

    public void saveAllTreatments() {
        treatmentDAO.saveAll();
    }
    
    public boolean isValidConsultationId(String consultationId) {
        Consultation c = consultationDAO.getConsultationById(consultationId);
        if (c == null) {
            System.out.println("Consultation not found.");
            return false;
        }
        return true;
    }

    // ===== Yearly Report =====
    public void generateYearlyReport(int year) {
        ArrayList<Treatment> treatments = treatmentDAO.getAllTreatments();
        int[] monthlyCounts = new int[12];

        ConsultationDAO consultationDAO = new ConsultationDAO();

        for (int i = 1; i <= treatments.size(); i++) {
            Treatment t = treatments.get(i);
            Consultation c = consultationDAO.getConsultationById(t.getConsultationId()); 
            if (c != null) {
                LocalDate date = c.getConsultationDate();
                if (date.getYear() == year) {
                    monthlyCounts[date.getMonthValue() - 1]++;
                }
            }
        }

        String[] months = {"January","February","March","April","May","June","July","August","September","October","November","December"};

        int total = 0;
        System.out.println("\n=== Yearly Report for " + year + " ===");
        for (int m = 0; m < 12; m++) {
            System.out.println(months[m] + " = " + monthlyCounts[m]);
            total += monthlyCounts[m];
        }
        System.out.println("Total treatments in " + year + " = " + total);
    }

	// ===== Year to Year Report =====
    public void generateYearToYearReport(int startYear, int endYear) {
    ArrayList<Treatment> treatments = treatmentDAO.getAllTreatments();
    ArrayList<Consultation> consultations = consultationDAO.getAllConsultations();

    ArrayList<Integer> years = new ArrayList<>();
    ArrayList<Integer> counts = new ArrayList<>();

    for (int year = startYear; year <= endYear; year++) {
        years.add(year);
        counts.add(0);
    }

    for (int i = 1; i <= treatments.size(); i++) {
        Treatment t = treatments.get(i);
        if (t != null) {
            String consId = t.getConsultationId();
            Consultation linked = null;

            for (int j = 1; j <= consultations.size(); j++) {
                Consultation c = consultations.get(j);
                if (c != null && c.getConsultationId().equalsIgnoreCase(consId)) {
                    linked = c;
                    break;
                }
            }

            if (linked != null) {
                LocalDate date = linked.getConsultationDate();
                int year = date.getYear();

                if (year >= startYear && year <= endYear) {
                    for (int j = 1; j <= years.size(); j++) {
                        if (years.get(j) == year) {
                            counts.add(j, counts.get(j) + 1); // ✅ use set, not add
                            break;
                        }
                    }
                }
            }
        }
    }

    System.out.println("\n=== Year-to-Year Treatment Report (" + startYear + " - " + endYear + ") ===");
    int total = 0;
    for (int j = 1; j <= years.size(); j++) {
        int count = counts.get(j);
        System.out.println(years.get(j) + " = " + count);
        total += count;
    }
    System.out.println("\nTotal treatments between " + startYear + " and " + endYear + " = " + total);
}


}
