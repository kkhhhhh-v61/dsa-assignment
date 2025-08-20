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
      StringBuilder capitalizeName = new StringBuilder();
      String[] words = name.split("\\s+");
      for (String word : words) {
        if (!word.isEmpty()) {
          capitalizeName.append(Character.toUpperCase(word.charAt(0)) + word.substring(1));
        }
      }
      String noSpacesName = capitalizeName.toString().replaceAll(" ", "");
      return noSpacesName.replaceAll("(?<!^)(?=[A-Z])", " ");
    }
    return name;
  }

  public String formatIcNumber(String icNumber) {
    if (!icNumber.isEmpty()) {
      icNumber = icNumber.replaceAll("[\\s-]", "");
      icNumber = icNumber.replaceAll("(\\d{6})(\\d{2})(\\d{4})", "$1-$2-$3");
    }
    return icNumber;
  }

  public String formatContactNumber(String contactNumber) {
    if (!contactNumber.isEmpty()) {
      contactNumber = contactNumber.replaceAll("[\\s-]", "");
      if (contactNumber.length() == 10) {
        contactNumber = contactNumber.replaceAll("(\\d{3})(\\d{3})", "$1-$2 ");
      } else if (contactNumber.length() == 11) {
        contactNumber = contactNumber.replaceAll("(\\d{3})(\\d{4})", "$1-$2 ");
      }
    }
    return contactNumber;
  }
}
