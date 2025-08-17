package client;

import adt.ArrayList;
import entity.Person;
import entity.Patient;
import entity.Doctor;
import entity.Schedule;
import entity.Medicine;
import entity.Appointment;
import entity.Consultation;
import entity.Treatment;
import entity.PrescribedMedicine;
import utility.Enum.Allergy;
import utility.Enum.BloodType;
import utility.Enum.DayOfWeek;
import utility.Enum.Gender;
import utility.Enum.MedicineCategory;
import utility.Enum.Specialization;
import utility.Enum.Status;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author khtan
 */
public class TestDriver {
  public static void main(String[] args) {
    /* Initialization */
    ArrayList<Allergy> allergies = new ArrayList<>();
    allergies.add(Allergy.Dairy);
    allergies.add(Allergy.Egg);
    allergies.add(Allergy.Peanut);

    ArrayList<String> appointmentRecords = new ArrayList<>();
    appointmentRecords.add("A0001");
    appointmentRecords.add("A0002");
    appointmentRecords.add("A0003");

    ArrayList<String> consultationRecords = new ArrayList<>();
    consultationRecords.add("C0001");
    consultationRecords.add("C0002");
    consultationRecords.add("C0003");

    ArrayList<String> treatmentRecords = new ArrayList<>();
    treatmentRecords.add("T0001");
    treatmentRecords.add("T0002");
    treatmentRecords.add("T0003");

    ArrayList<String> symptoms = new ArrayList<>();
    symptoms.add("Fever");
    symptoms.add("Cough");
    symptoms.add("Headache");
    
    ArrayList<String> diagnosis = new ArrayList<>();
    diagnosis.add("Flu");
    diagnosis.add("Cold");
    diagnosis.add("Malaria");
    
    ArrayList<PrescribedMedicine> medicines = new ArrayList<>();
    medicines.add(new PrescribedMedicine("M0001", "10mg", 3, 7, "Take with food"));
    medicines.add(new PrescribedMedicine("M0002", "20mg", 2, 5, "Take with water"));
    medicines.add(new PrescribedMedicine("M0003", "30mg", 1, 3, "Take with food"));


    
    /* Person */
    Person person = new Person("123456789", "John", Gender.Male, LocalDate.of(2000, 12, 31), "123456789", "123456789", "123456789", LocalDate.of(2023, 6, 1));
    System.out.println(person.toString() + "\n");
    System.out.println(person.toFileString());
    System.out.println();



    /* Patient */
    Patient patient = new Patient("123456789", "John", Gender.Male, LocalDate.of(2005, 6, 1), "123456789", "123456789", "123456789", LocalDate.of(2023, 6, 1), BloodType.A, allergies, appointmentRecords, consultationRecords, treatmentRecords);
    System.out.println(patient.toString() + "\n");
    System.out.println(patient.toFileString() + "\n");
    System.out.println(patient.toStringComplete() + "\n");
    System.out.println(patient.toFileStringComplete() + "\n");



    /* Doctor */
    Doctor doctor = new Doctor("123456789", "Mia", Gender.Female, LocalDate.of(1997, 12, 31), "123456789", "123456789", "123456789", LocalDate.of(2015, 12, 31), Specialization.Cardiology, new ArrayList<>(), appointmentRecords, consultationRecords, treatmentRecords);
    System.out.println(doctor.toString() + "\n");
    System.out.println(doctor.toFileString() + "\n");
    System.out.println(doctor.toStringComplete() + "\n");
    System.out.println(doctor.toFileStringComplete() + "\n");

    

    /* Schedule */
    Schedule schedule = new Schedule("123456789", DayOfWeek.Monday, LocalTime.of(12, 0), LocalTime.of(13, 0), true);
    System.out.println(schedule.toString() + "\n");
    System.out.println(schedule.toFileString() + "\n");



    /* Medicine */
    Medicine medicine = new Medicine("Aspirin", "Description", MedicineCategory.Tablet, 10.0, 100, 10);
    System.out.println(medicine.toString() + "\n");
    System.out.println(medicine.toFileString() + "\n");



    /* PrescribedMedicine */
    PrescribedMedicine prescribedMedicine = new PrescribedMedicine("M0001", "10mg", 3, 7, "Take with food");
    System.out.println(prescribedMedicine.toString() + "\n");
    System.out.println(prescribedMedicine.toFileString() + "\n");



    /* Appointment */
    Appointment appointment = new Appointment("D0001", "P0001", LocalDate.of(2023, 6, 1), LocalTime.of(12, 0), "Notes", Status.Completed);
    System.out.println(appointment.toString() + "\n");
    System.out.println(appointment.toFileString() + "\n");

    /* Consultation */
    Consultation consultation = new Consultation("D0001", "P0001", LocalDate.of(2023, 6, 1), LocalTime.of(12, 0), symptoms, diagnosis, "Notes", Status.Completed);
    System.out.println(consultation.toString() + "\n");
    System.out.println(consultation.toFileString() + "\n");

    /* Treatment */
    Treatment treatment = new Treatment("C0001", "D0001", "P0001", medicines, "Notes", Status.Completed);
    System.out.println(treatment.toString() + "\n");
    System.out.println(treatment.toFileString() + "\n");
  }
}
