package control;

import adt.RedBlackTreeMap;
import boundary.AppointmentUI;
import dao.PatientDAO;
import entity.Patient;
import utility.MessageUI;
import utility.Validation;

/**
 *
 * @author Tan Kai Hao
 */
public class AppointmentController {

  AppointmentUI appointmentUI = new AppointmentUI();
  
  PatientDAO patientDAO = new PatientDAO();

  RedBlackTreeMap<String, Patient> patientMap = new RedBlackTreeMap<>();
  
  public void runStudentAppointment() {

    boolean exit = false;
    String[] response;
    char userInput;
    char[] inputOptions;

    do {
      response = appointmentUI.runStudentAppointmentMenu();
      inputOptions = response[1].toCharArray();

      if (response[0] != "") {
        if (Validation.isSingleChar(response[0])) {
          userInput = response[0].charAt(0);

          if (Validation.isValidOption(userInput, inputOptions)) {
            switch (userInput) {
              case '1':
                runAppointmentBooking();
                break;
              case '2':
                break;
              case '3':
                break;
              case '0':
                exit = true;
                break;
            }
          } else {
            MessageUI.notValidOption(inputOptions);
          }
        } else {
          MessageUI.notSingleChar();
        }
      }
    } while (!exit);
  }
  
  public void runAppointmentBooking() {
    
    boolean exit = false;
    String[] response;
    String[] vResponse;
    char[] inputOptions;

    do {
      appointmentUI.runDoctorRecordListMenu();

    } while (!exit);
  }
}
