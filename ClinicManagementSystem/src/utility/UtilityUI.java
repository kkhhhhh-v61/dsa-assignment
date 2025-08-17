package utility;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
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

  public boolean checkInputOptionsMatch(char[] inputOptions, char input) {
    for (char option : inputOptions) {
      if (option == input) {
        return true;
      }
    }
    return false;
  }

  public boolean checkIsInputOptions(char[] inputOptions, String input) {
    for (char option : inputOptions) {
      if (checkIsCharInput(input)) {
        if (option == input.charAt(0)) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean checkIsCharInput(String input) {
    if (input.length() == 1) {
      return true;
    }
    return false;
  }

  public boolean checkIsContainNumber(String input) {
    return input.matches(".*\\d.*");
  }

  public boolean checkIsContainSpecialCharacter(String input) {
    return input.matches(".*\\W.*");
  }

  public boolean checkIsExceedLength(String input, int maxLength) {
    return input.length() > maxLength;
  }

  public boolean checkLocalDateFormat(String input, String format) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
    try {
      LocalDate.parse(input, formatter);
      return true;
    } catch (Exception e) {
      messageUI.displayFormatErrorMessage(format);
      return false;
    }
  }

  public boolean checkIcNumberFormat(String input) {
    String format = "XXXXXX-XX-XXXX";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
    try {
      LocalDate.parse(input, formatter);
      return true;
    } catch (Exception e) {
      messageUI.displayFormatErrorMessage(format);
      return false;
    }
  }
  
  public boolean checkContactNumberFormat(String input) {
    String[] formats = { "01X-XXX XXXX", "01X-XXXX XXXX" };
    for (String format : formats) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
      try {
        LocalDate.parse(input, formatter);
        return true;
      } catch (Exception e) {
        continue;
      }
    }
    messageUI.displayFormatErrorMessage(formats);
    return false;
  }

  public boolean checkEmailFormat(String input, String format) {
    if (input.contains("@") && input.contains(".") && input.indexOf(".") > input.indexOf("@")) {
      return true;
    } else {
      messageUI.displayFormatErrorMessage(format);
      return false;
    }
  }
}
