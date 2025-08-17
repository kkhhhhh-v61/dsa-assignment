package utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author khtan
 */
public class Validation {
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
}
