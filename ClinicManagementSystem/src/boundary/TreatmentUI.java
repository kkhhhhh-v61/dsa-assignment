package boundary;
// Author - Jason Tye
import control.TreatmentController;
import dao.MedicineDAO;
import entity.Medicine;
import entity.Treatment;
import entity.PrescribedMedicine;
import adt.ArrayList;
import java.time.LocalDate;

import java.util.Scanner;

public class TreatmentUI {
    private TreatmentController controller;
    private Scanner sc;

    public TreatmentUI() {
        this.controller = new TreatmentController();
        this.sc = new Scanner(System.in);
    }

    public void runMenu() {
        while (true) {
            System.out.println("\n=== Treatment Management ===");
            System.out.println("1. Create Treatment");
            System.out.println("2. View All Treatments");
            System.out.println("3. View Treatment by ID");
            System.out.println("4. Edit Treatment");
            System.out.println("5. Delete Treatment");
            System.out.println("6. Generate Yearly Report");
            System.out.println("7. Generate Year-to-Year Report");
            System.out.println("0. Exit");
            System.out.print("Choice: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1": createTreatment(); break;
                case "2": viewAllTreatments(); break;
                case "3": viewTreatmentById(); break;
                case "4": editTreatment(); break;
                case "5": deleteTreatment(); break;
                case "6": generateYearlyReport(); break;
                case "7": generateYearToYearReport(); break;
                case "0": return;
                default: System.out.println("Invalid choice.");
            }
        }
    }
    
    public void runStudentMenu(String patientId) {
        viewTreatmentByPatientId(patientId);
    }

    private void createTreatment() {
        String consultationId;
        while (true) {
            System.out.print("Enter Consultation ID (or 'exit' to cancel): ");
            consultationId = sc.nextLine().trim();
            if (consultationId.equalsIgnoreCase("exit")) return;

            if (controller.isValidConsultationId(consultationId)) {
                break;
            } else {
                System.out.println("Invalid Consultation ID. Please re-enter.");
            }
        }

        ArrayList<PrescribedMedicine> meds = collectMedicines();
        System.out.print("Enter Notes (or 'exit' to cancel): ");
        String notes = sc.nextLine().trim();
        if (notes.equalsIgnoreCase("exit")) return;

        boolean success = controller.addTreatment(consultationId, meds, notes);
        System.out.println(success ? "Treatment created successfully." : "Treatment creation failed.");
    }

    private void viewAllTreatments() {
        ArrayList<Treatment> treatments = controller.viewAllTreatments();
        if (treatments.isEmpty()) {
            System.out.println("No treatments found.");
            return;
        }
        else {
            System.out.println("-----------------------------\n");
            for (int i = 1; i <= treatments.size(); i++) {
                System.out.println(treatments.get(i));
                System.out.println("-----------------------------\n");
            }
        }
    }

    private void viewTreatmentById() {
        System.out.print("Enter Treatment ID: ");
        String id = sc.nextLine().trim();
        System.out.println("-----------------------------\n");
        Treatment t = controller.viewTreatmentById(id);
        System.out.println(t != null ? t : "Treatment not found.\n");
        System.out.println("-----------------------------");
    }
    
    private void viewTreatmentByPatientId(String patientId) {
    System.out.println("-----------------------------\n");
    ArrayList<Treatment> treatments = controller.viewTreatmentsByPatientId(patientId);

    if (treatments == null || treatments.isEmpty()) {
        System.out.println("No Treatment Found.\n");
        System.out.println("-----------------------------");
    } else {
        for (int i = 1; i <= treatments.size(); i++) {
            System.out.println(treatments.get(i));
            System.out.println("-----------------------------");
        }
    }
}

 
    private void editTreatment() {
        Treatment t;
        String id;

        while (true) {
            System.out.print("Enter Treatment ID to edit (or 'exit' to cancel): ");
            id = sc.nextLine().trim();
            if (id.equalsIgnoreCase("exit")) return;

            t = controller.viewTreatmentById(id);
            if (t != null) break;

            System.out.println("Invalid Treatment ID. Please re-enter.");
        }

        System.out.println("Editing treatment " + id + " (leave blank to keep old values)");

        System.out.print("Do you want to add/replace medicines? (Y/N): ");
        String medChoice = sc.nextLine().trim();
        ArrayList<PrescribedMedicine> meds = t.getMedicines();

        if (medChoice.equalsIgnoreCase("Y")) {
            ArrayList<PrescribedMedicine> newMeds = collectMedicines();
            if (!newMeds.isEmpty()) {
                meds = newMeds;  
            }
        }

        System.out.print("Enter new Notes (blank = keep old): ");
        String notes = sc.nextLine().trim();
        if (notes.isEmpty()) {
            notes = t.getNotes();
        }

        t.setMedicines(meds);
        t.setNotes(notes);

        boolean success = controller.editTreatment(t);
        System.out.println(success ? "Treatment updated successfully." : "Treatment update failed.");
    }

    private void deleteTreatment() {
        Treatment t;
        String id;

        while (true) {
            System.out.print("Enter Treatment ID to delete (or 'exit' to cancel): ");
            id = sc.nextLine().trim();
            if (id.equalsIgnoreCase("exit")) return;

            t = controller.viewTreatmentById(id);
            if (t != null) break;

            System.out.println("Invalid Treatment ID. Please re-enter.");
        }

        System.out.println("=== Treatment Details ===");
        System.out.println(t);
        System.out.print("Are you sure you want to delete this treatment? (Y/N): ");
        String confirm = sc.nextLine().trim();

        if (confirm.equalsIgnoreCase("Y")) {
            controller.deleteTreatment(id);
            System.out.println("Treatment deleted.");
        } 
        else if (confirm.equalsIgnoreCase("N")) {
            System.out.println("Delete cancelled.");
        }
        else {
            System.out.println("Invalid Option. Please try again.");
        }
    }

    // ===== Reports =====
    public void generateYearlyReport() {
        int currentYear = LocalDate.now().getYear();
        int minYear = 2000;
        int maxYear = currentYear + 5;

        while (true) {
            System.out.print("\nEnter year for yearly report (" + minYear + "-" + maxYear + ", 0 to exit): ");
            String input = sc.nextLine().trim(); 

            if (input.equals("0")) {
                System.out.println("Exiting yearly report...");
                break;
            }

            try {
                int year = Integer.parseInt(input);
                if (year < minYear || year > maxYear) {
                    System.out.println("Invalid year. Please enter between " + minYear + " and " + maxYear + ".");
                } else {
                    controller.generateYearlyReport(year);
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid year.");
            }
        }
    }



    public void generateYearToYearReport() {
        int currentYear = LocalDate.now().getYear();
        int minYear = 2000;
        int maxYear = currentYear + 5;

        while (true) {
            System.out.print("\nEnter start year (" + minYear + "-" + maxYear + ", 0 to exit): ");
            String startInput = sc.nextLine().trim(); 

            if (startInput.equals("0")) {
                System.out.println("Exiting year-to-year report...");
                break;
            }

            System.out.print("Enter end year (" + minYear + "-" + maxYear + ", 0 to exit): ");
            String endInput = sc.nextLine().trim();

            if (endInput.equals("0")) {
                System.out.println("Exiting year-to-year report...");
                break;
            }

            try {
                int startYear = Integer.parseInt(startInput);
                int endYear = Integer.parseInt(endInput);

                if (startYear < minYear || startYear > maxYear ||
                    endYear < minYear || endYear > maxYear) {
                    System.out.println("Invalid year(s). Please enter years between " + minYear + " and " + maxYear + ".");
                    continue;
                }

                if (startYear > endYear) {
                    System.out.println("Start year cannot be later than end year. Try again.");
                    continue;
                }

                controller.generateYearToYearReport(startYear, endYear);
                break;

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter valid years.");
            }
        }
    }



    // ===== helper for medicine entry =====
    private ArrayList<PrescribedMedicine> collectMedicines() {
        ArrayList<PrescribedMedicine> meds = new ArrayList<>();
        MedicineDAO medicineDAO = new MedicineDAO();

        while (true) {
            System.out.print("Enter Medicine ID (or 'done' to finish): ");
            String medId = sc.nextLine().trim();
            if (medId.equalsIgnoreCase("done")) break;

            Medicine medicine = medicineDAO.getMedicineById(medId);
            if (medicine == null) {
                System.out.println("Medicine not found.");
                continue;
            }

            int quantity;
            while (true) {
                System.out.print("Quantity: ");
                quantity = readInt("Invalid number: ");
                if (quantity > medicine.getStockQuantity()) {
                    System.out.println("Not enough stock for " + medicine.getName() + ". Available: " + medicine.getStockQuantity());
                } else {
                    break;
                }
            }

            System.out.print("Dosage: ");
            String dosage = sc.nextLine().trim();

            System.out.print("Times per day: ");
            int times = readInt("Invalid number: ");

            System.out.print("Duration (days): ");
            int duration = readInt("Invalid number: ");

            System.out.print("Remarks: ");
            String remarks = sc.nextLine().trim();

            meds.add(new PrescribedMedicine(medicine, quantity, dosage, times, duration, remarks));
        }
        return meds;
    }

    private int readInt(String errMsg) {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print(errMsg);
            }
        }
    }
}
