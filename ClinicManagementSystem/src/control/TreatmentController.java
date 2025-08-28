// ======================= TreatmentController.java ==========================
package control;

import dao.TreatmentDAO;
import dao.ConsultationDAO;
import dao.MedicineDAO;
import entity.Treatment;
import entity.Consultation;
import entity.PrescribedMedicine;
import entity.Medicine;
import adt.ArrayList;
import utility.Enum.Status;

import java.time.LocalDate;

public class TreatmentController {
    private TreatmentDAO treatmentDAO;
    private ConsultationDAO consultationDAO;
    private MedicineDAO medicineDAO;

    public TreatmentController() {
        this.treatmentDAO = new TreatmentDAO();
        this.consultationDAO = new ConsultationDAO();
        this.medicineDAO = new MedicineDAO();
    }

    // ===== CRUD =====
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

            if (pm.getQuantity() > med.getStockQuantity()) {
                System.out.println("Not enough stock for " + med.getName() + ". Available: " + med.getStockQuantity());
                return false;
            }
        }

        for (int i = 1; i <= meds.size(); i++) {
            PrescribedMedicine pm = meds.get(i);
            Medicine med = medicineDAO.getMedicineById(pm.getMedicine().getMedicineId());
            med.setStockQuantity(med.getStockQuantity() - pm.getQuantity());
            medicineDAO.updateMedicine(med);
        }

        int lastId = treatmentDAO.getLastId();
        String newId = String.format("T%04d", lastId + 1);

        Treatment t = new Treatment(consultationId, c.getDoctorId(), c.getPatientId(), meds, notes, Status.Unknown);
        t.setTreatmentId(newId);

        treatmentDAO.addTreatment(t);
        treatmentDAO.saveAll();
        return true;
    }

    public Treatment viewTreatmentById(String id) {
        return treatmentDAO.getTreatmentById(id);
    }
    
    public ArrayList<Treatment> viewTreatmentsByPatientId(String patientId) {
        return treatmentDAO.getTreatmentsByUserId(patientId);
    }



    public ArrayList<Treatment> viewAllTreatments() {
        treatmentDAO.reload();
        return treatmentDAO.getAllTreatments();
    }

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
        }

        ArrayList<PrescribedMedicine> newMeds = treatment.getMedicines();
        for (int i = 1; i <= newMeds.size(); i++) {
            PrescribedMedicine pm = newMeds.get(i);
            Medicine med = medicineDAO.getMedicineById(pm.getMedicine().getMedicineId());

            if (med == null || pm.getQuantity() > med.getStockQuantity()) {
                System.out.println("Stock validation failed for medicine " + pm.getMedicine().getMedicineId());
                return false;
            }
        }

        for (int i = 1; i <= newMeds.size(); i++) {
            PrescribedMedicine pm = newMeds.get(i);
            Medicine med = medicineDAO.getMedicineById(pm.getMedicine().getMedicineId());
            med.setStockQuantity(med.getStockQuantity() - pm.getQuantity());
            medicineDAO.updateMedicine(med);
        }

        treatmentDAO.updateTreatment(treatment);
        treatmentDAO.saveAll();
        return true;
    }

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

    // ===== Reporting =====
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

        String[] months = {"January","February","March","April","May","June",
                           "July","August","September","October","November","December"};

        int total = 0;
        System.out.println("\n=== Yearly Report for " + year + " ===");
        for (int m = 0; m < 12; m++) {
            System.out.println(months[m] + " = " + monthlyCounts[m]);
            total += monthlyCounts[m];
        }
        System.out.println("Total treatments in " + year + " = " + total);
    }


    public void generateYearToYearReport(int startYear, int endYear) {
        ArrayList<Consultation> consultations = consultationDAO.getAllConsultations();

        // parallel arrays for year and counts
        ArrayList<Integer> years = new ArrayList<>();
        ArrayList<Integer> counts = new ArrayList<>();

        // initialize
        for (int year = startYear; year <= endYear; year++) {
            years.add(year);
            counts.add(0);
        }

        // count consultations by year
        for (int i = 1; i <= consultations.size(); i++) {
            Consultation c = consultations.get(i);
            if (c != null) {
                LocalDate date = c.getConsultationDate();
                int year = date.getYear();

                if (year >= startYear && year <= endYear) {
                    // find index in years list
                    for (int j = 1; j <= years.size(); j++) {
                        if (years.get(j) == year) {
                            counts.add(j, counts.get(j) + 1);
                            break;
                        }
                    }
                }
            }
        }

        // print report
        System.out.println("\n=== Year-to-Year Consultation Report (" + startYear + " - " + endYear + ") ===");
        int total = 0;
        for (int j = 1; j <= years.size(); j++) {
            int count = counts.get(j);
            System.out.println(years.get(j) + " = " + count);
            total += count;
        }
        System.out.println("\nTotal treatments between " + startYear + " and " + endYear + " = " + total);
    }


}
