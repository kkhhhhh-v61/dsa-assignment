package utility;

/**
 *
 * @author Tan Kai Hao
 */
public class MessageUI {
  public static void pressAnyKey() {
    System.out.println();
    System.out.println("Press any key to continue...");
    Utilities.getUserInput().nextLine();
  }

  public static void registrationSuccess() {
    System.out.println();
    System.out.println("Registration Successful!");
    pressAnyKey();
  }

  public static void bookingSuccess() {
    System.out.println();
    System.out.println("Booking Successful!");
    pressAnyKey();
  }

  public static void editSuccess() {
    System.out.println();
    System.out.println("Edit Successful!");
    pressAnyKey();
  }

  public static void deleteSuccess() {
    System.out.println();
    System.out.println("Delete Successful!");
    pressAnyKey();
  }

  public static void cancellationSuccess() {
    System.out.println();
    System.out.println("Cancellation Successful!");
    pressAnyKey();
  }

  public static void invalidInput() {
    System.out.println();
    System.out.println("Invalid Input! Please re-enter.");
    Utilities.wait(1000);
  }

  public static void emptyInput() {
    invalidInput();
    System.out.println("Error! Input cannot be empty.");
    pressAnyKey();
  }

  public static void notSingleChar() {
    invalidInput();
    System.out.println("Error! Input must be a single character.");
    pressAnyKey();
  }

  public static void invalidOption(char[] options) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < options.length; i++) {
      sb.append(options[i]);
      if (i < options.length - 1) {
        sb.append(", ");
      }
    }

    invalidInput();
    System.out.println("Error! Input must be one of these options: " + sb.toString());
    pressAnyKey();
  }
  
  public static void notNumeric() {
    invalidInput();
    System.out.println("Error! Input must be numeric.");
    pressAnyKey();
  }

  public static void exceedLength(int length) {
    invalidInput();
    System.out.println("Error! Input must be at most " + length + " characters long.");
    pressAnyKey();
  }

  public static void invalidRange(int min, int max) {
    invalidInput();
    System.out.println("Error! Input must be within " + min + " and " + max + ".");
    pressAnyKey();
  }

  public static void idDoesNotExist() {
    invalidInput();
    System.out.println("Error! ID does not exist.");
    pressAnyKey();
  }

  public static void invalidIcBirthDate() {
    invalidInput();
    System.out.println("Error! First 6 digits must be a valid birthdate.");
    pressAnyKey();
  }

  public static void invalidIcNumber() {
    invalidInput();
    System.out.println("Error! Input must be in the format 'YYMMDD-XX-XXXX'.");
    pressAnyKey();
  }

  public static void icNumberExists() {
    invalidInput();
    System.out.println("Error! IC Number already registered.");
    pressAnyKey();
  }

  public static void invalidName() {
    invalidInput();
    System.out.println("Error! Input can only contain letters and spaces.");
    pressAnyKey();
  }

  public static void invalidBirthDate() {
    invalidInput();
    System.out.println("Error! Input must be a valid birthdate.");
    pressAnyKey();
  }

  public static void invalidContactNumber() {
    invalidInput();
    System.out.println("Error! Input must be in the format '01-XXX XXXX' or '01-XXXX XXXX'.");
    pressAnyKey();
  }

  public static void invalidEmail() {
    invalidInput();
    System.out.println("Error! Input must be in the format 'username@mailserver.domain'.");
    pressAnyKey();
  }

  public static void invalidDate() {
    invalidInput();
    System.out.println("Error! Input must be a valid date of format 'YYYY-MM-DD'.");
    pressAnyKey();
  }

  public static void notFutureDate() {
    invalidInput();
    System.out.println("Error! Input cannot be a future date.");
    pressAnyKey();
  }

  public static void mustBeFutureDate() {
    invalidInput();
    System.out.println("Error! Input must be a future date.");
    pressAnyKey();
  }

  public static void notWithinOneYear() {
    invalidInput();
    System.out.println("Error! Input date must be within one year from today.");
    pressAnyKey();
  }

  public static void invalidTime() {
    invalidInput();
    System.out.println("Error! Input must be a valid time of format 'HH:MM'.");
    pressAnyKey();
  }

  public static void withinTimeRange(String startTime, String endTime) {
    invalidInput();
    System.out.println("Error! Input must be within the time range of " + startTime + " and " + endTime + ".");
    pressAnyKey();
  }

  public static void noSearchResult() {
    System.out.println();
    System.out.println("Error! No search result found.");
    pressAnyKey();
  }

  public static void noScheduleAvailable(String date, String day) {
    System.out.println();
    System.out.println("Error! No schedule available on " + date + " (" + day + ").");
    pressAnyKey();
  }



  // Chee Kit
  public static void displayMessage(String message) {
    System.out.println(message);
  }

  public static void displaySuccessMessage(String message) {
    System.out.println("SUCCESS: " + message);
  }

  public static void displayErrorMessage(String message) {
    System.err.println("ERROR: " + message);
  }
}
