package control;

import entity.Consultation;
import entity.Patient;
import entity.Doctor;
import adt.ArrayList;
import utility.Enum.Status;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class ConsultationController {
    private ArrayList<Consultation> consultations = new ArrayList<>();
    private ArrayList<Patient> patients;
    private ArrayList<Doctor> doctors;

    public static final String FILE_CONSULTATION = "consultation.txt";

    public ConsultationController(ArrayList<Patient> patients, ArrayList<Doctor> doctors) {
        this.patients = patients;
        this.doctors = doctors;
    }

    // ===== CRUD =====

    public Consultation addConsultation(Patient patient, Doctor doctor, LocalDate date, LocalTime time, ArrayList<String> symptoms, ArrayList<String> diagnosis, String notes, Status status) {
        Consultation consult = new Consultation(doctor.getDoctorId(), patient.getPatientId(), date, time, symptoms, diagnosis, notes, status);
        consultations.add(consult);

        // Link records
        patient.getConsultationRecords().add(consult.getConsultationId());
        doctor.getConsultationRecords().add(consult.getConsultationId());

        saveConsultation(consult);
        return consult;
    }

    public ArrayList<Consultation> getAllConsultations() {
        ArrayList<Consultation> list = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_CONSULTATION))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 9) {
                    try {
                        // Split lists back
                        ArrayList<String> symptoms = new ArrayList<>();
                        if (!parts[5].isEmpty()) {
                            for (String s : parts[5].replaceAll("[\\[\\]]", "").split(",")) {
                                symptoms.add(s.trim());
                            }
                        }
                        ArrayList<String> diagnosis = new ArrayList<>();
                        if (!parts[6].isEmpty()) {
                            for (String d : parts[6].replaceAll("[\\[\\]]", "").split(",")) {
                                diagnosis.add(d.trim());
                            }
                        }

                        Consultation consult = new Consultation(parts[2], parts[1], LocalDate.parse(parts[3]), LocalTime.parse(parts[4]), symptoms, diagnosis, parts[7], Status.valueOf(parts[8])); consult.setConsultationId(parts[0]);
                        list.add(consult);
                    } catch (Exception e) {
                        System.out.println("Skipping invalid line: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return list;
    }

    public boolean updateConsultation(Consultation consult, LocalDate newDate, LocalTime newTime, ArrayList<String> newSymptoms, ArrayList<String> newDiagnosis, String newNotes, Status newStatus) {
        if (consult == null) return false;
        if (newDate != null) consult.setConsultationDate(newDate);
        if (newTime != null) consult.setConsultationTime(newTime);
        if (newSymptoms != null && !newSymptoms.isEmpty()) consult.setSymptoms(newSymptoms);
        if (newDiagnosis != null && !newDiagnosis.isEmpty()) consult.setDiagnosis(newDiagnosis);
        if (newNotes != null && !newNotes.isEmpty()) consult.setNotes(newNotes);
        if (newStatus != null) consult.setStatus(newStatus);

        return true;
    }

    public boolean deleteConsultation(Consultation consult) {
        if (consult == null) return false;

        // unlink from patient & doctor
        Patient patient = AppointmentController.findPatientById(consult.getPatientId());
        Doctor doctor = AppointmentController.findDoctorById(consult.getDoctorId());
        if (patient != null) patient.getConsultationRecords().remove(consult.getConsultationId());
        if (doctor != null) doctor.getConsultationRecords().remove(consult.getConsultationId());

        for (int i = 1; i <= consultations.size(); i++) {
            if (consultations.getEntry(i).equals(consult)) {
                consultations.remove(i);
                return true;
            }
        }
        return false;
    }

    public static void saveConsultation(Consultation consultation) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_CONSULTATION, true))) {
            writer.write(consultation.toFileString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Consultation findConsultationById(String consultationId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_CONSULTATION))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 9 && parts[0].equalsIgnoreCase(consultationId)) {

                    ArrayList<String> symptoms = new ArrayList<>();
                    if (!parts[5].isEmpty()) {
                        for (String s : parts[5].replaceAll("[\\[\\]]", "").split(",")) {
                            symptoms.add(s.trim());
                        }
                    }
                    ArrayList<String> diagnosis = new ArrayList<>();
                    if (!parts[6].isEmpty()) {
                        for (String d : parts[6].replaceAll("[\\[\\]]", "").split(",")) {
                            diagnosis.add(d.trim());
                        }
                    }

                    Consultation consult = new Consultation(parts[2], parts[1], LocalDate.parse(parts[3]), LocalTime.parse(parts[4]), symptoms, diagnosis, parts[7], Status.valueOf(parts[8]));
                    consult.setConsultationId(parts[0]);
                    return consult;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error parsing consultation: " + e.getMessage());
        }
        return null;
    }
}
