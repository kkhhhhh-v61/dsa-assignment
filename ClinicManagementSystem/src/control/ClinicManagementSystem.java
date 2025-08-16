package control;

import boundary.MainMenuUI;
import utility.UtilityUI;

public class ClinicManagementSystem {
  public static void main(String[] args) {
    MainMenuUI mainMenuUI = new MainMenuUI();
    char input;

    do {
      input = mainMenuUI.displayMainMenu();

      switch (input) {
        case '1':
          mainMenuUI.displayStudentUI();
          break;
        case '2':
          mainMenuUI.displayStaffUI();
          break;
        case '0':
          System.out.println("Goodbye!");
          break;
        default:
          break;
      }
    } while (input != '0');
  }
}
