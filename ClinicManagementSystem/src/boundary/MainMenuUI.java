// package boundary;

// public class MainMenuUI {
//   public void displayTitle() {
//     System.out.println("""
//       ╔═══════════════════════════════════════════════════════════════════════════════════════════════╗
//       ║                   ██╗   ██╗███╗   ██╗██╗    ██████╗ █████╗ ██████╗ ███████╗                   ║
//       ║                   ██║   ██║████╗  ██║██║   ██╔════╝██╔══██╗██╔══██╗██╔════╝                   ║
//       ║                   ██║   ██║██╔██╗ ██║██║   ██║     ███████║██████╔╝█████╗                     ║
//       ║                   ██║   ██║██║╚██╗██║██║   ██║     ██╔══██║██╔══██╗██╔══╝                     ║
//       ║                   ╚██████╔╝██║ ╚████║██║   ╚██████╗██║  ██║██║  ██║███████╗                   ║
//       ║                    ╚═════╝ ╚═╝  ╚═══╝╚═╝    ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝                   ║
//       ╠═══════════════════════════════════════════════════════════════════════════════════════════════╣
//       ║                                    CLINIC MANAGEMENT SYSTEM                                   ║
//       ╚═══════════════════════════════════════════════════════════════════════════════════════════════╝
//       """);
//   }

//   public void displayRoleSelection() {
//     System.out.println("""
//       ╔═══════════════════════════════════════════════════════════════════════════════════════════════╗
//       ║                                         SELECT A ROLE                                         ║
//       ╠═══════════════════════════════════════════════╦═══════════════════════════════════════════════╣
//       ║               STUDENT PORTAL                  ║                STAFF PORTAL                   ║
//       ╠═══════════════════════════════════════════════╬═══════════════════════════════════════════════╣
//       ║ + Appointment Registration                    ║ + Patient and Doctor Management               ║
//       ║ + View Medical History                        ║ + Manage Appointments Schedules               ║
//       ║ + View Prescriptions                          ║ + Consultation and Treatment Management       ║
//       ║ + Payment and Bills                           ║ + Pharmacy Management                         ║
//       ║ + Notifications and Reminders                 ║ + Reporting and Analytics                     ║
//       ╚═══════════════════════════════════════════════╩═══════════════════════════════════════════════╝

//                                   Enter 'S' for Student or 'T' for Staff: 
//   """);
//   }
// }

package boundary;

import utility.MessageUI;
import utility.UtilityUI;
import java.util.Scanner;

public class MainMenuUI {
  MessageUI messageUI = new MessageUI();
  UtilityUI utilityUI = new UtilityUI();
  Scanner scanner = utilityUI.getScannerInput();

  public char displayMainMenu() {
    displayTitle();
    utilityUI.nextLine();
    return displayRoleSelection();
  }

  public void displayTitle() {
    utilityUI.clearScreen();
    utilityUI.displayLine();
    System.out.println("|                 ##   ##  ##   ##  #######     #######   #####   ######   #######                 |");
    System.out.println("|                 ##   ##  ###  ##    ###       ##       ##   ##  ##   ##  ##                      |");
    System.out.println("|                 ##   ##  #### ##    ###       ##       ##   ##  ##   ##  ##                      |");
    System.out.println("|                 ##   ##  ## ####    ###       ##       #######  #######  #####                   |");
    System.out.println("|                 ##   ##  ##  ###    ###       ##       ##   ##  ##  ##   ##                      |");
    System.out.println("|                 ##   ##  ##  ###    ###       ##       ##   ##  ##   ##  ##                      |");
    System.out.println("|                 #######  ##   ##  #######     #######  ##   ##  ##   ##  #######                 |");
    utilityUI.displayLine();
    System.out.println("|                                     CLINIC MANAGEMENT SYSTEM                                     |");
    utilityUI.displayLine();
  }
  
  public char displayRoleSelection() {
    final char[] INPUT_OPTIONS = { '1', '2', '0' };

    utilityUI.displayLine();
    System.out.println("|                                         SELECT YOUR ROLE                                         |");
    utilityUI.displayLine();
    System.out.println("|                   [1]STUDENT                   ||                    [2]STAFF                    |");
    System.out.println("+================================================++================================================+");
    System.out.println("| + Appointment Registration                     || + Patient and Doctor Management                |");
    System.out.println("| + View Medical History                         || + Manage Appointments Schedules                |");
    System.out.println("| + View Prescriptions                           || + Consultation and Treatment Management        |");
    System.out.println("| + Payment and Bills                            || + Pharmacy Management                          |");
    System.out.println("| + Notifications and Reminders                  || + Reporting and Analytics                      |");
    utilityUI.displayLine();
    System.out.println("| 0 - Exit                                                                                         |");
    utilityUI.displayLine();
    utilityUI.nextLine();

    System.out.print("Select Role " + utilityUI.displayInputOptions(INPUT_OPTIONS) + " : ");
    char role = scanner.next().charAt(0);
    utilityUI.flushScanner();
    return utilityUI.checkInputOptionsError(INPUT_OPTIONS, role);
  }
  
  public char displayStudentUI() {
    final char[] INPUT_OPTIONS = { '1', '2', '3', '4', '5', '6', '0' };

    displayTitle();
    utilityUI.nextLine();
    utilityUI.displayLine();
    System.out.println("|                                          STUDENT PORTAL                                          |");
    utilityUI.displayLine();
    System.out.println("| [1] Make an Appointment                                                                          |");
    System.out.println("| [2] View Appointments                                                                            |");
    System.out.println("| [3] View Medical History                                                                         |");
    System.out.println("| [4] View Prescriptions                                                                           |");
    System.out.println("| [5] Payment and Bills                                                                            |");
    System.out.println("| [6] Notifications and Reminders                                                                  |");
    System.out.println("| [0] Exit                                                                                         |");
    utilityUI.displayLine();
    utilityUI.nextLine();

    System.out.print("Select Option " + utilityUI.displayInputOptions(INPUT_OPTIONS) + " : ");
    char option = scanner.next().charAt(0);
    utilityUI.flushScanner();
    return utilityUI.checkInputOptionsError(INPUT_OPTIONS, option);
  }
  
  public char displayStaffUI() {
    final char[] INPUT_OPTIONS = { '1', '2', '3', '4', '5', '6', '7', '0' };
    
    displayTitle();
    utilityUI.nextLine();
    utilityUI.displayLine();
    System.out.println("|                                           STAFF PORTAL                                           |");
    utilityUI.displayLine();
    System.out.println("| [1] Patient and Doctor Management                                                                |");
    System.out.println("| [2] Patient Queuing Management                                                                   |");
    System.out.println("| [3] Appointment Management                                                                       |");
    System.out.println("| [4] Consultation Management                                                                      |");
    System.out.println("| [5] Treatment Management                                                                         |");
    System.out.println("| [6] Pharmacy Management                                                                          |");
    System.out.println("| [7] Reporting and Analytics                                                                      |");
    System.out.println("| [0] Exit                                                                                         |");
    utilityUI.displayLine();
    utilityUI.nextLine();

    System.out.print("Select Option " + utilityUI.displayInputOptions(INPUT_OPTIONS) + " : ");
    char option = scanner.next().charAt(0);
    utilityUI.flushScanner();
    return utilityUI.checkInputOptionsError(INPUT_OPTIONS, option);
  }
}
