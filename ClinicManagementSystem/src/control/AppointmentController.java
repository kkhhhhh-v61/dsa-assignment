package control;

import entity.Appointment;
import entity.Patient;
import entity.Doctor;
import adt.ArrayList;
import utility.Enum.Status;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import utility.Enum.Allergy;
import utility.Enum.Specialization;
import utility.Enum.BloodType;
import utility.Enum.Gender;
import static control.PatientFileHandler.findPatientById;
import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentController {
    private ArrayList<Appointment> appointments = new ArrayList<>();
    private ArrayList<Patient> patients;
    private ArrayList<Doctor> doctors;
    private static final String FILE_PATIENT = "patient.txt";
    public static final String FILE_DOCTOR = "doctor.txt";
    public static final String FILE_APPOINTMENT = "appointment.txt";
    
    public AppointmentController(ArrayList<Patient> patients, ArrayList<Doctor> doctors) {
        this.patients = patients;
        this.doctors = doctors;
    }

    // ============ CRUD LOGIC ============

    public Appointment addAppointment(Patient patient, Doctor doctor, LocalDate date, LocalTime time, String reason, Status status) {
        Appointment appt = new Appointment(patient.getPatientId(), doctor.getDoctorId(), date, time, reason, status);
        appointments.add(appt);

        // Link records
        patient.getAppointmentRecords().add(appt.getAppointmentId());
        doctor.getAppointmentRecords().add(appt.getAppointmentId());
        saveAppointment(appt);
        return appt;
    }

    public ArrayList<Appointment> getAllAppointments() {
        ArrayList<Appointment> appointments = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_APPOINTMENT))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 7) {
                    try {
                        Appointment appt = new Appointment(parts[1], parts[2], LocalDate.parse(parts[3]), LocalTime.parse(parts[4]), parts[5], Status.valueOf(parts[6]));
                        appt.setAppointmentId(parts[0]); // keep original ID
                        appointments.add(appt);
                    } catch (Exception e) {
                        System.out.println("Skipping invalid line: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return appointments;
    }

    public boolean updateAppointment(Appointment appt, LocalDate newDate, LocalTime newTime, String newReason, Status newStatus) {
        if (appt == null) return false;

        if (newDate != null) appt.setAppointmentDate(newDate);
        if (newTime != null) appt.setAppointmentTime(newTime);
        if (newReason != null && !newReason.isEmpty()) appt.setReason(newReason);
        if (newStatus != null) appt.setStatus(newStatus);

        return true;
    }

    public boolean deleteAppointment(Appointment appt) {
        if (appt == null) return false;

        // unlink from patient & doctor
        Patient patient = findPatientById(appt.getPatientId());
        Doctor doctor = findDoctorById(appt.getDoctorId());
        if (patient != null) patient.getAppointmentRecords().remove(appt.getAppointmentId());
        if (doctor != null) doctor.getAppointmentRecords().remove(appt.getAppointmentId());

        for (int i = 1; i <= appointments.size(); i++) {
            if (appointments.getEntry(i).equals(appt)) {
                appointments.remove(i);
                return true;
            }
        }
        return false;
    }
    
    public static void saveAppointment(Appointment appointment) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_APPOINTMENT, true))) {
            writer.write(appointment.toFileString()); // or toFileString()
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static Appointment findAppointmentById(String appointmentId){
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_APPOINTMENT))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 7 && parts[0].equalsIgnoreCase(appointmentId)) {
                    // Reconstruct Appointment object
                    Appointment appt = new Appointment(parts[1], parts[2], LocalDate.parse(parts[3]),LocalTime.parse(parts[4]), parts[5], Status.valueOf(parts[6]));
                    appt.setAppointmentId(parts[0]); // override auto ID
                    return appt;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error parsing appointment: " + e.getMessage());
        }
        return null; // not found
    }
    
    public static Patient findPatientById(String patientId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATIENT))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length < 11) continue; // skip malformed lines

                String filePatientId = parts[0];
                if (filePatientId.equals(patientId)) {
                    // reconstruct the patient
                    String icNumber = parts[1];
                    String name = parts[2];
                    Gender gender = Gender.valueOf(parts[3]);
                    LocalDate birthDate = LocalDate.parse(parts[4]);
                    int age = 0;
                    try {
                        age = Integer.parseInt(parts[5]);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number format: " + parts[5]);
                    }
                    String contactNumber = parts[6];
                    String email = parts[7];
                    String address = parts[8];
                    LocalDate registrationDate = LocalDate.parse(parts[9]);
                    BloodType bloodType = BloodType.valueOf(parts[10]);

                    Patient patient = new Patient(icNumber, name, gender, birthDate, contactNumber, email, address, registrationDate, bloodType, new adt.ArrayList<>(), new adt.ArrayList<>(), new adt.ArrayList<>(), new adt.ArrayList<>());
                    patient.setPatientId(filePatientId);
                    return patient;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // not found
    }
    
    public static Doctor findDoctorById(String doctorId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_DOCTOR))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                // Ensure line has enough parts (Doctor.toFileStringComplete has many fields)
                if (parts.length < 12) continue;

                String fileDoctorId = parts[0];
                if (fileDoctorId.equals(doctorId)) {
                    // reconstruct Doctor from parts
                    String icNumber = parts[1];
                    String name = parts[2];
                    Gender gender = Gender.valueOf(parts[3]);
                    LocalDate birthDate = LocalDate.parse(parts[4]);
                    int age = 0;
                    try {
                        age = Integer.parseInt(parts[5]);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number format: " + parts[5]);
                    }
                    String contactNumber = parts[6];
                    String email = parts[7];
                    String address = parts[8];
                    LocalDate registrationDate = LocalDate.parse(parts[9]);
                    Specialization specialization = Specialization.valueOf(parts[10]);
                    int yearsOfExperience = Integer.parseInt(parts[11]);

                    // dutySchedule, appointmentRecords, consultationRecords, treatmentRecords
                    adt.ArrayList<String> dutySchedule = new adt.ArrayList<>();
                    adt.ArrayList<String> appointmentRecords = new adt.ArrayList<>();
                    adt.ArrayList<String> consultationRecords = new adt.ArrayList<>();
                    adt.ArrayList<String> treatmentRecords = new adt.ArrayList<>();

                    // If you serialize these lists as comma-separated values, you need to split them:
                    if (!parts[11].isEmpty()) {
                        for (String s : parts[11].split(",")) dutySchedule.add(s.trim());
                    }
                    if (parts.length > 12 && !parts[12].isEmpty()) {
                        for (String s : parts[12].split(",")) appointmentRecords.add(s.trim());
                    }
                    if (parts.length > 13 && !parts[13].isEmpty()) {
                        for (String s : parts[13].split(",")) consultationRecords.add(s.trim());
                    }
                    if (parts.length > 14 && !parts[14].isEmpty()) {
                        for (String s : parts[14].split(",")) treatmentRecords.add(s.trim());
                    }

                    Doctor doctor = new Doctor(icNumber, name, gender, birthDate, contactNumber, email, address, registrationDate, specialization, dutySchedule, appointmentRecords, consultationRecords, treatmentRecords);
                    doctor.setDoctorId(fileDoctorId); // override auto-generated
                    doctor.setYearsOfExperience(yearsOfExperience);

                    return doctor;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // not found
    }
}