package dao;

import entity.Consultation;
import adt.ArrayList;
import entity.Appointment;
import java.io.*;

public class ConsultationDAO {
    private static final String FILE_NAME = "data/consultation.dat";
    private ArrayList<Consultation> consultations;

    public ConsultationDAO() {
        consultations = loadFromFile(); // ✅ load at startup
    }

    // ====== CRUD on in-memory consultations ======

    public ArrayList<Consultation> getAllConsultations() {
        return consultations;
    }

    public Consultation getConsultationById(String id) {
        for (int i = 1; i <= consultations.size(); i++) {
            Consultation c = consultations.get(i);
            if (c.getConsultationId().equalsIgnoreCase(id)) {
                return c;
            }
        }
        return null;
    }

    public void addConsultation(Consultation consultation) {
        consultations.add(consultation);  // ✅ only memory, no file yet
    }

    public void updateConsultation(Consultation consultation) {
        for (int i = 1; i <= consultations.size(); i++) {
            if (consultations.get(i).getConsultationId().equalsIgnoreCase(consultation.getConsultationId())) {
                consultations.replace(i, consultation);
                break;
            }
        }
    }

    public void deleteConsultation(String id) {
        id = id.trim();
        for (int i = 1; i <= consultations.size(); i++) {
            if (consultations.get(i).getConsultationId().equalsIgnoreCase(id)) {
                consultations.remove(i);
                saveAll();
                break;
            }
        }
    }

    // ====== File Operations ======

    @SuppressWarnings("unchecked")
    private ArrayList<Consultation> loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (ArrayList<Consultation>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // ✅ Explicit save method (call before exiting program)
    public void saveAll() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(consultations);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ====== Helpers ======

    public int getLastId() {
        int maxId = 0;
        for (int i = 1; i <= consultations.size(); i++) {
            String id = consultations.get(i).getConsultationId();
            try {
                int num = Integer.parseInt(id.substring(1));
                if (num > maxId) maxId = num;
            } catch (NumberFormatException e) {
                // ignore malformed IDs
            }
        }
        return maxId;
    }

    public void reload() {
        consultations = loadFromFile();
    }
    
    public ArrayList<Consultation> getConsultationsByUserId(String userId) {
        ArrayList<Consultation> result = new ArrayList<>();
        for (int i = 1; i <= consultations.size(); i++) {
            Consultation c = consultations.get(i);
            if (c.getPatientId().equalsIgnoreCase(userId) ||
                c.getDoctorId().equalsIgnoreCase(userId)) {
                result.add(c);
            }
        }
        return result;
    }

    public boolean existsForAppointment(Appointment appt) {
        for (int i = 1; i <= consultations.size(); i++) {
            Consultation c = consultations.get(i);
            if (c.getPatientId().equalsIgnoreCase(appt.getPatientId()) &&
                c.getDoctorId().equalsIgnoreCase(appt.getDoctorId()) &&
                c.getConsultationDate().isEqual(appt.getAppointmentDate()) &&
                c.getConsuLocalTime().equals(appt.getAppointmentTime())) {
                return true;
            }
        }
        return false;
    }
}
