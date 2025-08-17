package utility;

public class MessageUI {
  private static UtilityUI utilityUI = new UtilityUI();

  public char displayPatientRegistrationConfirmationMessage() {
    final char[] INPUT_OPTIONS = { 'Y', 'N' };
    utilityUI.flushScanner();
    System.out.print("Sure to register this patient? " + utilityUI.displayInputOptions(INPUT_OPTIONS) + " : ");
    char option = utilityUI.getScannerInput().next().charAt(0);
    if (utilityUI.checkIsInputOptions(INPUT_OPTIONS, String.valueOf(option))) {
      return option;
    } else {
      return 'X';
    }
  }

  public void displayPatientRegistrationSuccessMessage() {
    System.out.println("Patient Successfully Registered!");
    utilityUI.pressAnyKeyToContinue();
  }

  public void displayInputErrorMessage() {
    System.out.println("Invalid Input! Please try again.");
  }

  public void displayInputOptionErrorMessage(char[] inputOptions) {
    displayInputErrorMessage();
    System.out.println("Note: Input must be one of these options : " + utilityUI.displayInputOptions(inputOptions));
    utilityUI.pressAnyKeyToContinue();
  }

  public void displayFormatErrorMessage(String format) {
    displayInputErrorMessage();
    System.out.println("Note: Input must be in the format of " + format);
    utilityUI.pressAnyKeyToContinue();
  }

  public void displayFormatErrorMessage(String[] formats) {
    displayInputErrorMessage();
    System.out.println("Note: Input must be in the format of " + String.join(" OR ", formats));
    utilityUI.pressAnyKeyToContinue();
  }

  public void displayCannotContainNumberErrorMessage() {
    displayInputErrorMessage();
    System.out.println("Note: Input cannot contain numbers.");
    utilityUI.pressAnyKeyToContinue();
  }

  public void displayCannotContainSpecialCharacterErrorMessage() {
    displayInputErrorMessage();
    System.out.println("Note: Input cannot contain special characters.");
    utilityUI.pressAnyKeyToContinue();
  }

  public void displayCannotExceedLengthErrorMessage(int maxLength) {
    displayInputErrorMessage();
    System.out.println("Note: Input cannot exceed " + maxLength + " characters.");
    utilityUI.pressAnyKeyToContinue();
  }
}
