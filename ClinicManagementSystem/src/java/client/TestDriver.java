package client;

import entity.Doctor;
import entity.Patient;
import utility.Enum.Gender;
import adt.ArrayList;
import utility.Enum.Specialisation;

public class TestDriver {
  public static void main(String[] args) {
    ArrayList<String> allergies = new ArrayList<>();
    allergies.add("Penicillin");
    allergies.add("Aspirin");
    
    Patient patient = new Patient("John", Gender.Male, "01/01/2000", "12345678", "12345678", "12345678", allergies);
    System.out.println(patient);

    System.out.println();
    
    Doctor doctor = new Doctor("Alice", Gender.Female, "01/01/1990", "12345678", "12345678", "12345678", Specialisation.General, true);
    System.out.println(doctor);
  }
}
