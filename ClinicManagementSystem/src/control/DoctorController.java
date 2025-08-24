package control;

import adt.ArrayList;
import adt.HashMap;
import adt.ListInterface;
import adt.MapInterface;
import dao.DoctorDAO;
import entity.Doctor;
import entity.Schedule;
import java.time.LocalDate;
import java.time.LocalTime;
import utility.Enum.DayOfWeek;
import utility.Enum.Gender;
import utility.Enum.Specialization;
import utility.DoctorValidator;

public class DoctorController {

    private static final String DOCTORS_FILE = "doctors.txt";
    private static final String SCHEDULES_FILE = "schedules.txt";

    private ListInterface<Doctor> doctorList = new ArrayList<>();
    private ListInterface<Schedule> scheduleList = new ArrayList<>();

    public DoctorController() {
        loadData();
    }

    public void loadData() {
        // Parser for Doctor - handles the file format from doctors.txt
        DoctorDAO.loadFromFile(DOCTORS_FILE, doctorList, data -> {
            if (data.length >= 10) {
                // The format in doctors.txt is:
                // doctorId|ic|name|gender|birthDate|age|contact|email|address|regDate|specialization|yearsOfExperience|dutySchedule|appointmentRecords|consultationRecords|treatmentRecords
                try {
                    String doctorId = data[0];
                    String ic = data[1];
                    String name = data[2];
                    Gender gender = Gender.valueOf(data[3]);
                    LocalDate birthDate = LocalDate.parse(data[4]);
                    // Skip age (data[5]) as it can be calculated from birthDate
                    String contact = data[6];
                    String email = data[7];
                    String address = data[8];
                    LocalDate regDate = LocalDate.parse(data[9]);
                    Specialization spec = Specialization.valueOf(data[10]);
                    int yearsOfExp = data.length > 11 ? Integer.parseInt(data[11]) : 0;
                    
                    // Create the doctor with empty lists for records (we'll load the actual records separately)
                    Doctor doctor = new Doctor(ic, name, gender, birthDate, contact, email, address, regDate, spec, 
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
                    
                    // Set the doctor ID and years of experience
                    doctor.setDoctorId(doctorId);
                    doctor.setYearsOfExperience(yearsOfExp);
                    
                    return doctor;
                } catch (Exception e) {
                    System.err.println("Error parsing doctor data: " + String.join("|", data));
                    e.printStackTrace();
                    return null;
                }
            }
            return null; // Return null if the data format is incorrect
        });

        // Parser for Schedule
        DoctorDAO.loadFromFile(SCHEDULES_FILE, scheduleList, data -> {
            // Format: scheduleId|doctorId|day|startTime|endTime|isAvailable
            if (data.length >= 6) {
                String doctorId = data[1];
                DayOfWeek day = DayOfWeek.valueOf(data[2]);
                LocalTime startTime = LocalTime.parse(data[3]);
                LocalTime endTime = LocalTime.parse(data[4]);
                boolean isAvailable = Boolean.parseBoolean(data[5]);
                return new Schedule(doctorId, day, startTime, endTime, isAvailable);
            }
            return null;
        });
    }

    private void saveData() {
        DoctorDAO.saveToFile(DOCTORS_FILE, doctorList);
        DoctorDAO.saveToFile(SCHEDULES_FILE, scheduleList);
    }

    // ==================================================================
    // Doctor CRUD Operations (with saveData calls)
    // ==================================================================
    public ListInterface<String> addDoctor(Doctor newDoctor) {
        ListInterface<String> errors = DoctorValidator.validateDoctor(newDoctor, doctorList);
        if (errors.isEmpty()) {
            doctorList.add(newDoctor);
            saveData();
        }
        return errors;
    }

    public ListInterface<Doctor> getAllDoctors() {
        return doctorList;
    }

    public Doctor getDoctorById(String doctorId) {
        for (int i = 1; i <= doctorList.size(); i++) {
            if (doctorList.get(i).getDoctorId().equals(doctorId)) {
                return doctorList.get(i);
            }
        }
        return null;
    }

    public ListInterface<String> updateDoctor(Doctor updatedDoctor) {
        ListInterface<String> errors = DoctorValidator.validateDoctor(updatedDoctor, doctorList);
        if (errors.isEmpty()) {
            for (int i = 1; i <= doctorList.size(); i++) {
                if (doctorList.get(i).getDoctorId().equals(updatedDoctor.getDoctorId())) {
                    doctorList.replace(i, updatedDoctor);
                    saveData();
                    break;
                }
            }
        }
        return errors;
    }

    public void deleteDoctor(String doctorId) {
        for (int i = 1; i <= doctorList.size(); i++) {
            if (doctorList.get(i).getDoctorId().equals(doctorId)) {
                doctorList.remove(i);
                deleteSchedulesForDoctor(doctorId);
                saveData();
                break;
            }
        }
    }

    // ==================================================================
    // Schedule CRUD and Availability Tracking (with saveData calls)
    // ==================================================================
    public ListInterface<String> addSchedule(Schedule newSchedule) {
        ListInterface<Schedule> existingSchedules = getSchedulesForDoctor(newSchedule.getDoctorId());
        ListInterface<String> errors = DoctorValidator.validateSchedule(newSchedule, existingSchedules);
        
        if (errors.isEmpty()) {
            scheduleList.add(newSchedule);
            saveData();
        }
        return errors;
    }

    public ListInterface<Schedule> getSchedulesForDoctor(String doctorId) {
        ListInterface<Schedule> doctorSchedules = new ArrayList<>();
        for (int i = 1; i <= scheduleList.size(); i++) {
            Schedule currentSchedule = scheduleList.get(i);
            if (currentSchedule.getDoctorId().equals(doctorId)) {
                doctorSchedules.add(currentSchedule);
            }
        }
        return doctorSchedules;
    }
    
    public void updateSchedule(Schedule updatedSchedule) {
        for (int i = 1; i <= scheduleList.size(); i++) {
            if (scheduleList.get(i).getScheduleId().equals(updatedSchedule.getScheduleId())) {
                scheduleList.replace(i, updatedSchedule);
                saveData();
                break;
            }
        }
    }

    public void deleteSchedule(String scheduleId) {
        for (int i = 1; i <= scheduleList.size(); i++) {
            if (scheduleList.get(i).getScheduleId().equals(scheduleId)) {
                scheduleList.remove(i);
                saveData();
                break;
            }
        }
    }

    private void deleteSchedulesForDoctor(String doctorId) {
        boolean changed = false;
        for (int i = scheduleList.size() - 1; i >= 0; i--) {
            if (scheduleList.get(i).getDoctorId().equals(doctorId)) {
                scheduleList.remove(i);
                changed = true;
            }
        }
        if (changed) {
            saveData();
        }
    }

    // ==================================================================
    // Reporting (No changes needed here)
    // ==================================================================
    public void generateDoctorSpecializationReport() {
        System.out.println("\n--- Doctor Specialization Report ---");
        MapInterface<Specialization, Integer> specCount = new HashMap<>();

        for (Specialization spec : Specialization.values()) {
            if (spec != Specialization.Unknown) {
                specCount.put(spec, 0);
            }
        }

        for (int i = 1; i <= doctorList.size(); i++) {
            Doctor doctor = doctorList.get(i);
            Specialization spec = doctor.getSpecialization();
            if (spec != Specialization.Unknown) {
                Integer count = specCount.get(spec);
                specCount.put(spec, count + 1);
            }
        }

        System.out.println("Specialization         | Number of Doctors");
        System.out.println("-------------------------------------------");
        for (Specialization spec : Specialization.values()) {
             if (spec != Specialization.Unknown) {
                System.out.printf("%-22s | %d\n", spec, specCount.get(spec));
             }
        }
        System.out.println("-------------------------------------------");
    }

    public void generateDoctorAvailabilityReport(DayOfWeek day) {
        System.out.println("\n--- Doctor Availability Report for " + day + " ---");
        System.out.println("----------------------------------------------------------------------");
        System.out.printf("%-10s | %-20s | %-15s | %s\n", "Doctor ID", "Doctor Name", "Specialization", "Available Time");
        System.out.println("----------------------------------------------------------------------");

        boolean found = false;
        for (int i = 1; i <= scheduleList.size(); i++) {
            Schedule schedule = scheduleList.get(i);
            if (schedule.getDay() == day && schedule.getIsAvailable()) {
                Doctor doctor = getDoctorById(schedule.getDoctorId());
                if (doctor != null) {
                    found = true;
                    System.out.printf("%-10s | %-20s | %-15s | %s - %s\n",
                            doctor.getDoctorId(),
                            doctor.getName(),
                            doctor.getSpecialization(),
                            schedule.getStartTime(),
                            schedule.getEndTime());
                }
            }
        }

        if (!found) {
            System.out.println("No doctors are available on " + day + ".");
        }
        System.out.println("----------------------------------------------------------------------");
    }
}