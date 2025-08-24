package control;

import adt.RedBlackTreeMap;
import entity.Patient;
import utility.MessageUI;
import utility.Utilities;
import utility.Validation;

/**
 *
 * @author Tan Kai Hao
 */
public class PatientValidation {

  public static String[] validatePatientSearch(RedBlackTreeMap<String, Patient> patientMap, String userInput,
      char[] inputOptions) {
    
    boolean valid = false;
    String response = userInput;

    if (!userInput.equals("")) {
      if (Validation.isSingleChar(userInput)) {
        char option = Character.toUpperCase(userInput.charAt(0));

        if (Validation.isValidOption(option, inputOptions)) {
          response = String.valueOf(option);
          valid = true;
        } else {
          MessageUI.notValidOption(inputOptions);
          valid = false;
        }
      } else {
        if (Validation.isValidId(patientMap, "P" + userInput)) {
          response = "P" + userInput;
          valid = true;
        } else {
          MessageUI.notValidId("P" + userInput);
          valid = false;
        }
      }
    }

    return new String[] { String.valueOf(valid), response };
  }

  public static String[] validateIcNumber(RedBlackTreeMap<String, Patient> patientMap, String userInput,
      char[] inputOptions) {
    
    boolean valid = false;
    String response = userInput;

    if (!userInput.equals("")) {
      if (Validation.isSingleChar(userInput)) {
        char option = Character.toUpperCase(userInput.charAt(0));

        if (Validation.isValidOption(option, inputOptions)) {
          response = String.valueOf(option);
          valid = true;
        } else {
          MessageUI.notValidOption(inputOptions);
          valid = false;
        }
      } else {
        if (Validation.isIcNumberFormat(userInput)) {
          userInput = userInput.replace("-", "");
          if (Validation.isNumeric(userInput)) {
            if (Validation.isValidIcBirthDate(userInput)) {
              if (!patientMap.containsUniqueValue("icNumber", Utilities.formatIcNumber(userInput))) {
                response = userInput;
                valid = true;
              } else {
                MessageUI.notUniqueValue("IC Number", Utilities.formatIcNumber(userInput));
                valid = false;
              }
            } else {
              MessageUI.notValidIcBirthDate();
              valid = false;
            }
          } else {
            MessageUI.notNumeric();
            valid = false;
          }
        } else {
          MessageUI.notIcNumberFormat();
          valid = false;
        }
      }
    }

    return new String[] { String.valueOf(valid), response };
  }
  
  public static String[] validateName(String userInput, char[] inputOptions, int maxLength) {

    boolean valid = false;
    String response = userInput;

    if (!userInput.equals("")) {
      if (Validation.isSingleChar(userInput)) {
        char option = Character.toUpperCase(userInput.charAt(0));

        if (Validation.isValidOption(option, inputOptions)) {
          response = String.valueOf(option);
          valid = true;
        } else {
          MessageUI.notValidOption(inputOptions);
          valid = false;
        }
      } else {
        if (Validation.isExceedLength(userInput, maxLength)) {
          if (Validation.isValidName(userInput)) {
            response = userInput;
            valid = true;
          } else {
            MessageUI.notValidName();
            valid = false;
          }
        } else {
          MessageUI.exceedLength(maxLength);
          valid = false;
        }
      }
    }

    return new String[] { String.valueOf(valid), response };
  }
  
  public static String[] validateContactNumber(String userInput, char[] inputOptions) {

    boolean valid = false;
    String response = userInput;

    if (!userInput.equals("")) {
      if (Validation.isSingleChar(userInput)) {
        char option = Character.toUpperCase(userInput.charAt(0));

        if (Validation.isValidOption(option, inputOptions)) {
          response = String.valueOf(option);
          valid = true;
        } else {
          MessageUI.notValidOption(inputOptions);
          valid = false;
        }
      } else {
        userInput = userInput.replaceAll("[\\s-]", "");
        if (Validation.isNumeric(userInput)) {
          if (Validation.isContactNumberFormat(userInput)) {
            response = userInput;
            valid = true;
          } else {
            MessageUI.notContactNumberFormat();
            valid = false;
          }
        } else {
          MessageUI.notNumeric();
          valid = false;
        }
      }
    }

    return new String[] { String.valueOf(valid), response };
  }

  public static String[] validateEmail(String userInput, char[] inputOptions, int maxLength) {

    boolean valid = false;
    String response = userInput;

    if (!userInput.equals("")) {
      if (Validation.isSingleChar(userInput)) {
        char option = Character.toUpperCase(userInput.charAt(0));

        if (Validation.isValidOption(option, inputOptions)) {
          response = String.valueOf(option);
          valid = true;
        } else {
          MessageUI.notValidOption(inputOptions);
          valid = false;
        }
      } else {
        if (Validation.isEmailFormat(userInput)) {
          if (Validation.isExceedLength(userInput, maxLength)) {
            response = userInput;
            valid = true;
          } else {
            MessageUI.exceedLength(maxLength);
            valid = false;
          }
        } else {
          MessageUI.notEmailFormat();
          valid = false;
        }
      }
    }

    return new String[] { String.valueOf(valid), response };
  }

  public static String[] validateAddress(String userInput, char[] inputOptions, int maxLength) {

    boolean valid = false;
    String response = userInput;

    if (!userInput.equals("")) {
      if (Validation.isSingleChar(userInput)) {
        char option = Character.toUpperCase(userInput.charAt(0));

        if (Validation.isValidOption(option, inputOptions)) {
          response = String.valueOf(option);
          valid = true;
        } else {
          MessageUI.notValidOption(inputOptions);
          valid = false;
        }
      } else {
        if (Validation.isExceedLength(userInput, maxLength)) {
          response = userInput;
          valid = true;
        } else {
          MessageUI.exceedLength(maxLength);
          valid = false;
        }
      }
    }

    return new String[] { String.valueOf(valid), response };
  }

  public static String[] validateBloodType(String userInput, char[] inputOptions) {

    boolean valid = false;
    String response = userInput;

    if (!userInput.equals("")) {
      if (Validation.isSingleChar(userInput)) {
        char option = Character.toUpperCase(userInput.charAt(0));

        if (Validation.isValidOption(option, inputOptions)) {
          response = String.valueOf(option);
          valid = true;
        } else {
          MessageUI.notValidOption(inputOptions);
          valid = false;
        }
      } else {
        MessageUI.notSingleChar();
        valid = false;
      }
    }

    return new String[] { String.valueOf(valid), response };
  }

  public static String[] validateConfirmRegistration(String userInput, char[] inputOptions) {

    boolean valid = false;
    String response = userInput;

    if (!userInput.equals("")) {
      if (Validation.isSingleChar(userInput)) {
        char option = Character.toUpperCase(userInput.charAt(0));

        if (Validation.isValidOption(option, inputOptions)) {
          response = String.valueOf(option);
          valid = true;
        } else {
          MessageUI.notValidOption(inputOptions);
          valid = false;
        }
      } else {
        MessageUI.notSingleChar();
        valid = false;
      }
    }

    return new String[] { String.valueOf(valid), response };
  }

  public static String[] validateRegistrationEdit(String userInput, char[] inputOptions) {

    boolean valid = false;
    String response = userInput;

    if (!userInput.equals("")) {
      if (Validation.isSingleChar(userInput)) {
        char option = Character.toUpperCase(userInput.charAt(0));

        if (Validation.isValidOption(option, inputOptions)) {
          response = String.valueOf(option);
          valid = true;
        } else {
          MessageUI.notValidOption(inputOptions);
          valid = false;
        }
      } else {
        MessageUI.notSingleChar();
        valid = false;
      }
    }

    return new String[] { String.valueOf(valid), response };
  }

  public static String[] validatePatientListMenu(String userInput, char[] inputOptions) {

    boolean valid = false;
    String response = userInput;

    if (!userInput.equals("")) {
      if (Validation.isSingleChar(userInput)) {
        char option = Character.toUpperCase(userInput.charAt(0));

        if (Validation.isValidOption(option, inputOptions)) {
          response = String.valueOf(option);
          valid = true;
        } else {
          MessageUI.notValidOption(inputOptions);
          valid = false;
        }
      } else {
        MessageUI.notSingleChar();
        valid = false;
      }
    }

    return new String[] { String.valueOf(valid), response };
  }

  public static String[] validatePatientEditMenu(String userInput, char[] inputOptions) {

    boolean valid = false;
    String response = userInput;

    if (!userInput.equals("")) {
      if (Validation.isSingleChar(userInput)) {
        char option = Character.toUpperCase(userInput.charAt(0));

        if (Validation.isValidOption(option, inputOptions)) {
          response = String.valueOf(option);
          valid = true;
        } else {
          MessageUI.notValidOption(inputOptions);
          valid = false;
        }
      } else {
        MessageUI.notSingleChar();
        valid = false;
      }
    }

    return new String[] { String.valueOf(valid), response };
  }

  public static String[] validateEditIcNumber(String userInput, char[] inputOptions, String icNumber) {

    boolean valid = false;
    String response = userInput;

    if (!userInput.equals("")) {
      if (Validation.isSingleChar(userInput)) {
        char option = Character.toUpperCase(userInput.charAt(0));

        if (Validation.isValidOption(option, inputOptions)) {
          response = String.valueOf(option);
          valid = true;
        } else {
          MessageUI.notValidOption(inputOptions);
          valid = false;
        }
      } else {
        if (Validation.isIcNumberFormat(userInput)) {
          userInput = userInput.replace("-", "");
          if (Validation.isNumeric(userInput)) {
            if (Validation.isValidIcBirthDate(userInput)) {
              if (!Utilities.formatIcNumber(userInput).equals(icNumber)) {
                response = userInput;
                valid = true;
              } else {
                MessageUI.sameAsCurrent();
                valid = false;
              }
            } else {
              MessageUI.notValidIcBirthDate();
              valid = false;
            }
          } else {
            MessageUI.notNumeric();
            valid = false;
          }
        } else {
          MessageUI.notIcNumberFormat();
          valid = false;
        }
      }
    }

    return new String[] { String.valueOf(valid), response };
  }

  public static String[] validateEditName(String userInput, char[] inputOptions, int maxLength, String name) {

    boolean valid = false;
    String response = userInput;

    if (!userInput.equals("")) {
      if (Validation.isSingleChar(userInput)) {
        char option = Character.toUpperCase(userInput.charAt(0));

        if (Validation.isValidOption(option, inputOptions)) {
          response = String.valueOf(option);
          valid = true;
        } else {
          MessageUI.notValidOption(inputOptions);
          valid = false;
        }
      } else {
        if (Validation.isExceedLength(userInput, maxLength)) {
          if (Validation.isValidName(userInput)) {
            if (!Utilities.formatName(userInput).equals(name)) {
              response = userInput;
              valid = true;
            } else {
              MessageUI.sameAsCurrent();
              valid = false;
            }
          } else {
            MessageUI.notValidName();
            valid = false;
          }
        } else {
          MessageUI.exceedLength(maxLength);
          valid = false;
        }
      }
    }

    return new String[] { String.valueOf(valid), response };
  }
  
  public static String[] validateEditContactNumber(String userInput, char[] inputOptions, String contactNumber) {
    
    boolean valid = false;
    String response = userInput;

    if (!userInput.equals("")) {
      if (Validation.isSingleChar(userInput)) {
        char option = Character.toUpperCase(userInput.charAt(0));

        if (Validation.isValidOption(option, inputOptions)) {
          response = String.valueOf(option);
          valid = true;
        } else {
          MessageUI.notValidOption(inputOptions);
          valid = false;
        }
      } else {
        userInput = userInput.replaceAll("[\\s-]", "");
        if (Validation.isNumeric(userInput)) {
          if (Validation.isContactNumberFormat(userInput)) {
            if (!Utilities.formatContactNumber(userInput).equals(contactNumber)) {
              response = userInput;
              valid = true;
            } else {
              MessageUI.sameAsCurrent();
              valid = false;
            }
          } else {
            MessageUI.notContactNumberFormat();
            valid = false;
          }
        } else {
          MessageUI.notNumeric();
          valid = false;
        }
      }
    }

    return new String[] { String.valueOf(valid), response };
  }
  
  public static String[] validateEditEmail(String userInput, char[] inputOptions, int maxLength, String email) {

    boolean valid = false;
    String response = userInput;

    if (!userInput.equals("")) {
      if (Validation.isSingleChar(userInput)) {
        char option = Character.toUpperCase(userInput.charAt(0));

        if (Validation.isValidOption(option, inputOptions)) {
          response = String.valueOf(option);
          valid = true;
        } else {
          MessageUI.notValidOption(inputOptions);
          valid = false;
        }
      } else {
        if (Validation.isEmailFormat(userInput)) {
          if (Validation.isExceedLength(userInput, maxLength)) {
            if (!userInput.equals(email)) {
              response = userInput;
              valid = true;
            } else {
              MessageUI.sameAsCurrent();
              valid = false;
            }
          } else {
            MessageUI.exceedLength(maxLength);
            valid = false;
          }
        } else {
          MessageUI.notEmailFormat();
          valid = false;
        }
      }
    }

    return new String[] { String.valueOf(valid), response };
  }
  
  public static String[] validateEditAddress(String userInput, char[] inputOptions, int maxLength, String address) {

    boolean valid = false;
    String response = userInput;

    if (!userInput.equals("")) {
      if (Validation.isSingleChar(userInput)) {
        char option = Character.toUpperCase(userInput.charAt(0));

        if (Validation.isValidOption(option, inputOptions)) {
          response = String.valueOf(option);
          valid = true;
        } else {
          MessageUI.notValidOption(inputOptions);
          valid = false;
        }
      } else {
        if (Validation.isExceedLength(userInput, maxLength)) {
          if (!userInput.equals(address)) {
            response = userInput;
            valid = true;
          } else {
            MessageUI.sameAsCurrent();
            valid = false;
          }
        } else {
          MessageUI.exceedLength(maxLength);
          valid = false;
        }
      }
    }

    return new String[] { String.valueOf(valid), response };
  }
  
  public static String[] validateEditBloodType(String userInput, char[] inputOptions, String bloodType) {

    boolean valid = false;
    String response = userInput;

    if (!userInput.equals("")) {
      if (Validation.isSingleChar(userInput)) {
        char option = Character.toUpperCase(userInput.charAt(0));

        if (Validation.isValidOption(option, inputOptions)) {
          String bt = "";
          switch (option) {
            case '1':
              bt = "A";
              break;
            case '2':
              bt = "B";
              break;
            case '3':
              bt = "AB";
              break;
            case '4':
              bt = "O";
              break;
          }
          if (!bt.equals(bloodType)) {
            response = String.valueOf(option);
            valid = true;
          } else {
            MessageUI.sameAsCurrent();
            valid = false;
          }
        } else {
          MessageUI.notValidOption(inputOptions);
          valid = false;
        }
      } else {
        MessageUI.notSingleChar();
        valid = false;
      }
    }

    return new String[] { String.valueOf(valid), response };
  }
  
  public static String[] validateConfirmEdit(String userInput, char[] inputOptions) {

    boolean valid = false;
    String response = userInput;

    if (!userInput.equals("")) {
      if (Validation.isSingleChar(userInput)) {
        char option = Character.toUpperCase(userInput.charAt(0));

        if (Validation.isValidOption(option, inputOptions)) {
          response = String.valueOf(option);
          valid = true;
        } else {
          MessageUI.notValidOption(inputOptions);
          valid = false;
        }
      } else {
        MessageUI.notSingleChar();
        valid = false;
      }
    }

    return new String[] { String.valueOf(valid), response };
  }

  public static String[] validateDeleteMenu(String userInput, char[] inputOptions) {

    boolean valid = false;
    String response = userInput;

    if (!userInput.equals("")) {
      if (Validation.isSingleChar(userInput)) {
        char option = Character.toUpperCase(userInput.charAt(0));

        if (Validation.isValidOption(option, inputOptions)) {
          response = String.valueOf(option);
          valid = true;
        } else {
          MessageUI.notValidOption(inputOptions);
          valid = false;
        }
      } else {
        MessageUI.notSingleChar();
        valid = false;
      }
    }

    return new String[] { String.valueOf(valid), response };
  }

  public static String[] validatePatientClearance(String userInput, char[] inputOptions) {
    
    boolean valid = false;
    String response = userInput;

    if (!userInput.equals("")) {
      if (Validation.isSingleChar(userInput)) {
        char option = Character.toUpperCase(userInput.charAt(0));

        if (Validation.isValidOption(option, inputOptions)) {
          response = String.valueOf(option);
          valid = true;
        } else {
          MessageUI.notValidOption(inputOptions);
          valid = false;
        }
      } else {
        MessageUI.notSingleChar();
        valid = false;
      }
    }

    return new String[] { String.valueOf(valid), response };
  }
}
