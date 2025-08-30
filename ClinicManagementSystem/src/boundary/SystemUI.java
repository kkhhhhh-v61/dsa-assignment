package boundary;

import entity.Patient;
import utility.Utilities;

/**
 *
 * @author Tan Kai Hao
 */
public class SystemUI {
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

  public void displayFormatInfo(String format) {
    System.out.println();
    System.out.println("[FORMAT] " + format);
  }

  public void displayOptionsInfo(String[] options) {
    System.out.println();
    for (String option : options) {
      System.out.println(option);
    }
  }

  public void displayTitle() {
    System.out.println();
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
  }

  public char[] displayMainMenu() {
    System.out.println();
    System.out.println("+-------------------------+");
    System.out.println("| [1] Clinic Staff Portal |");
    System.out.println("| [2] Student Portal      |");
    System.out.println("| [0] Exit                |");
    System.out.println("+-------------------------+");

    return new char[] { '1', '2', '0' };
  }
  
  public char[] displayClinicStaffPortalMenu() {
    System.out.println();
    System.out.println("+-----------------------------+");
    System.out.println("|     CLINIC STAFF PORTAL     |");
    System.out.println("+-----------------------------+");
    System.out.println("| [1] Queue Management        |");
    System.out.println("| [2] Patient Management      |");
    System.out.println("| [3] Doctor Management       |");
    System.out.println("| [4] Appointment Management  |");
    System.out.println("| [5] Consultation Management |");
    System.out.println("| [6] Treatment Management    |");
    System.out.println("| [7] Pharmacy Management     |");
    System.out.println("| [0] Return                  |");
    System.out.println("+-----------------------------+");

    return new char[] { '1', '2', '3', '4', '5', '6', '7', '0' };
  }

  public char[] displayPatientIdLoginInput() {
    System.out.println();
    System.out.println("+------------------------+");
    System.out.println("| LOG IN WITH PATIENT ID |");
    System.out.println("+------------------------+");

    displayFormatInfo("PXXXX");
    displayOptionsInfo(new String[] { "[0] Cancel" });

    return new char[] { '0' };
  }

  public void displayLoginSuccess(Patient patient) {
    System.out.println();
    System.out.println("+------------------+ ");
    System.out.println("| LOGIN SUCCESSFUL | ");
    System.out.println("+------------------+ ");

    System.out.println();
    System.out.println("WELCOME [" + patient.getPatientId() + "] " + patient.getName() + "!");
  }
  
  public char[] displayStudentPortalMenu() {
    System.out.println();
    System.out.println("+------------------------+");
    System.out.println("|     STUDENT PORTAL     |");
    System.out.println("+------------------------+");
    System.out.println("| [1] Queue Registration |");
    System.out.println("| [2] Appointments       |");
    System.out.println("| [3] Consultations      |");
    System.out.println("| [4] Treatments         |");
    System.out.println("| [0] Return             |");
    System.out.println("+------------------------+");

    return new char[] { '1', '2', '3', '4', '0' };
  }
}
