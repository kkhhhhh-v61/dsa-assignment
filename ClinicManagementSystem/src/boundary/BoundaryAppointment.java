package boundary;

import control.ManageAppointment;
import entity.Appointment;
import entity.Patient;
import entity.Doctor;
import adt.ArrayList;
import utility.Enum.Status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.Scanner;

public class BoundaryAppointment {
    private ManageAppointment manageAppointment;
    private Scanner scanner = new Scanner(System.in);

    public BoundaryAppointment(ArrayList<Patient> patients, ArrayList<Doctor> doctors) {
        this.manageAppointment = new ManageAppointment(patients, doctors);
    }

    public void run() {
        int choice;
        do {
            System.out.println("\n=== Appointment Management ===");
            System.out.println("1. Add Appointment");
            System.out.println("2. View All Appointments");
            System.out.println("3. Search Appointment by ID");
            System.out.println("4. Update Appointment");
            System.out.println("5. Delete Appointment");
            System.out.println("0. Exit");
            System.out.print("\nEnter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> addAppointmentUI();
                case 2 -> viewAllAppointmentsUI();
                case 3 -> searchAppointmentUI();
                case 4 -> updateAppointmentUI();
                case 5 -> deleteAppointmentUI();
                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice, try again.");
            }
        } while (choice != 0);
    }

    // ====== UI Methods ======
    private void addAppointmentUI() {
        Patient patient = null;
        Doctor doctor = null;

        // Patient ID
        while (patient == null) {
            System.out.print("\nEnter Patient ID (or EXIT to cancel): ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("EXIT")) {
                System.out.println("Appointment creation cancelled.");
                return;
            }
            patient = manageAppointment.findPatientById(input);
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
            doctor = manageAppointment.findDoctorById(input);
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
        Status status = null;
        while (status == null) {
            System.out.println("\nAvailable Status: " + java.util.Arrays.toString(Status.values()));
            System.out.print("\nEnter Status (or EXIT to cancel): ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("EXIT")) {
                System.out.println("Appointment creation cancelled.");
                return;
            }
            try {
                status = Status.valueOf(input);
            } catch (Exception e) {
                System.out.println("Invalid status. Please select either 1 "  + java.util.Arrays.toString(Status.values()));
            }
        }

        Appointment appt = manageAppointment.addAppointment(patient, doctor, date, time, reason, status);
        System.out.println("\nAppointment created: " + appt.getAppointmentId());
    }

    private void viewAllAppointmentsUI() {
        ArrayList<Appointment> appts = manageAppointment.getAllAppointments();
        if (appts.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }
        for (int i = 1; i <= appts.size(); i++) {
            Appointment a = appts.getEntry(i);
            Patient p = manageAppointment.findPatientById(a.getPatientId());
            Doctor d = manageAppointment.findDoctorById(a.getDoctorId());
            System.out.println("\n" + a);
            System.out.println("Patient: " + (p != null ? p.getName() : "Unknown"));
            System.out.println("Doctor : " + (d != null ? d.getName() : "Unknown"));
            System.out.println("\n----------------------------");
        }
    }

    private void searchAppointmentUI() {
        System.out.print("Enter Appointment ID: ");
        Appointment appt = manageAppointment.findAppointmentById(scanner.nextLine());
        if (appt == null) {
            System.out.println("Appointment not found.");
        } else {
            Patient p = manageAppointment.findPatientById(appt.getPatientId());
            Doctor d = manageAppointment.findDoctorById(appt.getDoctorId());
            System.out.println("\n" + appt);
            System.out.println("Patient: " + (p != null ? p.getName() : "Unknown"));
            System.out.println("Doctor : " + (d != null ? d.getName() : "Unknown"));
        }
    }

    private void updateAppointmentUI() {
        System.out.print("Enter Appointment ID to update (or type EXIT to cancel): ");
        String id = scanner.nextLine();
        if (id.equalsIgnoreCase("EXIT")) {
            System.out.println("Update cancelled.");
            return;
        }

        Appointment appt = manageAppointment.findAppointmentById(id);
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
        manageAppointment.updateAppointment(appt, newDate, newTime, newReason, newStatus);

        System.out.println("\nAppointment updated successfully!");
    }
    
    private void deleteAppointmentUI() {
        System.out.print("Enter Appointment ID to delete: ");
        Appointment appt = manageAppointment.findAppointmentById(scanner.nextLine());
        if (appt == null) {
            System.out.println("Appointment not found.");
            return;
        }
        Patient p = manageAppointment.findPatientById(appt.getPatientId());
        Doctor d = manageAppointment.findDoctorById(appt.getDoctorId());
        System.out.println("\n" + appt);
        System.out.println("Patient: " + (p != null ? p.getName() : "Unknown"));
        System.out.println("Doctor : " + (d != null ? d.getName() : "Unknown"));
        System.out.print("\nAre you sure? (Y/N): ");
        if (scanner.nextLine().equalsIgnoreCase("Y")) {
            manageAppointment.deleteAppointment(appt);
            System.out.println("Appointment deleted.");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }
}
