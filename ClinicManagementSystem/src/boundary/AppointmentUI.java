package boundary;

import adt.ArrayList;
import adt.ListInterface;
import dao.DoctorDAO;
import entity.Doctor;
import entity.Schedule;
import utility.Utilities;

/**
 *
 * @author Tan Kai Hao
 */
public class AppointmentUI {

  DoctorDAO doctorDAO = new DoctorDAO();

  ListInterface<Doctor> doctorList = new ArrayList<>();
  ListInterface<Schedule> scheduleList = new ArrayList<>();
  
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

  public void runDoctorRecordListMenu() {

    doctorList = doctorDAO.

    System.out.println();

    Utilities.clearScreen();

    System.out.println("+=================================================================================+");
    System.out.println("|  DID |             DOCTOR NAME             |    DAY    | SCHEDULE TIME | STATUS |");
    System.out.println("+=================================================================================+");
  }

  public void runAppointmentBookingMenu() {
    
    final char[] INPUT_OPTIONS = { '0' };

    System.out.println();

    Utilities.clearScreen();

    System.out.println("| APPOINTMENT BOOKING |");

  }
}
