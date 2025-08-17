package control;

import boundary.MainMenuUI;
import utility.MessageUI;
import utility.UtilityUI;

public class MainMenu {
  public boolean runMainMenu(char input) {
    MainMenuUI mainMenuUI = new MainMenuUI();
    PatientMaintenance patientMaintenance = new PatientMaintenance();
    MessageUI messageUI = new MessageUI();
    UtilityUI utilityUI = new UtilityUI();
    String[] response;
    char[] options;
    boolean exit = false;
    
    switch (input) {
      case '1':
        do {
          response = mainMenuUI.displayStudentUI();
          input = response[0].charAt(0);
          options = response[1].toCharArray();

          if (utilityUI.checkInputOptionsMatch(options, input)) {
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
          
          if (utilityUI.checkInputOptionsMatch(options, input)) {
            if (input == '0') {
              exit = true;
            } else {
              switch (input) {
                case '1':
                  patientMaintenance.runPatientMaintenance();
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
