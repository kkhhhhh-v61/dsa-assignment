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
import utility.Utilities;

/**
 *
 * @author Tan Kai Hao
 */
public class ClinicManagementSystem {
  
  private static SystemUI systemUI = new SystemUI();

  private static SystemController systemController = new SystemController();
  
  public static void main(String[] args) {
    do {
      Utilities.clearScreen();
      systemUI.displayTitle();
      char[] inputOptions = systemUI.displayMainMenu();
      String userInput = systemUI.getGeneralInput();
      boolean valid = SystemValidation.validateCharInput(userInput, inputOptions);

      if (valid) {
        switch (userInput.charAt(0)) {
          case '1':
            systemController.runClinicStaffPortal();
            continue;
          case '2':
            systemController.runStudentPortal();
            continue;
          case '0':
            return;
        }
      }
    } while (true);
  }
}
