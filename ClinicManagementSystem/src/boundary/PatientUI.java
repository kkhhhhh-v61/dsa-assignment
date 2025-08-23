package boundary;

import adt.ListInterface;
import adt.RedBlackTreeMap;
import entity.Patient;
import utility.Enum.BloodType;
import utility.Utilities;

/**
 *
 * @author Tan Kai Hao
 */
public class PatientUI {
  
  public String[] runPatientMenu() {

    final char[] INPUT_OPTIONS = { '1', '2', '3', '4', '0' };

    System.out.println();

    Utilities.clearScreen();

    System.out.println("+----------------------------+");
    System.out.println("|     PATIENT MANAGEMENT     |");
    System.out.println("+----------------------------+");
    System.out.println("| [1] Patient Registration   |");
    System.out.println("| [2] Patient Record List    |");
    System.out.println("| [3] Clear Patient Record   |");
    System.out.println("| [4] Initialize Patient     |");
    System.out.println("| [0] Return                 |");
    System.out.println("+----------------------------+");

    System.out.println();

    System.out.print("> ");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS) };    
  }
  
  public String[] inputPatientId() {

    final char[] INPUT_OPTIONS = { '0' };

    System.out.println();

    Utilities.clearScreen();

    System.out.println("+------------------+");
    System.out.println("|    PATIENT ID    |");
    System.out.println("+------------------+");

    System.out.println();

    System.out.println("[FORMAT] PXXXX");
    System.out.println("[0] Exit");

    System.out.println();

    System.out.print("> P");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS) };
  }
  
  public String[] inputIcNumber() {

    final char[] INPUT_OPTIONS = { '0' };

    System.out.println();

    Utilities.clearScreen();

    System.out.println("+------------------+");
    System.out.println("|    IC NUMBER     |");
    System.out.println("+------------------+");

    System.out.println();

    System.out.println("[FORMAT] YYMMDD-XX-XXXX");
    System.out.println("[0] Exit");

    System.out.println();

    System.out.print("> ");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS) };
  }
  
  public String[] inputPatientName() {

    final char[] INPUT_OPTIONS = { '0' };
    final int MAX_LENGTH = 35;

    System.out.println();

    Utilities.clearScreen();

    System.out.println("+------------------+");
    System.out.println("|   PATIENT NAME   |");
    System.out.println("+------------------+");

    System.out.println();

    System.out.println("[FORMAT] Only alphabet characters are allowed. Maximum length is " + MAX_LENGTH + " characters.");
    System.out.println("[NOTE] Use capitalization or spaces to separate words for best results.");
    System.out.println("[EXAMPLE] 'Your Name', 'yourName', 'your name' are all acceptable.");
    System.out.println("[0] Exit");

    System.out.println();

    System.out.print("> ");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS), String.valueOf(MAX_LENGTH) };
  }
  
  public String[] inputContactNumber() {

    final char[] INPUT_OPTIONS = { '0' };

    System.out.println();

    Utilities.clearScreen();

    System.out.println("+------------------+");
    System.out.println("|  CONTACT NUMBER  |");
    System.out.println("+------------------+");

    System.out.println();

    System.out.println("[FORMAT] 01X-XXX XXXX or 01X-XXXX XXXX");
    System.out.println("[0] Exit");

    System.out.println();

    System.out.print("> ");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS) };
  }
  
  public String[] inputEmail() {

    final char[] INPUT_OPTIONS = { '0' };
    final int MAX_LENGTH = 35;

    System.out.println();

    Utilities.clearScreen();

    System.out.println("+-----------------+");
    System.out.println("|      EMAIL      |");
    System.out.println("+-----------------+");

    System.out.println("[FORMAT] username@mailserver.domain");
    System.out.println("[0] Exit");

    System.out.println();

    System.out.print("> ");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS), String.valueOf(MAX_LENGTH) };
  }
  
  public String[] inputAddress() {

    final char[] INPUT_OPTIONS = { '0' };
    final int MAX_LENGTH = 45;

    System.out.println();

    Utilities.clearScreen();

    System.out.println("+-----------------+");
    System.out.println("|     ADDRESS     |");
    System.out.println("+-----------------+");

    System.out.println("[0] Exit");

    System.out.println();

    System.out.print("> ");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS), String.valueOf(MAX_LENGTH) };
  }
  
  public String[] inputBloodType() {

    final char[] INPUT_OPTIONS = { '1', '2', '3', '4', '0' };

    System.out.println();

    Utilities.clearScreen();

    System.out.println("+----------------+");
    System.out.println("|   BLOOD TYPE   |");
    System.out.println("+----------------+");

    System.out.println("[1] A | [2] B | [3] AB | [4] O");
    System.out.println("[0] Exit");

    System.out.println();

    System.out.print("> ");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS) };
  }
  
  public String[] confirmRegistration(Patient patient) {

    final char[] INPUT_OPTIONS = { '1', '2', '0' };

    System.out.println();

    Utilities.clearScreen();

    System.out.println("+-----------------------------------------------------------------+");
    System.out.println("|                    Registration Confirmation                    |");
    System.out.println("+-----------------------------------------------------------------+");

    System.out.printf("| %-15s : %-45s |\n", "IC Number", patient.getIcNumber());
    System.out.printf("| %-15s : %-45s |\n", "Name", patient.getName());
    System.out.printf("| %-15s : %-45s |\n", "Gender", patient.getGender());
    System.out.printf("| %-15s : %-45s |\n", "Birth Date", patient.getBirthDate());
    System.out.printf("| %-15s : %-45s |\n", "Age", patient.getAge());
    System.out.printf("| %-15s : %-45s |\n", "Contact Number", patient.getContactNumber());
    System.out.printf("| %-15s : %-45s |\n", "Email", patient.getEmail());
    System.out.printf("| %-15s : %-45s |\n", "Address", patient.getAddress());
    System.out.printf("| %-15s : %-45s |\n", "Blood Type", patient.getBloodType());

    System.out.println("+-----------------------------------------------------------------+");

    System.out.println();

    System.out.println("[1] Confirm | [2] Edit | [0] Cancel");

    System.out.println();

    System.out.print("> ");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS) };
  }

  public String[] runPatientRecordListMenu(RedBlackTreeMap<String, Patient> patientMap, String ui, int page) {

    final char[] INPUT_OPTIONS = { 'Q', 'E', 'W', '1', '2', '0' };

    System.out.println();

    Utilities.clearScreen();

    if (ui.equals("left")) {
      System.out.println("+===========================================================================================================+");
      System.out.printf("|                                         [PT1] PATIENT LIST [PG%02d]                                         |\n", page);
      System.out.println("+===========================================================================================================+");
      System.out.println("|  PID  |       IC       |              FULL NAME              | GENDER | BIRTH DATE | AGE | CONTACT NUMBER |");
      System.out.println("+===========================================================================================================+");

      if (patientMap.isEmpty()) {
        System.out.println(
            "|                                  No Patient records found in the system.                                  |");
      } else {
        int startIndex = (page - 1) * 10 + 1;
        int endIndex = Math.min(startIndex + 9, patientMap.size());
        ListInterface<String> keys = patientMap.getKeys();
        for (int i = startIndex; i <= endIndex; i++) {
          System.out.println(String.format("| %-5s | %14s | %-35s | %6s | %10s | %3s | %-14s |", keys.get(i),
              patientMap.get(keys.get(i)).getIcNumber(), patientMap.get(keys.get(i)).getName(),
              patientMap.get(keys.get(i)).getGender(), patientMap.get(keys.get(i)).getBirthDate(),
              patientMap.get(keys.get(i)).getAge(), patientMap.get(keys.get(i)).getContactNumber()));
        }
      }
      System.out.println("+===========================================================================================================+");
    } else {
      System.out.println("+===============================================================================================================+");
      System.out.printf("|                                           [PT2] PATIENT LIST [PG%02d]                                           |\n", page);
      System.out.println("+===============================================================================================================+");
      System.out.println("|  PID  |                EMAIL                |                    ADDRESS                    |  REG DATE  | BT |");
      System.out.println("+===============================================================================================================+");

      if (patientMap.isEmpty()) {
        System.out.println("|                                    No Patient records found in the system.                                    |");
      } else {
        int startIndex = (page - 1) * 10 + 1;
        int endIndex = Math.min(startIndex + 9, patientMap.size());
        ListInterface<String> keys = patientMap.getKeys();
        for (int i = startIndex; i <= endIndex; i++) {
          System.out.println(String.format("| %-5s | %-35s | %-45s | %10s | %-2s |", keys.get(i),
              patientMap.get(keys.get(i)).getEmail(), patientMap.get(keys.get(i)).getAddress(),
              patientMap.get(keys.get(i)).getRegistrationDate(), patientMap.get(keys.get(i)).getBloodType()));
        }
      }
      System.out.println("+===============================================================================================================+");
    }

    System.out.println();

    System.out.println("[Q] Previous Page | [E] Next Page | [W] Switch Part View");
    System.out.println("[1] Edit Record | [2] Delete Record | [0] Return");

    System.out.println();

    System.out.print("> ");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS) };
  }
  
  public String[] runPatientEditMenu(Patient patient, String ui) {

    final char[] INPUT_OPTIONS = { 'W', '1', '2', '3', '4', '5', '6', '0' };

    System.out.println();

    Utilities.clearScreen();

    if (ui.equals("left")) {
      System.out.println("+===========================================================================================================+");
      System.out.println("|                                         [PT1] PATIENT LIST                                                |");
      System.out.println("+===========================================================================================================+");
      System.out.println("|  PID  |       IC       |              FULL NAME              | GENDER | BIRTH DATE | AGE | CONTACT NUMBER |");
      System.out.println("+===========================================================================================================+");

      System.out.println(String.format("| %-5s | %14s | %-35s | %6s | %10s | %3s | %-14s |", patient.getPatientId(),
          patient.getIcNumber(), patient.getName(), patient.getGender(), patient.getBirthDate(), patient.getAge(),
          patient.getContactNumber()));

      System.out.println("+===========================================================================================================+");
    } else {
      System.out.println("+===============================================================================================================+");
      System.out.println("|                                           [PT2] PATIENT LIST                                                  |");
      System.out.println("+===============================================================================================================+");
      System.out.println("|  PID  |                EMAIL                |                    ADDRESS                    |  REG DATE  | BT |");
      System.out.println("+===============================================================================================================+");

      System.out.println(String.format("| %-5s | %-35s | %-45s | %10s | %-2s |", patient.getPatientId(),
          patient.getEmail(), patient.getAddress(), patient.getRegistrationDate(), patient.getBloodType()));

      System.out.println("+===============================================================================================================+");
    }

    System.out.println();

    System.out.println("+----------------------+");
    System.out.println("| SELECT FIELD TO EDIT |");
    System.out.println("+----------------------+");

    System.out.println("[1] IC Number | [2] Name | [3] Contact Number | [4] Email | [5] Address | [6] Blood Type");
    System.out.println("[W] Switch Part View | [0] Return");

    System.out.println();

    System.out.print("> ");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS) };
  }
  
  public String[] editIcNumber(String icNumber) {

    final char[] INPUT_OPTIONS = { '0' };

    System.out.println();

    Utilities.clearScreen();

    System.out.println("+------------------+");
    System.out.println("|    IC NUMBER     |");
    System.out.println("+------------------+");

    System.out.println();

    System.out.println("[FORMAT] YYMMDD-XX-XXXX");
    System.out.println("[0] Exit");

    System.out.println();

    System.out.println("Current IC Number: " + icNumber);
    System.out.print("> ");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS) };
  }
  
  public String[] editPatientName(String name) {

    final char[] INPUT_OPTIONS = { '0' };
    final int MAX_LENGTH = 35;

    System.out.println();

    Utilities.clearScreen();

    System.out.println("+------------------+");
    System.out.println("|   PATIENT NAME   |");
    System.out.println("+------------------+");

    System.out.println();

    System.out.println("[FORMAT] Only alphabet characters are allowed.");
    System.out.println("[NOTE] Use capitalization or spaces to separate words for best results.");
    System.out.println("[EXAMPLE] 'Your Name', 'yourName', 'your name' are all acceptable.");
    System.out.println("[0] Exit");

    System.out.println();

    System.out.println("Current Patient Name: " + name);
    System.out.print("> ");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS), String.valueOf(MAX_LENGTH) };
  }
  
  public String[] editContactNumber(String contactNumber) {

    final char[] INPUT_OPTIONS = { '0' };

    System.out.println();

    Utilities.clearScreen();

    System.out.println("+------------------+");
    System.out.println("|  CONTACT NUMBER  |");
    System.out.println("+------------------+");

    System.out.println();

    System.out.println("[FORMAT] 01X-XXX XXXX or 01X-XXXX XXXX");
    System.out.println("[0] Exit");

    System.out.println();

    System.out.println("Current Contact Number: " + contactNumber);
    System.out.print("> ");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS) };
  }
  
  public String[] editEmail(String email) {

    final char[] INPUT_OPTIONS = { '0' };
    final int MAX_LENGTH = 35;

    System.out.println();

    Utilities.clearScreen();

    System.out.println("+-----------------+");
    System.out.println("|      EMAIL      |");
    System.out.println("+-----------------+");

    System.out.println();

    System.out.println("[FORMAT] username@mailserver.domain");
    System.out.println("[0] Exit");

    System.out.println();

    System.out.println("Current Email: " + email);
    System.out.print("> ");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS), String.valueOf(MAX_LENGTH) };
  }
  
  public String[] editAddress(String address) {

    final char[] INPUT_OPTIONS = { '0' };
    final int MAX_LENGTH = 45;

    System.out.println();

    Utilities.clearScreen();

    System.out.println("+-----------------+");
    System.out.println("|     ADDRESS     |");
    System.out.println("+-----------------+");

    System.out.println();

    System.out.println("[0] Exit");

    System.out.println();

    System.out.println("Current Address: " + address);
    System.out.print("> ");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS), String.valueOf(MAX_LENGTH) };
  }
  
  public String[] editBloodType(BloodType bloodType) {

    final char[] INPUT_OPTIONS = { '1', '2', '3', '4', '0' };

    System.out.println();

    Utilities.clearScreen();

    System.out.println("+----------------+");
    System.out.println("|   BLOOD TYPE   |");
    System.out.println("+----------------+");

    System.out.println();

    System.out.println("[1] A | [2] B | [3] AB | [4] O");
    System.out.println("[0] Exit");

    System.out.println();

    System.out.println("Current Blood Type: " + bloodType);
    System.out.print("> ");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS) };
  }
  
  public String[] confirmEdit(String field, String oldValue, String newValue) {

    final char[] INPUT_OPTIONS = { '1', '0' };

    System.out.println();

    Utilities.clearScreen();

    System.out.println("+----------------------+");
    System.out.println("|   Edit Confimation   |");
    System.out.println("+----------------------+");

    System.out.println();

    System.out.println(field + ": " + oldValue + " -> " + newValue);
    System.out.println("[1] Confirm | [0] Cancel");

    System.out.println();

    System.out.print("> ");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS) };
  }
  
  public String[] runPatientDeleteMenu(Patient patient, String ui) {
    
    final char[] INPUT_OPTIONS = { 'W', '1', '0' };

    System.out.println();

    Utilities.clearScreen();

    if (ui.equals("left")) {
      System.out.println("+===========================================================================================================+");
      System.out.println("|                                         [PT1] PATIENT LIST                                                |");
      System.out.println("+===========================================================================================================+");
      System.out.println("|  PID  |       IC       |              FULL NAME              | GENDER | BIRTH DATE | AGE | CONTACT NUMBER |");
      System.out.println("+===========================================================================================================+");

      System.out.println(String.format("| %-5s | %14s | %-35s | %6s | %10s | %3s | %-14s |", patient.getPatientId(),
          patient.getIcNumber(), patient.getName(), patient.getGender(), patient.getBirthDate(), patient.getAge(),
          patient.getContactNumber()));

      System.out.println("+===========================================================================================================+");
    } else {
      System.out.println("+===============================================================================================================+");
      System.out.println("|                                           [PT2] PATIENT LIST                                                  |");
      System.out.println("+===============================================================================================================+");
      System.out.println("|  PID  |                EMAIL                |                    ADDRESS                    |  REG DATE  | BT |");
      System.out.println("+===============================================================================================================+");

      System.out.println(String.format("| %-5s | %-35s | %-45s | %10s | %-2s |", patient.getPatientId(),
          patient.getEmail(), patient.getAddress(), patient.getRegistrationDate(), patient.getBloodType()));

      System.out.println("+===============================================================================================================+");
    }

    System.out.println();

    System.out.println("+---------------------+");
    System.out.println("| Delete Confirmation |");
    System.out.println("+---------------------+");

    System.out.println();

    System.out.println("[W] Switch Part View");
    System.out.println("[1] Confirm | [0] Cancel");

    System.out.println();

    System.out.print("> ");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS) };
  }
  
  public String[] runPatientClearance() {

    final char[] INPUT_OPTIONS = { '1', '0' };

    System.out.println();

    Utilities.clearScreen();

    System.out.println("+----------------------+");
    System.out.println("| Clear Patient Record |");
    System.out.println("+----------------------+");

    System.out.println();

    System.out.println("[1] Confirm | [0] Cancel");

    System.out.println();

    System.out.print("> ");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS) };
  }
}
