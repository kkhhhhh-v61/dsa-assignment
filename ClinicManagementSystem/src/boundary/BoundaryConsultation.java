package boundary;

import control.ConsultationController;
import control.AppointmentController;
import entity.Consultation;
import entity.Patient;
import entity.Doctor;
import adt.ArrayList;
import utility.Enum.Status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class BoundaryConsultation {
    private ConsultationController consultationController;
    private Scanner scanner = new Scanner(System.in);

    public BoundaryConsultation(ArrayList<Patient> patients, ArrayList<Doctor> doctors) {
        this.consultationController = new ConsultationController(patients, doctors);
    }

    public void run() {
        int choice;
        do {
            System.out.println("\n=== Consultation Management ===");
            System.out.println("1. Add Consultation");
            System.out.println("2. View All Consultations");
            System.out.println("3. Search Consultation by ID");
            System.out.println("4. Update Consultation");
            System.out.println("5. Delete Consultation");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addConsultationUI();
                case 2 -> viewAllConsultationsUI();
                case 3 -> searchConsultationUI();
                case 4 -> updateConsultationUI();
                case 5 -> deleteConsultationUI();
                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private void addConsultationUI() {
        Patient patient = null;
        Doctor doctor = null;
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

// Patient ID
        while (patient == null) {
            System.out.print("Enter Patient ID (or type EXIT to cancel): ");
            String id = scanner.nextLine();
            if (id.equalsIgnoreCase("EXIT")) {
                System.out.println("Consultation creation cancelled.");
                return;
            }
            patient = AppointmentController.findPatientById(id);
            if (patient == null) System.out.println("Patient not found.");
        }

// Doctor ID        
        while (doctor == null) {
            System.out.print("Enter Doctor ID (or type EXIT to cancel): ");
            String id = scanner.nextLine();
            if (id.equalsIgnoreCase("EXIT")) {
                System.out.println("Consultation creation cancelled.");
                return;
            }
            doctor = AppointmentController.findDoctorById(id);
            if (doctor == null) System.out.println("Doctor not found.");
        }

// Symptms        
        ArrayList<String> symptoms = new ArrayList<>();
        System.out.println("Enter Symptoms (comma-separated, or type EXIT to cancel): ");
        String inputSymtom = scanner.nextLine();
            if (inputSymtom.equalsIgnoreCase("EXIT")) {
                System.out.println("Consultation creation cancelled.");
                return;
            }
        for (String s : inputSymtom.split(",")) {
            symptoms.add(s.trim());
        }

// Diagnosis        
        ArrayList<String> diagnosis = new ArrayList<>();
        System.out.println("Enter Diagnosis (comma-separated, or type EXIT to cancel): ");
        String inputDiagnosis = scanner.nextLine();
            if (inputDiagnosis.equalsIgnoreCase("EXIT")) {
                System.out.println("Consultation creation cancelled.");
                return;
            }
        for (String d : inputDiagnosis.split(",")) {
            diagnosis.add(d.trim());
        }

// Notes        
        System.out.print("Enter notes (or type EXIT to cancel): ");
        String inputNotes = scanner.nextLine();
            if (inputNotes.equalsIgnoreCase("EXIT")) {
                System.out.println("Consultation creation cancelled.");
                return;
            }
        String notes = inputNotes;

// Status        
        Status status = null;
        while (status == null) {
            System.out.println("\nAvailable Status: " + java.util.Arrays.toString(Status.values()));
            System.out.print("Enter Status (or type EXIT to cancel): ");
            String inputStatus = scanner.nextLine();
            if (inputNotes.equalsIgnoreCase("EXIT")) {
                System.out.println("Consultation creation cancelled.");
                return;
            }
            try {
                status = Status.valueOf(inputStatus);
            } catch (Exception e) {
                System.out.println("Invalid status.");
            }
        }

        Consultation consult = consultationController.addConsultation(patient, doctor, date, time, symptoms, diagnosis, notes, status);
        System.out.println("\nConsultation created: " + consult.getConsultationId());
    }

    private void viewAllConsultationsUI() {
        ArrayList<Consultation> list = consultationController.getAllConsultations();
        if (list.isEmpty()) {
            System.out.println("No consultations found.");
            return;
        }
        for (int i = 1; i <= list.size(); i++) {
            Consultation c = list.getEntry(i);
            Patient p = AppointmentController.findPatientById(c.getPatientId());
            Doctor d = AppointmentController.findDoctorById(c.getDoctorId());
            System.out.println("\n" + c);
            System.out.println("Patient: " + (p != null ? p.getName() : "Unknown"));
            System.out.println("Doctor : " + (d != null ? d.getName() : "Unknown"));
            System.out.println("----------------------");
        }
    }

    private void searchConsultationUI() {
        System.out.print("Enter Consultation ID: ");
        Consultation consult = ConsultationController.findConsultationById(scanner.nextLine());
        if (consult == null) {
            System.out.println("Consultation not found.");
        } else {
            Patient p = AppointmentController.findPatientById(consult.getPatientId());
            Doctor d = AppointmentController.findDoctorById(consult.getDoctorId());
            System.out.println("\n" + consult);
            System.out.println("Patient: " + (p != null ? p.getName() : "Unknown"));
            System.out.println("Doctor : " + (d != null ? d.getName() : "Unknown"));
        }
    }

    private void updateConsultationUI() {
// Consultation ID
        System.out.print("Enter Consultation ID: ");
        Consultation consult = ConsultationController.findConsultationById(scanner.nextLine());
        if (consult == null) {
            System.out.println("Not found.");
            return;
        }

// New Symtoms
        System.out.print("Enter new Symptoms (comma separated without space, or type EXIT to cancel/press ENTER to skip) [" + consult.getSymptoms() + "]: ");
        String inputSymtom = scanner.nextLine();
        if (inputSymtom.equalsIgnoreCase("EXIT")) {
            System.out.println("Consultation creation cancelled.");
            return;
        }
        ArrayList<String> newSymptoms = inputSymtom.isEmpty() ? null : new adt.ArrayList<>();
        if (newSymptoms != null) for (String s : inputSymtom.split(",")) newSymptoms.add(s.trim());

// New Diagnosis
        System.out.print("Enter new Diagnosis (comma separated without space, or type EXIT to cancel/press ENTER to skip) [" + consult.getDiagnosis() + "]: ");
        String inputDiagnosis = scanner.nextLine();
        if (inputDiagnosis.equalsIgnoreCase("EXIT")) {
            System.out.println("Consultation creation cancelled.");
            return;
        }
        ArrayList<String> newDiagnosis = inputDiagnosis.isEmpty() ? null : new adt.ArrayList<>();
        if (newDiagnosis != null) for (String d : inputDiagnosis.split(",")) newDiagnosis.add(d.trim());

// New Notes
        System.out.print("Enter new Notes (or type EXIT to cancel/press ENTER to skip)[" + consult.getNotes() + "]: ");
        String inputNotes = scanner.nextLine();
        if (inputNotes.equalsIgnoreCase("EXIT")) {
            System.out.println("Consultation creation cancelled.");
            return;
        }
        String newNotes = inputNotes.isEmpty() ? null : inputNotes;
        
// New Status
        System.out.println("\nAvailable Status: " + java.util.Arrays.toString(Status.values()));
        System.out.print("Enter new Status (or type EXIT to cancel/press ENTER to skip)[" + consult.getStatus() + "]: ");
        String inputStatus = scanner.nextLine();
        if (inputStatus.equalsIgnoreCase("EXIT")) {
            System.out.println("Consultation creation cancelled.");
            return;
        }
        Status newStatus = inputStatus.isEmpty() ? null : Status.valueOf(inputStatus);

        consultationController.updateConsultation(consult, null, null, newSymptoms, newDiagnosis, newNotes, newStatus);
        System.out.println("Consultation updated.");
    }

    private void deleteConsultationUI() {
        System.out.print("Enter Consultation ID: ");
        Consultation consult = ConsultationController.findConsultationById(scanner.nextLine());
        if (consult == null) {
            System.out.println("Not found.");
            return;
        }
        Patient p = AppointmentController.findPatientById(consult.getPatientId());
        Doctor d = AppointmentController.findDoctorById(consult.getDoctorId());
        System.out.println("\n" + consult);
        System.out.println("Patient: " + (p != null ? p.getName() : "Unknown"));
        System.out.println("Doctor : " + (d != null ? d.getName() : "Unknown"));
        System.out.print("Delete this consultation? (Y/N): ");
        if (scanner.nextLine().equalsIgnoreCase("Y")) {
            consultationController.deleteConsultation(consult);
            System.out.println("Deleted.");
        }
    }
}
