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
 * @author Brian Ooi Shu Yi
 */
public class DoctorUI {

    private final DoctorController doctorController = new DoctorController();
    private final Scanner scanner = new Scanner(System.in);
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void showDoctorMenu() {
        int choice = -1; // Initialize to a non-exit value
        do {
            System.out.println("\n+-----------------------------------+");
            System.out.println("|         Doctor Management         |");
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
            
            // IMPROVED: Handle non-integer input gracefully
            String input = scanner.nextLine();
            try {
                choice = Integer.parseInt(input);

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
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                choice = -1; // Reset choice to continue the loop
            }
        } while (choice != 0);
    }

    private void addDoctorUI() {
        // get last id from doctors.txt
        ListInterface<Doctor> doctorList = doctorController.getAllDoctors();
        String lastId = doctorList.get(doctorList.size()).getDoctorId();
        int lastIdInt = Integer.parseInt(lastId.substring(1));
        Doctor.setLastId(lastIdInt);

        String name, icNumber, contactNumber, email, address;
        Gender gender;
        LocalDate birthDate;
        Specialization specialization;

        System.out.println("\n--- Add New Doctor ---");

        // Ic
        while (true) {
            System.out.print("Enter IC Number (e.g., 901231-14-1234): ");
            icNumber = scanner.nextLine();
            String error = DoctorValidator.validateNewIcNumber(icNumber, doctorController.getAllDoctors());
            if (error == null) {
                break;
            }
            System.out.println(error);
        }

        birthDate = DoctorValidator.getBirthDateFromIc(icNumber);
        gender = DoctorValidator.getGenderFromIc(icNumber);

        System.out.println("IC Number accepted.");
        System.out.println("Derived Birth Date: " + birthDate.format(dateFormatter));
        System.out.println("Derived Gender: " + gender);

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

        // Contact  Number
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
                if (choice >= 1 && choice <= choiceCount) {
                    specialization = availableSpecs[choice - 1];
                    break;
                } else {
                    System.out
                            .println("Error: Invalid choice. Please enter a number between 1 and " + choiceCount + ".");
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
        for (int i = 1; i <= doctors.size(); i++) {
            Doctor doc = doctors.get(i);
            System.out.printf("%-10s | %-20s | %-15s | %s\n", doc.getDoctorId(), doc.getName(), doc.getSpecialization(), doc.getContactNumber());
        }
    }

     private void searchDoctorUI() {
        System.out.print("\nEnter Doctor ID to search: ");
        String docId = scanner.nextLine();
        Doctor doc = doctorController.getDoctorById(docId);

        if (doc != null) {
            // IMPROVED: Formatted output for single doctor details
            System.out.println("\n--- Doctor Details [" + doc.getDoctorId() + "] ---");
            System.out.println("+-------------------------+--------------------------------+");
            System.out.printf("| %-23s | %-30s |\n", "Field", "Value");
            System.out.println("+-------------------------+--------------------------------+");
            System.out.printf("| %-23s | %-30s |\n", "Name", doc.getName());
            System.out.printf("| %-23s | %-30s |\n", "IC Number", doc.getIcNumber());
            System.out.printf("| %-23s | %-30s |\n", "Gender", doc.getGender());
            System.out.printf("| %-23s | %-30s |\n", "Birth Date", doc.getBirthDate().format(dateFormatter));
            System.out.printf("| %-23s | %-30d |\n", "Age", doc.getAge());
            System.out.printf("| %-23s | %-30s |\n", "Contact No.", doc.getContactNumber());
            System.out.printf("| %-23s | %-30s |\n", "Email", doc.getEmail());
            System.out.printf("| %-23s | %-30s |\n", "Address", doc.getAddress());
            System.out.println("+-------------------------+--------------------------------+");
            System.out.printf("| %-23s | %-30s |\n", "Specialization", doc.getSpecialization());
            System.out.printf("| %-23s | %-30d |\n", "Years of Experience", doc.getYearsOfExperience());
            System.out.printf("| %-23s | %-30s |\n", "Registration Date", doc.getRegistrationDate().format(dateFormatter));
            System.out.println("+-------------------------+--------------------------------+");

        } else {
            System.out.println("Doctor with ID " + docId + " not found.");
        }
    }

    private void updateDoctorUI() {
        viewAllDoctorsUI();
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
                    System.out.println("- " + finalErrors.get(i));
                }
            }
        } else {
            System.out.println("Error: Doctor with ID " + docId + " not found.");
        }
    }

    private void deleteDoctorUI() {
        viewAllDoctorsUI();
        System.out.print("\nEnter Doctor ID to delete: ");
        String docId = scanner.nextLine();
        Doctor doc = doctorController.getDoctorById(docId);

        if (doc != null) {
            System.out.print("Are you sure you want to delete Dr. " + doc.getName() + " (Y-Yes)? ");
            String confirm = scanner.nextLine();
            if (confirm.equalsIgnoreCase("Y") | confirm.equalsIgnoreCase("y")) {
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

        int choice = -1;
        do {
            System.out.println("\n--- Schedule Management for Dr. " + doc.getName() + " ---");
            System.out.println("1. Add Schedule");
            System.out.println("2. View Schedules");
            System.out.println("3. Delete Schedule");
            System.out.println("0. Back");
            System.out.print("Enter your choice: ");
            
            String input = scanner.nextLine();
            try {
                choice = Integer.parseInt(input);
                switch (choice) {
                    case 1 -> addScheduleUI(docId);
                    case 2 -> viewSchedulesUI(docId);
                    case 3 -> deleteScheduleUI(docId);
                    case 0 -> System.out.println("Returning to Doctor Menu...");
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                choice = -1;
            }
        } while (choice != 0);
    }

    private void addScheduleUI(String doctorId) {
        // get last id from schedules.txt
        ListInterface<Schedule> scheduleList = doctorController.getAllSchedules();
        String lastId = scheduleList.get(scheduleList.size()).getScheduleId();
        int lastIdInt = Integer.parseInt(lastId.substring(1));
        Schedule.setLastId(lastIdInt);

        DayOfWeek day;
        LocalTime start;
        LocalTime end;

        // IMPROVED: Loop for Day of Week selection
        while (true) {
            System.out.println("\nSelect Day of the Week:");
            DayOfWeek[] days = DayOfWeek.values();
            for (int i = 0; i < days.length; i++) {
                System.out.printf("%d. %s\n", (i + 1), days[i]);
            }
            System.out.print("Enter your choice (1-" + days.length + "): ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= days.length) {
                    day = days[choice - 1];
                    break; // Exit loop
                } else {
                    System.out.println("Error: Invalid choice. Please enter a number between 1 and " + days.length + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.");
            }
        }

        // IMPROVED: Loop for Start Time with validation
        while (true) {
            System.out.print("Enter Start Time (HH:mm): ");
            try {
                start = LocalTime.parse(scanner.nextLine(), timeFormatter);
                break; // Exit loop
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time format. Please use HH:mm.");
            }
        }

        // IMPROVED: Loop for End Time with validation
        while (true) {
            System.out.print("Enter End Time (HH:mm): ");
            try {
                end = LocalTime.parse(scanner.nextLine(), timeFormatter);
                break; // Exit loop
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time format. Please use HH:mm.");
            }
        }

        Schedule newSchedule = new Schedule(doctorId, day, start, end, true);
        
        ListInterface<String> errors = DoctorValidator.validateSchedule(newSchedule, doctorController.getSchedulesForDoctor(doctorId));
        if (errors.isEmpty()) {
            doctorController.addSchedule(newSchedule);
            System.out.println("Schedule added successfully!");
        } else {
            System.out.println("Error adding schedule:");
            for (int i = 0; i < errors.size(); i++) {
                System.out.println("- " + errors.get(i));
            }
        }
    }

    private void viewSchedulesUI(String doctorId) {
        Doctor doc = doctorController.getDoctorById(doctorId);
        System.out.println("\n--- Schedules for Dr. " + doc.getName() + " ---");
        ListInterface<Schedule> schedules = doctorController.getSchedulesForDoctor(doctorId);
        if (schedules.isEmpty()) {
            System.out.println("No schedules found for this doctor.");
            return;
        }

        // IMPROVED: Formatted table output
        String topBorder = "+------------+-----------+-------------+-----------+-------------+";
        String header = "| Schedule ID| Day       | Start Time  | End Time  | Status      |";

        System.out.println(topBorder);
        System.out.println(header);
        System.out.println(topBorder);

        for (int i = 1; i < schedules.size(); i++) {
            Schedule s = schedules.get(i);
            String status = s.getIsAvailable() ? "Available" : "Booked";
            System.out.printf("| %-10s | %-9s | %-11s | %-9s | %-11s |\n",
                    s.getScheduleId(),
                    s.getDay(),
                    s.getStartTime().format(timeFormatter),
                    s.getEndTime().format(timeFormatter),
                    status);
        }
        System.out.println(topBorder);
    }

    private void deleteScheduleUI(String doctorId) {
        viewSchedulesUI(doctorId);
        System.out.print("\nEnter Schedule ID to delete: ");
        String scheduleId = scanner.nextLine();
        doctorController.deleteSchedule(scheduleId);
        System.out.println("Schedule deleted successfully (if it existed).");
    }

    private void showReportMenu() {
        int choice = -1;
        do {
            System.out.println("\n--- Reports Menu ---");
            System.out.println("1. Doctor Specialization Report");
            System.out.println("2. Doctor Availability Report");
            System.out.println("0. Back");
            System.out.print("Enter your choice: ");
            
            String input = scanner.nextLine();
            try {
                choice = Integer.parseInt(input);
                switch (choice) {
                    case 1 -> doctorController.generateDoctorSpecializationReport();
                    case 2 -> {
                        // IMPROVED: Allow user to select day from a list
                        DayOfWeek day;
                        while (true) {
                            System.out.println("\nSelect Day for Availability Report:");
                            DayOfWeek[] days = DayOfWeek.values();
                            for (int i = 0; i < days.length; i++) {
                                System.out.printf("%d. %s\n", (i + 1), days[i]);
                            }
                            System.out.print("Enter your choice (1-" + days.length + "): ");
                            try {
                                int dayChoice = Integer.parseInt(scanner.nextLine());
                                if (dayChoice >= 1 && dayChoice <= days.length) {
                                    day = days[dayChoice - 1];
                                    doctorController.generateDoctorAvailabilityReport(day);
                                    break; // Exit the day selection loop
                                } else {
                                    System.out.println("Error: Invalid choice. Please enter a number between 1 and " + days.length + ".");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Error: Please enter a valid number.");
                            }
                        }
                    }
                    case 0 -> System.out.println("Returning to Doctor Menu...");
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                choice = -1;
            }
        } while (choice != 0);
    }
}
