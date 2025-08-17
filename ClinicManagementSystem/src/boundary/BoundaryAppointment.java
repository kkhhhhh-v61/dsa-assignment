package boundary;

import control.AppointmentController;
import entity.Appointment;
import entity.Patient;
import entity.Doctor;
import adt.ArrayList;
import utility.Enum.Status;
import control.MainMenu;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import utility.UtilityUI;

public class BoundaryAppointment {
    private AppointmentController appointmentController;
    private Scanner scanner = new Scanner(System.in);
    UtilityUI utilityUI = new UtilityUI();
    private String role;
    private static MainMenu mainMenu = new MainMenu();
    public BoundaryAppointment(ArrayList<Patient> patients, ArrayList<Doctor> doctors, String role) {
        this.appointmentController = new AppointmentController(patients, doctors);
        this.role = role.toUpperCase();
    }

    public void run() {
        int choice;
            if (role.equals("STUDENTADD")) {
                addAppointmentUI();
                mainMenu.runMainMenu('1');
            } else if (role.equals("STUDENTVIEW")) {
                viewAllAppointmentsByPatientIdUI();
                mainMenu.runMainMenu('1');
            } else if (role.equals("STAFF")) {
            do {
                utilityUI.displayLine();
                System.out.println("|                                      APPOINTMENT MANAGEMENT                                      |");
                utilityUI.displayLine();
                System.out.println("| [1] Add Appointment                                                                              |");
                System.out.println("| [2] View All Appointments                                                                        |");
                System.out.println("| [3] Search Appointment by ID                                                                     |");
                System.out.println("| [4] Update Appointment                                                                           |");
                System.out.println("| [5] Delete Appointment                                                                           |");
                System.out.println("| [0] Exit                                                                                         |");
                utilityUI.displayLine();
                
                System.out.print("\nEnter choice: ");
            choice = scanner.nextInt(); 
            scanner.nextLine(); // consume newline
                switch (choice) {
                    case 1 -> addAppointmentUI();
                    case 2 -> viewAllAppointmentsUI();
                    case 3 -> searchAppointmentUI();
                    case 4 -> updateAppointmentUI();
                    case 5 -> deleteAppointmentUI();
                    case 0 -> mainMenu.runMainMenu('2');
                    default -> System.out.println("Invalid choice, try again.");
                }
            }while (choice != 0);
        } 
    }

    // ====== UI Methods ======
    private void addAppointmentUI() {
        Patient patient = null;
        Doctor doctor = null;
        utilityUI.displayLine();
        System.out.println("|                                         ADD APPOINTMENT                                          |");
        utilityUI.displayLine();
        // Patient ID
        while (patient == null) {
            System.out.print("Enter Patient ID (or EXIT to cancel): ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("EXIT")) {
                System.out.println("Appointment creation cancelled.");
                return;
            }
            patient = appointmentController.findPatientById(input);
            if (patient == null) System.out.println("Patient not found.");
        }

// Doctor ID
        while (doctor == null) {
            System.out.print("\nEnter Doctor ID (or EXIT to cancel): ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("EXIT")) {
                System.out.println("Appointment creation cancelled.");
                return;
            }
            doctor = appointmentController.findDoctorById(input);
            if (doctor == null) System.out.println("Doctor not found.");
        }

// Date
        LocalDate date = null;
        while (date == null) {
            System.out.print("\nEnter Appointment Date (YYYY-MM-DD, or EXIT to cancel): ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("EXIT")) {
                System.out.println("Appointment creation cancelled.");
                return;
            }
            try {
                LocalDate enteredDate = LocalDate.parse(input);
                LocalDate oneWeekLater = LocalDate.now().plusWeeks(1);

                if (enteredDate.isAfter(oneWeekLater.minusDays(1))) { 
                    // valid: must be strictly after now + 7 days
                    date = enteredDate;
                } else {
                    System.out.println("Appointment must be at least 1 week later than today (" + oneWeekLater + " or after).");
                }
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }

// Time
        LocalTime time = null;
        while (time == null) {
            System.out.print("\nEnter Appointment Time (HH:MM, or EXIT to cancel): ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("EXIT")) {
                System.out.println("Appointment creation cancelled.");
                return;
            }
            try {
                time = LocalTime.parse(input);
            } catch (Exception e) {
                System.out.println("Invalid time.");
            }
        }
        
// Reason
        System.out.print("\nEnter Reason (or EXIT to cancel): ");
        String reason = scanner.nextLine();
        if (reason.equalsIgnoreCase("EXIT")) {
                System.out.println("Appointment creation cancelled.");
                return;
            }
        
// Status
        Status status = Status.valueOf("Pending");

        Appointment appt = appointmentController.addAppointment(patient, doctor, date, time, reason, status);
        System.out.println("\nAppointment created: " + appt.getAppointmentId());
    }

    private void viewAllAppointmentsUI() {
        utilityUI.displayLine();
        System.out.println("|                                      VIEW ALL APPOINTMENTS                                       |");
        utilityUI.displayLine();
        ArrayList<Appointment> appts = appointmentController.getAllAppointments();
        if (appts.isEmpty()) {
            System.out.println("\nNo appointments found.\n");
            utilityUI.displayLine();
            return;
        }
        
        for (int i = 1; i <= appts.size(); i++) {
            Appointment a = appts.getEntry(i);
            Patient p = appointmentController.findPatientById(a.getPatientId());
            Doctor d = appointmentController.findDoctorById(a.getDoctorId());
            System.out.println("\n" + a);
            System.out.println("Patient: " + (p != null ? p.getName() : "Unknown"));
            System.out.println("Doctor : " + (d != null ? d.getName() : "Unknown"));
            utilityUI.displayLine();
        }
    }

    private void viewAllAppointmentsByPatientIdUI() {
        System.out.print("Enter Patient ID: ");
        String patientId = scanner.nextLine();

        Patient patient = appointmentController.findPatientById(patientId);
        if (patient == null) {
            System.out.println("\nPatient not found.\n");
            utilityUI.displayLine();
            return;
        }

        utilityUI.displayLine();
        System.out.println("|                    VIEW ALL APPOINTMENTS FOR PATIENT: " + patient.getName() + "                    |");
        utilityUI.displayLine();

        ArrayList<Appointment> appts = appointmentController.getAllAppointments();
        boolean found = false;

        for (int i = 1; i <= appts.size(); i++) {
            Appointment a = appts.getEntry(i);
            if (a.getPatientId().equalsIgnoreCase(patientId)) {
                Doctor d = appointmentController.findDoctorById(a.getDoctorId());
                System.out.println("\n" + a);
                System.out.println("Patient: " + patient.getName());
                System.out.println("Doctor : " + (d != null ? d.getName() : "Unknown"));
                utilityUI.displayLine();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No appointments found for this patient.");
        }
    }
    
    private void searchAppointmentUI() {
        utilityUI.displayLine();
        System.out.println("|                                     VIEW APPOINTMENT BY ID                                       |");
        utilityUI.displayLine();
        System.out.print("Enter Appointment ID: ");
        Appointment appt = appointmentController.findAppointmentById(scanner.nextLine());
        
        if (appt == null) {
            System.out.println("\nAppointment not found.\n");
            utilityUI.displayLine();
        } else {
            Patient p = appointmentController.findPatientById(appt.getPatientId());
            Doctor d = appointmentController.findDoctorById(appt.getDoctorId());
            System.out.println("\n" + appt);
            System.out.println("Patient: " + (p != null ? p.getName() : "Unknown"));
            System.out.println("Doctor : " + (d != null ? d.getName() : "Unknown"));
            utilityUI.displayLine();
        }
    }

    private void updateAppointmentUI() {
        utilityUI.displayLine();
        System.out.println("|                                       UPDATE APPOINTMENT                                         |");
        utilityUI.displayLine();
        System.out.print("Enter Appointment ID to update (or type EXIT to cancel): ");
        String id = scanner.nextLine();
        if (id.equalsIgnoreCase("EXIT")) {
            System.out.println("Update cancelled.");
            return;
        }

        Appointment appt = appointmentController.findAppointmentById(id);
        if (appt == null) {
            System.out.println("Appointment not found.");
            return;
        }

        System.out.println("Updating appointment: " + appt.getAppointmentId());

// === Date validation ===
        LocalDate newDate = null;
        while (true) {
            System.out.print("\nEnter Appointment Date (YYYY-MM-DD, or type EXIT to cancel/press ENTER to skip) [" + appt.getAppointmentDate() + "]: ");
            String dateStr = scanner.nextLine();
            if (dateStr.equalsIgnoreCase("EXIT")) {
                System.out.println("Update cancelled.");
                return;
            }
            if (dateStr.isEmpty()) break; // keep old date
            try {
                LocalDate date = LocalDate.parse(dateStr);

                LocalDate today = LocalDate.now();
                LocalDate minDate = today.plusDays(1);   // must be at least tomorrow
                LocalDate maxDate = today.plusWeeks(1);  // within 1 week

                if (date.isBefore(minDate) || date.isAfter(maxDate)) {
                    System.out.println("Appointment must be scheduled between " + minDate + " and " + maxDate + ".");
                    continue;
                }
                newDate = date;
                break;
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }

// === Time validation ===
        LocalTime newTime = null;
        while (true) {
            System.out.print("\nEnter new Time (HH:MM) (or type EXIT to cancel/press ENTER to skip) [" + appt.getAppointmentTime() + "]: ");
            String timeStr = scanner.nextLine();
            if (timeStr.equalsIgnoreCase("EXIT")) {
                System.out.println("Update cancelled.");
                return;
            }
            if (timeStr.isEmpty()) break; // keep old
            try {
                newTime = LocalTime.parse(timeStr);
                break;
            } catch (Exception e) {
                System.out.println("Invalid time format. Please use HH:MM (24-hour).");
            }
        }

// === Reason ===
        System.out.print("\nEnter new Reason (or type EXIT to cancel/press ENTER to skip) [" + appt.getReason() + "]: ");
        String reason = scanner.nextLine();
        if (reason.equalsIgnoreCase("EXIT")) {
                System.out.println("Update cancelled.");
                return;
            }
        String newReason = reason.isEmpty() ? null : reason;

// === Status validation ===
        Status newStatus = null;
        while (true) {
            System.out.println("\nAvailable Status: " + java.util.Arrays.toString(Status.values()));
            System.out.print("Enter new Status (or type EXIT to cancel/press ENTER to skip) [" + appt.getStatus() + "]: ");
            String statusStr = scanner.nextLine();
            if (statusStr.equalsIgnoreCase("EXIT")) {
                System.out.println("Update cancelled.");
                return;
            }
            if (statusStr.isEmpty()) break; // keep old
            try {
                newStatus = Status.valueOf(statusStr);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid status. Available: " + java.util.Arrays.toString(Status.values()));
            }
        }

        // Call control layer to apply changes
        appointmentController.updateAppointment(appt, newDate, newTime, newReason, newStatus);

        System.out.println("\nAppointment updated successfully!");
    }
    
    private void deleteAppointmentUI() {
        utilityUI.displayLine();
        System.out.println("|                                       DELETE APPOINTMENT                                         |");
        utilityUI.displayLine();
        System.out.print("Enter Appointment ID to delete: ");
        Appointment appt = appointmentController.findAppointmentById(scanner.nextLine());
        if (appt == null) {
            System.out.println("Appointment not found.");
            return;
        }
        Patient p = appointmentController.findPatientById(appt.getPatientId());
        Doctor d = appointmentController.findDoctorById(appt.getDoctorId());
        utilityUI.displayLine();
        System.out.println("\n" + appt);
        System.out.println("Patient: " + (p != null ? p.getName() : "Unknown"));
        System.out.println("Doctor : " + (d != null ? d.getName() : "Unknown"));
        utilityUI.displayLine();
        System.out.print("\nAre you sure? (Y/N): ");
        if (scanner.nextLine().equalsIgnoreCase("Y")) {
            appointmentController.deleteAppointment(appt);
            System.out.println("Appointment deleted.");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }
}
