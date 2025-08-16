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
        System.out.println("\n--- Add New Doctor ---");
        System.out.print("Enter IC Number (e.g., 901231-14-1234): ");
        String ic = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Gender (Male/Female): ");
        Gender gender = Gender.valueOf(scanner.nextLine());
        System.out.print("Enter Birth Date (YYYY-MM-DD): ");
        LocalDate birthDate = LocalDate.parse(scanner.nextLine(), dateFormatter);
        System.out.print("Enter Contact Number: ");
        String contact = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();
        System.out.print("Enter Specialization (Cardiology, Dermatology, etc.): ");
        Specialization spec = Specialization.valueOf(scanner.nextLine());

        Doctor newDoctor = new Doctor(ic, name, gender, birthDate, contact, email, address, LocalDate.now(), spec, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        doctorController.addDoctor(newDoctor);
        System.out.println("Doctor added successfully! Doctor ID: " + newDoctor.getDoctorId());
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
        System.out.print("\nEnter Doctor ID to update: ");
        String docId = scanner.nextLine();
        Doctor doc = doctorController.getDoctorById(docId);

        if (doc != null) {
            System.out.println("Enter new information (leave blank to keep current value):");
            System.out.print("Update Contact Number (" + doc.getContactNumber() + "): ");
            String contact = scanner.nextLine();
            if (!contact.isEmpty()) doc.setContactNumber(contact);

            System.out.print("Update Email (" + doc.getEmail() + "): ");
            String email = scanner.nextLine();
            if (!email.isEmpty()) doc.setEmail(email);

            System.out.print("Update Address (" + doc.getAddress() + "): ");
            String address = scanner.nextLine();
            if (!address.isEmpty()) doc.setAddress(address);

            doctorController.updateDoctor(doc);
            System.out.println("Doctor information updated successfully.");
        } else {
            System.out.println("Doctor with ID " + docId + " not found.");
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
                case 1 -> addScheduleUI(docId);
                case 2 -> viewSchedulesUI(docId);
                case 3 -> deleteScheduleUI(docId);
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
                case 1 -> doctorController.generateDoctorSpecializationReport();
                case 2 -> {
                    System.out.print("Enter Day of Week (e.g., Monday): ");
                    DayOfWeek day = DayOfWeek.valueOf(scanner.nextLine());
                    doctorController.generateDoctorAvailabilityReport(day);
                }
            }
        } while (choice != 0);
    }
}