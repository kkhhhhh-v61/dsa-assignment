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
import java.time.LocalTime;
import java.util.regex.Pattern;
import utility.Enum.Gender;

/**
 *
 * @author Brian Ooi Shu Yi
 */
public class DoctorValidator {

    // Define the required rest period between schedules
    private static final int REQUIRED_REST_HOURS = 1;
    
    // Email Validation
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,6}$");

    // Ic Validation
    private static final Pattern IC_PATTERN = Pattern.compile("^\\d{6}-\\d{2}-\\d{4}$");

    // Phone Number Validation
    private static final Pattern PHONE_PATTERN = Pattern.compile("^01[0-9]-\\d{7,8}$");

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
        for (int i = 1; i <= doctors.size(); i++) {
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


    public static ListInterface<String> validateSchedule(Schedule newSchedule, ListInterface<Schedule> doctorSchedules) {
        ListInterface<String> errors = new ArrayList<>();

        // 1. Basic Time Validation
        if (newSchedule.getStartTime().isAfter(newSchedule.getEndTime()) || newSchedule.getStartTime().equals(newSchedule.getEndTime())) {
            errors.add("End time must be after the start time.");
            return errors; // No need to check further if times are invalid
        }

        // 2. Overlap and Rest Period Validation
        for (int i = 1; i < doctorSchedules.size(); i++) {
            Schedule existingSchedule = doctorSchedules.get(i);

            // Only compare with schedules on the same day
            if (newSchedule.getDay() == existingSchedule.getDay()) {
                
                // Define the "no-go zone" for the new schedule.
                // This is the existing schedule's time plus the required rest period on both ends.
                LocalTime noGoZoneStart = existingSchedule.getStartTime().minusHours(REQUIRED_REST_HOURS);
                LocalTime noGoZoneEnd = existingSchedule.getEndTime().plusHours(REQUIRED_REST_HOURS);

                // Check for overlap with the "no-go zone"
                // A conflict exists if: (newStart < noGoEnd) AND (newEnd > noGoStart)
                if (newSchedule.getStartTime().isBefore(noGoZoneEnd) && newSchedule.getEndTime().isAfter(noGoZoneStart)) {
                    errors.add("Schedule conflicts with an existing slot on " + newSchedule.getDay() 
                            + " (" + existingSchedule.getStartTime() + " - " + existingSchedule.getEndTime() + "). "
                            + "There must be at least a " + REQUIRED_REST_HOURS + "-hour gap between schedules.");
                    break; // One conflict is enough, no need to check further
                }
            }
        }
        return errors;
    }
}
