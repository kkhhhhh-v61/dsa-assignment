package boundary;

import entity.Patient;
import utility.Utilities;

/**
 *
 * @author Tan Kai Hao
 */
public class SystemUI {

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
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS) };
  }
  
  public String[] runClinicStaffMenu() {

    final char[] INPUT_OPTIONS = { '1', '2', '3', '4', '5', '0' };

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
    System.out.println("| [0] Return                  |");
    System.out.println("+-----------------------------+");

    System.out.println();

    System.out.print("> ");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS) };
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
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS) };
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

    final char[] INPUT_OPTIONS = { '1', '2', '3', '0' };

    System.out.println();

    Utilities.clearScreen();

    System.out.println("+--------------------+");
    System.out.println("|   STUDENT PORTAL   |");
    System.out.println("+--------------------+");
    System.out.println("| [1] Appointments   |");
    System.out.println("| [2] Consultations  |");
    System.out.println("| [3] Treatments     |");
    System.out.println("| [0] Return         |");
    System.out.println("+--------------------+");

    System.out.println();

    System.out.print("> ");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS) };
  }
}
