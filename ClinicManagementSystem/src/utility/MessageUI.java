package utility;

import java.time.LocalTime;

/**
 *
 * @author Tan Kai Hao
 */
public class MessageUI {

  public static void pressAnyKey() {
    System.out.println("Press any key to continue...");
    Utilities.getUserInput().nextLine();
  }

  public static void noPatientRecord() {
    System.out.println("No existing Patient records found.");
    Utilities.wait(1000);
  }

  public static void invalidInput() {
    System.out.println("Invalid Input! Please re-enter.");
    Utilities.wait(1000);
  }
  
  public static void notSingleChar() {
    invalidInput();
    System.out.println("Error! Input must be a single character.");
    pressAnyKey();
  }

  public static void notValidOption(char[] options) {
    String optionString = "";
    for (int i = 0; i < options.length - 1; i++) {
      optionString += options[i] + ", ";
    }
    optionString += options[options.length - 1];

    invalidInput();
    System.out.println("Error! Input must be one of these options: " + optionString);
    pressAnyKey();
  }

  public static void notValidId(String id) {
    invalidInput();
    System.out.println("Error! Unable to find ID: " + id);
    pressAnyKey();
  }
 
  public static void notIcNumberFormat() {
    invalidInput();
    System.out.println("Error! IC Number must follow the format of 'YYMMDD-XX-XXXX'");
    pressAnyKey();
  }

  public static void notNumeric() {
    invalidInput();
    System.out.println("Error! Input must be numeric.");
    pressAnyKey();
  }

  public static void notUniqueValue(String attribute, String value) {
    invalidInput();
    System.out.println("Error! " + attribute + ": " + value + " already exists.");
    pressAnyKey();
  }

  public static void notValidIcBirthDate() {
    invalidInput();
    System.out.println("Error! The first 6 digits of the IC Number must be a valid birth date.");
    pressAnyKey();
  }

  public static void exceedLength(int maxLength) {
    invalidInput();
    System.out.println("Error! Input must not exceed " + maxLength + " characters.");
    pressAnyKey();
  }

  public static void notValidName() {
    invalidInput();
    System.out.println("Error! Input can only contain alphabet characters and spaces.");
    pressAnyKey();
  }

  public static void notContactNumberFormat() {
    invalidInput();
    System.out.println("Error! Contact Number must follow the format of '01X-XXX XXXX'.");
    pressAnyKey();
  }

  public static void notEmailFormat() {
    invalidInput();
    System.out.println("Error! Email must follow the format of 'username@mailserver.domain'.");
    pressAnyKey();
  }

  public static void sameAsCurrent() {
    invalidInput();
    System.out.println("Error! Input must be different from the current value.");
    pressAnyKey();
  }

  public static void notValidDate() {
    invalidInput();
    System.out.println("Error! Input must be a valid date with the format of 'YYYY-MM-DD'.");
    pressAnyKey();
  }

  public static void notFutureDate() {
    invalidInput();
    System.out.println("Error! Input must be a future date.");
    pressAnyKey();
  }

  public static void notWithinTimeFrame() {
    invalidInput();
    System.out.println("Error! Input must be within the time frame of not exceeding 1 year from the current date.");
    pressAnyKey();
  }

  public static void notValidTime(LocalTime startTime, LocalTime endTime) {
    invalidInput();
    System.out.println("Error! Input must be within the time range of " + startTime.toString() + " to " + endTime.toString() + ". (Does not include " + endTime.toString() + " itself)");
    pressAnyKey();
  }

  public static void mustBeLength(int length) {
    invalidInput();
    System.out.println("Error! Input must be " + length + " characters long.");
    pressAnyKey();
  }
}
