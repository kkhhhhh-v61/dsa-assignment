package control;

import adt.RedBlackTreeMap;
import boundary.DoctorUI;
import boundary.SystemUI;
import dao.PatientDAO;
import entity.Patient;
import utility.MessageUI;
import utility.Validation;

/**
 *
 * @author Tan Kai Hao
 */
public class SystemController {

  SystemUI systemUI = new SystemUI();
  
  AppointmentController appointmentController = new AppointmentController();
  PatientController patientController = new PatientController();
  DoctorUI doctorUI = new DoctorUI();

  PatientDAO patientDAO = new PatientDAO();

  RedBlackTreeMap<String, Patient> patientMap = new RedBlackTreeMap<>();
  
  public void runClinicStaffPortal() {

    boolean exit = false;
    String[] response;
    char userInput;
    char[] inputOptions;

    do {
      response = systemUI.runClinicStaffMenu();
      inputOptions = response[1].toCharArray();

      if (response[0] != "") {
        if (Validation.isSingleChar(response[0])) {
          userInput = response[0].charAt(0);

          if (Validation.isValidOption(userInput, inputOptions)) {
            switch (userInput) {
              case '1':
                patientController.runPatientManagement();
                break;
              case '2':
                doctorUI.showDoctorMenu();
                break;
              case '3':
                break;
              case '4':
                break;
              case '5':
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
  
  public void runStudentPortal() {
    
    patientMap = patientDAO.retrieveFromFile();
    
    boolean exit = false;
    String[] response;
    String[] vResponse;
    char userInput;
    char[] inputOptions;

    do {
      response = systemUI.inputPatientId();
      vResponse = AppointmentValidation.validatePatientIdExistence(patientMap, response[0], response[1].toCharArray());
    } while (!Boolean.parseBoolean(vResponse[0]));

    if (vResponse[1].equals("0")) {
      return;
    } else {
      systemUI.loginSuccess(patientMap.get(vResponse[1]));
    }

    do {
      response = systemUI.runStudentMenu();
      inputOptions = response[1].toCharArray();

      if (response[0] != "") {
        if (Validation.isSingleChar(response[0])) {
          userInput = response[0].charAt(0);

          if (Validation.isValidOption(userInput, inputOptions)) {
            switch (userInput) {
              case '1':
                appointmentController.runStudentAppointment();
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
}
