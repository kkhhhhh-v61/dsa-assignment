package boundary;

import utility.MessageUI;
import utility.UtilityUI;
import java.util.Scanner;

public class PatientMaintenanceUI {
  private static MessageUI messageUI = new MessageUI();
  private static UtilityUI utilityUI = new UtilityUI();
  private static Scanner scanner = new Scanner(System.in);
  private static String input;

  public String[] displayPatientManagementUI() {
    final char[] INPUT_OPTIONS = { '1', '2', '0' };
    utilityUI.clearScreen();
    utilityUI.displayLine();
    System.out.println("|                                        PATIENT MANAGEMENT                                        |");
    utilityUI.displayLine();
    System.out.println("| [1] Patient Registration                                                                         |");
    System.out.println("| [2] Patient List                                                                                 |");
    System.out.println("| [0] Back                                                                                         |");
    utilityUI.displayLine();
    utilityUI.nextLine();
    System.out.print("Select Option " + utilityUI.displayInputOptions(INPUT_OPTIONS) + " : ");
    String option = scanner.nextLine();
    return new String[] {option, new String(INPUT_OPTIONS)};
  }
  
  public char displayPatientRegistrationUI() {
    utilityUI.clearScreen();
    utilityUI.displayLine();
    System.out.println("|                                        PATIENT REGISTRATION                                        |");
    utilityUI.displayLine();
    String registrationDetails = getRegistrationDetails();
    if (registrationDetails.equals("0")) {
      return '0';
    } else {
      return '1';
    }
    
  }

  public String getRegistrationDetails() {
    String inputString = "";
    boolean exit = false;
    do {
      input = inputPatientIcNumber();
      if (input.equals("0")) {
        exit = true;
        break;
      } else {
        inputString += input + "|";
        do {
          input = inputPatientName();
          if (input.equals("0")) {
            exit = true;
            break;
          } else {
            inputString += input + "|";
          }
        } while (!exit);
      }
    } while (!exit);
    return inputString;
  }
  
  public String inputPatientId() {
    final char[] INPUT_OPTIONS = { '0' };
    final int MAX_LENGTH = 4;

    System.out.println("Format : PXXXX");
    System.out.println("[0] Cancel");
    System.out.print("Enter Patient ID : P");
    utilityUI.flushScanner();
    input = scanner.nextLine();
    if (utilityUI.checkIsCharInput(input)) {
      if (!utilityUI.checkIsInputOptions(INPUT_OPTIONS, input)) {
        messageUI.displayInputOptionErrorMessage(INPUT_OPTIONS);
      } else {
        return input;
      }
    } else {
      if (!utilityUI.checkIsExceedLength(input, MAX_LENGTH)) {
        messageUI.displayCannotExceedLengthErrorMessage(MAX_LENGTH);
      }
    }
    return "P" + input;
  }

  public String inputPatientIcNumber() {
    final char[] INPUT_OPTIONS = { '0' };
    final String FORMAT = "XXXXXX-XX-XXXX";

    System.out.println("Format : " + FORMAT);
    System.out.println("[0] Cancel");
    System.out.print("Enter Patient IC Number : ");
    utilityUI.flushScanner();
    input = scanner.nextLine();
    if (utilityUI.checkIsCharInput(input)) {
      if (!utilityUI.checkIsInputOptions(INPUT_OPTIONS, input)) {
        messageUI.displayInputOptionErrorMessage(INPUT_OPTIONS);
      }
    } else {
      if (!utilityUI.checkIcNumberFormat(input)) {
        messageUI.displayFormatErrorMessage(FORMAT);
      }
    }
    return input;
  }

  public String inputPatientName() {
    final char[] INPUT_OPTIONS = { '0' };
    final int MAX_LENGTH = 50;
    
    System.out.println("[0] Cancel");
    System.out.print("Enter Patient Name : ");
    utilityUI.flushScanner();
    input = scanner.nextLine();
    if (!utilityUI.checkIsCharInput(input)) {
      if (!utilityUI.checkIsInputOptions(INPUT_OPTIONS, input)) {
        if (utilityUI.checkIsExceedLength(input, MAX_LENGTH)) {
          messageUI.displayCannotExceedLengthErrorMessage(MAX_LENGTH);
        } else if (utilityUI.checkIsContainNumber(input)) {
          messageUI.displayCannotContainNumberErrorMessage();
        } else if (utilityUI.checkIsContainSpecialCharacter(input)) {
          messageUI.displayCannotContainSpecialCharacterErrorMessage();
        }
      }
    }
    return input;
  }

  public char inputPatientGender() {
    final char[] INPUT_OPTIONS = { '1', '2', '0' };

    System.out.println("[1] Male | [2] Female | [0] Cancel");
    System.out.println("Enter Patient Gender " + utilityUI.displayInputOptions(INPUT_OPTIONS) + " : ");
    utilityUI.flushScanner();
    char option = scanner.next().charAt(0);
    if (utilityUI.checkInputOptionsMatch(INPUT_OPTIONS, option)) {
      return option;
    } else {
      return 'X';
    }
  }
  
  public String inputPatientBirthDate() {
    final char[] INPUT_OPTIONS = { '0' };
    final String FORMAT = "YYYY-MM-DD";

    System.out.println("Format : " + FORMAT);
    System.out.println("[0] Cancel");
    System.out.print("Enter Patient Birth Date : ");
    utilityUI.flushScanner();
    input = scanner.nextLine();
    if (utilityUI.checkIsInputOptions(INPUT_OPTIONS, input)) {
      return input;
    } else if (utilityUI.checkLocalDateFormat(input, FORMAT)) {
      return input;
    } else {
      return "0";
    }
  }

  public String inputPatientContactNumber() {
    final char[] INPUT_OPTIONS = { '0' };
    final String FORMAT = "01X-XXX XXXX OR 01X-XXXX XXXX";

    System.out.println("Format : " + FORMAT);
    System.out.println("[0] Cancel");
    System.out.print("Enter Patient Contact Number : ");
    utilityUI.flushScanner();
    input = scanner.nextLine();
    if (utilityUI.checkIsInputOptions(INPUT_OPTIONS, input)) {
      return input;
    } else if (utilityUI.checkContactNumberFormat(input)) {
      return input;
    } else {
      return "0";
    }
  }

  public String inputPatientEmail() {
    final char[] INPUT_OPTIONS = { '0' };
    final String FORMAT = "XXX@XXXXX.XXX";

    System.out.println("Format : " + FORMAT);
    System.out.println("[0] Cancel");
    System.out.print("Enter Patient Email : ");
    input = scanner.nextLine();
    utilityUI.flushScanner();
    if (utilityUI.checkIsInputOptions(INPUT_OPTIONS, input)) {
      return input;
    } else if (utilityUI.checkEmailFormat(input, FORMAT)) {
      return input;
    } else {
      return "0";
    }
  }

  public String inputPatientAddress() {
    final char[] INPUT_OPTIONS = { '0' };
    final int MAX_LENGTH = 100;

    System.out.println("[0] Cancel");
    System.out.print("Enter Patient Address : ");
    input = scanner.nextLine();
    utilityUI.flushScanner();
    if (utilityUI.checkIsInputOptions(INPUT_OPTIONS, input)) {
      return input;
    } else if (utilityUI.checkIsExceedLength(input, MAX_LENGTH)) {
      messageUI.displayCannotExceedLengthErrorMessage(MAX_LENGTH);
      return "X";
    } else {
      return input;
    }
  }

  public String inputPatientRegistrationDate() {
    final char[] INPUT_OPTIONS = { '0' };
    final String FORMAT = "YYYY-MM-DD";

    System.out.println("Format : " + FORMAT);
    System.out.println("[0] Cancel");
    System.out.print("Enter Patient Registration Date : ");
    input = scanner.nextLine();
    utilityUI.flushScanner();
    if (utilityUI.checkIsInputOptions(INPUT_OPTIONS, input)) {
      return input;
    } else if (utilityUI.checkLocalDateFormat(input, FORMAT)) {
      return input;
    } else {
      return "0";
    }
  }

  public char inputPatientBloodType() {
    final char[] INPUT_OPTIONS = { '1', '2', '3', '4', '0' };

    System.out.println("[1] A | [2] B | [3] AB | [4] O | [0] Cancel");
    System.out.println("Enter Patient Blood Type " + utilityUI.displayInputOptions(INPUT_OPTIONS) + " : ");
    utilityUI.flushScanner();
    char option = scanner.next().charAt(0);
    if (utilityUI.checkInputOptionsMatch(INPUT_OPTIONS, option)) {
      return option;
    } else {
      return 'X';
    }
  }
}
