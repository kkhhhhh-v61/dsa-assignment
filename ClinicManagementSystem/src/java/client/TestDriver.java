package client;

import entity.Doctor;
import entity.Patient;
import entity.Schedule;
import utility.Enum.DayOfWeek;
import utility.Enum.Gender;
import adt.ArrayList;
import utility.Enum.Specialisation;
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
  }
}
