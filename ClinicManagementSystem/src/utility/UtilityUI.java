package utility;

import java.util.Scanner;

public class UtilityUI {
  MessageUI messageUI = new MessageUI();
  Scanner scanner = new Scanner(System.in);

  public void displayLine() {
    System.out.println("+==================================================================================================+");
  }
  
  public void nextLine() {
    System.out.println();
  }

  public void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  public Scanner getScannerInput() {
    return scanner;
  }

  public void flushScanner() {
    scanner.nextLine();
  }

  public void pressAnyKeyToContinue() {
    System.out.println("Press any key to continue...");
    scanner.nextLine();
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
}
