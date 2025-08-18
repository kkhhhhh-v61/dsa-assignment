package utility;

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
}
