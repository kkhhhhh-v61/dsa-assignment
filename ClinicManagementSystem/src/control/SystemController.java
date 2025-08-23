package control;

import boundary.DoctorUI;
import boundary.SystemUI;
import utility.MessageUI;
import utility.Validation;

/**
 *
 * @author Tan Kai Hao
 */
public class SystemController {

  SystemUI systemUI = new SystemUI();
  
  PatientController patientController = new PatientController();
  DoctorUI doctorUI = new DoctorUI();
  
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
    
    boolean exit = false;
    String[] response;
    char userInput;
    char[] inputOptions;

    do {
      response = systemUI.runStudentMenu();
      inputOptions = response[1].toCharArray();

      if (response[0] != "") {
        if (Validation.isSingleChar(response[0])) {
          userInput = response[0].charAt(0);

          if (Validation.isValidOption(userInput, inputOptions)) {
            switch (userInput) {
              case '1':
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
