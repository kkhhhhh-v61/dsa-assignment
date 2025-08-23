package boundary;

import adt.ArrayList;
import dao.DoctorDAO;
import utility.Utilities;

/**
 *
 * @author Tan Kai Hao
 */
public class AppointmentUI {

  DoctorDAO doctorDAO = new DoctorDAO();
  
  public String[] runStudentAppointmentMenu() {

    final char[] INPUT_OPTIONS = { '1', '2', '3', '0' };

    System.out.println();

    Utilities.clearScreen();

    System.out.println("+---------------------------+");
    System.out.println("|    STUDENT APPOINTMENT    |");
    System.out.println("+---------------------------+");
    System.out.println("| [1] Appointment Booking   |");
    System.out.println("| [2] Appointment Records   |");
    System.out.println("| [3] Upcoming Appointments |");
    System.out.println("| [0] Return                |");
    System.out.println("+---------------------------+");

    System.out.print("> ");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS) };
  }

  public void runDoctorRecordListMenu(ArrayList<String[]> doctorData, int page) {
    System.out.println();
    Utilities.clearScreen();

    int itemsPerPage = 10;
    int startIndex = (page - 1) * itemsPerPage + 1;
    int endIndex = Math.min(startIndex + itemsPerPage - 1, doctorData.size() - 1);

    System.out.println("+========================================================================================+");
    System.out.printf("|                                  DOCTOR SCHEDULE LIST [PG%02d]                           |\n", page);
    System.out.println("+========================================================================================+");
    System.out.println("|  DID  |             DOCTOR NAME             |    DAY    | SCHEDULE TIME | AVAILABILITY |");
    System.out.println("+========================================================================================+");
    
    if (doctorData.size() <= 1) {
      System.out.println("|                                   No schedules found.                                  |");
    } else {
      for (int i = startIndex; i <= endIndex; i++) {
        if (i < doctorData.size()) {
          String[] data = doctorData.get(i);
          System.out.println(String.format("| %-5s | %-35s | %9s | %5s - %5s | %12s |", 
            data[0], data[1], data[2], data[3], data[4], data[5]));
        }
      }
    }
    System.out.println("+========================================================================================+");
  }

  public String[] runAppointmentBookingMenu() {
    
    final char[] INPUT_OPTIONS = { 'Q', 'E', '0' };

    System.out.println();

    System.out.println("[Q] Previous Page | [E] Next Page");
    System.out.println("[0] Exit");

    System.out.println();

    System.out.println("+-------------------------+");
    System.out.println("| ENTER DOCTOR ID TO BOOK |");
    System.out.println("+-------------------------+");

    System.out.println();

    System.out.print("> D");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS) };
  }
}
