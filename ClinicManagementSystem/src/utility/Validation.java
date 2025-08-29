package utility;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Tan Kai Hao
 */
public class Validation {
  public static boolean isChar(String input) {
    return input.length() == 1;
  }

  public static boolean isNumeric(String input) {
    return input.matches("\\d+");
  }

  public static boolean withinLength(String input, int length) {
    return input.length() <= length;
  }

  public static boolean withinRange(int input, int min, int max) {
    return input >= min && input <= max;
  }

  public static boolean isValidDate(String input) {
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
      LocalDate date = LocalDate.parse(input, formatter);
      if (date == null) {
        return false;
      }
      return true;
    } catch (DateTimeParseException e) {
      return false;
    }
  }

  public static boolean isFutureDate(String input) {
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
      LocalDate date = LocalDate.parse(input, formatter);
      return date.isAfter(LocalDate.now());
    } catch (DateTimeParseException e) {
      return false;
    }
  }

  public static boolean isWithinOneYear(String input) {
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
      LocalDate date = LocalDate.parse(input, formatter);
      LocalDate today = LocalDate.now();
      LocalDate dateOneYearLater = today.plusYears(1);
      return date.isAfter(today) && date.isBefore(dateOneYearLater);
    } catch (DateTimeParseException e) {
      return false;
    }
  }

  public static boolean isValidTime(String input) {
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
      LocalTime time = LocalTime.parse(input, formatter);
      if (time == null) {
        return false;
      }
      return true;
    } catch (DateTimeParseException e) {
      return false;
    }
  }

  public static boolean isValidOption(char input, char[] inputOptions) {
    for (char option : inputOptions) {
      if (option == input) {
        return true;
      }
    }
    return false;
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

  public static boolean isValidIcNumber(String input) {
    if (!input.matches("^\\d{6}-\\d{2}-\\d{4}$")) {
      MessageUI.invalidIcNumber();
      return false;
    }

    if (!isValidIcBirthDate(input.substring(0, 6))) {
      MessageUI.invalidIcBirthDate();
      return false;
    }

    return true;
  }

  public static boolean isValidName(String input) {
    return input.matches("^[a-zA-Z ]+$");
  }

  public static boolean isValidBirthDate(String input) {
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
      LocalDate date = LocalDate.parse(input, formatter);
      if (date.isAfter(LocalDate.now())) {
        return false;
      }
      return true;
    } catch (DateTimeParseException e) {
      return false;
    }
  }

  public static boolean isValidContactNumber(String input) {
    return input.matches("^01\\d-\\d{3} \\d{4}$|^01\\d-\\d{4} \\d{4}$");
  }

  public static boolean isValidEmail(String input) {
    return input.matches("^[\\w-\\+]+(\\.[\\w]+)*@([\\w-]+\\.)+[\\w-]{2,4}$");
  }

  

  // Chee Kit
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
