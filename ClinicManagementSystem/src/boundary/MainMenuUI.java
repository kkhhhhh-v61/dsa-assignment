package boundary;

import utility.MessageUI;
import utility.Validation;
import java.util.Scanner;

/**
 *
 * @author khtan
 */
public class MainMenuUI {
  private final static Scanner scanner = new Scanner(System.in);
  private static String option;

  public String[] displayMainMenu() {
    String[] response = new String[2];
    do {
      displayTitle();
      MessageUI.displayContinueMessage(); // Replaced utilityUI.nextLine()
      response = displayRoleSelection();
    } while (response[0].isEmpty());
    return response;
  }

  public void displayTitle() {
    MessageUI.clearScreen(); // Replaced utilityUI.clearScreen()
    MessageUI.displayBorder(); // Replaced utilityUI.displayLine()
    System.out.println("|                 ##   ##  ##   ##  #######     #######   #####   ######   #######                 |");
    System.out.println("|                 ##   ##  ###  ##    ###       ##       ##   ##  ##   ##  ##                      |");
    System.out.println("|                 ##   ##  #### ##    ###       ##       ##   ##  ##   ##  ##                      |");
    System.out.println("|                 ##   ##  ## ####    ###       ##       #######  #######  #####                   |");
    System.out.println("|                 ##   ##  ##  ###    ###       ##       ##   ##  ##  ##   ##                      |");
    System.out.println("|                 ##   ##  ##  ###    ###       ##       ##   ##  ##   ##  ##                      |");
    System.out.println("|                 #######  ##   ##  #######     #######  ##   ##  ##   ##  #######                 |");
    MessageUI.displayBorder(); // Replaced utilityUI.displayLine()
    System.out.println("|                                     CLINIC MANAGEMENT SYSTEM                                     |");
    MessageUI.displayBorder(); // Replaced utilityUI.displayLine()
  }
  
  public String[] displayRoleSelection() {
    final char[] INPUT_OPTIONS = { '1', '2', '0' };

    MessageUI.displayBorder(); // Replaced utilityUI.displayLine()
    System.out.println("|                                         SELECT YOUR ROLE                                         |");
    MessageUI.displayBorder(); // Replaced utilityUI.displayLine()
    System.out.println("|                   [1]STUDENT                   ||                    [2]STAFF                    |");
    System.out.println("+================================================++================================================+");
    System.out.println("| + Appointment Registration                     || + Patient and Doctor Management                |");
    System.out.println("| + View Medical History                         || + Manage Appointments Schedules                |");
    System.out.println("| + View Prescriptions                           || + Consultation and Treatment Management        |");
    System.out.println("| + Payment and Bills                            || + Pharmacy Management                          |");
    System.out.println("| + Notifications and Reminders                  || + Reporting and Analytics                      |");
    MessageUI.displayBorder(); // Replaced utilityUI.displayLine()
    System.out.println("| 0 - Exit                                                                                         |");
    MessageUI.displayBorder(); // Replaced utilityUI.displayLine()
    MessageUI.displayContinueMessage(); // Replaced utilityUI.nextLine()
    System.out.print("Select Role " + Validation.displayInputOptions(INPUT_OPTIONS) + " : "); // Replaced utilityUI.displayInputOptions()
    option = scanner.nextLine();
    return new String[] {option, new String(INPUT_OPTIONS)};
  }
  
  public String[] displayStudentUI() {
    final char[] INPUT_OPTIONS = { '1', '2', '3', '4', '5', '6', '0' };
    do {
      MessageUI.clearScreen(); // Replaced utilityUI.clearScreen()
      MessageUI.displayBorder(); // Replaced utilityUI.displayLine()
      System.out.println("|                                          STUDENT PORTAL                                          |");
      MessageUI.displayBorder(); // Replaced utilityUI.displayLine()
      System.out.println("| [1] Make an Appointment                                                                          |");
      System.out.println("| [2] View Appointments                                                                            |");
      System.out.println("| [3] View Medical History                                                                         |");
      System.out.println("| [4] View Prescriptions                                                                           |");
      System.out.println("| [5] Payment and Bills                                                                            |");
      System.out.println("| [6] Notifications and Reminders                                                                  |");
      System.out.println("| [0] Exit                                                                                         |");
      MessageUI.displayBorder(); // Replaced utilityUI.displayLine()
      MessageUI.displayContinueMessage(); // Replaced utilityUI.nextLine()
      System.out.print("Select Option " + Validation.displayInputOptions(INPUT_OPTIONS) + " : "); // Replaced utilityUI.displayInputOptions()
      option = scanner.nextLine();
    } while (option.isEmpty());
    return new String[] {option, new String(INPUT_OPTIONS)};
  }
  
  public String[] displayStaffUI() {
    final char[] INPUT_OPTIONS = { '1', '2', '3', '4', '5', '6', '7', '0' };
    do {
      MessageUI.clearScreen(); // Replaced utilityUI.clearScreen()
      MessageUI.displayBorder(); // Replaced utilityUI.displayLine()
      System.out.println("|                                           STAFF PORTAL                                           |");
      MessageUI.displayBorder(); // Replaced utilityUI.displayLine()
      System.out.println("| [1] Patient Management                                                                           |");
      System.out.println("| [2] Doctor Management                                                                            |");
      System.out.println("| [3] Appointment Management                                                                       |");
      System.out.println("| [4] Consultation Management                                                                      |");
      System.out.println("| [5] Medical Treatment Management                                                                 |");
      System.out.println("| [6] Pharmacy Management                                                                          |");
      System.out.println("| [7] Reporting and Analytics                                                                      |");
      System.out.println("| [0] Exit                                                                                         |");
      MessageUI.displayBorder(); // Replaced utilityUI.displayLine()
      MessageUI.displayContinueMessage(); // Replaced utilityUI.nextLine()
      System.out.print("Select Option " + Validation.displayInputOptions(INPUT_OPTIONS) + " : "); // Replaced utilityUI.displayInputOptions()
      option = scanner.nextLine();
    } while (option.isEmpty());
    return new String[] {option, new String(INPUT_OPTIONS)};  
  }
  
  public void displayGoodbyeMessage() {
    System.out.println("Goodbye!");
  }
}
