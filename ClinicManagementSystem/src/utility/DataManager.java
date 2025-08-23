package utility;

import adt.ListInterface;
import adt.ArrayList;
import entity.Medicine;
import entity.Consultation;
import entity.Doctor;
import entity.Patient;
import utility.Enum.MedicineCategory;
import utility.Enum.Status;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class DataManager {

    private static final String MEDICINES_FILE = "medicines.txt";
    private static final String CONSULTATIONS_FILE = "consultations.txt";

    public static ListInterface<Medicine> loadMedicines() {
        ListInterface<Medicine> medicineList = new ArrayList<>();
        File file = new File(MEDICINES_FILE);
        if (!file.exists()) {
            return medicineList;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split("\\|");
                if (data.length == 7) {
                    Medicine medicine = new Medicine(
                            data[1], // name
                            data[2], // description
                            MedicineCategory.valueOf(data[3]), // category
                            Double.parseDouble(data[4]), // price
                            Integer.parseInt(data[5]), // stockQuantity
                            Integer.parseInt(data[6])  // reorderLevel
                    );
                    medicine.setMedicineId(data[0]); // Set the original medicineId
                    medicineList.add(medicine);
                }
            }
        } catch (Exception e) {
            MessageUI.displayErrorMessage("Error loading medicines from file: " + e.getMessage());
        }
        return medicineList;
    }

    public static void saveMedicines(ListInterface<Medicine> medicineList) {
        try (FileWriter writer = new FileWriter(MEDICINES_FILE, false)) {
            for (int i = 0; i < medicineList.size(); i++) {
                writer.write(medicineList.getEntry(i + 1).toFileString() + "\n");
            }
        } catch (IOException e) {
            MessageUI.displayErrorMessage("Error saving medicines to file: " + e.getMessage());
        }
    }

    public static ListInterface<Consultation> loadConsultations() {
        ListInterface<Consultation> consultationList = new ArrayList<>();
        File file = new File(CONSULTATIONS_FILE);
        if (!file.exists()) {
            return consultationList;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split("\\|");
                if (data.length == 9) {
                    Patient patient = new Patient();
                    patient.setPatientId(data[1]); // Assuming patient ID is at index 1
                    Doctor doctor = new Doctor();
                    doctor.setDoctorId(data[0]); // Assuming doctor ID is at index 0
                    
                    ArrayList<String> symptoms = new ArrayList<>();
                    String[] symptomArray = data[4].replace("[", "").replace("]", "").split(",");
                    for(String s : symptomArray){
                        if(!s.trim().isEmpty()) symptoms.add(s.trim());
                    }
                    
                    ArrayList<String> diagnosis = new ArrayList<>();
                    String[] diagnosisArray = data[5].replace("[", "").replace("]", "").split(",");
                     for(String s : diagnosisArray){
                        if(!s.trim().isEmpty()) diagnosis.add(s.trim());
                    }

                    Consultation consultation = new Consultation(
                            doctor,
                            patient,
                            LocalDate.parse(data[2]), // Assuming date is at index 2
                            LocalTime.parse(data[3]), // Assuming time is at index 3
                            symptoms,
                            diagnosis,
                            data[6], // Assuming remarks is at index 6
                            Status.valueOf(data[7]) // Assuming status is at index 7
                    );
                    consultation.setConsultationId(data[8]); // Assuming consultation ID is at index 8
                    consultationList.add(consultation);
                }
            }
        } catch (Exception e) {
            MessageUI.displayErrorMessage("Error loading consultations from file: " + e.getMessage());
        }
        return consultationList;
    }

    public static void saveConsultations(ListInterface<Consultation> consultationList) {
        try (FileWriter writer = new FileWriter(CONSULTATIONS_FILE, false)) {
            for (int i = 0; i < consultationList.size(); i++) {
                writer.write(consultationList.getEntry(i).toFileString() + "\n");
            }
        } catch (IOException e) {
            MessageUI.displayErrorMessage("Error saving consultations to file: " + e.getMessage());
        }
    }
}
