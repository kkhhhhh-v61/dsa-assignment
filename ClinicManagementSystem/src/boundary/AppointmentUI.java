package boundary;

import adt.ArrayList;
import adt.ListInterface;
import adt.RedBlackTreeMap;
import dao.DoctorDAO;
import entity.Appointment;
import utility.Utilities;

/**
 *
 * @author Tan Kai Hao
 */
public class AppointmentUI {

  DoctorDAO doctorDAO = new DoctorDAO();

  public String[] runAppointmentMenu() {

    final char[] INPUT_OPTIONS = { '1', '2', '3', '4', '0' };

    System.out.println();

    Utilities.clearScreen();

    System.out.println("+--------------------------------+");
    System.out.println("|        APPOINTMENT MENU        |");
    System.out.println("+--------------------------------+");
    System.out.println("| [1] Appointment List           |");
    System.out.println("| [2] Appointment Search         |");
    System.out.println("| [3] Appointment Clearance      |");
    System.out.println("| [4] Appointment Initialization |");
    System.out.println("| [0] Return                     |");
    System.out.println("+--------------------------------+");

    System.out.println();

    System.out.print("> ");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS) };
  }

  public void runAppointmentListMenu(RedBlackTreeMap<String, Appointment> appointmentMap, String patientId,
      int page) {

    System.out.println();

    Utilities.clearScreen();

    System.out.println("+===========================================================================================+");
    System.out.printf("|                                      APPOINTMENT LIST                           [Page %02d] |\n", page);
    System.out.println("+===========================================================================================+");
    System.out.println("|  AID  |  PID  |  DID  |    DATE    |  TIME  |             REASON             |   STATUS   |");
    System.out.println("+===========================================================================================+");

    if (appointmentMap.isEmpty()) {
      System.out
          .println("|                           No Appointments found in the system.                           |");
    } else {
      if (patientId.isEmpty()) {
        int startIndex = (page - 1) * 10 + 1;
        int endIndex = Math.min(startIndex + 9, appointmentMap.size());
        ListInterface<String> keys = appointmentMap.getKeys();
        for (int i = startIndex; i <= endIndex; i++) {
          System.out.println(String.format("| %-5s | %-5s | %-5s | %10s | %6s | %-30s | %10s |",
              keys.get(i), appointmentMap.get(keys.get(i)).getPatientId(),
              appointmentMap.get(keys.get(i)).getDoctorId(),
              appointmentMap.get(keys.get(i)).getAppointmentDate(),
              appointmentMap.get(keys.get(i)).getAppointmentTime(),
              appointmentMap.get(keys.get(i)).getReason(),
              appointmentMap.get(keys.get(i)).getStatus()));
        }
      } else {
        RedBlackTreeMap<String, Appointment> filteredAppointmentMap = new RedBlackTreeMap<>();
        ListInterface<String> keys = appointmentMap.getKeys();
        for (int i = 1; i <= keys.size(); i++) {
          if (appointmentMap.get(keys.get(i)).getPatientId().equals(patientId)) {
            filteredAppointmentMap.put(keys.get(i), appointmentMap.get(keys.get(i)));
          }
        }
        int startIndex = (page - 1) * 10 + 1;
        int endIndex = Math.min(startIndex + 9, filteredAppointmentMap.size());
        ListInterface<String> filteredKeys = filteredAppointmentMap.getKeys();
        for (int i = startIndex; i <= endIndex; i++) {
          System.out.println(String.format("| %-5s | %-5s | %-5s | %10s | %6s | %-30s | %10s |",
              filteredKeys.get(i), filteredAppointmentMap.get(filteredKeys.get(i)).getPatientId(),
              filteredAppointmentMap.get(filteredKeys.get(i)).getDoctorId(),
              filteredAppointmentMap.get(filteredKeys.get(i)).getAppointmentDate(),
              filteredAppointmentMap.get(filteredKeys.get(i)).getAppointmentTime(),
              filteredAppointmentMap.get(filteredKeys.get(i)).getReason(),
              filteredAppointmentMap.get(filteredKeys.get(i)).getStatus()));
        }
      }
    }

    System.out.println("+===========================================================================================+");
  }

  public String[] inputAppointmentListMenu() {

    final char[] INPUT_OPTIONS = { 'Q', 'E', '1', '2', '0' };

    System.out.println();

    System.out.println("[Q] Previous Page | [E] Next Page");
    System.out.println("[1] Edit | [2] Delete | [0] Exit");

    System.out.println();

    System.out.print("> ");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS) };
  }
  
  public String[] inputAppointmentSearch() {

    final char[] INPUT_OPTIONS = { 'Q', 'E', '0' };

    System.out.println();

    System.out.println("[Q] Previous Page | [E] Next Page");
    System.out.println("[0] Exit");

    System.out.println();

    System.out.print("> A");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS) };
  }
  
  public String[] runAppointmentEditMenu(Appointment appointment) {

    final char[] INPUT_OPTIONS = { '1', '2', '3', '4', '0' };

    System.out.println();

    Utilities.clearScreen();

    System.out.println("+===========================================================================================+");
    System.out.println("|                                      APPOINTMENT LIST                                     |");
    System.out.println("+===========================================================================================+");
    System.out.println("|  AID  |  PID  |  DID  |    DATE    |  TIME  |             REASON             |   STATUS   |");
    System.out.println("+===========================================================================================+");

    System.out.println(String.format("| %-5s | %-5s | %-5s | %10s | %6s | %-30s | %10s |",
        appointment.getAppointmentId(), appointment.getPatientId(), appointment.getDoctorId(),
        appointment.getAppointmentDate(), appointment.getAppointmentTime(), appointment.getReason(),
        appointment.getStatus()));

    System.out.println("+===========================================================================================+");

    System.out.println();

    System.out.println("+----------------------+");
    System.out.println("| SELECT FIELD TO EDIT |");
    System.out.println("+----------------------+");

    System.out.println();

    System.out.println("[1] Doctor ID | [2] Appointment Date | [3] Appointment Time | [4] Status");
    System.out.println("[0] Return");

    System.out.println();

    System.out.print("> ");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS) };
  }
  
  public String[] editDoctorId(ArrayList<String[]> doctorData, String doctorId, int page) {

    final char[] INPUT_OPTIONS = { 'Q', 'E', '0' };

    System.out.println();

    Utilities.clearScreen();

    System.out.println("+========================================================================================+");
    System.out.printf("|                                  DOCTOR SCHEDULE LIST                          [Page %02d] |\n", page);
    System.out.println("+========================================================================================+");
    System.out.println("|  DID  |             DOCTOR NAME             |    DAY    | SCHEDULE TIME | AVAILABILITY |");
    System.out.println("+========================================================================================+");

    if (doctorData.isEmpty()) {
      System.out.println("|                        No doctor schedules found in the system.                        |");
    } else {
      int startIndex = (page - 1) * 10 + 1;
      int endIndex = Math.min(startIndex + 9, doctorData.size());
      for (int i = startIndex; i <= endIndex; i++) {
        if (i <= doctorData.size()) {
          String[] data = doctorData.get(i);
          System.out.println(String.format("| %-5s | %-35s | %9s | %5s - %5s | %12s |",
              data[0], data[1], data[2], data[3], data[4], data[5]));
        }
      }
    }
    System.out.println("+========================================================================================+");

    System.out.println();

    System.out.println("[Q] Previous Page | [E] Next Page");
    System.out.println("[0] Exit");

    System.out.println();

    System.out.println("Current Doctor ID: " + doctorId);
    System.out.print("> D");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS) };
  }
  
  public String[] editAppointmentDate(String appointmentDate) {

    final char[] INPUT_OPTIONS = { '0' };

    System.out.println();

    Utilities.clearScreen();

    System.out.println("+------------------+");
    System.out.println("| APPOINTMENT DATE |");
    System.out.println("+------------------+");

    System.out.println();

    System.out.println("[FORMAT] YYYY-MM-DD");
    System.out.println("[0] Exit");

    System.out.println();

    System.out.println("Current Appointment Date: " + appointmentDate);
    System.out.print("> ");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS) };
  }

  public String[] editAppointmentTime(String appointmentTime) {

    final char[] INPUT_OPTIONS = { '0' };
    final int MAX_LENGTH = 4;

    System.out.println();

    Utilities.clearScreen();

    System.out.println("+------------------+");
    System.out.println("| APPOINTMENT TIME |");
    System.out.println("+------------------+");

    System.out.println();

    System.out.println("[FORMAT] HH:MM");
    System.out.println("[0] Exit");

    System.out.println();

    System.out.println("Current Appointment Time: " + appointmentTime);
    System.out.print("> ");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS), String.valueOf(MAX_LENGTH) };
  }
  
  public String[] editStatus(String status) {

    final char[] INPUT_OPTIONS = { '1', '2', '3', '0' };

    System.out.println();

    Utilities.clearScreen();

    System.out.println("+------------------+");
    System.out.println("| APPOINTMENT TIME |");
    System.out.println("+------------------+");

    System.out.println();

    System.out.println("[1] Approved | [2] Rejected | [3] Cancelled");
    System.out.println("[0] Exit");

    System.out.println();

    System.out.println("Current Status: " + status);
    System.out.print("> ");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS) };
  }
  
  public String[] confirmEdit(String field, String currentValue, String newValue) {

    final char[] INPUT_OPTIONS = { '1', '0' };

    System.out.println();

    Utilities.clearScreen();

    System.out.println("+----------------------+");
    System.out.println("|   Edit Confimation   |");
    System.out.println("+----------------------+");

    System.out.println();

    System.out.println(field + ": " + currentValue + " -> " + newValue);
    System.out.println("[1] Confirm | [0] Cancel");

    System.out.println();

    System.out.print("> ");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS) };
    
  }
  
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
    System.out.printf("|                                  DOCTOR SCHEDULE LIST                          [Page %02d] |\n", page);
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
