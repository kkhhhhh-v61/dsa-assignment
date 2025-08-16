package utility;

import java.util.Scanner;

public class UtilityUI {
  MessageUI messageUI = new MessageUI();
  Scanner scanner = new Scanner(System.in);

  public void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  public void displayLine() {
    System.out.println("+==================================================================================================+");
  }
  
  public void nextLine() {
    System.out.println("");
  }

  public String displayInputOptions(char[] inputOptions) {
    String input = "(";
    for (char option : inputOptions) {
      input += option + "/";
    }
    input = input.substring(0, input.length() - 1);
    input += ")";
    return input;
  }

  public Scanner getScannerInput() {
    return scanner;
  }

  public void flushScanner() {
    scanner.nextLine();
  }

  public char checkInputOptionsError(char[] inputOptions, char input) {
    for (char option : inputOptions) {
      if (option == input) {
        return input;
      }
    }
    messageUI.displayInputErrorMessage(inputOptions);
    return input;
  }
}
