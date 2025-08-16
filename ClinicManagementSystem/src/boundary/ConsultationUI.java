package boundary;

import adt.ArrayList;
import entity.Consultation;
import entity.Doctor;
import entity.Patient;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import control.ConsultationManager;
import java.util.Scanner;
import utility.Enum;
import utility.Enum.Gender;

/**
 *
 * @author User
 */
public class ConsultationUI {

    private ConsultationManager consultationManager;
    private Scanner scanner;

    public ConsultationUI(ConsultationManager consultationManager) {
        this.consultationManager = consultationManager;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        int choice;
        do {
            showMenu();
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addConsultation();
                    break;
                case 2:
                    viewConsultationDetails();
                    break;
                case 3:
                    updateConsultation();
                    break;
                case 4:
                    deleteConsultation();
                    break;
                case 5:
                    generateReports();
                    break;
                case 6:
                    scheduleFollowUpAppointment();
                    break;
                case 7:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 7);
    }

    private void showMenu() {
        System.out.println("\n--- Consultation Management ---");
        System.out.println("1. Add Consultation");
        System.out.println("2. View Consultation Details");
        System.out.println("3. Update Consultation");
        System.out.println("4. Delete Consultation");
        System.out.println("5. Generate Reports");
        System.out.println("6. Schedule Follow-up Appointment");
        System.out.println("7. Exit");
    }

    private void addConsultation() {
        System.out.println("\n--- Add New Consultation ---");
        // For simplicity, we'll use hardcoded Patient and Doctor objects.
        // In a real application, you would search for existing patients/doctors.
        System.out.print("Enter Patient IC Number (e.g., 990101-10-1234): ");
        String patientIc = scanner.nextLine();
        Patient patient = new Patient();
        patient.setIcNumber(patientIc);
        patient.setName("Dummy Patient");
        patient.setGender(Gender.Male);


        System.out.print("Enter Doctor IC Number (e.g., 800202-10-5678): ");
        String doctorIc = scanner.nextLine();
        Doctor doctor = new Doctor();
        doctor.setIcNumber(doctorIc);
        doctor.setName("Dummy Doctor");
        doctor.setGender(Gender.Female);

        System.out.print("Enter Symptoms (comma-separated): ");
        String[] symptomsArr = scanner.nextLine().split(",");
        ArrayList<String> symptoms = new ArrayList<>();
        for (String s : symptomsArr) {
            symptoms.add(s.trim());
        }

        System.out.print("Enter Diagnosis (comma-separated): ");
        String[] diagnosisArr = scanner.nextLine().split(",");
        ArrayList<String> diagnosis = new ArrayList<>();
        for (String d : diagnosisArr) {
            diagnosis.add(d.trim());
        }

        System.out.print("Enter Notes: ");
        String notes = scanner.nextLine();

        Consultation newConsultation = new Consultation(
                doctor,
                patient,
                LocalDate.now(),
                LocalTime.now(),
                symptoms,
                diagnosis,
                notes,
                Enum.Status.Completed
        );

        consultationManager.addConsultation(newConsultation);
        System.out.println("Consultation added successfully! New Consultation ID: " + newConsultation.getConsultationId());
    }

    private void viewConsultationDetails() {
        System.out.println("\n--- View Consultation Details ---");
        System.out.println("Search by:");
        System.out.println("1. Consultation ID");
        System.out.println("2. Patient ID");
        System.out.println("3. Doctor ID");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                System.out.print("Enter Consultation ID: ");
                String consultationId = scanner.nextLine();
                Consultation consultation = consultationManager.getConsultationById(consultationId);
                if (consultation != null) {
                    System.out.println(consultation);
                } else {
                    System.out.println("Consultation not found.");
                }
                break;
            case 2:
                System.out.print("Enter Patient ID: ");
                String patientId = scanner.nextLine();
                adt.ListInterface<Consultation> patientConsultations = consultationManager.getConsultationsByPatientId(patientId);
                if (!patientConsultations.isEmpty()) {
                    for (int i = 1; i <= patientConsultations.size(); i++) {
                        System.out.println(patientConsultations.getEntry(i));
                        System.out.println("---");
                    }
                } else {
                    System.out.println("No consultations found for this patient.");
                }
                break;
            case 3:
                System.out.print("Enter Doctor ID: ");
                String doctorId = scanner.nextLine();
                adt.ListInterface<Consultation> doctorConsultations = consultationManager.getConsultationsByDoctorId(doctorId);
                if (!doctorConsultations.isEmpty()) {
                    for (int i = 1; i <= doctorConsultations.size(); i++) {
                        System.out.println(doctorConsultations.getEntry(i));
                        System.out.println("---");
                    }
                } else {
                    System.out.println("No consultations found for this doctor.");
                }
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void updateConsultation() {
        System.out.println("\n--- Update Consultation ---");
        System.out.print("Enter Consultation ID to update: ");
        String consultationId = scanner.nextLine();
        Consultation consultation = consultationManager.getConsultationById(consultationId);

        if (consultation != null) {
            System.out.println("Enter new details (leave blank to keep current value):");

            System.out.print("Enter Symptoms (comma-separated): ");
            String symptomsStr = scanner.nextLine();
            if (!symptomsStr.isEmpty()) {
                String[] symptomsArr = symptomsStr.split(",");
                ArrayList<String> symptoms = new ArrayList<>();
                for (String s : symptomsArr) {
                    symptoms.add(s.trim());
                }
                consultation.setSymptoms(symptoms);
            }

            System.out.print("Enter Diagnosis (comma-separated): ");
            String diagnosisStr = scanner.nextLine();
            if (!diagnosisStr.isEmpty()) {
                String[] diagnosisArr = diagnosisStr.split(",");
                ArrayList<String> diagnosis = new ArrayList<>();
                for (String d : diagnosisArr) {
                    diagnosis.add(d.trim());
                }
                consultation.setDiagnosis(diagnosis);
            }

            System.out.print("Enter Notes: ");
            String notes = scanner.nextLine();
            if (!notes.isEmpty()) {
                consultation.setNotes(notes);
            }

            consultationManager.updateConsultation(consultation);
            System.out.println("Consultation updated successfully.");
        } else {
            System.out.println("Consultation not found.");
        }
    }

    private void deleteConsultation() {
        System.out.println("\n--- Delete Consultation ---");
        System.out.print("Enter Consultation ID to delete: ");
        String consultationId = scanner.nextLine();
        Consultation consultation = consultationManager.getConsultationById(consultationId);

        if (consultation != null) {
            System.out.print("Are you sure you want to delete this consultation? (yes/no): ");
            String confirmation = scanner.nextLine();
            if (confirmation.equalsIgnoreCase("yes")) {
                consultationManager.deleteConsultation(consultationId);
                System.out.println("Consultation deleted successfully.");
            } else {
                System.out.println("Deletion cancelled.");
            }
        } else {
            System.out.println("Consultation not found.");
        }
    }

    private void generateReports() {
        System.out.println("\n--- Generate Reports ---");
        System.out.println("1. Consultations Per Doctor");
        System.out.println("2. Common Symptoms");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                System.out.println(consultationManager.generateConsultationsPerDoctorReport());
                break;
            case 2:
                System.out.println(consultationManager.generateCommonSymptomsReport());
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void scheduleFollowUpAppointment() {
        System.out.println("\n--- Schedule Follow-up Appointment ---");
        System.out.print("Enter Consultation ID for follow-up: ");
        String consultationId = scanner.nextLine();
        Consultation consultation = consultationManager.getConsultationById(consultationId);

        if (consultation != null) {
            try {
                System.out.print("Enter follow-up date (YYYY-MM-DD): ");
                LocalDate date = LocalDate.parse(scanner.nextLine());
                System.out.print("Enter follow-up time (HH:MM): ");
                LocalTime time = LocalTime.parse(scanner.nextLine());

                entity.Appointment followUp = consultationManager.scheduleFollowUp(consultationId, date, time);
                if (followUp != null) {
                    System.out.println("Follow-up appointment scheduled successfully:");
                    System.out.println(followUp);
                } else {
                    System.out.println("Failed to schedule follow-up appointment.");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date or time format. Please use YYYY-MM-DD for date and HH:MM for time.");
            }
        } else {
            System.out.println("Consultation not found.");
        }
    }
}