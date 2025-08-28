package dao;

import entity.Treatment;
import adt.ArrayList;
import java.io.*;

public class TreatmentDAO {
    private static final String FILE_NAME = "data/treatment.dat";
    private ArrayList<Treatment> treatments;

    public TreatmentDAO() {
        treatments = loadFromFile();
    }

    // ===== CRUD Methods =====
    public ArrayList<Treatment> getAllTreatments() {
        return treatments;
    }

    public ArrayList<Treatment> getTreatmentsByUserId(String userId) {
        ArrayList<Treatment> result = new ArrayList<>();
        for (int i = 1; i <= treatments.size(); i++) {
            Treatment t = treatments.get(i);
            if (t.getPatientId().equalsIgnoreCase(userId) ||
                t.getDoctorId().equalsIgnoreCase(userId)) {
                result.add(t);
            }
        }
        return result;
    }
    public Treatment getTreatmentById(String id) {
        for (int i = 1; i <= treatments.size(); i++) {
            if (treatments.get(i).getTreatmentId().equalsIgnoreCase(id)) {
                return treatments.get(i);
            }
        }
        return null;
    }

    public void addTreatment(Treatment treatment) {
        treatments.add(treatment);
    }

    public void updateTreatment(Treatment treatment) {
        for (int i = 1; i <= treatments.size(); i++) {
            if (treatments.get(i).getTreatmentId().equalsIgnoreCase(treatment.getTreatmentId())) {
                treatments.replace(i, treatment);
                break;
            }
        }
    }

    public void deleteTreatment(String id) {
        for (int i = 1; i <= treatments.size(); i++) {
            if (treatments.get(i).getTreatmentId().equalsIgnoreCase(id)) {
                treatments.remove(i);
                break;
            }
        }
    }

    // ===== File Operations =====
    @SuppressWarnings("unchecked")
    private ArrayList<Treatment> loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (ArrayList<Treatment>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void saveAll() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(treatments);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== Helper =====
    public int getLastId() {
        int maxId = 0;
        for (int i = 1; i <= treatments.size(); i++) {
            String id = treatments.get(i).getTreatmentId();
            try {
                int num = Integer.parseInt(id.substring(1));
                if (num > maxId) maxId = num;
            } catch (NumberFormatException e) {
                // ignore malformed ID
            }
        }
        return maxId;
    }

    public void reload() {
        treatments = loadFromFile();
    }
}
