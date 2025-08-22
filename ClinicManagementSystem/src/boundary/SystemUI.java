package boundary;

import utility.MessageUI;
import utility.Utilities;
import utility.Validation;

/**
 *
 * @author Tan Kai Hao
 */
public class SystemUI {

  public char runMainMenu() {

    final char[] INPUT_OPTIONS = { '1', '2', '0' };

    boolean exit = false;
    char response = 'X';

    do {
      System.out.println();

      Utilities.clearScreen();

      System.out.println("+==================================================================================================+");
      System.out.println("|                 ##   ##  ##   ##  #######     #######   #####   ######   #######                 |");
      System.out.println("|                 ##   ##  ###  ##    ###       ##       ##   ##  ##   ##  ##                      |");
      System.out.println("|                 ##   ##  #### ##    ###       ##       ##   ##  ##   ##  ##                      |");
      System.out.println("|                 ##   ##  ## ####    ###       ##       #######  #######  #####                   |");
      System.out.println("|                 ##   ##  ##  ###    ###       ##       ##   ##  ##  ##   ##                      |");
      System.out.println("|                 ##   ##  ##  ###    ###       ##       ##   ##  ##   ##  ##                      |");
      System.out.println("|                 #######  ##   ##  #######     #######  ##   ##  ##   ##  #######                 |");
      System.out.println("+==================================================================================================+");
      System.out.println("|                                     CLINIC MANAGEMENT SYSTEM                                     |");
      System.out.println("+==================================================================================================+");

      System.out.println();

      System.out.println("+-------------------------+");
      System.out.println("| [1] Clinic Staff Portal |");
      System.out.println("| [2] Student Portal      |");
      System.out.println("| [0] Exit                |");
      System.out.println("+-------------------------+");

      System.out.print("> ");
      String userInput = Utilities.getUserInput().nextLine();

      if (userInput != "") {
        if (Validation.isSingleChar(userInput)) {
          response = userInput.charAt(0);

          if (Validation.isValidOption(response, INPUT_OPTIONS)) {
            exit = true;
          } else {
            MessageUI.notValidOption(INPUT_OPTIONS);
          }
        } else {
          MessageUI.notSingleChar();
        }
      }
    } while (!exit);

    return response;
  }
  
  public char runClinicStaffMenu() {

    final char[] INPUT_OPTIONS = { '1', '2', '0' };
    
    boolean exit = false;
    char response = 'X';

    do {
      exit = false;

      System.out.println();

      Utilities.clearScreen();

      System.out.println("+-----------------------------+");
      System.out.println("|     CLINIC STAFF PORTAL     |");
      System.out.println("+-----------------------------+");
      System.out.println("| [1] Patient Management      |");
      System.out.println("| [2] Doctor Management       |");
      System.out.println("| [3] Appointment Management  |");
      System.out.println("| [4] Consultation Management |");
      System.out.println("| [5] Treatment Management    |");
      System.out.println("| [0] Return                  |");
      System.out.println("+-----------------------------+");

      System.out.println();

      System.out.print("> ");
      String userInput = Utilities.getUserInput().nextLine();

      if (userInput != "") {
        if (Validation.isSingleChar(userInput)) {
          response = userInput.charAt(0);

          if (Validation.isValidOption(response, INPUT_OPTIONS)) {
            exit = true;
          } else {
            MessageUI.notValidOption(INPUT_OPTIONS);
          }
        } else {
          MessageUI.notSingleChar();
        }
      }
    } while (!exit);

    return response;
  }
}
