package control;

import boundary.PatientMaintenanceUI;
import entity.Patient;
import utility.Enum.BloodType;
import utility.Enum.Gender;
import utility.MessageUI;
import utility.UtilityUI;
import utility.Validation;
import java.time.LocalDate;

public class PatientMaintenance {
  private static PatientMaintenanceUI patientMaintenanceUI = new PatientMaintenanceUI();
  private static MessageUI messageUI = new MessageUI();
  private static UtilityUI utilityUI = new UtilityUI();
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
            boolean exit2 = false;
            boolean isDetailsValid = false;
            Patient newPatient = new Patient();
            do {
              String result;
              result = patientMaintenanceUI.inputPatientIcNumber();
              if (result.equals("0")) {
                exit2 = true;
              } else {
                newPatient.setIcNumber(result);
                result = utilityUI.formatName(utilityUI.removeSpaces(patientMaintenanceUI.inputPatientName()));
                if (result.equals("0")) {
                  exit2 = true;
                } else {
                  newPatient.setName(result);
                  result = String.valueOf(patientMaintenanceUI.inputPatientGender());
                  if (result.equals("0")) {
                    exit2 = true;
                  } else {
                    switch (result.charAt(0)) {
                      case '1':
                        result = "Male";
                        break;
                      case '2':
                        result = "Female";
                        break;
                      default:
                        break;
                    }
                    newPatient.setGender(Gender.valueOf(result));
                    result = patientMaintenanceUI.inputPatientBirthDate();
                    if (result.equals("0")) {
                      exit2 = true;
                    } else {
                      newPatient.setBirthDate(LocalDate.parse(result));
                      newPatient.setAge(newPatient.calculateAge());
                      result = patientMaintenanceUI.inputPatientContactNumber();
                      if (result.equals("0")) {
                        exit2 = true;
                      } else {
                        newPatient.setContactNumber(utilityUI.formatContactNumber(result));
                        result = patientMaintenanceUI.inputPatientEmail();
                        if (result.equals("0")) {
                          exit2 = true;
                        } else {
                          newPatient.setEmail(result);
                          result = patientMaintenanceUI.inputPatientAddress();
                          if (result.equals("0")) {
                            exit2 = true;
                          } else {
                            newPatient.setAddress(result);
                            result = String.valueOf(patientMaintenanceUI.inputPatientBloodType());
                            if (result.equals("0")) {
                              exit2 = true;
                            } else {
                              switch (result.charAt(0)) {
                                case '1':
                                  result = "A";
                                  break;
                                case '2':
                                  result = "B";
                                  break;
                                case '3':
                                  result = "AB";
                                  break;
                                case '4':
                                  result = "O";
                                  break;
                                default:
                                  break;
                              }
                              newPatient.setBloodType(BloodType.valueOf(result));
                              isDetailsValid = true;
                              exit2 = true;
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            } while (!exit2);
            if (isDetailsValid) {
              System.out.println(newPatient.toStringComplete());
              utilityUI.pressAnyKeyToContinue();
            }
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
    if (input.isEmpty()) { // check if input is empty
      messageUI.displayInputErrorMessage();
      return new boolean[] {isId, exit};
    }
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

  public boolean validatePatientIcNumber(String input, char[] options, String format) { // TODO: add formatting to automatically add hyphens
    if (input.isEmpty()) { // check if input is empty
      messageUI.displayInputErrorMessage();
      return false;
    }
    if (validation.checkIsCharInput(input)) { // check if input is a char
      if (validation.checkIsInputOptions(options, input)) { // check if input is in the options
        return true;
      } else {
        messageUI.displayInputOptionErrorMessage(options);
      }
    } else {
      if (validation.checkIsNumeric(input.replace("-", ""))) { // check if input is numeric
        if (validation.checkIcNumberFormat(input)) { // check if input matches the format
          return true;
        } else {
          messageUI.displayFormatErrorMessage(format);
        }
      } else {
        messageUI.displayMustBeNumericErrorMessage();
      }
    }
    return false;
  }
  
  public boolean validatePatientName(String input, char[] options, int length) {
    boolean inputError = false;
    if (input.isEmpty()) { // check if input is empty
      messageUI.displayInputErrorMessage();
      return false;
    }
    if (!input.isEmpty()) { // check if input is not empty
      if (input.matches(".*[A-Z]{2,}.*")) { // check if input contains at least 2 consecutive uppercase letters
        messageUI.displayNameErrorMessage();
        inputError = true;
      } else {
        input = utilityUI.removeSpaces(input);
        if (validation.checkIsContainSpecialCharacter(input)) { // check if input contains special characters
          messageUI.displayCannotContainSpecialCharacterErrorMessage();
          return false;
        }
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
        } else {
          return true;
        }
      }
    }
    return false;
  }
  
  public boolean validatePatientGender(String input, char[] options) {
    if (input.isEmpty()) { // check if input is empty
      messageUI.displayInputErrorMessage();
      return false;
    }
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
    if (input.isEmpty()) { // check if input is empty
      messageUI.displayInputErrorMessage();
      return false;
    }
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
    if (input.isEmpty()) { // check if input is empty
      messageUI.displayInputErrorMessage();
      return false;
    }
    if (validation.checkIsCharInput(input)) { // check if input is a char
      if (validation.checkIsInputOptions(options, input)) { // check if input is in the options
        return true;
      } else {
        messageUI.displayInputOptionErrorMessage(options);
      }
    } else {
      if (validation.checkContactNumberFormat(input)) { // check if input matches the format
        return true;
      } else {
        messageUI.displayFormatErrorMessage(format);
      }
    }
    return false;
  }

  public boolean validatePatientEmail(String input, char[] options, String format) { // TODO: add check to ensure at least a letter is present between '@' and '.'
    if (input.isEmpty()) { // check if input is empty
      messageUI.displayInputErrorMessage();
      return false;
    }
    if (validation.checkIsCharInput(input)) { // check if input is a char
      if (validation.checkIsInputOptions(options, input)) { // check if input is in the options
        return true;
      } else {
        messageUI.displayInputOptionErrorMessage(options);
      }
    } else {
      if (validation.checkEmailFormat(input)) { // check if input matches the format
        return true;
      } else {
        messageUI.displayFormatErrorMessage(format);
      }
    }
    return false;
  }

  public boolean validatePatientAddress(String input, char[] options, int maxLength) {
    if (input.isEmpty()) { // check if input is empty
      messageUI.displayInputErrorMessage();
      return false;
    }
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
    if (input.isEmpty()) { // check if input is empty
      messageUI.displayInputErrorMessage();
      return false;
    }
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
    if (input.isEmpty()) { // check if input is empty
      messageUI.displayInputErrorMessage();
      return false;
    }
    if (validation.checkIsCharInput(input)) { // check if input is a char
      if (validation.checkIsInputOptions(options, input)) { // check if input is in the options
        return true;
      }
    }
    messageUI.displayInputOptionErrorMessage(options);
    return false;
  }

}
