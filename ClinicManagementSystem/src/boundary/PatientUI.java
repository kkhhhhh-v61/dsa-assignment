package boundary;

import adt.ListInterface;
import adt.RedBlackTreeMap;
import entity.Patient;
import utility.Utilities;

/**
 *
 * @author Tan Kai Hao
 */
public class PatientUI {
  public String getGeneralInput() {
    System.out.println();
    System.out.print("> ");
    return Utilities.getUserInput().nextLine().trim();
  }

  public String getSpecificInput(String prefix) {
    System.out.println();
    System.out.print("> " + prefix);
    return Utilities.getUserInput().nextLine().trim();
  }

  public void displayNoteInfo(String note) {
    System.out.println();
    System.out.println("[NOTE] " + note);
  }

  public void displayFormatInfo(String format) {
    System.out.println();
    System.out.println("[FORMAT] " + format);
  }
  
  public void displayExampleInfo(String example) {
    System.out.println();
    System.out.println("[EXAMPLE] " + example);
  }

  public void displayOptionsInfo(String[] options) {
    System.out.println();
    for (String option : options) {
      System.out.println(option);
    }
  }

  public char[] displayPatientManagement() {
    System.out.println();
    System.out.println("+------------------------------+");
    System.out.println("|      PATIENT MANAGEMENT      |");
    System.out.println("+------------------------------+");
    System.out.println("| [1] View Patient List        |");
    System.out.println("| [2] Add Patient              |");
    System.out.println("| [3] Edit Patient             |");
    System.out.println("| [4] Delete Patient           |");
    System.out.println("| [5] Search Patient           |");
    System.out.println("| [6] Clear Patient List       |");
    System.out.println("| [7] Initialize Patient List  |");
    System.out.println("| [8] Report Generation        |");
    System.out.println("| [0] Return                   |");
    System.out.println("+------------------------------+");

    return new char[] { '1', '2', '3', '4', '5', '6', '7', '8', '0' };
  }
  
  public char[] displayPatientIdInput() {
    System.out.println();
    System.out.println("+------------------+");
    System.out.println("|    PATIENT ID    |");
    System.out.println("+------------------+");

    displayFormatInfo("PXXXX");
    displayOptionsInfo(new String[] { "[0] Cancel" });

    return new char[] { '0' };
  }
  
  public char[] displayIcNumberInput() {
    System.out.println();
    System.out.println("+------------------+");
    System.out.println("|    IC NUMBER     |");
    System.out.println("+------------------+");

    displayFormatInfo("YYMMDD-XX-XXXX");
    displayOptionsInfo(new String[] { "[0] Cancel" });

    return new char[] { '0' };
  }
  
  public char[] displayPatientNameInput() {
    System.out.println();
    System.out.println("+------------------+");
    System.out.println("|   PATIENT NAME   |");
    System.out.println("+------------------+");

    System.out.println();
    displayNoteInfo("Use CAPITALIZATION or SPACES to separate words for best results.");
    displayExampleInfo("'Your Name', 'yourName', 'your name'");
    displayOptionsInfo(new String[] { "[0] Cancel" });

    return new char[] { '0' };
  }
  
  public char[] displayGenderInput() {
    System.out.println();
    System.out.println("+------------------+");
    System.out.println("|      GENDER      |");
    System.out.println("+------------------+");

    displayOptionsInfo(new String[] { "[1] Male", "[2] Female", "[0] Cancel" });

    return new char[] { '1', '2', '0' };
  }

  public char[] displayBirthDateInput() {
    System.out.println();
    System.out.println("+------------------+");
    System.out.println("|    BIRTH DATE    |");
    System.out.println("+------------------+");

    displayFormatInfo("YYYY-MM-DD");
    displayOptionsInfo(new String[] { "[0] Cancel" });

    return new char[] { '0' };
  }
  
  public char[] displayAgeInput() {
    System.out.println();
    System.out.println("+-----------------+");
    System.out.println("|       AGE       |");
    System.out.println("+-----------------+");

    displayOptionsInfo(new String[] { "[0] Cancel" });

    return new char[] { '0' };
  }

  public char[] displayContactNumberInput() {
    System.out.println();
    System.out.println("+------------------+");
    System.out.println("|  CONTACT NUMBER  |");
    System.out.println("+------------------+");

    displayFormatInfo("01X-XXX XXXX or 01X-XXXX XXXX");
    displayOptionsInfo(new String[] { "[0] Cancel" });

    return new char[] { '0' };
  }

  public char[] displayEmailInput() {
    System.out.println();
    System.out.println("+-----------------+");
    System.out.println("|      EMAIL      |");
    System.out.println("+-----------------+");

    displayFormatInfo("username@mailserver.domain");
    displayOptionsInfo(new String[] { "[0] Cancel" });

    return new char[] { '0' };
  }
  
  public char[] displayAddressInput() {
    System.out.println();
    System.out.println("+-----------------+");
    System.out.println("|     ADDRESS     |");
    System.out.println("+-----------------+");

    displayOptionsInfo(new String[] { "[0] Cancel" });

    return new char[] { '0' };
  }
  
  public char[] displayRegistrationDateInput() {
    System.out.println();
    System.out.println("+-------------------+");
    System.out.println("| REGISTRATION DATE |");
    System.out.println("+-------------------+");

    displayFormatInfo("YYYY-MM-DD");
    displayOptionsInfo(new String[] { "[0] Cancel" });

    return new char[] { '0' };
  }
  
  public char[] displayBloodTypeInput() {
    System.out.println();
    System.out.println("+----------------+");
    System.out.println("|   BLOOD TYPE   |");
    System.out.println("+----------------+");

    displayOptionsInfo(new String[] { "[1] A", "[2] B", "[3] AB", "[4] O", "[0] Cancel" });

    return new char[] { '1', '2', '3', '4', '0' };
  }
  
  public void displayRegistrationConfirmation(Patient patient) {
    System.out.println();
    System.out.println("+-------------------------------------------------------------------+");
    System.out.println("|                     Registration Confirmation                     |");
    System.out.println("+-------------------------------------------------------------------+");
    System.out.printf("| %-17s : %-45s |\n", "Patient ID", patient.getPatientId());
    System.out.printf("| %-17s : %-45s |\n", "IC Number", patient.getIcNumber());
    System.out.printf("| %-17s : %-45s |\n", "Name", patient.getName());
    System.out.printf("| %-17s : %-45s |\n", "Gender", patient.getGender());
    System.out.printf("| %-17s : %-45s |\n", "Birth Date", patient.getBirthDate());
    System.out.printf("| %-17s : %-45s |\n", "Age", patient.getAge());
    System.out.printf("| %-17s : %-45s |\n", "Contact Number", patient.getContactNumber());
    System.out.printf("| %-17s : %-45s |\n", "Email", patient.getEmail());
    System.out.printf("| %-17s : %-45s |\n", "Address", patient.getAddress());
    System.out.printf("| %-17s : %-45s |\n", "Registration Date", patient.getRegistrationDate());
    System.out.printf("| %-17s : %-45s |\n", "Blood Type", patient.getBloodType());
    System.out.println("+-------------------------------------------------------------------+");
  }

  public void displaySelectAnOptionHeader() {
    System.out.println();
    System.out.println("+------------------+");
    System.out.println("| SELECT AN OPTION |");
    System.out.println("+------------------+");
  }

  public char[] displayRegistrationConfirmationMenu() {
    displaySelectAnOptionHeader();
    displayOptionsInfo(new String[] { "[1] Confirm", "[2] Edit", "[0] Cancel" });
    return new char[] { '1', '2', '0' };
  }
  
  public void displayPatientListDetailsPart1(RedBlackTreeMap<String, Patient> patientMap, int page) {
    int startIndex = (page - 1) * 10;
    int endIndex = Math.min(startIndex + 10, patientMap.size());
    ListInterface<String> patientKeys = patientMap.getKeys();
    for (int i = startIndex; i < endIndex; i++) {
      String key = patientKeys.get(i + 1);
      System.out.println(String.format("| %5s | %14s | %-35s | %6s | %10s | %3s | %-14s |", 
        patientMap.get(key).getPatientId(), patientMap.get(key).getIcNumber(), patientMap.get(key).getName(), 
        patientMap.get(key).getGender(), patientMap.get(key).getBirthDate(), patientMap.get(key).getAge(), 
        patientMap.get(key).getContactNumber()));
    }
  }
  
  public void displayPatientListDetailsPart2(RedBlackTreeMap<String, Patient> patientMap, int page) {
    int startIndex = (page - 1) * 10;
    int endIndex = Math.min(startIndex + 10, patientMap.size());
    ListInterface<String> patientKeys = patientMap.getKeys();
    for (int i = startIndex; i < endIndex; i++) {
      String key = patientKeys.get(i + 1);
      System.out.println(String.format("| %5s | %-35s | %-45s | %10s | %-2s |", 
        patientMap.get(key).getPatientId(), patientMap.get(key).getEmail(), patientMap.get(key).getAddress(), 
        patientMap.get(key).getRegistrationDate(), patientMap.get(key).getBloodType()));
    }
  }

  public char[] displayPatientListPart1(RedBlackTreeMap<String, Patient> patientMap, int page) {
    System.out.println();
    System.out.println("+===========================================================================================================+");
    System.out.printf("| [Part 1]                                      PATIENT LIST                                      [Page %02d] |\n", page);
    System.out.println("+===========================================================================================================+");
    System.out.println("|  PID  |       IC       |              FULL NAME              | GENDER | BIRTH DATE | AGE | CONTACT NUMBER |");
    System.out.println("+===========================================================================================================+");
    if (patientMap.isEmpty() || page > (patientMap.size() + 9) / 10) {
      System.out.println("|                                         No Patient records found.                                         |");
    } else {
      displayPatientListDetailsPart1(patientMap, page);
    }
    System.out.println("+===========================================================================================================+");
    System.out.println("| NAVIGATION | [Q] Previous Page | [E] Next Page | [W] Switch Part View |                      | [0] Return |");
    System.out.println("+===========================================================================================================+");
    
    return new char[] { 'Q', 'E', 'W', '0' };
  }

  public char[] displayPatientListPart2(RedBlackTreeMap<String, Patient> patientMap, int page) {
    System.out.println();
    System.out.println("+===============================================================================================================+");
    System.out.printf("| [Part 2]                                        PATIENT LIST                                        [Page %02d] |\n", page);
    System.out.println("+===============================================================================================================+");
    System.out.println("|  PID  |                EMAIL                |                    ADDRESS                    |  REG DATE  | BT |");
    System.out.println("+===============================================================================================================+");
    if (patientMap.isEmpty() || page > (patientMap.size() + 9) / 10) {
      System.out.println("|                                           No Patient records found.                                           |");
    } else {
      displayPatientListDetailsPart2(patientMap, page);
    }
    System.out.println("+===============================================================================================================+");
    System.out.println("| NAVIGATION | [Q] Previous Page | [E] Next Page | [W] Switch Part View |                          | [0] Return |");
    System.out.println("+===============================================================================================================+");
    
    return new char[] { 'Q', 'E', 'W', '0' };
  }
  
  public char[] displaySinglePatientPart1(Patient patient) {
    System.out.println();
    System.out.println("+===========================================================================================================+");
    System.out.println("| [Part 1]                                      PATIENT LIST                                                |");
    System.out.println("+===========================================================================================================+");
    System.out.println("|  PID  |       IC       |              FULL NAME              | GENDER | BIRTH DATE | AGE | CONTACT NUMBER |");
    System.out.println("+===========================================================================================================+");
    if (patient == null) {
      System.out.println("|                                         No Patient records found.                                         |");
    } else {
      System.out.println(String.format("| %5s | %14s | %-35s | %6s | %10s | %3s | %14s |", 
        patient.getPatientId(), patient.getIcNumber(), patient.getName(), patient.getGender(), 
        patient.getBirthDate(), patient.getAge(), patient.getContactNumber()));
    }
    System.out.println("+===========================================================================================================+");
    System.out.println("| NAVIGATION | [W] Switch Part View |                                                                       |");
    System.out.println("+===========================================================================================================+");
    
    return new char[] { 'W' };
  }
  
  public char[] displaySinglePatientPart2(Patient patient) {
    System.out.println();
    System.out.println("+===============================================================================================================+");
    System.out.println("| [Part 2]                                        PATIENT LIST                                                  |");
    System.out.println("+===============================================================================================================+");
    System.out.println("|  PID  |                EMAIL                |                    ADDRESS                    |  REG DATE  | BT |");
    System.out.println("+===============================================================================================================+");
    if (patient == null) {
      System.out.println("|                                           No Patient records found.                                           |");
    } else {
      System.out.println(String.format("| %-5s | %-35s | %-45s | %10s | %-2s |",
        patient.getPatientId(), patient.getEmail(), patient.getAddress(), patient.getRegistrationDate(), patient.getBloodType()));
    }
    System.out.println("+===============================================================================================================+");
    System.out.println("| NAVIGATION | [W] Switch Part View |                                                                           |");
    System.out.println("+===============================================================================================================+");
    
    return new char[] { 'W' };
  }
  
  public char[] displayPatientEditMenu() {
    System.out.println();
    System.out.println("+------------------+");
    System.out.println("|   EDIT OPTIONS   |");
    System.out.println("+------------------+");
    
    displayOptionsInfo(new String[] { "[1] IC Number", "[2] Name", "[3] Contact Number", "[4] Email", "[5] Address", "[6] Blood Type", "[0] Cancel" });

    return new char[] { '1', '2', '3', '4', '5', '6', '0' };
  }

  public void displayCurrentValue(String field, String currentValue) {
    System.out.println();
    System.out.println("Current " + field + ": " + currentValue);
  }

  public void displayNewValue(String field, String newValue) {
    System.out.println();
    System.out.println("New " + field + ": " + newValue);
  }

  public char[] displayEditConfirmationMenu(String field, String currentValue, String newValue) {
    System.out.println();
    System.out.println("+-------------------+");
    System.out.println("| EDIT CONFIRMATION |");
    System.out.println("+-------------------+");

    displayCurrentValue(field, currentValue);
    displayNewValue(field, newValue);
    displayOptionsInfo(new String[] { "[1] Confirm", "[0] Cancel" });

    return new char[] { '1', '0' };
  }

  public char[] displayDeleteConfirmationMenu() {
    System.out.println();
    System.out.println("+---------------------+");
    System.out.println("| DELETE CONFIRMATION |");
    System.out.println("+---------------------+");

    displayOptionsInfo(new String[] { "[1] Confirm", "[0] Cancel" });

    return new char[] { '1', '0' };
  }

  public char[] displaySearchMenu() {
    System.out.println();
    System.out.println("+------------------+");
    System.out.println("|  SEARCH OPTIONS  |");
    System.out.println("+------------------+");

    displayOptionsInfo(new String[] { "[1] Patient ID", "[2] IC Number", "[3] Name", "[4] Gender", "[5] Birth Date",
      "[6] Age", "[7] Contact Number", "[8] Email", "[9] Address", "[A] Registration Date", "[B] Blood Type", "[0] Cancel" });
    
    return new char[] { '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', '0' };
  }

  public char[] displayRecordClearanceMenu() {
    System.out.println();
    System.out.println("+------------------+");
    System.out.println("| RECORD CLEARANCE |");
    System.out.println("+------------------+");

    displayOptionsInfo(new String[] { "[1] Confirm", "[0] Cancel" });

    return new char[] { '1', '0' };
  }

  public char[] displayInitializationSizeInput() {
    System.out.println();
    System.out.println("+-------------------+");
    System.out.println("|    RECORD SIZE    |");
    System.out.println("+-------------------+");

    displayOptionsInfo(new String[] { "[0] Cancel" });

    return new char[] { '0' };
  }
  
  public char[] displayInitializationConfirmationMenu() {
    System.out.println();
    System.out.println("+-----------------------------+");
    System.out.println("| INITIALIZATION CONFIRMATION |");
    System.out.println("+-----------------------------+");

    displayOptionsInfo(new String[] { "[1] Confirm", "[0] Cancel" });

    return new char[] { '1', '0' };
  }

  public char[] displayReportGenerationMenu() {
    System.out.println();
    System.out.println("+---------------------------------+");
    System.out.println("|        REPORT GENERATION        |");
    System.out.println("+---------------------------------+");
    System.out.println("| [1] Patient Demographic Summary |");
    System.out.println("| [2] Patient Appointment Summary |");
    System.out.println("| [0] Cancel                      |");
    System.out.println("+---------------------------------+");

    return new char[] { '1', '2', '0' };
  }
}
