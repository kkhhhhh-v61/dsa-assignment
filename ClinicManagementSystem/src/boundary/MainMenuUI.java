package boundary;

import utility.Utilities;
import java.util.Scanner;

/**
 *
 * @author khtan
 */
public class MainMenuUI {
  private final static Utilities utilities = new Utilities();
  private final static Scanner scanner = utilities.getScannerInput();
  private static String option;

  public String[] displayMainMenu() {
    String[] response = new String[2];
    do {
      displayTitle();
      utilities.nextLine();
      response = displayRoleSelection();
    } while (response[0].isEmpty());
    return response;
  }

  public void displayTitle() {
    utilities.clearScreen();
    utilities.displayLine();
    System.out.println("|                 ##   ##  ##   ##  #######     #######   #####   ######   #######                 |");
    System.out.println("|                 ##   ##  ###  ##    ###       ##       ##   ##  ##   ##  ##                      |");
    System.out.println("|                 ##   ##  #### ##    ###       ##       ##   ##  ##   ##  ##                      |");
    System.out.println("|                 ##   ##  ## ####    ###       ##       #######  #######  #####                   |");
    System.out.println("|                 ##   ##  ##  ###    ###       ##       ##   ##  ##  ##   ##                      |");
    System.out.println("|                 ##   ##  ##  ###    ###       ##       ##   ##  ##   ##  ##                      |");
    System.out.println("|                 #######  ##   ##  #######     #######  ##   ##  ##   ##  #######                 |");
    utilities.displayLine();
    System.out.println("|                                     CLINIC MANAGEMENT SYSTEM                                     |");
    utilities.displayLine();
  }
  
  public String[] displayRoleSelection() {
    final char[] INPUT_OPTIONS = { '1', '2', '0' };

    utilities.displayLine();
    System.out.println("|                                         SELECT YOUR ROLE                                         |");
    utilities.displayLine();
    System.out.println("|                   [1]STUDENT                   ||                    [2]STAFF                    |");
    System.out.println("+================================================++================================================+");
    System.out.println("| + Appointment Registration                     || + Patient and Doctor Management                |");
    System.out.println("| + View Medical History                         || + Manage Appointments Schedules                |");
    System.out.println("| + View Prescriptions                           || + Consultation and Treatment Management        |");
    System.out.println("| + Payment and Bills                            || + Pharmacy Management                          |");
    System.out.println("| + Notifications and Reminders                  || + Reporting and Analytics                      |");
    utilities.displayLine();
    System.out.println("| 0 - Exit                                                                                         |");
    utilities.displayLine();
    utilities.nextLine();
    System.out.print("Select Role " + utilities.displayInputOptions(INPUT_OPTIONS) + " : ");
    option = scanner.nextLine();
    return new String[] {option, new String(INPUT_OPTIONS)};
  }
  
  public String[] displayStudentUI() {
    final char[] INPUT_OPTIONS = { '1', '2', '3', '4', '5', '6', '0' };
    do {
      utilities.clearScreen();
      utilities.displayLine();
      System.out.println("|                                          STUDENT PORTAL                                          |");
      utilities.displayLine();
      System.out.println("| [1] Make an Appointment                                                                          |");
      System.out.println("| [2] View Appointments                                                                            |");
      System.out.println("| [3] View Medical History                                                                         |");
      System.out.println("| [4] View Prescriptions                                                                           |");
      System.out.println("| [5] Payment and Bills                                                                            |");
      System.out.println("| [6] Notifications and Reminders                                                                  |");
      System.out.println("| [0] Exit                                                                                         |");
      utilities.displayLine();
      utilities.nextLine();
      System.out.print("Select Option " + utilities.displayInputOptions(INPUT_OPTIONS) + " : ");
      option = scanner.nextLine();
    } while (option.isEmpty());
    return new String[] {option, new String(INPUT_OPTIONS)};
  }
  
  public String[] displayStaffUI() {
    final char[] INPUT_OPTIONS = { '1', '2', '3', '4', '5', '6', '7', '0' };
    do {
      utilities.clearScreen();
      utilities.displayLine();
      System.out.println("|                                           STAFF PORTAL                                           |");
      utilities.displayLine();
      System.out.println("| [1] Patient Management                                                                           |");
      System.out.println("| [2] Doctor Management                                                                            |");
      System.out.println("| [3] Appointment Management                                                                       |");
      System.out.println("| [4] Consultation Management                                                                      |");
      System.out.println("| [5] Treatment Management                                                                         |");
      System.out.println("| [6] Pharmacy Management                                                                          |");
      System.out.println("| [7] Reporting and Analytics                                                                      |");
      System.out.println("| [0] Exit                                                                                         |");
      utilities.displayLine();
      utilities.nextLine();
      System.out.print("Select Option " + utilities.displayInputOptions(INPUT_OPTIONS) + " : ");
      option = scanner.nextLine();
    } while (option.isEmpty());
    return new String[] {option, new String(INPUT_OPTIONS)};  
  }
  
  public void displayGoodbyeMessage() {
    System.out.println("Goodbye!");
  }
}
