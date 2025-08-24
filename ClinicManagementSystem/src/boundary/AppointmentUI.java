package boundary;

import adt.ArrayList;
import dao.DoctorDAO;
import entity.Appointment;
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

    System.out.println();

    System.out.print("> ");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS) };
  }

  public void runDoctorRecordListMenu(ArrayList<String[]> doctorData, String doctorId, String appointmentDay, int page) {

    System.out.println();

    Utilities.clearScreen();

    int itemsPerPage = 10;
    int startIndex = (page - 1) * itemsPerPage + 1;
    int endIndex = Math.min(startIndex + itemsPerPage - 1, doctorData.size());
    ArrayList<String[]> filteredDoctorData = new ArrayList<>();

    if (!doctorData.isEmpty()) {
      if (!doctorId.isEmpty() && !appointmentDay.isEmpty()) { // for display of time for the selected doctor and date
        for (int i = startIndex; i <= endIndex; i++) {
          if (i <= doctorData.size()) {
            String[] data = doctorData.get(i);
            if (data[0].equalsIgnoreCase(doctorId) && data[2].equalsIgnoreCase(appointmentDay)) {
              filteredDoctorData.add(data);
            }
          }
        }
      } else if (doctorId.isEmpty() && !appointmentDay.isEmpty()) { // for display of all doctors available on the selected date
        for (int i = startIndex; i <= endIndex; i++) {
          if (i <= doctorData.size()) {
            String[] data = doctorData.get(i);
            if (data[2].equalsIgnoreCase(appointmentDay)) {
              filteredDoctorData.add(data);
            }
          }
        }
      }
    }

    System.out.println("+========================================================================================+");
    System.out.printf("|                                  DOCTOR SCHEDULE LIST [PG%02d]                           |\n", page);
    System.out.println("+========================================================================================+");
    System.out.println("|  DID  |             DOCTOR NAME             |    DAY    | SCHEDULE TIME | AVAILABILITY |");
    System.out.println("+========================================================================================+");
    
    if (filteredDoctorData.isEmpty() || appointmentDay.isEmpty()) {
      System.out.println("|                                   No schedules found.                                  |");
    } else {
      for (int i = startIndex; i <= endIndex; i++) {
        if (i <= filteredDoctorData.size()) {
          String[] data = filteredDoctorData.get(i);
          System.out.println(String.format("| %-5s | %-35s | %9s | %5s - %5s | %12s |", 
            data[0], data[1], data[2], data[3], data[4], data[5]));
        }
      }
    }
    System.out.println("+========================================================================================+");
  }
  
  public String[] inputDoctorId() {

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
  
  public String[] inputAppointmentDate() {

    System.out.println();

    Utilities.clearScreen();
    
    final char[] INPUT_OPTIONS = { '0' };

    System.out.println();

    System.out.println("+------------------------+");
    System.out.println("| ENTER APPOINTMENT DATE |");
    System.out.println("+------------------------+");

    System.out.println();

    System.out.println("[FORMAT] YYYY-MM-DD");
    System.out.println("[0] Exit");

    System.out.println();

    System.out.print("> ");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS) };
  }

  public String[] inputAppointmentTime() {

    final char[] INPUT_OPTIONS = { '0' };
    final int MAX_LENGTH = 4;

    System.out.println();

    System.out.println("+------------------------+");
    System.out.println("| ENTER APPOINTMENT TIME |");
    System.out.println("+------------------------+");

    System.out.println();

    System.out.println("[FORMAT] HH:MM");
    System.out.println("[0] Exit");

    System.out.println();

    System.out.print("> ");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS), String.valueOf(MAX_LENGTH) };
  }
  
  public String[] inputReason() {

    System.out.println();

    Utilities.clearScreen();

    final char[] INPUT_OPTIONS = { '0' };
    final int MAX_LENGTH = 30;

    System.out.println();

    System.out.println("+--------------------------+");
    System.out.println("| ENTER APPOINTMENT REASON |");
    System.out.println("+--------------------------+");

    System.out.println();

    System.out.println("[0] Exit");

    System.out.println();

    System.out.print("> ");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS), String.valueOf(MAX_LENGTH)};
  }
  
  public String[] confirmAppointment(Appointment newAppointment) {

    System.out.println();

    Utilities.clearScreen();

    final char[] INPUT_OPTIONS = { '1', '2', '0' };

    System.out.println();

    System.out.println("+----------------------------------------------------+");
    System.out.println("|              APPOINTMENT CONFIRMATION              |");
    System.out.println("+----------------------------------------------------+");

    System.out.printf("| %-17s : %-30s |\n", "Patient ID", newAppointment.getPatientId());
    System.out.printf("| %-17s : %-30s |\n", "Doctor ID", newAppointment.getDoctorId());
    System.out.printf("| %-17s : %-30s |\n", "Appointment Date", newAppointment.getAppointmentDate());
    System.out.printf("| %-17s : %-30s |\n", "Appointment Time", newAppointment.getAppointmentTime());
    System.out.printf("| %-17s : %-30s |\n", "Reason", newAppointment.getReason());

    System.out.println("+---------------------------------------------------+");

    System.out.println();

    System.out.println("[1] Confirm | [0] Cancel");

    System.out.println();

    System.out.print("> ");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS) };
  }
}
