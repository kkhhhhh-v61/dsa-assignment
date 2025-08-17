/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import adt.ArrayList;
import adt.ListInterface;
import control.DoctorController;
import entity.Doctor;
import entity.Schedule;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import utility.DoctorValidator;
import utility.Enum.DayOfWeek;
import utility.Enum.Gender;
import utility.Enum.Specialization;

/**
 *
 * @author brian
 */
public class DoctorUI {

    private final DoctorController doctorController = new DoctorController();
    private final Scanner scanner = new Scanner(System.in);
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public void showDoctorMenu() {
        int choice;
        do {
            System.out.println("\n+-----------------------------------+");
            System.out.println("|        Doctor Management        |");
            System.out.println("+-----------------------------------+");
            System.out.println("| 1. Add New Doctor                 |");
            System.out.println("| 2. View All Doctors               |");
            System.out.println("| 3. Search Doctor by ID            |");
            System.out.println("| 4. Update Doctor Information      |");
            System.out.println("| 5. Delete Doctor                  |");
            System.out.println("| 6. Manage Doctor Schedules        |");
            System.out.println("| 7. Generate Reports               |");
            System.out.println("| 0. Back to Main Menu              |");
            System.out.println("+-----------------------------------+");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addDoctorUI();
                    break;
                case 2:
                    viewAllDoctorsUI();
                    break;
                case 3:
                    searchDoctorUI();
                    break;
                case 4:
                    updateDoctorUI();
                    break;
                case 5:
                    deleteDoctorUI();
                    break;
                case 6:
                    showScheduleMenu();
                    break;
                case 7:
                    showReportMenu();
                    break;
                case 0:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private void addDoctorUI() {
        String name, icNumber, contactNumber, email, address;
        Gender gender;
        LocalDate birthDate;
        Specialization specialization;

        System.out.println("\n--- Add New Doctor ---");

        // Ic
        while (true) {
            System.out.print("Enter IC Number (YYMMDD-XX-XXXX): ");
            icNumber = scanner.nextLine();

            String formatError = DoctorValidator.validateIcFormat(icNumber);
            if (formatError != null) {
                System.out.println("Error: " + formatError);
                continue; // Repeat loop
            }

            // For a new doctor, the current ID is null or empty
            String duplicateError = DoctorValidator.validateDuplicateIc(icNumber, "", doctorController.getAllDoctors());
            if (duplicateError != null) {
                System.out.println("Error: " + duplicateError);
                continue; // Repeat loop
            }

            break; // Exit loop if both format and uniqueness are valid
        }

        // Name
        while (true) {
            System.out.print("Enter Doctor's Name: ");
            name = scanner.nextLine();
            String error = DoctorValidator.validateName(name);
            if (error == null) {
                break; // Exit loop if valid
            }
            System.out.println("Error: " + error); // Show error and repeat loop
        }

        // Gender
        while (true) {
            System.out.print("Enter Gender (1 = Male, 2 = Female): ");
            String genderInput = scanner.nextLine();
            if (genderInput.equals("1")) {
                gender = Gender.Male;
                break;
            } else if (genderInput.equals("2")) {
                gender = Gender.Female;
                break;
            } else {
                System.out.println("Error: Please enter only '1' or '2'.");
            }
        }

        // Birth Date
        while (true) {
            System.out.print("Enter Birth Date (YYYY-MM-DD): ");
            String dateInput = scanner.nextLine();
            try {
                birthDate = LocalDate.parse(dateInput);
                String error = DoctorValidator.validateBirthDate(birthDate);
                if (error == null) {
                    break; // Exit loop if valid
                }
                System.out.println("Error: " + error);
            } catch (DateTimeParseException e) {
                System.out.println("Error: Invalid date format. Please use YYYY-MM-DD.");
            }
        }

        // Contack  Number
        while (true) {
            System.out.print("Enter Contact Number (e.g., 012-3456789): ");
            contactNumber = scanner.nextLine();
            String error = DoctorValidator.validatePhoneNumber(contactNumber);
            if (error == null) {
                break;
            }
            System.out.println("Error: " + error);
        }

        // Email
        while (true) {
            System.out.print("Enter Email Address: ");
            email = scanner.nextLine();
            String error = DoctorValidator.validateEmail(email);
            if (error == null) {
                break;
            }
            System.out.println("Error: " + error);
        }

        // Address
        while (true) {
            System.out.print("Enter Address: ");
            address = scanner.nextLine();
            String error = DoctorValidator.validateAddress(address);
            if (error == null) {
                break;
            }
            System.out.println("Error: " + error);
        }

        // Specialization
        while (true) {
            System.out.println("\nSelect Specialization:");
            Specialization[] availableSpecs = Specialization.values();
            int choiceCount = 0;
            // Display only valid, non-Unknown specializations
            for (int i = 0; i < availableSpecs.length; i++) {
                if (availableSpecs[i] != Specialization.Unknown) {
                    System.out.printf("%d. %s\n", (i + 1), availableSpecs[i]);
                    choiceCount++;
                }
            }

            System.out.print("Enter your choice (1-" + choiceCount + "): ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice > 0 && choice <= choiceCount) {
                    specialization = availableSpecs[choice - 1];
                    break;
                } else {
                    System.out.println("Error: Invalid choice. Please enter a number between 1 and " + choiceCount + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.");
            }
        }

        Doctor newDoctor = new Doctor(icNumber, name, gender, birthDate, contactNumber, email, address,
                LocalDate.now(), specialization, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>());

        doctorController.addDoctor(newDoctor);
        System.out.println("\nDoctor added successfully!");
    }

    private void viewAllDoctorsUI() {
        System.out.println("\n--- List of All Doctors ---");
        ListInterface<Doctor> doctors = doctorController.getAllDoctors();
        if (doctors.isEmpty()) {
            System.out.println("No doctors found.");
            return;
        }
        System.out.printf("%-10s | %-20s | %-15s | %s\n", "Doctor ID", "Name", "Specialization", "Contact No.");
        System.out.println("--------------------------------------------------------------------");
        for (int i = 0; i < doctors.size(); i++) {
            Doctor doc = doctors.getEntry(i);
            System.out.printf("%-10s | %-20s | %-15s | %s\n", doc.getDoctorId(), doc.getName(), doc.getSpecialization(), doc.getContactNumber());
        }
    }

    private void searchDoctorUI() {
        System.out.print("\nEnter Doctor ID to search: ");
        String docId = scanner.nextLine();
        Doctor doc = doctorController.getDoctorById(docId);
        if (doc != null) {
            System.out.println("\n--- Doctor Details ---");
            System.out.println(doc.toStringComplete());
        } else {
            System.out.println("Doctor with ID " + docId + " not found.");
        }
    }

    private void updateDoctorUI() {
        System.out.print("\nEnter Doctor ID to update (e.g., D0001): ");
        String docId = scanner.nextLine();
        Doctor doc = doctorController.getDoctorById(docId);

        if (doc != null) {
            System.out.println("\nNow updating Dr. " + doc.getName() + ". Press Enter to keep the current value.");

            // Update Contact Number
            while (true) {
                System.out.print("Update Contact Number (" + doc.getContactNumber() + "): ");
                String newContact = scanner.nextLine();
                if (newContact.isEmpty()) {
                    break; // User skipped, keep old value and exit loop
                }

                String error = DoctorValidator.validatePhoneNumber(newContact); //
                if (error == null) {
                    doc.setContactNumber(newContact); //
                    break; // Valid new value, exit loop
                }
                System.out.println("Error: " + error); // Invalid, show error and repeat loop
            }

            // Update Email
            while (true) {
                System.out.print("Update Email (" + doc.getEmail() + "): ");
                String newEmail = scanner.nextLine();
                if (newEmail.isEmpty()) {
                    break;
                }

                String error = DoctorValidator.validateEmail(newEmail); //
                if (error == null) {
                    doc.setEmail(newEmail); //
                    break;
                }
                System.out.println("Error: " + error);
            }

            // Update Address
            while (true) {
                System.out.print("Update Address (" + doc.getAddress() + "): ");
                String newAddress = scanner.nextLine();
                if (newAddress.isEmpty()) {
                    break;
                }

                String error = DoctorValidator.validateAddress(newAddress); //
                if (error == null) {
                    doc.setAddress(newAddress); //
                    break;
                }
                System.out.println("Error: " + error);
            }

            //  Update Doctor  Specialization
            while (true) {
                System.out.println("\nUpdate Specialization (" + doc.getSpecialization() + "):");
                Specialization[] availableSpecs = Specialization.values(); //
                int choiceCount = 0;
                for (int i = 0; i < availableSpecs.length; i++) {
                    if (availableSpecs[i] != Specialization.Unknown) {
                        System.out.printf("%d. %s\n", (i + 1), availableSpecs[i]);
                        choiceCount++;
                    }
                }

                System.out.print("Enter new choice (1-" + choiceCount + ") or leave blank to skip: ");
                String choiceInput = scanner.nextLine();

                if (choiceInput.isEmpty()) {
                    break; // User skipped
                }

                try {
                    int choice = Integer.parseInt(choiceInput);
                    if (choice > 0 && choice <= choiceCount) {
                        doc.setSpecialization(availableSpecs[choice - 1]); //
                        break; // Valid choice
                    } else {
                        System.out.println("Error: Invalid choice. Please enter a number between 1 and " + choiceCount + ".");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Please enter a valid number.");
                }
            }

            ListInterface<String> finalErrors = doctorController.updateDoctor(doc);

            if (finalErrors.isEmpty()) {
                System.out.println("\nDoctor information updated successfully!");
            } else {
                // This is a safeguard; should not happen if UI validation is thorough
                System.out.println("\nUpdate failed due to a system validation error:");
                for (int i = 0; i < finalErrors.size(); i++) {
                    System.out.println("- " + finalErrors.getEntry(i));
                }
            }
        } else {
            System.out.println("Error: Doctor with ID " + docId + " not found.");
        }
    }

    private void deleteDoctorUI() {
        System.out.print("\nEnter Doctor ID to delete: ");
        String docId = scanner.nextLine();
        Doctor doc = doctorController.getDoctorById(docId);

        if (doc != null) {
            System.out.print("Are you sure you want to delete Dr. " + doc.getName() + " (Y/N)? ");
            String confirm = scanner.nextLine();
            if (confirm.equalsIgnoreCase("Y")) {
                doctorController.deleteDoctor(docId);
                System.out.println("Doctor deleted successfully.");
            } else {
                System.out.println("Deletion cancelled.");
            }
        } else {
            System.out.println("Doctor with ID " + docId + " not found.");
        }
    }

    private void showScheduleMenu() {
        System.out.print("\nEnter Doctor ID to manage schedules: ");
        String docId = scanner.nextLine();
        Doctor doc = doctorController.getDoctorById(docId);
        if (doc == null) {
            System.out.println("Doctor not found.");
            return;
        }

        int choice;
        do {
            System.out.println("\n--- Schedule Management for Dr. " + doc.getName() + " ---");
            System.out.println("1. Add Schedule");
            System.out.println("2. View Schedules");
            System.out.println("3. Delete Schedule");
            System.out.println("0. Back");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 ->
                    addScheduleUI(docId);
                case 2 ->
                    viewSchedulesUI(docId);
                case 3 ->
                    deleteScheduleUI(docId);
            }
        } while (choice != 0);
    }

    private void addScheduleUI(String doctorId) {
        try {
            System.out.print("Enter Day of Week (e.g., Monday): ");
            DayOfWeek day = DayOfWeek.valueOf(scanner.nextLine());
            System.out.print("Enter Start Time (HH:mm): ");
            LocalTime start = LocalTime.parse(scanner.nextLine(), timeFormatter);
            System.out.print("Enter End Time (HH:mm): ");
            LocalTime end = LocalTime.parse(scanner.nextLine(), timeFormatter);

            Schedule newSchedule = new Schedule(doctorId, day, start, end, true);
            doctorController.addSchedule(newSchedule);
            System.out.println("Schedule added successfully!");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid time format. Please use HH:mm.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Day of Week. Please enter a valid day.");
        }
    }

    private void viewSchedulesUI(String doctorId) {
        System.out.println("\n--- Schedules for Doctor " + doctorId + " ---");
        ListInterface<Schedule> schedules = doctorController.getSchedulesForDoctor(doctorId);
        if (schedules.isEmpty()) {
            System.out.println("No schedules found for this doctor.");
            return;
        }
        for (int i = 0; i < schedules.size(); i++) {
            System.out.println(schedules.getEntry(i).toString());
        }
    }

    private void deleteScheduleUI(String doctorId) {
        viewSchedulesUI(doctorId);
        System.out.print("\nEnter Schedule ID to delete: ");
        String scheduleId = scanner.nextLine();
        // Here you would ideally check if the schedule belongs to the doctor first
        doctorController.deleteSchedule(scheduleId);
        System.out.println("Schedule deleted successfully (if it existed).");
    }

    private void showReportMenu() {
        int choice;
        do {
            System.out.println("\n--- Reports Menu ---");
            System.out.println("1. Doctor Specialization Report");
            System.out.println("2. Doctor Availability Report");
            System.out.println("0. Back");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 ->
                    doctorController.generateDoctorSpecializationReport();
                case 2 -> {
                    System.out.print("Enter Day of Week (e.g., Monday): ");
                    DayOfWeek day = DayOfWeek.valueOf(scanner.nextLine());
                    doctorController.generateDoctorAvailabilityReport(day);
                }
            }
        } while (choice != 0);
    }
}
