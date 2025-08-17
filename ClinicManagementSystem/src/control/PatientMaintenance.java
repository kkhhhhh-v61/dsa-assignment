package control;

import boundary.PatientMaintenanceUI;
import utility.MessageUI;
import utility.UtilityUI;

public class PatientMaintenance {
  private static PatientMaintenanceUI patientMaintenanceUI = new PatientMaintenanceUI();
  private static MessageUI messageUI = new MessageUI();
  private static UtilityUI utilityUI = new UtilityUI();

  public void runPatientMaintenance() {
    String[] response;
    char input;
    char[] options;
    boolean exit = false;
    do {
      response = patientMaintenanceUI.displayPatientManagementUI();
      input = response[0].charAt(0);
      options = response[1].toCharArray();
      
      if (utilityUI.checkInputOptionsMatch(options, input)) {
        System.out.println(input);
        System.out.println(options);

        switch (input) {
          case '1':
            break;
          case '2':
            break;
          case '3':
            break;
          case '0':
            exit = true;
            break;
          default:
            break;
        }
      } else {
        messageUI.displayInputOptionErrorMessage(options);
      }
    } while (!exit);
  }
}
