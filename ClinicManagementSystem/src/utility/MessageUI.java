package utility;

public class MessageUI {
  private static UtilityUI utilityUI = new UtilityUI();

  public void displayInputErrorMessage(char[] inputOptions) {
    System.out.println("Invalid Input! Please try again.");
    System.out.println("Note: Input must be one of these options : " + utilityUI.displayInputOptions(inputOptions));
    System.out.println("Press any key to continue...");
    utilityUI.getScannerInput().nextLine();
  }
}
