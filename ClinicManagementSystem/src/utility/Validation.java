package utility;

import java.time.LocalDate;
<<<<<<< Updated upstream
import java.time.format.DateTimeFormatter;
=======
>>>>>>> Stashed changes
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author khtan
 */
public class Validation {

  private static Scanner scanner = new Scanner(System.in);

  public static int getIntegerInput() {
    while (true) {
      try {
        int input = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        return input;
      } catch (InputMismatchException e) {
        System.err.println("[ERROR] Invalid input. Please enter an integer.");
        scanner.nextLine(); // Consume the invalid input
      }
    }
  }

  public static int getIntegerInputWithExit() {
    while (true) {
      try {
        String inputStr = scanner.nextLine();
        if (inputStr.equals("0")) {
          return 0;
        }
        int input = Integer.parseInt(inputStr);
        return input;
      } catch (NumberFormatException e) {
        System.err.println("[ERROR] Invalid input. Please enter an integer or '0' to exit.");
      }
    }
  }

  public static double getDoubleInput() {
    while (true) {
      try {
        double input = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        return input;
      } catch (InputMismatchException e) {
        System.err.println("[ERROR] Invalid input. Please enter a number.");
        scanner.nextLine(); // Consume the invalid input
      }
    }
  }

  public static double getDoubleInputWithExit() {
    while (true) {
      try {
        String inputStr = scanner.nextLine();
        if (inputStr.equals("0")) {
          return 0.0;
        }
        double input = Double.parseDouble(inputStr);
        return input;
      } catch (NumberFormatException e) {
        System.err.println("[ERROR] Invalid input. Please enter a number or '0' to exit.");
      }
    }
  }

  public static String displayInputOptions(char[] options) {
    StringBuilder sb = new StringBuilder("(");
    for (char option : options) {
      sb.append(option).append("/");
    }
    sb.deleteCharAt(sb.length() - 1); // Remove trailing slash
    sb.append(")");
    return sb.toString();
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
    if (input.length() == 1) {
      for (char option : inputOptions) {
        if (checkIsCharInput(input)) {
          if (option == input.charAt(0)) {
            return true;
          }
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
    return input.matches(".*[^a-zA-Z0-9].*");
  }

  public boolean checkIsExceedLength(String input, int maxLength) {
    return input.length() > maxLength;
  }

  public boolean checkIsEqualsLength(String input, int length) {
    return input.length() == length;
  }

  public boolean checkLocalDateFormat(String input, String format) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
    try {
      LocalDate.parse(input, formatter);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public String formatIcNumber(String icNumber) {
    // Remove all non-digit characters
    String cleanedIcNumber = icNumber.replaceAll("[^0-9]", "");

    // Check if the cleaned IC number has the expected length (12 digits)
    if (cleanedIcNumber.length() == 12) {
      // Format the IC number with hyphens
      return cleanedIcNumber.substring(0, 6) + "-" +
             cleanedIcNumber.substring(6, 8) + "-" +
             cleanedIcNumber.substring(8, 12);
    } else {
      // Return original input or throw an exception if length is not 12
      return icNumber;
    }
  }

  public boolean checkIcNumberFormat(String input) {
    String format = "\\d{6}-\\d{2}-\\d{4}";
    if (input.matches(format)) {
      return true;
    }
    return false;
  }
  
  public boolean checkContactNumberFormat(String input) {
    String format = "01[012346789]\\d{7,8}";
    input = input.replaceAll("[\\s-]", "");
    return input.matches(format);
  }

  public boolean checkEmailFormat(String input) {
    if (input.contains("@") && input.contains(".") && input.indexOf(".") > input.indexOf("@")) {
      return true;
    } else {
      return false;
    }
  }

  public boolean checkIsNumeric(String input) {
    return input.matches("^\\d+$");
  }

  public Enum.Gender assignGenderFromIcNumber(String formattedIcNumber) {
    if (formattedIcNumber == null || formattedIcNumber.length() != 14) {
      return Enum.Gender.Unknown;
    }

    try {
      char lastDigitChar = formattedIcNumber.charAt(13); // Last digit of XXXXXX-XX-XXXX
      int lastDigit = Character.getNumericValue(lastDigitChar);

      if (lastDigit % 2 == 1) {
        return Enum.Gender.Male;
      } else {
        return Enum.Gender.Female;
      }
    } catch (Exception e) {
      return Enum.Gender.Unknown;
    }
  }

  public static int getValidIntegerInput(Scanner scanner, String prompt) {
    int input = -1;
    boolean isValid = false;
    do {
      System.out.print(prompt);
      try {
        input = scanner.nextInt();
        if (input >= 0) {
          isValid = true;
        } else {
          MessageUI.displayErrorMessage("Input cannot be negative. Please try again.");
        }
      } catch (InputMismatchException e) {
        MessageUI.displayErrorMessage("Invalid input. Please enter a whole number.");
      } finally {
        scanner.nextLine(); // Consume newline
      }
    } while (!isValid);
    return input;
  }

  public static double getValidDoubleInput(Scanner scanner, String prompt) {
    double input = -1.0;
    boolean isValid = false;
    do {
      System.out.print(prompt);
      try {
        input = scanner.nextDouble();
        if (input >= 0) {
          isValid = true;
        } else {
          MessageUI.displayErrorMessage("Input cannot be negative. Please try again.");
        }
      } catch (InputMismatchException e) {
        MessageUI.displayErrorMessage("Invalid input. Please enter a decimal number.");
      } finally {
        scanner.nextLine(); // Consume newline
      }
    } while (!isValid);
    return input;
  }
}
