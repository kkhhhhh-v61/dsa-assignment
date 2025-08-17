package control;

import boundary.MainMenuUI;
import utility.MessageUI;
import utility.Validation;

/**
 *
 * @author khtan
 */
public class MainMenuController {
  public boolean runMainMenu(char input) {
    MainMenuUI mainMenuUI = new MainMenuUI();
    PatientController patientMaintenance = new PatientController();
    MessageUI messageUI = new MessageUI();
    Validation validation = new Validation();
    String[] response;
    char[] options;
    boolean exit = false;
    
    switch (input) {
      case '1':
        do {
          response = mainMenuUI.displayStudentUI();
          input = response[0].charAt(0);
          options = response[1].toCharArray();

          if (validation.checkInputOptionsMatch(options, input)) { // check if input is in the options
            if (input == '0') {
              exit = true;
            } else {
              switch (input) {
                case '1':
                  break;
                case '2':
                  break;
                case '3':
                  break;
                case '4':
                  break;
                case '5':
                  break;
                case '6':
                  break;
                case '0':
                  exit = true;
                  break;
                default:
                  break;
              }
            } while (!exit);
          } else {
            messageUI.displayInputOptionErrorMessage(options);
          }
        } while (!exit);
        break;
      case '2':
        do {
          response = mainMenuUI.displayStaffUI();
          input = response[0].charAt(0);
          options = response[1].toCharArray();
          
          if (validation.checkInputOptionsMatch(options, input)) { // check if input is in the options
            if (input == '0') {
              exit = true;
            } else {
              switch (input) {
                case '1':
                  patientMaintenance.runPatientManagement();
                  break;
                case '2':
                  break;
                case '3':
                  break;
                case '4':
                  break;
                case '5':
                  break;
                case '6':
                  break;
                case '7':
                  break;
                case '0':
                  exit = true;
                  break;
                default:
                  break;
              }
            } while (!exit);
          } else {
            messageUI.displayInputOptionErrorMessage(options);
          }
        } while (!exit);
        break;
      case '0':
        mainMenuUI.displayGoodbyeMessage();
        return true;
      default:
        break;
      }
    return false;
  }
}
