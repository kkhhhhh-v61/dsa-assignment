package control;

import boundary.MainMenuUI;

public class ClinicManagementSystem {
  public static void main(String[] args) {
    MainMenuUI mainMenuUI = new MainMenuUI();
    char input;

    do {
      input = mainMenuUI.displayMainMenu();
      
      boolean exit = false;
      switch (input) {
        case '1':
          do {
            input = mainMenuUI.displayStudentUI();
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
          break;
        case '2':
          do {
            input = mainMenuUI.displayStaffUI();
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
            case '7':
              break;
            case '0':
              exit = true;
              break;
            default:
              break;
            }
          } while (!exit);
          break;
        case '0':
          input = 'X';
          System.out.println("Goodbye!");
          break;
        default:
          break;
      }
    } while (input != 'X');
  }
}
