package boundary;

// Author - Jason Tye

import control.ConsultationController;
import entity.Consultation;
import adt.ArrayList;
import utility.Enum.Status;

import java.util.Scanner;

public class ConsultationUI {
    private ConsultationController controller;
    private Scanner sc;

    public ConsultationUI() {
        this.controller = new ConsultationController();
        this.sc = new Scanner(System.in);
    }

    public void runMenu() {
        while (true) {
            System.out.println("\n=== Consultation Management ===");
            System.out.println("1. Create Consultation");
            System.out.println("2. View Consultation by ID");
            System.out.println("3. View All Consultations");
            System.out.println("4. Edit Consultation");
            System.out.println("5. Delete Consultation");
            System.out.println("6. Generate Yearly Consultation Report");
            System.out.println("7. Generate Year-to-Year Consultation Report");
            System.out.println("0. Back");
            System.out.print("Select option: ");

            int choice = readInt("Enter a valid number: ");

            switch (choice) {
                case 1: createConsultation(); break;
                case 2: viewConsultationById(); break;
                case 3: viewAllConsultations(); break;
                case 4: editConsultation(); break;
                case 5: deleteConsultation(); break;
                case 6: generateYearlyConsultationReport(); break;
                case 7: generateYearToYearConsultationReport(); break;
                case 0: {controller.saveAllConsultations(); return;}
                default: System.out.println("Invalid choice.");
            }
        }
    }

    
    public void runStudent(String userID) {
        displayConsultationsByUserId(userID);
    }

    private void createConsultation() {
        System.out.println("\n(Consultations are auto-generated from approved appointments)");
    }

    private void viewConsultationById() {
        System.out.print("Enter Consultation ID: ");
        String id = sc.nextLine().trim();
        Consultation c = controller.viewConsultationById(id);
        System.out.println(c != null ? c : "Consultation not found.");
    }

    private void viewAllConsultations() {
        ArrayList<Consultation> list = controller.viewAllConsultations();
        if (list.isEmpty()) {
            System.out.println("No consultations available.");
            return;
        }
        for (int i = 1; i <= list.size(); i++) {
            System.out.println("-------------------------------");
            System.out.println(list.get(i));
        }
        System.out.println("-------------------------------");
    }

    private void editConsultation() {
        Consultation c = null;
        String id;

        // ✅ Loop until valid ID or exit
        while (true) {
            System.out.print("Enter Consultation ID to edit (or type 'exit' to cancel): ");
            id = sc.nextLine().trim();
            if (id.equalsIgnoreCase("exit")) {
                System.out.println("Edit cancelled.");
                return;
            }

            c = controller.viewConsultationById(id);
            if (c != null) {
                break; // valid consultation found
            } else {
                System.out.println("Consultation not found. Please try again.");
            }
        }

        System.out.println("\nEditing Consultation: " + c.getConsultationId());

        // Edit notes
        System.out.print("Enter new notes (leave blank to keep current, or type 'exit' to cancel): ");
        String notes = sc.nextLine().trim();
        if (notes.equalsIgnoreCase("exit")) {
            System.out.println("Edit cancelled.");
            return;
        }
        if (!notes.isEmpty()) {
            c.setNotes(notes);
        }

        // Edit symptoms
        System.out.println("Current Symptoms: " + formatList(c.getSymptoms()));
        System.out.print("Do you want to update symptoms? (y/n or 'exit'): ");
        String choice = sc.nextLine().trim();
        if (choice.equalsIgnoreCase("exit")) return;
        if (choice.equalsIgnoreCase("y")) {
            ArrayList<String> newSymptoms = new ArrayList<>();
            while (true) {
                System.out.print("Enter symptom (or 'done' to finish, 'exit' to cancel): ");
                String symptom = sc.nextLine().trim();
                if (symptom.equalsIgnoreCase("exit")) return;
                if (symptom.equalsIgnoreCase("done")) break;
                if (!symptom.isEmpty()) {
                    newSymptoms.add(symptom);
                } else {
                    System.out.println("Symptom cannot be empty.");
                }
            }
            if (newSymptoms.isEmpty()) {
                newSymptoms.add("none");
            }
            c.setSymptoms(newSymptoms);
        }

        // Edit diagnosis
        System.out.println("Current Diagnosis: " + formatList(c.getDiagnosis()));
        System.out.print("Do you want to update diagnosis? (y/n or 'exit'): ");
        choice = sc.nextLine().trim();
        if (choice.equalsIgnoreCase("exit")) return;
        if (choice.equalsIgnoreCase("y")) {
            ArrayList<String> newDiagnosis = new ArrayList<>();
            while (true) {
                System.out.print("Enter diagnosis (or 'done' to finish, 'exit' to cancel): ");
                String diag = sc.nextLine().trim();
                if (diag.equalsIgnoreCase("exit")) return;
                if (diag.equalsIgnoreCase("done")) break;
                if (!diag.isEmpty()) {
                    newDiagnosis.add(diag);
                } else {
                    System.out.println("Diagnosis cannot be empty.");
                }
            }
            if (newDiagnosis.isEmpty()) {
                newDiagnosis.add("none");
            }
            c.setDiagnosis(newDiagnosis);
        }

        // Edit status with validation loop
        while (true) {
            System.out.print("Update status (Approved/Cancelled/Unknown, or 'exit' to cancel, leave blank to keep current): ");
            String status = sc.nextLine().trim();
            if (status.equalsIgnoreCase("exit")) return;
            if (status.isEmpty()) break; // keep old one
            try {
                c.setStatus(Status.valueOf(status));
                break; // valid input
            } catch (Exception e) {
                System.out.println("Invalid status. Please enter one of: Approved, Cancelled, Unknown.");
            }
        }

        controller.editConsultation(c);
        System.out.println("Consultation updated.");
    }

    private void deleteConsultation() {
        System.out.print("Enter Consultation ID to delete: ");
        String id = sc.nextLine().trim();

        if (id.isEmpty()) {
            System.out.println("Consultation ID cannot be empty.");
            return;
        }

        Consultation c = controller.viewConsultationById(id);
        if (c == null) {
            System.out.println("Consultation with ID " + id + " not found.");
            return;
        }

        System.out.println("Consultation found:");
        System.out.println(c);

        System.out.print("Are you sure you want to delete this consultation? (y/n): ");
        String confirm = sc.nextLine().trim();

        if (confirm.equalsIgnoreCase("y")) {
            controller.deleteConsultation(id);
            System.out.println("Consultation " + id + " deleted successfully.");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private void displayConsultationsByUserId(String userID) {
        String userId = userID;

        ArrayList<Consultation> consultations = controller.viewConsultationsByUserId(userId);
            for (int i = 1; i <= consultations.size(); i++) {
                Consultation c = consultations.get(i);
                System.out.println("-------------------");
                System.out.println("Consultation ID: " + c.getConsultationId());
                System.out.println("Patient ID: " + c.getPatientId());
                System.out.println("Doctor ID: " + c.getDoctorId());
                System.out.println("Consultation Date: " + c.getConsultationDate());
                System.out.println("Consultation Time: " + c.getConsuLocalTime());
                System.out.println("Symptoms: " + formatList(c.getSymptoms()));
                System.out.println("Diagnosis: " + formatList(c.getDiagnosis()));
                System.out.println("Notes: " + c.getNotes());
                System.out.println("Status: " + c.getStatus());
        }
            System.out.println("-------------------");
    }
    
    private void generateYearlyConsultationReport() {
        int minYear = 2000;
        int maxYear = java.time.LocalDate.now().getYear() + 5;

        while (true) {
            System.out.print("Enter year (" + minYear + "-" + maxYear + ", or 'exit'): ");
            String input = sc.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) return;

            try {
                int year = Integer.parseInt(input);
                if (year < minYear || year > maxYear) {
                    System.out.println("Invalid year. Try again.");
                    continue;
                }
                controller.generateYearlyReport(year);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a number.");
            }
        }
    }

    private void generateYearToYearConsultationReport() {
        int minYear = 2000;
        int maxYear = java.time.LocalDate.now().getYear() + 5;

        int startYear, endYear;
        while (true) {
            System.out.print("Enter start year (" + minYear + "-" + maxYear + ", or 'exit'): ");
            String input = sc.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) return;

            try {
                startYear = Integer.parseInt(input);
                if (startYear < minYear || startYear > maxYear) {
                    System.out.println("Invalid year. Try again.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a number.");
            }
        }

        while (true) {
            System.out.print("Enter end year (" + minYear + "-" + maxYear + ", or 'exit'): ");
            String input = sc.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) return;

            try {
                endYear = Integer.parseInt(input);
                if (endYear < minYear || endYear > maxYear) {
                    System.out.println("Invalid year. Try again.");
                    continue;
                }
                if (endYear < startYear) {
                    System.out.println("End year cannot be earlier than start year.");
                    continue;
                }
                controller.generateYearToYearReport(startYear, endYear);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a number.");
            }
        }
    }

    // =================== Helper Methods ===================

    private int readInt(String errorMsg) {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print(errorMsg);
            }
        }
    }

    private String formatList(ArrayList<String> list) {
        if (list == null || list.isEmpty()) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        for (int i = 1; i <= list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size()) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
