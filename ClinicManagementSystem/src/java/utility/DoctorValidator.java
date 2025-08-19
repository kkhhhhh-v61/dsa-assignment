/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

import adt.ArrayList;
import adt.ListInterface;
import entity.Doctor;
import entity.Schedule;
import java.time.LocalDate;
import java.util.regex.Pattern;
import utility.Enum.*;

/**
 *
 * @author brian
 */
public class DoctorValidator {

    // Email Validation
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,6}$");

    // Ic Validation
    private static final Pattern IC_PATTERN = Pattern.compile("^\\d{6}-\\d{2}-\\d{4}$");

    // Phone Number Validation
    private static final Pattern PHONE_PATTERN = Pattern.compile("^01[0-9]-\\d{7,8}$");

    /**
     * Validates a Doctor object.
     *
     * @param doctor The doctor to validate.
     * @param doctorList The list of existing doctors to check for duplicates.
     * @return A list of error messages. An empty list means the doctor is
     * valid.
     */
    // Name Validation
    public static String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "Doctor's name cannot be empty.";
        }
        return null; // Valid
    }

    // IC Number Validation
    public static String validateIcFormat(String icNumber) {
        if (icNumber == null || icNumber.trim().isEmpty()) {
            return "IC number cannot be empty.";
        }
        if (!IC_PATTERN.matcher(icNumber).matches()) {
            return "Invalid IC Number format. Expected format: YYMMDD-XX-XXXX.";
        }
        return null; // Valid format
    }

    public static String validateDuplicateIc(String icNumber, String currentDoctorId, ListInterface<Doctor> doctorList) {
        for (int i = 0; i < doctorList.size(); i++) {
            Doctor existingDoctor = doctorList.getEntry(i);
            if (!existingDoctor.getDoctorId().equals(currentDoctorId)
                    && existingDoctor.getIcNumber().equalsIgnoreCase(icNumber)) {
                return "Another doctor with this IC number already exists.";
            }
        }
        return null; // No duplicates
    }

    // Email Validation
    public static String validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return "Email address cannot be empty.";
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            return "Please enter a valid email address.";
        }
        return null;
    }

    // Contact Number Validation
    public static String validatePhoneNumber(String contactNumber) {
        if (contactNumber == null || contactNumber.trim().isEmpty()) {
            return "Contact number cannot be empty.";
        }
        if (!PHONE_PATTERN.matcher(contactNumber).matches()) {
            return "Invalid phone number format. Expected format: 01X-XXXXXXXX.";
        }
        return null; // Valid
    }
    
    // Address Validation
    public static String validateAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            return "Address cannot be empty.";
        }
        return null; // Valid
    }

    // Birth Date Validation
    public static String validateBirthDate(LocalDate birthDate) {
        if (birthDate.isAfter(LocalDate.now())) {
            return "Birth date cannot be in the future.";
        }
        return null; // Valid
    }

    public static ListInterface<String> validateDoctor(Doctor doctor, ListInterface<Doctor> doctorList) {
        ListInterface<String> errors = new ArrayList<>();
        // This method can now be a collection of the smaller checks
        addError(errors, validateName(doctor.getName()));
        addError(errors, validateIcFormat(doctor.getIcNumber()));
        addError(errors, validateEmail(doctor.getEmail()));
        if (validateIcFormat(doctor.getIcNumber()) == null) { // Only check for duplicates if format is OK
            addError(errors, validateDuplicateIc(doctor.getIcNumber(), doctor.getDoctorId(), doctorList));
        }
        return errors;
    }

    private static void addError(ListInterface<String> errors, String error) {
        if (error != null) {
            errors.add(error);
        }
    }

    /**
     * Validates a Schedule object.
     *
     * @param schedule The schedule to validate.
     * @param doctorSchedules The list of existing schedules for the doctor.
     * @return A list of error messages. An empty list means the schedule is
     * valid.
     */
    public static ListInterface<String> validateSchedule(Schedule schedule, ListInterface<Schedule> doctorSchedules) {
        ListInterface<String> errors = new ArrayList<>();

        // Time Validation
        if (schedule.getStartTime().isAfter(schedule.getEndTime()) || schedule.getStartTime().equals(schedule.getEndTime())) {
            errors.add("End time must be after the start time.");
        }

        // Overlap Validation
        for (int i = 0; i < doctorSchedules.size(); i++) {
            Schedule existing = doctorSchedules.getEntry(i);
            if (schedule.getDay() == existing.getDay()) {
                // Check for time overlap: (StartA < EndB) and (EndA > StartB)
                if (schedule.getStartTime().isBefore(existing.getEndTime())
                        && schedule.getEndTime().isAfter(existing.getStartTime())) {
                    errors.add("The new schedule overlaps with an existing slot on " + schedule.getDay()
                            + " (" + existing.getStartTime() + " - " + existing.getEndTime() + ").");
                    break;
                }
            }
        }

        return errors;
    }
}
