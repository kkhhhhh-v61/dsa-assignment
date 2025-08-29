package boundary;

import adt.ArrayList;
import adt.ListInterface;
import adt.RedBlackTreeMap;
import entity.Appointment;
import utility.Utilities;

/**
 *
 * @author Tan Kai Hao
 */
public class AppointmentUI {
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

  public char[] displayAppointmentManagement() {
    System.out.println();
    System.out.println("+--------------------------------+");
    System.out.println("|     APPOINTMENT MANAGEMENT     |");
    System.out.println("+--------------------------------+");
    System.out.println("| [1] View Appointment List      |");
    System.out.println("| [2] Update Appointment Status  |");
    System.out.println("| [3] Edit Appointment           |");
    System.out.println("| [4] Delete Appointment         |");
    System.out.println("| [5] Search Appointment         |");
    System.out.println("| [6] Clear Appointment List     |");
    System.out.println("| [0] Return                     |");
    System.out.println("+--------------------------------+");

    return new char[] { '1', '2', '3', '4', '5', '6', '0' };
  }

  public void displayAppointmentListDetails(RedBlackTreeMap<String, Appointment> appointmentMap, int page) {
    int startIndex = (page - 1) * 10;
    int endIndex = Math.min(startIndex + 10, appointmentMap.size());
    ListInterface<String> appointmentKeys = appointmentMap.getKeys();
    for (int i = startIndex + 1; i <= endIndex; i++) {
      String key = appointmentKeys.get(i);
      System.out.println(String.format("| %5s | %5s | %5s | %10s | %6s | %-30s | %10s |", 
        appointmentMap.get(key).getAppointmentId(), appointmentMap.get(key).getPatientId(), appointmentMap.get(key).getDoctorId(), 
        appointmentMap.get(key).getAppointmentDate(), appointmentMap.get(key).getAppointmentTime(), appointmentMap.get(key).getReason(), 
        appointmentMap.get(key).getStatus()));
    }
  }

  public char[] displayAppointmentList(RedBlackTreeMap<String, Appointment> appointmentMap, int page) {
    System.out.println();
    System.out.println("+===========================================================================================+");
    System.out.printf("|                                      APPOINTMENT LIST                           [Page %02d] |\n", page);
    System.out.println("+===========================================================================================+");
    System.out.println("|  AID  |  PID  |  DID  |    DATE    |  TIME  |             REASON             |   STATUS   |");
    System.out.println("+===========================================================================================+");
    if (appointmentMap.isEmpty() || page > (appointmentMap.size() + 9) / 10) {
      System.out.println("|                               No Appointment records found.                               |");
    } else {
      displayAppointmentListDetails(appointmentMap, page);
    }
    System.out.println("+===========================================================================================+");
    System.out.println("| NAVIGATION | [Q] Previous Page | [E] Next Page |                             | [0] Return |");
    System.out.println("+===========================================================================================+");

    return new char[] { 'Q', 'E', '0' };
  }

  public void displaySelectAnOptionHeader() {
    System.out.println();
    System.out.println("+------------------+");
    System.out.println("| SELECT AN OPTION |");
    System.out.println("+------------------+");
  }

  public char[] displayStaffAppointmentListMenu() {
    displaySelectAnOptionHeader();
    displayOptionsInfo(new String[] { "[1] Edit", "[2] Delete", "[0] Cancel" });
    return new char[] { '1', '2', '0' };
  }

  public void displaySingleAppointment(Appointment appointment) {
    System.out.println();
    System.out.println("+===========================================================================================+");
    System.out.println("|                                      APPOINTMENT LIST                                     |");
    System.out.println("+===========================================================================================+");
    System.out.println("|  AID  |  PID  |  DID  |    DATE    |  TIME  |             REASON             |   STATUS   |");
    System.out.println("+===========================================================================================+");
    if (appointment == null) {
      System.out.println("|                               No Appointment records found.                               |");
    } else {
      System.out.println(String.format("| %5s | %5s | %5s | %10s | %6s | %-30s | %10s |", 
        appointment.getAppointmentId(), appointment.getPatientId(), appointment.getDoctorId(), 
        appointment.getAppointmentDate(), appointment.getAppointmentTime(), appointment.getReason(), 
        appointment.getStatus()));
    }
    System.out.println("+===========================================================================================+");
  }

  public char[] displayAppointmentEditMenu() {
    System.out.println();
    System.out.println("+------------------+");
    System.out.println("|   EDIT OPTIONS   |");
    System.out.println("+------------------+");

    displayOptionsInfo(new String[] { "[1] Doctor ID", "[2] Appointment Date", "[3] Appointment Time", "[4] Status", "[0] Cancel" });
    
    return new char[] { '1', '2', '3', '4', '0' };
  }

  public void displayDoctorScheduleListDetails(ArrayList<String[]> doctorSchedule, int page) {
    int startIndex = (page - 1) * 10;
    int endIndex = Math.min(startIndex + 10, doctorSchedule.size());
    for (int i = startIndex + 1; i <= endIndex; i++) {
      String[] data = doctorSchedule.get(i);
      System.out.println(String.format("| %-5s | %-35s | %9s | %5s - %5s | %12s |", 
        data[0], data[1], data[2], data[3], data[4], data[5]));
    }
  }

  public char[] displayDoctorScheduleList(ArrayList<String[]> doctorSchedule, int page) {
    System.out.println();
    System.out.println("+========================================================================================+");
    System.out.printf("|                                  DOCTOR SCHEDULE LIST                        [Page %02d] |\n", page);
    System.out.println("+========================================================================================+");
    System.out.println("|  DID  |             DOCTOR NAME             |    DAY    | SCHEDULE TIME | AVAILABILITY |");
    System.out.println("+========================================================================================+");
    if (doctorSchedule.isEmpty() || page > (doctorSchedule.size() + 9) / 10) {
      System.out.println("|                               No Doctor Schedules found.                               |");
    } else {
      displayDoctorScheduleListDetails(doctorSchedule, page);
    }
    System.out.println("+========================================================================================+");
    System.out.println("| NAVIGATION | [Q] Previous Page | [E] Next Page |                          | [0] Return |");
    System.out.println("+========================================================================================+");

    return new char[] { 'Q', 'E', '0' };
  }

  public void displaySingleDoctorSchedule(String[] doctorSchedule) {
    System.out.println();
    System.out.println("+========================================================================================+");
    System.out.println("|                                     DOCTOR SCHEDULE                                    |");
    System.out.println("+========================================================================================+");
    System.out.println("|  DID  |             DOCTOR NAME             |    DAY    | SCHEDULE TIME | AVAILABILITY |");
    System.out.println("+========================================================================================+");
    System.out.println(String.format("| %-5s | %-35s | %9s | %5s - %5s | %12s |", 
      doctorSchedule[0], doctorSchedule[1], doctorSchedule[2], doctorSchedule[3], doctorSchedule[4], doctorSchedule[5]));
    System.out.println("+========================================================================================+");
  }

  public void displayCurrentValue(String field, String currentValue) {
    System.out.println();
    System.out.println("Current " + field + ": " + currentValue);
  }

  public void displayNewValue(String field, String newValue) {
    System.out.println();
    System.out.println("New " + field + ": " + newValue);
  }

  public char[] displayAppointmentIdInput() {
    System.out.println();
    System.out.println("+------------------+");
    System.out.println("|  APPOINTMENT ID  |");
    System.out.println("+------------------+");

    displayFormatInfo("AXXXX");
    displayOptionsInfo(new String[] { "[0] Cancel" });

    return new char[] { '0' };
  }

  public char[] displayPatientIdInput() {
    System.out.println();
    System.out.println("+------------------+");
    System.out.println("|    PATIENT ID    |");
    System.out.println("+------------------+");

    displayFormatInfo("PXXXX");
    displayOptionsInfo(new String[] { "[0] Cancel" });

    return new char[] { '0' };
  }

  public char[] displayDoctorIdInput() {
    System.out.println();
    System.out.println("+-------------------+");
    System.out.println("|     DOCTOR ID     |");
    System.out.println("+-------------------+");

    displayFormatInfo("DXXXX");
    displayOptionsInfo(new String[] { "[0] Cancel" });

    return new char[] { '0' };
  }

  public char[] displayDateInput() {
    System.out.println();
    System.out.println("+------------------+");
    System.out.println("| APPOINTMENT DATE |");
    System.out.println("+------------------+");

    displayFormatInfo("YYYY-MM-DD");
    displayOptionsInfo(new String[] { "[0] Cancel" });

    return new char[] { '0' };
  }

  public char[] displayTimeInput() {
    System.out.println();
    System.out.println("+------------------+");
    System.out.println("| APPOINTMENT TIME |");
    System.out.println("+------------------+");

    displayFormatInfo("HH:MM");
    displayOptionsInfo(new String[] { "[0] Cancel" });

    return new char[] { '0' };
  }

  public char[] displayReasonInput() {
    System.out.println();
    System.out.println("+--------------------+");
    System.out.println("| APPOINTMENT REASON |");
    System.out.println("+--------------------+");

    displayOptionsInfo(new String[] { "[0] Cancel" });

    return new char[] { '0' };
  }

  public char[] displayStatusInput() {
    System.out.println();
    System.out.println("+--------------------+");
    System.out.println("| APPOINTMENT STATUS |");
    System.out.println("+--------------------+");

    displayOptionsInfo(new String[] { "[1] Approved", "[2] Rejected", "[3] Pending", "[4] Cancelled", "[0] Cancel" });

    return new char[] { '1', '2', '3', '4', '0' };
  }

  public char[] displayEditConfirmationMenu(String field, String currentValue, String newValue) {
    System.out.println();
    System.out.println("+-------------------+");
    System.out.println("| EDIT CONFIRMATION |");
    System.out.println("+-------------------+");

    displayCurrentValue(field, currentValue);
    displayNewValue(field, newValue);
    displayOptionsInfo(new String[] { "[1] Confirm", "[0] Cancel" });

    return new char[] { '1', '0' };
  }

  public char[] displayDeleteConfirmationMenu() {
    System.out.println();
    System.out.println("+---------------------+");
    System.out.println("| DELETE CONFIRMATION |");
    System.out.println("+---------------------+");

    displayOptionsInfo(new String[] { "[1] Confirm", "[0] Cancel" });

    return new char[] { '1', '0' };
  }

  public char[] displaySearchMenu() {
    System.out.println();
    System.out.println("+------------------+");
    System.out.println("|  SEARCH OPTIONS  |");
    System.out.println("+------------------+");

    displayOptionsInfo(new String[] { "[1] Appointment ID", "[2] Patient ID", "[3] Doctor ID", "[4] Date", "[5] Status", "[0] Cancel" });
    
    return new char[] { '1', '2', '3', '4', '5', '0' };
  }

  public char[] displayRecordClearanceMenu() {
    System.out.println();
    System.out.println("+------------------+");
    System.out.println("| RECORD CLEARANCE |");
    System.out.println("+------------------+");

    displayOptionsInfo(new String[] { "[1] Confirm", "[0] Cancel" });

    return new char[] { '1', '0' };
  }

  public char[] displayInitializationSizeInput() {
    System.out.println();
    System.out.println("+-------------------+");
    System.out.println("|    RECORD SIZE    |");
    System.out.println("+-------------------+");

    displayOptionsInfo(new String[] { "[0] Cancel" });

    return new char[] { '0' };
  }
  
  public char[] displayInitializationConfirmationMenu() {
    System.out.println();
    System.out.println("+-----------------------------+");
    System.out.println("| INITIALIZATION CONFIRMATION |");
    System.out.println("+-----------------------------+");

    displayOptionsInfo(new String[] { "[1] Confirm", "[0] Cancel" });

    return new char[] { '1', '0' };
  }

  public char[] displayStudentAppointment() {
    System.out.println();
    System.out.println("+---------------------------+");
    System.out.println("|    STUDENT APPOINTMENT    |");
    System.out.println("+---------------------------+");
    System.out.println("| [1] View Appointment List |");
    System.out.println("| [2] Book Appointment      |");
    System.out.println("| [3] Cancel Appointment    |");
    System.out.println("| [0] Return                |");
    System.out.println("+---------------------------+");

    return new char[] { '1', '2', '3', '0' };
  }

  public void displayAppointmentConfirmation(Appointment appointment) {
    System.out.println();
    System.out.println("+----------------------------------------------------+");
    System.out.println("|              APPOINTMENT CONFIRMATION              |");
    System.out.println("+----------------------------------------------------+");
    System.out.printf("| %-17s : %-30s |\n", "Appointment ID", appointment.getAppointmentId());
    System.out.printf("| %-17s : %-30s |\n", "Patient ID", appointment.getPatientId());
    System.out.printf("| %-17s : %-30s |\n", "Doctor ID", appointment.getDoctorId());
    System.out.printf("| %-17s : %-30s |\n", "Appointment Date", appointment.getAppointmentDate());
    System.out.printf("| %-17s : %-30s |\n", "Appointment Time", appointment.getAppointmentTime());
    System.out.printf("| %-17s : %-30s |\n", "Reason", appointment.getReason());
    System.out.println("+----------------------------------------------------+");
  }

  public char[] displayAppointmentConfirmationMenu() {
    displaySelectAnOptionHeader();
    displayOptionsInfo(new String[] { "[1] Confirm", "[0] Cancel" });
    return new char[] { '1', '0' };
  }

  public char[] displayStudentAppointmentListMenu() {
    displaySelectAnOptionHeader();
    displayOptionsInfo(new String[] { "[1] Appointment Cancellation", "[0] Cancel" });
    return new char[] { '1', '0' };
  }

  public char[] displayAppointmentCancellationMenu() {
    System.out.println();
    System.out.println("+--------------------------+");
    System.out.println("| APPOINTMENT CANCELLATION |");
    System.out.println("+--------------------------+");
    displayOptionsInfo(new String[] { "[1] Confirm", "[0] Cancel" });
    return new char[] { '1', '0' };
  }
}
