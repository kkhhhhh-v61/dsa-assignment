package utility;

import adt.ArrayList;
import adt.ListInterface;
import entity.Consultation;
import entity.Doctor;
import entity.Patient;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

public class DataManager {

    private static final String FILE_PATH = "consultations.txt";

    public static void saveConsultations(ListInterface<Consultation> consultationList) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (int i = 1; i <= consultationList.size(); i++) {
                writer.println(consultationList.getEntry(i).toFileString());
            }
        } catch (IOException e) {
            System.err.println("Error saving consultations to file: " + e.getMessage());
        }
    }

    public static ListInterface<Consultation> loadConsultations() {
        ListInterface<Consultation> consultationList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");
                
                // Recreate Patient and Doctor objects (simplified)
                Patient patient = new Patient();
                patient.setPatientId(data[1]);
                Doctor doctor = new Doctor();
                doctor.setDoctorId(data[2]);

                // Recreate symptoms and diagnosis lists
                ArrayList<String> symptoms = new ArrayList<>();
                String[] sympArr = data[5].replace("[", "").replace("]", "").split(", ");
                for(String s : sympArr) {
                    if(!s.isEmpty()) symptoms.add(s);
                }

                ArrayList<String> diagnosis = new ArrayList<>();
                String[] diagArr = data[6].replace("[", "").replace("]", "").split(", ");
                 for(String d : diagArr) {
                    if(!d.isEmpty()) diagnosis.add(d);
                }

                Consultation consultation = new Consultation(
                    doctor,
                    patient,
                    LocalDate.parse(data[3]),
                    LocalTime.parse(data[4]),
                    symptoms,
                    diagnosis,
                    data[7],
                    Enum.Status.valueOf(data[8])
                );
                consultation.setConsultationId(data[0]); // Set the original ID
                consultationList.add(consultation);
            }
        } catch (IOException e) {
            // File might not exist on first run, which is fine.
            System.out.println("No existing consultation data found. Starting fresh.");
        }
        return consultationList;
    }
}