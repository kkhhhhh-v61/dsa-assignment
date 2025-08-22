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

/**
 *
 * @author Tan Kai Hao
 */
public class ClinicManagementSystem {
  
  private static SystemUI systemUI = new SystemUI();

  private static SystemController systemController = new SystemController();
  
  public static void main(String[] args) {
    boolean exit = false;
    
    do {
      exit = false;
      char response = systemUI.runMainMenu();
      
      switch (response) {
        case '1':
          systemController.runClinicStaffPortal();
          break;
        case '2':
          // systemController.runStudentPortal();
          break;
        case '0':
          exit = true;
          break;
      }
    } while (!exit);
  }
}
