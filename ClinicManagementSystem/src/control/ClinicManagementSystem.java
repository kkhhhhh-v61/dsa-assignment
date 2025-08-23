// ╔═══════════════════════════════════════════════════════════════════════════════════════════════╗
// ║                   ██╗   ██╗███╗   ██╗██╗    ██████╗ █████╗ ██████╗ ███████╗                   ║
// ║                   ██║   ██║████╗  ██║██║   ██╔════╝██╔══██╗██╔══██╗██╔════╝                   ║
// ║                   ██║   ██║██╔██╗ ██║██║   ██║     ███████║██████╔╝█████╗                     ║
// ║                   ██║   ██║██║╚██╗██║██║   ██║     ██╔══██║██╔══██╗██╔══╝                     ║
// ║                   ╚██████╔╝██║ ╚████║██║   ╚██████╗██║  ██║██║  ██║███████╗                   ║
// ║                    ╚═════╝ ╚═╝  ╚═══╝╚═╝    ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝                   ║
// ╠═══════════════════════════════════════════════════════════════════════════════════════════════╣
// ║                                    CLINIC MANAGEMENT SYSTEM                                   ║
// ╚═══════════════════════════════════════════════════════════════════════════════════════════════╝

package control;

import boundary.SystemUI;
import utility.MessageUI;
import utility.Validation;

/**
 *
 * @author Tan Kai Hao
 */
public class ClinicManagementSystem {
  
  private static SystemUI systemUI = new SystemUI();

  private static SystemController systemController = new SystemController();
  
  public static void main(String[] args) {

    boolean exit = false;
    String[] response;
    char userInput;
    char[] inputOptions;
    
    do {
      response = systemUI.runMainMenu();
      inputOptions = response[1].toCharArray();

      if (response[0] != "") {
        if (Validation.isSingleChar(response[0])) {
          userInput = response[0].charAt(0);

          if (Validation.isValidOption(userInput, inputOptions)) {
            switch (userInput) {
              case '1':
                systemController.runClinicStaffPortal();
                break;
              case '2':
                systemController.runStudentPortal();
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
