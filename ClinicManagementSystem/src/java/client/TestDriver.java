package client;

import adt.ArrayList;
import entity.Appointment;
import entity.Doctor;
import entity.Medicine;
import entity.Patient;
import entity.Schedule;
import entity.Consultation;
import utility.Enum.DayOfWeek;
import utility.Enum.Gender;
import utility.Enum.Specialisation;
import utility.Enum.MedicineCategory;
import java.time.LocalDate;
import java.time.LocalTime;

public class TestDriver {
  public static void main(String[] args) {
    ArrayList<String> allergies = new ArrayList<>();
    allergies.add("Penicillin");
    allergies.add("Aspirin");
    
    Patient patient = new Patient("John", Gender.Male, "01/01/2000", "12345678", "12345678", "12345678", allergies);
    System.out.println(patient);

    System.out.println();
    
    ArrayList<Schedule> schedules = new ArrayList<>();
    schedules.add(new Schedule(DayOfWeek.Monday, LocalTime.of(8, 0), LocalTime.of(12, 0), true));
    schedules.add(new Schedule(DayOfWeek.Tuesday, LocalTime.of(8, 0), LocalTime.of(12, 0), true));
    schedules.add(new Schedule(DayOfWeek.Wednesday, LocalTime.of(8, 0), LocalTime.of(12, 0), true));
    schedules.add(new Schedule(DayOfWeek.Thursday, LocalTime.of(8, 0), LocalTime.of(12, 0), true));
    schedules.add(new Schedule(DayOfWeek.Friday, LocalTime.of(8, 0), LocalTime.of(12, 0), true));
    schedules.add(new Schedule(DayOfWeek.Saturday, LocalTime.of(8, 0), LocalTime.of(12, 0), true));
    schedules.add(new Schedule(DayOfWeek.Sunday, LocalTime.of(8, 0), LocalTime.of(12, 0), true));

    Doctor doctor = new Doctor("Alice", Gender.Female, "01/01/1990", "12345678", "12345678", "12345678", Specialisation.General, true, schedules);
    System.out.println(doctor);
    
    System.out.println();

    Appointment appointment = new Appointment(patient.getPatientID(), doctor.getDoctorID(), "", "", LocalDate.of(2025, 8, 15), LocalTime.of(12, 0));
    System.out.println(appointment);

    System.out.println();

    Medicine medicine = new Medicine("Paracetamol", MedicineCategory.Tablet, "500mg", 100, 10);
    System.out.println(medicine);

    System.out.println();

    ArrayList<String> symptoms = new ArrayList<>();
    symptoms.add("Fever");
    symptoms.add("Cough");
    symptoms.add("Headache");
    
    ArrayList<String> diagnosis = new ArrayList<>();
    diagnosis.add("Flu");
    diagnosis.add("Cold");
    diagnosis.add("Malaria");

    Consultation consultation = new Consultation(patient.getPatientID(), doctor.getDoctorID(), LocalDate.of(2025, 8, 15), LocalTime.of(12, 0), symptoms, diagnosis, "");
    System.out.println(consultation);
  }
}
