package dao;

// Author - Jason Tye

import entity.Consultation;
import adt.ArrayList;
import java.io.*;

public class ConsultationDAO {
    private static final String FILE_NAME = "data/consultation.dat";
    private ArrayList<Consultation> consultations;

    public ConsultationDAO() {
        consultations = new ArrayList<>();
        loadFromFile();
    }

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
        consultations.add(consultation);
        saveToFile();
    }

    public void updateConsultation(Consultation consultation) {
        for (int i = 1; i <= consultations.size(); i++) {
            if (consultations.get(i).getConsultationId().equalsIgnoreCase(consultation.getConsultationId())) {
                consultations.add(i, consultation); // ✅ use set instead of add
                break;
            }
        }
        saveToFile();
    }

    public void deleteConsultation(String id) {
        for (int i = 1; i <= consultations.size(); i++) {
            if (consultations.get(i).getConsultationId().equalsIgnoreCase(id)) {
                consultations.remove(i);
                break;
            }
        }
        saveToFile();
    }
 
    @SuppressWarnings("unchecked")
    private ArrayList<Consultation> loadFromFile() {
        File file = new File("consultations.dat");
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>(); // return empty if no file
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (ArrayList<Consultation>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(consultations);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public int getLastId() {
    int maxId = 0;
    for (int i = 1; i <= consultations.size(); i++) {
        String id = consultations.get(i).getConsultationId(); // e.g., "C0007"
        try {
            int num = Integer.parseInt(id.substring(1)); // remove "C" and parse
            if (num > maxId) {
                maxId = num;
            }
        } catch (NumberFormatException e) {
            // ignore malformed IDs
        }
    }
    return maxId;
}
    
    public ArrayList<Consultation> getConsultationsByUserId(String userId) {
    ArrayList<Consultation> result = new ArrayList<>();
    for (int i = 1; i <= consultations.size(); i++) {
        Consultation c = consultations.get(i);
        if (c.getPatientId().equalsIgnoreCase(userId) || c.getDoctorId().equalsIgnoreCase(userId)) {
            result.add(c);
        }
    }
    return result;
}

}

