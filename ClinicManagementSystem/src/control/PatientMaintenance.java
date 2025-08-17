package control;

import boundary.PatientMaintenanceUI;
import utility.MessageUI;
import utility.Validation;

public class PatientMaintenance {
  private static PatientMaintenanceUI patientMaintenanceUI = new PatientMaintenanceUI();
  private static MessageUI messageUI = new MessageUI();
  private static Validation validation = new Validation();

  public void runPatientMaintenance() {
    String[] response;
    char input;
    char[] options;
    boolean exit = false;
    do {
      response = patientMaintenanceUI.displayPatientManagementUI();
      input = response[0].charAt(0);
      options = response[1].toCharArray();

      if (validation.checkInputOptionsMatch(options, input)) {
        switch (input) {
          case '1':
            String patientDetailsString = "";
            patientMaintenanceUI.displayPatientRegistrationUI();
            patientDetailsString += patientMaintenanceUI.inputPatientId() + "|";
            patientDetailsString += patientMaintenanceUI.inputPatientIcNumber() + "|";
            patientDetailsString += patientMaintenanceUI.inputPatientName() + "|";
            patientDetailsString += patientMaintenanceUI.inputPatientGender() + "|";
            patientDetailsString += patientMaintenanceUI.inputPatientBirthDate() + "|";
            patientDetailsString += patientMaintenanceUI.inputPatientContactNumber() + "|";
            patientDetailsString += patientMaintenanceUI.inputPatientEmail() + "|";
            patientDetailsString += patientMaintenanceUI.inputPatientAddress() + "|";
            patientDetailsString += patientMaintenanceUI.inputPatientRegistrationDate() + "|";
            patientDetailsString += patientMaintenanceUI.inputPatientBloodType();
            break;
          case '2':
            break;
          case '3':
            break;
          case '0':
            exit = true;
            break;
          default:
            break;
        }
      } else {
        messageUI.displayInputOptionErrorMessage(options);
      }
    } while (!exit);
  }
  
  public boolean[] validatePatientId(String input, char[] options, int length) {
    boolean exit = false;
    boolean isId = false;
    if (validation.checkIsCharInput(input)) { // check if input is a char
      if (validation.checkIsInputOptions(options, input)) { // check if input is in the options
        exit = true;
      } else {
        messageUI.displayInputOptionErrorMessage(options);
      }
    } else {
      if (validation.checkIsNumeric(input)) { // check if input is numeric
        if (validation.checkIsEqualsLength(input, length)) { // check if input equals the length
          isId = true;
          exit = true;
        } else {
          messageUI.displayMustBeEqualsLength(length);
        }
      } else {
        messageUI.displayMustBeNumericErrorMessage();
      }
    }
    return new boolean[] {isId, exit};
  }

  public boolean validatePatientIcNumber(String input, char[] options, String format) {
    if (validation.checkIsCharInput(input)) { // check if input is a char
      if (validation.checkIsInputOptions(options, input)) { // check if input is in the options
        return true;
      } else {
        messageUI.displayInputOptionErrorMessage(options);
      }
    } else {
      if (validation.checkLocalDateFormat(input, format)) { // check if input matches the format
        return true;
      } else {
        messageUI.displayFormatErrorMessage(format);
      }
    }
    return false;
  }
  
  public boolean validatePatientName(String input, char[] options, int length) {
    boolean inputError = false;
    if (!input.isEmpty()) { // check if input is not empty
      char firstChar = input.charAt(0); // get the first char
      if (Character.isLowerCase(firstChar)) { // check if first char is lowercase
        input = Character.toUpperCase(firstChar) + input.substring(1); // capitalize first char
      }
      if (input.matches(".*[A-Z]{2,}.*")) { // check if input contains at least 2 consecutive uppercase letters
        messageUI.displayNameErrorMessage();
        inputError = true;
      } else {
        input = input.replaceAll("(?<!^)(?=[A-Z])", " "); // add space between each word
      }
    }
    if (!inputError) { // check if input is valid
      if (validation.checkIsCharInput(input)) { // check if input is a char
        if (validation.checkIsInputOptions(options, input)) { // check if input is in the options
          return true;
        } else {
          messageUI.displayInputOptionErrorMessage(options);
        }
      } else {
        if (validation.checkIsExceedLength(input, length)) { // check if input exceeds the max length
          messageUI.displayCannotExceedLengthErrorMessage(length);
        } else if (validation.checkIsContainNumber(input)) { // check if input contains numbers
          messageUI.displayCannotContainNumberErrorMessage();
        } else if (validation.checkIsContainSpecialCharacter(input)) { // check if input contains special characters
          messageUI.displayCannotContainSpecialCharacterErrorMessage();
        } else {
          return true;
        }
      }
    }
    return false;
  }
  
  public boolean validatePatientGender(String input, char[] options) {
    if (validation.checkIsCharInput(input)) { // check if input is a char
        if (validation.checkIsInputOptions(options, input)) { // check if input is in the options
          return true;
        } else {
          messageUI.displayInputOptionErrorMessage(options);
        }
      }
    return false;
  }

  public boolean validatePatientBirthDate(String input, char[] options, String format) {
    if (validation.checkIsCharInput(input)) { // check if input is a char
      if (validation.checkIsInputOptions(options, input)) { // check if input is in the options
        return true;
      } else {
        messageUI.displayInputOptionErrorMessage(options);
      }
    } else {
      if (validation.checkLocalDateFormat(input, format)) { // check if input matches the format
        return true;
      } else {
        messageUI.displayFormatErrorMessage(format);
      }
    }
    return false;
  }

  public boolean validatePatientContactNumber(String input, char[] options, String format) {
    if (validation.checkIsCharInput(input)) { // check if input is a char
      if (validation.checkIsInputOptions(options, input)) { // check if input is in the options
        return true;
      } else {
        messageUI.displayInputOptionErrorMessage(options);
      }
    } else {
      if (validation.checkContactNumberFormat(input, format)) { // check if input matches the format
        return true;
      } else {
        messageUI.displayFormatErrorMessage(format);
      }
    }
    return false;
  }

  public boolean validatePatientEmail(String input, char[] options, String format) {
    if (validation.checkIsCharInput(input)) { // check if input is a char
      if (validation.checkIsInputOptions(options, input)) { // check if input is in the options
        return true;
      } else {
        messageUI.displayInputOptionErrorMessage(options);
      }
    } else {
      if (validation.checkEmailFormat(input, format)) { // check if input matches the format
        return true;
      } else {
        messageUI.displayFormatErrorMessage(format);
      }
    }
    return false;
  }

  public boolean validatePatientAddress(String input, char[] options, int maxLength) {
    if (validation.checkIsCharInput(input)) { // check if input is a char
      if (validation.checkIsInputOptions(options, input)) { // check if input is in the options
        return true;
      } else {
        messageUI.displayInputOptionErrorMessage(options);
      }
    } else {
      if (validation.checkIsExceedLength(input, maxLength)) { // check if input exceeds the max length
        messageUI.displayCannotExceedLengthErrorMessage(maxLength);
      } else {
        return true;
      }
    }
    return false;
  }

  public boolean validatePatientRegistrationDate(String input, char[] options, String format) {
    if (validation.checkIsCharInput(input)) { // check if input is a char
      if (validation.checkIsInputOptions(options, input)) { // check if input is in the options
        return true;
      } else {
        messageUI.displayInputOptionErrorMessage(options);
      }
    } else {
      if (validation.checkLocalDateFormat(input, format)) { // check if input matches the format
        return true;
      } else {
        messageUI.displayFormatErrorMessage(format);
      }
    }
    return false;
  }

  public boolean validatePatientBloodType(String input, char[] options) {
    if (validation.checkIsCharInput(input)) { // check if input is a char
      if (validation.checkIsInputOptions(options, input)) { // check if input is in the options
        return true;
      }
    }
    messageUI.displayInputOptionErrorMessage(options);
    return false;
  }

}
