package boundary;

import entity.Patient;
import utility.Utilities;
import control.SystemController;
import utility.MessageUI;
import utility.Validation;
import java.util.Scanner;

/**
 *
 * @author Tan Kai Hao
 */
public class SystemUI {

  private SystemController systemController;
  private PharmacyUI pharmacyUI;
  private Scanner scanner;

  public SystemUI(SystemController systemController) {
    this.systemController = systemController;
    this.pharmacyUI = new PharmacyUI();
    this.scanner = new Scanner(System.in);
  }

  public String[] runMainMenu() {

    final char[] INPUT_OPTIONS = { '1', '2', '0' };

    System.out.println();

    Utilities.clearScreen();

    System.out.println("+==================================================================================================+");
    System.out.println("|                 ##   ##  ##   ##  #######     #######   #####   ######   #######                 |");
    System.out.println("|                 ##   ##  ###  ##    ###       ##       ##   ##  ##   ##  ##                      |");
    System.out.println("|                 ##   ##  #### ##    ###       ##       ##   ##  ##   ##  ##                      |");
    System.out.println("|                 ##   ##  ## ####    ###       ##       #######  #######  #####                   |");
    System.out.println("|                 ##   ##  ##  ###    ###       ##       ##   ##  ##  ##   ##                      |");
    System.out.println("|                 ##   ##  ##  ###    ###       ##       ##   ##  ##   ##  ##                      |");
    System.out.println("|                 #######  ##   ##  #######     #######  ##   ##  ##   ##  #######                 |");
    System.out.println("+==================================================================================================+");
    System.out.println("|                                     CLINIC MANAGEMENT SYSTEM                                     |");
    System.out.println("+==================================================================================================+");

    System.out.println();

    System.out.println("+-------------------------+");
    System.out.println("| [1] Clinic Staff Portal |");
    System.out.println("| [2] Student Portal      |");
    System.out.println("| [0] Exit                |");
    System.out.println("+-------------------------+");

    System.out.println();

    System.out.print("> ");
    return new String[] { scanner.nextLine().trim(), String.valueOf(INPUT_OPTIONS) };
  }
  
  public String[] runClinicStaffMenu() {

    final char[] INPUT_OPTIONS = { '1', '2', '3', '4', '5', '6', '7', '0' }; // Added '7' for Pharmacy

    System.out.println();

    Utilities.clearScreen();

    System.out.println("+-----------------------------+");
    System.out.println("|     CLINIC STAFF PORTAL     |");
    System.out.println("+-----------------------------+");
    System.out.println("| [1] Patient Management      |");
    System.out.println("| [2] Doctor Management       |");
    System.out.println("| [3] Appointment Management  |");
    System.out.println("| [4] Consultation Management |");
    System.out.println("| [5] Treatment Management    |");
    System.out.println("| [6] Report and Analytics    |");
    System.out.println("| [7] Pharmacy Management     |"); // Added Pharmacy Management
    System.out.println("| [0] Return                  |");
    System.out.println("+-----------------------------+");

    System.out.println();

    System.out.print("> ");
    String choice = scanner.nextLine().trim();
    
    if (Validation.isSingleChar(choice) && Validation.isValidOption(choice.charAt(0), INPUT_OPTIONS)) {
      // The calling method (SystemController) will handle the action based on the choice.
      // No direct call to pharmacyUI.displayPharmacyMenu() here.
    }
    return new String[] { choice, String.valueOf(INPUT_OPTIONS) };
  }

  public String[] inputPatientId() {

    final char[] INPUT_OPTIONS = { '0' };

    System.out.println();

    Utilities.clearScreen();

    System.out.println("+------------------------+");
    System.out.println("| LOG IN WITH PATIENT ID |");
    System.out.println("+------------------------+");

    System.out.println();

    System.out.println("[FORMAT] PXXXX");

    System.out.println();
    
    System.out.println("[0] Exit");

    System.out.println();

    System.out.print("> P");
    return new String[] { scanner.nextLine().trim(), String.valueOf(INPUT_OPTIONS) };
  }

  public void loginSuccess(Patient patient) {

    System.out.println();

    Utilities.clearScreen();

    System.out.println("+------------------+ ");
    System.out.println("| LOGIN SUCCESSFUL | ");
    System.out.println("+------------------+ ");

    System.out.println();

    System.out.println("Welcome " + patient.getPatientId() + " : " + patient.getName());
    Utilities.pressAnyKey();
  }
  
  public String[] runStudentMenu() {

    final char[] INPUT_OPTIONS = { '1', '2', '3', '4', '0' };

    System.out.println();

    Utilities.clearScreen();

    System.out.println("+----------------------+");
    System.out.println("|    STUDENT PORTAL    |");
    System.out.println("+----------------------+");
    System.out.println("| [1] Get Queue Number |");
    System.out.println("| [2] Appointments     |");
    System.out.println("| [3] Consultations    |");
    System.out.println("| [4] Treatments       |");
    System.out.println("| [0] Return           |");
    System.out.println("+----------------------+");

    System.out.println();

    System.out.print("> ");
    return new String[] { scanner.nextLine().trim(), String.valueOf(INPUT_OPTIONS) };
  }
}
