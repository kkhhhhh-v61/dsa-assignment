package utility;

import java.util.Scanner;

/**
 *
 * @author khtan
 */
public class Utilities {
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
    System.out.print("Press any key to continue...");
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

  public String removeSpaces(String input) {
    return input.replaceAll("\\s", "");
  }

  public String formatName(String name) {
    if (!name.isEmpty()) {
      char firstChar = name.charAt(0); // get the first char
      if (Character.isLowerCase(firstChar)) { // check if first char is lowercase
        name = Character.toUpperCase(firstChar) + name.substring(1); // capitalize first char
      }
      name = name.replaceAll("(?<!^)(?=[A-Z])", " "); // add space between each word
    }
    return name;
  }

  public String formatContactNumber(String contactNumber) {
    if (!contactNumber.isEmpty()) {
      contactNumber = contactNumber.replaceAll("[\\s-]", "");
      contactNumber = contactNumber.replaceAll("(\\d{3})(\\d{4})", "$1-$2 ");
    }
    return contactNumber;
  }
}
