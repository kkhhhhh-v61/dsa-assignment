package utility;

import java.util.Scanner; // Import Scanner

/**
 *
 * @author khtan
 */
public class MessageUI {

    public static void displaySuccessMessage(String message) {
        System.out.println("[SUCCESS] " + message);
    }

    public static void displayErrorMessage(String message) {
        System.err.println("[ERROR] " + message);
    }

    public static void displayInvalidChoice() {
        System.out.println("[INVALID] Invalid choice. Please try again.");
    }

    public static void displayExitMessage(String message) {
        System.out.println(message);
    }

    public static void displayPatientRegistrationSuccessMessage() {
        System.out.println("[SUCCESS] Patient registered successfully!");
    }

    public static void displayInputOptionErrorMessage(char[] options) {
        StringBuilder sb = new StringBuilder("[INVALID] Invalid input. Please choose from options: (");
        for (char option : options) {
            sb.append(option).append("/");
        }
        sb.deleteCharAt(sb.length() - 1); // Remove trailing slash
        sb.append(")");
        System.err.println(sb.toString());
    }

    public static void displayInputErrorMessage() {
        System.err.println("[ERROR] Input cannot be empty. Please try again.");
    }

    public static void displayMustBeEqualsLength(int length) {
        System.err.println("[ERROR] Input must be exactly " + length + " characters long.");
    }

    public static void displayMustBeNumericErrorMessage() {
        System.err.println("[ERROR] Input must be numeric.");
    }

    public static void displayFormatErrorMessage(String format) {
        System.err.println("[ERROR] Invalid format. Expected format: " + format);
    }

    public static void displayNameErrorMessage() {
        System.err.println("[ERROR] Name cannot contain consecutive uppercase letters.");
    }

    public static void displayCannotContainSpecialCharacterErrorMessage() {
        System.err.println("[ERROR] Input cannot contain special characters.");
    }

    public static void displayCannotExceedLengthErrorMessage(int length) {
        System.err.println("[ERROR] Input cannot exceed " + length + " characters.");
    }

    public static void displayCannotContainNumberErrorMessage() {
        System.err.println("[ERROR] Input cannot contain numbers.");
    }

    public static void clearScreen() {
        // Clears the console screen. This is a common way to do it in Java for simple console apps.
        // Note: This might not work on all IDEs or terminals.
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final Exception e) {
            // Handle exceptions
            System.out.println("Error clearing screen: " + e.getMessage());
        }
    }

    public static void displayBorder() {
        System.out.println("+------------------------------------------------------------------------------------------------------------------+");
    }

    private static Scanner continueScanner = new Scanner(System.in); // Static scanner for continue message

<<<<<<< Updated upstream
    public static void displayContinueMessage() {
        System.out.println("Press Enter to continue...");
        continueScanner.nextLine(); // Use the static scanner
    }
=======
  public static void searchValueNotFound(String category, String value) {
    invalidInput();
    System.out.println("Error! " + category + ": " + value + " not found.");
    pressAnyKey();
  }

  public static void displayMessage(String message) {
    System.out.println(message);
  }

  public static void displaySuccessMessage(String message) {
    System.out.println("SUCCESS: " + message);
  }

  public static void displayErrorMessage(String message) {
    System.err.println("ERROR: " + message);
  }
>>>>>>> Stashed changes
}
