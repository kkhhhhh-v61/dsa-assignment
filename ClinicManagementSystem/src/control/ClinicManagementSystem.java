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

import boundary.MainMenuUI;
import utility.MessageUI;
import utility.Validation;

/**
 *
 * @author khtan
 */
public class ClinicManagementSystem {
<<<<<<< Updated upstream
  private final static MainMenuUI mainMenuUI = new MainMenuUI();
  private final static MainMenuController mainMenu = new MainMenuController();
  // private final static MessageUI messageUI = new MessageUI(); // Now static
  private final static Validation validation = new Validation(); // Validation methods are not static, so keep instance
=======
  
  private static SystemController systemController = new SystemController();
  private static SystemUI systemUI = new SystemUI(systemController);
>>>>>>> Stashed changes
  
  public static void main(String[] args) {
    String[] response;
    char input;
    char[] options;
    boolean exit = false;

    do {
      response = mainMenuUI.displayMainMenu();
      input = response[0].charAt(0);
      options = response[1].toCharArray();
      
      if (validation.checkInputOptionsMatch(options, input)) { // check if input is in the options
        if (input == '0') {
          exit = true;
        } else {
          exit = mainMenu.runMainMenu(input);
        }
      } else {
        MessageUI.displayInputOptionErrorMessage(options); // Use static method
      }
    } while (!exit);
  }
}
