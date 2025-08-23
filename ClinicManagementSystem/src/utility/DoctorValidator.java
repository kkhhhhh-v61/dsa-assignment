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
import utility.Enum.Gender;

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
    public static String validateNewIcNumber(String icNumber, ListInterface<Doctor> doctorList) {
        if (icNumber == null || icNumber.trim().isEmpty()) {
            return "Error: IC Number cannot be empty.";
        }
        if (!isIcNumberFormat(icNumber)) {
            return "Error: Invalid IC format. Please use YYMMDD-XX-XXXX.";
        }
        if (getBirthDateFromIc(icNumber) == null) {
            return "Error: The birth date in the IC is not a valid date (e.g., month is > 12).";
        }
        if (!isIcUnique(icNumber, doctorList)) {
            return "Error: This IC Number already exists in the system.";
        }
        return null; // All checks passed
    }

    // === Individual Field Validators ===
    public static String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "Doctor's name cannot be empty.";
        }
        return null;
    }

    public static String validateEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            return "Invalid email format (e.g., user@example.com).";
        }
        return null;
    }

    public static String validatePhoneNumber(String phone) {
        if (phone == null || !PHONE_PATTERN.matcher(phone).matches()) {
            return "Invalid phone number format (e.g., 012-3456789).";
        }
        return null;
    }

    public static String validateAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            return "Address cannot be empty.";
        }
        return null;
    }

    // === IC Helper Methods ===
    public static boolean isIcNumberFormat(String icNumber) {
        return icNumber != null && IC_PATTERN.matcher(icNumber).matches();
    }

    public static boolean isIcUnique(String icNumber, ListInterface<Doctor> doctors) {
        for (int i = 0; i < doctors.size(); i++) {
            if (doctors.get(i).getIcNumber().equals(icNumber)) {
                return false;
            }
        }
        return true;
    }
    
    public static String validateDuplicateIc(String icNumber, String currentDoctorId, ListInterface<Doctor> doctorList) {
        for (int i = 1; i <= doctorList.size(); i++) {
            Doctor existingDoctor = doctorList.get(i);
            if (!existingDoctor.getDoctorId().equals(currentDoctorId)
                    && existingDoctor.getIcNumber().equalsIgnoreCase(icNumber)) {
                return "Another doctor with this IC number already exists.";
            }
        }
        return null;
    }

    public static LocalDate getBirthDateFromIc(String icNumber) {
        if (!isIcNumberFormat(icNumber)) {
            return null;
        }
        String datePart = icNumber.substring(0, 6);
        int year = Integer.parseInt(datePart.substring(0, 2));
        int month = Integer.parseInt(datePart.substring(2, 4));
        int day = Integer.parseInt(datePart.substring(4, 6));
        year += (year > LocalDate.now().getYear() % 100) ? 1900 : 2000;
        try {
            return LocalDate.of(year, month, day);
        } catch (Exception e) {
            return null;
        }
    }

    public static Gender getGenderFromIc(String icNumber) {
        if (!isIcNumberFormat(icNumber)) {
            return Gender.Unknown;
        }
        int lastDigit = Character.getNumericValue(icNumber.charAt(icNumber.length() - 1));
        return (lastDigit % 2 == 0) ? Gender.Female : Gender.Male;
    }

    // === Comprehensive Doctor Validator (as requested) ===
    public static ListInterface<String> validateDoctor(Doctor doctor, ListInterface<Doctor> doctorList) {
        ListInterface<String> errors = new ArrayList<>();
        addError(errors, validateName(doctor.getName()));
        addError(errors, validateEmail(doctor.getEmail()));
        addError(errors, validatePhoneNumber(doctor.getContactNumber()));
        addError(errors, validateAddress(doctor.getAddress()));
        // Use the comprehensive IC validator, but adapt it for updates vs. new entries
        addError(errors, validateDuplicateIc(doctor.getIcNumber(), doctor.getDoctorId(), doctorList));
        
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
        for (int i = 1; i <= doctorSchedules.size(); i++) {
            Schedule existing = doctorSchedules.get(i);
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
