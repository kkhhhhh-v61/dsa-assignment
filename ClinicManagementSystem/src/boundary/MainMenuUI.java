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

import utility.UtilityUI;
import java.util.Scanner;

public class MainMenuUI {
  UtilityUI utilityUI = new UtilityUI();
  Scanner scanner = utilityUI.getScannerInput();

  public char displayMainMenu() {
    utilityUI.clearScreen();
    displayTitle();
    utilityUI.nextLine();
    return displayRoleSelection();
  }

  public void displayTitle() {
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
    System.out.println("|                   (1)STUDENT                   ||                    (2)STAFF                    |");
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
    if (role == '1' || role == '2' || role == '0') {
      return role;
    } else {
      return 'X';
    }
  }
  
  public void displayStudentUI() {
    System.out.println("Student UI");
    System.out.println("Press any key to continue...");
    utilityUI.flushScanner();
  }
  
  public void displayStaffUI() {
    System.out.println("Staff UI");
    System.out.println("Press any key to continue...");
    utilityUI.flushScanner();
  }
}
