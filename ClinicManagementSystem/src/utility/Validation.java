package utility;

import adt.ListInterface;
import adt.RedBlackTreeMap;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Tan Kai Hao
 */
public class Validation {
  
  public static boolean isSingleChar(String input) {
    return input.length() == 1;
  }

  public static boolean isValidOption(char input, char[] options) {
    for (char option : options) {
      if (input == option) {
        return true;
      }
    }
    return false;
  }

  public static boolean isUniqueIcNumber(RedBlackTreeMap<String, ?> map, String input) {
    if (!map.containsValue(input)) {
      return true;
    }
    return false;
  }

  public static boolean isValidId(RedBlackTreeMap<String, ?> map, String input) {
    return map.containsKey(input);
  }

  public static boolean isValidId(ListInterface<?> list, String input) {
    return list.contains(input);
  }

  public static boolean isIcNumberFormat(String input) {
    input = input.replaceAll("[\\s-]", "");
    return input.matches("^\\d{12}$");
  }

  public static boolean isNumeric(String input) {
    return input.matches("^\\d+$");
  }

  public static boolean isValidIcBirthDate(String input) {
    int year = Integer.parseInt(input.substring(0, 2));
    int month = Integer.parseInt(input.substring(2, 4));
    int day = Integer.parseInt(input.substring(4, 6));

    int currentYear = LocalDate.now().getYear();
    int century = (currentYear / 100) * 100;
    year = year > (currentYear % 100) ? century - 100 + year : century + year;

    if (month < 1 || month > 12) {
      return false;
    }

    int daysInMonth = LocalDate.of(year, month, 1).lengthOfMonth();
    if (day < 1 || day > daysInMonth) {
      return false;
    }

    return true;
  }

  public static boolean isExceedLength(String input, int maxLength) {
    return input.length() <= maxLength;
  }
  
  public static boolean isValidName(String input) {
    return input.matches("^[a-zA-Z ]+$");
  }

  public static boolean isContactNumberFormat(String input) {
    input = input.replaceAll("[\\s-]", "");
    return input.matches("^01\\d{8,9}$");
  }

  public static boolean isEmailFormat(String input) {
    return input.matches("^[\\w-\\+]+(\\.[\\w]+)*@([\\w-]+\\.)+[\\w-]{2,4}$");
  }

  public static boolean isEqualsLength(String input, int length) {
    return input.length() == length;
  }

  public static boolean isValidDate(String input) {
    int year = Integer.parseInt(input.substring(0, 4));
    int month = Integer.parseInt(input.substring(4, 6));
    int day = Integer.parseInt(input.substring(6, 8));

    int currentYear = LocalDate.now().getYear();
    int century = (currentYear / 100) * 100;
    year = year > (currentYear % 100) ? century - 100 + year : century + year;

    if (month < 1 || month > 12) {
      return false;
    }

    int daysInMonth = LocalDate.of(year, month, 1).lengthOfMonth();
    if (day < 1 || day > daysInMonth) {
      return false;
    }

    return true;
  }

  public static boolean isValidTime(String input) {
    int hour = Integer.parseInt(input.substring(0, 2));
    int minute = Integer.parseInt(input.substring(2, 4));
    if (hour < 0 || hour > 23) {
      return false;
    }
    if (minute < 0 || minute > 59) {
      return false;
    }
    return true;
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
