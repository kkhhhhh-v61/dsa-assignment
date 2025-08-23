package utility;

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
    Utilities.wait(1000);
  }

  public static void notValidOption(char[] options) {
    String optionString = "";
    for (int i = 0; i < options.length - 1; i++) {
      optionString += options[i] + ", ";
    }
    optionString += options[options.length - 1];

    invalidInput();
    System.out.println("Error! Input must be one of these options: " + optionString);
    Utilities.wait(1000);
  }

  public static void notValidId(String id) {
    invalidInput();
    System.out.println("Error! Unable to find ID: " + id);
    Utilities.wait(1000);
  }
 
  public static void notIcNumberFormat() {
    invalidInput();
    System.out.println("Error! IC Number must follow the format of 'YYMMDD-XX-XXXX'");
    Utilities.wait(1000);
  }

  public static void notNumeric() {
    invalidInput();
    System.out.println("Error! Input must be numeric.");
    Utilities.wait(1000);
  }

  public static void notUniqueValue(String attribute, String value) {
    invalidInput();
    System.out.println("Error! " + attribute + ": " + value + " already exists.");
    Utilities.wait(1000);
  }

  public static void notValidIcBirthDate() {
    invalidInput();
    System.out.println("Error! The first 6 digits of the IC Number must be a valid birth date.");
    Utilities.wait(1000);
  }

  public static void exceedLength(int maxLength) {
    invalidInput();
    System.out.println("Error! Input must not exceed " + maxLength + " characters.");
    Utilities.wait(1000);
  }

  public static void notValidName() {
    invalidInput();
    System.out.println("Error! Input can only contain alphabet characters and spaces.");
    Utilities.wait(1000);
  }

  public static void notContactNumberFormat() {
    invalidInput();
    System.out.println("Error! Contact Number must follow the format of '01X-XXX XXXX'.");
    Utilities.wait(1000);
  }

  public static void notEmailFormat() {
    invalidInput();
    System.out.println("Error! Email must follow the format of 'username@mailserver.domain'.");
    Utilities.wait(1000);
  }

  public static void sameAsCurrent() {
    invalidInput();
    System.out.println("Error! Input must be different from the current value.");
    Utilities.wait(1000);
  }
}
