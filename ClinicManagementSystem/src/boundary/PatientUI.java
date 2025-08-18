package boundary;

import adt.HashMap;
import control.PatientController;
import entity.Patient;
import utility.Utilities;
import java.util.Scanner;

/**
 *
 * @author khtan
 */
public class PatientUI {
  private final static PatientController patientMaintenance = new PatientController();
  private final static Utilities utilities = new Utilities();
  private final static Scanner scanner = new Scanner(System.in);
  private static String input;

  public String[] displayPatientManagementUI() {
    final char[] INPUT_OPTIONS = { '1', '2', '0' };
    do {
      utilities.clearScreen();
      utilities.displayLine();
      System.out.println("|                                        PATIENT MANAGEMENT                                        |");
      utilities.displayLine();
      System.out.println("| [1] Patient Registration                                                                         |");
      System.out.println("| [2] Patient List                                                                                 |");
      System.out.println("| [0] Back                                                                                         |");
      utilities.displayLine();
      utilities.nextLine();
      System.out.print("Select Option " + utilities.displayInputOptions(INPUT_OPTIONS) + " : ");
      input = scanner.nextLine();
    } while (input.isEmpty());
    return new String[] {input, new String(INPUT_OPTIONS)};
  }
  
  public void displayPatientRegistrationUI() {
    utilities.clearScreen();
    utilities.displayLine();
    System.out.println("|                                       PATIENT REGISTRATION                                       |");
    utilities.displayLine();
  }
  
  public String inputPatientId() {
    final char[] INPUT_OPTIONS = { '0' };
    final int LENGTH = 4;
    boolean isId = false;
    boolean exit = false;
    do {
      displayPatientRegistrationUI();
      System.out.println("Format : PXXXX");
      System.out.println("[0] Cancel");
      System.out.print("Enter Patient ID : P");
      input = scanner.nextLine().trim();
      boolean[] response = patientMaintenance.validatePatientId(input, INPUT_OPTIONS, LENGTH);
      isId = response[0];
      exit = response[1];
    } while (!exit);
    if (isId) { // check if input is valid ID
      return "P" + input;
    }
    return input;
  }

  public String inputPatientIcNumber() {
    boolean exit = false;
    do {
      final char[] INPUT_OPTIONS = { '0' };
      final String FORMAT = "XXXXXX-XX-XXXX";
      displayPatientRegistrationUI();
      System.out.println("Format : " + FORMAT);
      System.out.println("[0] Cancel");
      System.out.print("Enter Patient IC Number : ");
      input = scanner.nextLine().trim();
      exit = patientMaintenance.validatePatientIcNumber(input, INPUT_OPTIONS, FORMAT);
    } while (!exit);
    return input;
  }

  public String inputPatientName() {
    boolean exit = false;
    do {
      final char[] INPUT_OPTIONS = { '0' };
      final int MAX_LENGTH = 35;
      displayPatientRegistrationUI();
      System.out.println("Format : Capitalize the first letter of each separate word for best result. Spaces are optional.");
      System.out.println("[0] Cancel");
      System.out.print("Enter Patient Name : ");
      input = scanner.nextLine().trim();
      exit = patientMaintenance.validatePatientName(input, INPUT_OPTIONS, MAX_LENGTH);
    } while (!exit);
    return input;
  }

  public char inputPatientGender() {
    final char[] INPUT_OPTIONS = { '1', '2', '0' };
    boolean exit = false;
    do {
      displayPatientRegistrationUI();
      System.out.println("[1] Male | [2] Female | [0] Cancel");
      System.out.print("Enter Patient Gender " + utilities.displayInputOptions(INPUT_OPTIONS) + " : ");
      input = scanner.nextLine().trim();
      exit = patientMaintenance.validatePatientGender(input, INPUT_OPTIONS);
    } while (!exit);
    return input.charAt(0);
  }
  
  public String inputPatientBirthDate() {
    final char[] INPUT_OPTIONS = { '0' };
    final String FORMAT = "yyyy-MM-dd";
    boolean exit = false;
    do {
      displayPatientRegistrationUI();
      System.out.println("Format : " + FORMAT);
      System.out.println("[0] Cancel");
      System.out.print("Enter Patient Birth Date : ");
      input = scanner.nextLine();
      exit = patientMaintenance.validatePatientBirthDate(input, INPUT_OPTIONS, FORMAT);
    } while (!exit);
    return input;
  }

  public String inputPatientContactNumber() {
    final char[] INPUT_OPTIONS = { '0' };
    final String FORMAT = "01X-XXX XXXX OR 01X-XXXX XXXX (hypens and spaces can be excluded)";
    boolean exit = false;
    do {
      displayPatientRegistrationUI();
      System.out.println("Format : " + FORMAT);
      System.out.println("[0] Cancel");
      System.out.print("Enter Patient Contact Number : ");
      input = scanner.nextLine().trim();
      exit = patientMaintenance.validatePatientContactNumber(input, INPUT_OPTIONS, FORMAT);
    } while (!exit);
    return input;
  }

  public String inputPatientEmail() {
    final char[] INPUT_OPTIONS = { '0' };
    final String FORMAT = "XXX@XXXXX.XXX";
    boolean exit = false;
    do {
      displayPatientRegistrationUI();
      System.out.println("Format : " + FORMAT);
      System.out.println("[0] Cancel");
      System.out.print("Enter Patient Email : ");
      input = scanner.nextLine().trim();
      exit = patientMaintenance.validatePatientEmail(input, INPUT_OPTIONS, FORMAT);
    } while (!exit);
    return input;
  }

  public String inputPatientAddress() {
    final char[] INPUT_OPTIONS = { '0' };
    final int MAX_LENGTH = 50;
    boolean exit = false;
    do {
      displayPatientRegistrationUI();
      System.out.println("[0] Cancel");
      System.out.print("Enter Patient Address : ");
      input = scanner.nextLine();
      exit = patientMaintenance.validatePatientAddress(input, INPUT_OPTIONS, MAX_LENGTH);
    } while (!exit);
    return input;
  }

  public String inputPatientRegistrationDate() {
    final char[] INPUT_OPTIONS = { '0' };
    final String FORMAT = "yyyy-MM-dd";
    boolean exit = false;
    do {
      displayPatientRegistrationUI();
      System.out.println("Format : " + FORMAT);
      System.out.println("[0] Cancel");
      System.out.print("Enter Patient Registration Date : ");
      input = scanner.nextLine();
      exit = patientMaintenance.validatePatientRegistrationDate(input, INPUT_OPTIONS, FORMAT);
    } while (!exit);
    return input;
  }

  public char inputPatientBloodType() {
    final char[] INPUT_OPTIONS = { '1', '2', '3', '4', '0' };
    boolean exit = false;
    do {
      displayPatientRegistrationUI();
      System.out.println("[1] A | [2] B | [3] AB | [4] O | [0] Cancel");
      System.out.print("Enter Patient Blood Type " + utilities.displayInputOptions(INPUT_OPTIONS) + " : ");
      input = scanner.nextLine().trim();
      exit = patientMaintenance.validatePatientBloodType(input, INPUT_OPTIONS);
    } while (!exit);
    return input.charAt(0);
  }

  public String[] displayPatientListUI(HashMap<String, Patient> patientMap, String ui, int page) {
    final char[] INPUT_OPTIONS = { 'E', 'Q', 'W', '1', '2', '3', '0' };
    char option;
    if (ui.equals("listLeft")) {
      displayListLeftUI(patientMap, page);
    } else if (ui.equals("listRight")) {
      displayListRightUI(patientMap, page);
    }
    option = displayListOptionSelection(INPUT_OPTIONS);
    return new String[] { String.valueOf(option), new String(INPUT_OPTIONS) };
  }
  
  public void displayListLeftUI(HashMap<String, Patient> patientMap, int page) {
    utilities.clearScreen();
    System.out.println("+===========================================================================================================+");
    System.out.printf("| [<-E]                                   [PT1] PATIENT LIST [PG%02d]                                   [Q->] |\n", page);
    System.out.println("+===========================================================================================================+");
    System.out.println("|  PID  |       IC       |              FULL NAME              | GENDER | BIRTH DATE | AGE | CONTACT NUMBER |");
    System.out.println("+===========================================================================================================+");
    for (String key : patientMap.getKeys()) {
      System.out.println(patientMap.get(key).toStringListLeft());
    }
  }

  public void displayListRightUI(HashMap<String, Patient> patientMap, int page) {
    utilities.clearScreen();
    System.out.println("+===============================================================================================================+");
    System.out.printf("| [<-E]                                   [PT2] PATIENT LIST [PG%02d]                                   [Q->] |\n", page);
    System.out.println("+===============================================================================================================+");
    System.out.println("|  PID  |                EMAIL                |                    ADDRESS                    |  REG DATE  | BT |");
    System.out.println("+===============================================================================================================+");
    for (String key : patientMap.getKeys()) {
      System.out.println(patientMap.get(key).toStringListRight());
    }
  }
  
  public char displayListOptionSelection(char[] options) {
    utilities.nextLine();
    System.out.println("[E] Previous Page | [Q] Next Page | [W] Switch Part View");
    System.out.println("[1] View Details | [2] Update Record | [3] Remove Record | [0] Back");
    System.out.print("Select Option " + utilities.displayInputOptions(options) + " : ");
    return scanner.nextLine().trim().charAt(0);
  }
}
