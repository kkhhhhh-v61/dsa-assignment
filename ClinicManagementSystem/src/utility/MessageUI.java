package utility;

/**
 *
 * @author khtan
 */
public class MessageUI {
  private static Utilities utilities = new Utilities();
  private static Validation validation = new Validation();

  public char displayPatientRegistrationConfirmationMessage() {
    final char[] INPUT_OPTIONS = { 'Y', 'N' };
    utilities.flushScanner();
    System.out.print("Sure to register this patient? " + utilities.displayInputOptions(INPUT_OPTIONS) + " : ");
    char option = utilities.getScannerInput().next().charAt(0);
    if (validation.checkIsInputOptions(INPUT_OPTIONS, String.valueOf(option))) {
      return option;
    } else {
      return 'X';
    }
  }

  public void displayPatientRegistrationSuccessMessage() {
    System.out.println("Patient Successfully Registered!");
    utilities.pressAnyKeyToContinue();
  }

  public void displayInputErrorMessage() {
    System.out.println("Invalid Input! Please try again.");
  }

  public void displayInputEmptyErrorMessage() {
    displayInputErrorMessage();
    System.out.println("Note: Input cannot be empty.");
    utilities.pressAnyKeyToContinue();
  }

  public void displayInputOptionErrorMessage(char[] inputOptions) {
    displayInputErrorMessage();
    System.out.println("Note: Input must be one of these options : " + utilities.displayInputOptions(inputOptions));
    utilities.pressAnyKeyToContinue();
  }

  public void displayFormatErrorMessage(String format) {
    displayInputErrorMessage();
    System.out.println("Note: Input must be in the format of " + format);
    utilities.pressAnyKeyToContinue();
  }

  public void displayFormatErrorMessage(String[] formats) {
    displayInputErrorMessage();
    System.out.println("Note: Input must be in the format of " + String.join(" OR ", formats));
    utilities.pressAnyKeyToContinue();
  }

  public void displayNameErrorMessage() {
    displayInputErrorMessage();
    System.out.println("Note: Input must be a name with proper capitalization for the first letter of each word.");
    utilities.pressAnyKeyToContinue();
  }

  public void displayCannotContainNumberErrorMessage() {
    displayInputErrorMessage();
    System.out.println("Note: Input cannot contain numbers.");
    utilities.pressAnyKeyToContinue();
  }

  public void displayCannotContainSpecialCharacterErrorMessage() {
    displayInputErrorMessage();
    System.out.println("Note: Input cannot contain special characters.");
    utilities.pressAnyKeyToContinue();
  }

  public void displayCannotExceedLengthErrorMessage(int maxLength) {
    displayInputErrorMessage();
    System.out.println("Note: Input cannot exceed " + maxLength + " characters.");
    utilities.pressAnyKeyToContinue();
  }

  public void displayMustBeEqualsLength(int length) {
    displayInputErrorMessage();
    System.out.println("Note: Input must be " + length + " characters.");
    utilities.pressAnyKeyToContinue();
  }

  public void displayMustBeNumericErrorMessage() {
    displayInputErrorMessage();
    System.out.println("Note: Input must be numeric.");
    utilities.pressAnyKeyToContinue();
  }

  public void displayInvalidIcBirthDateErrorMessage() {
    displayInputErrorMessage();
    System.out.println("Note: The first 6 digits of the IC number must be a valid birth date.");
    utilities.pressAnyKeyToContinue();
  }
}
