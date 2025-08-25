package control;

import adt.ArrayList;
import adt.RedBlackTreeMap;
import entity.Patient;
import utility.Enum.BloodType;
import utility.Enum.Gender;
import utility.MessageUI;
import utility.Utilities;
import utility.Validation;
import java.time.LocalDate;

/**
 *
 * @author Tan Kai Hao
 */
public class PatientValidation {

  public static String[] validatePatientId(RedBlackTreeMap<String, Patient> patientMap, String userInput,
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
        if (Validation.isNumeric(userInput)) {
          if (Validation.isValidId(patientMap, "P" + userInput)) {
            response = "P" + userInput;
            valid = true;
          } else {
            MessageUI.notValidId("P" + userInput);
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

  public static String[] validateSearchCategory(String userInput, char[] inputOptions) {

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

  public static String[] validatePatientIdSearch(RedBlackTreeMap<String, Patient> patientMap, String userInput,
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
        if (Validation.isNumeric(userInput)) {
          if (validSearch(patientMap, "Patient ID", "P" + userInput)) {
            response = "P" + userInput;
            valid = true;
          } else {
            MessageUI.searchValueNotFound("Patient ID", "P" + userInput);
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

  public static String[] validateIcNumberSearch(RedBlackTreeMap<String, Patient> patientMap, String userInput,
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
        userInput = userInput.replaceAll("\\s-", "");
        if (Validation.isNumeric(userInput)) {
          if (Validation.isIcNumberFormat(userInput)) {
            if (Validation.isValidIcBirthDate(userInput)) {
              if (validSearch(patientMap, "IC Number", Utilities.formatIcNumber(userInput))) {
                response = userInput;
                valid = true;
              } else {
                MessageUI.searchValueNotFound("IC Number", Utilities.formatIcNumber(userInput));
                valid = false;
              }
            } else {
              MessageUI.notValidIcBirthDate();
              valid = false;
            }
          } else {
            MessageUI.notIcNumberFormat();
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

  public static String[] validateNameSearch(RedBlackTreeMap<String, Patient> patientMap, String userInput,
      char[] inputOptions, int maxLength) {

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
        userInput = Utilities.formatName(userInput);
        if (Validation.isExceedLength(userInput, maxLength)) {
          if (validSearch(patientMap, "Name", userInput)) {
            response = userInput;
            valid = true;
          } else {
            MessageUI.searchValueNotFound("Name", userInput);
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

  public static String[] validateGenderSearch(RedBlackTreeMap<String, Patient> patientMap, String userInput,
      char[] inputOptions) {

    boolean valid = false;
    String response = userInput;

    if (!userInput.equals("")) {
      if (Validation.isSingleChar(userInput)) {
        char option = Character.toUpperCase(userInput.charAt(0));

        if (Validation.isValidOption(option, inputOptions)) {
          if (userInput.equals("1") || userInput.equals("2")) {
            switch (userInput) {
              case "1":
                userInput = "Male";
                break;
              case "2":
                userInput = "Female";
                break;
            }
            if (validSearch(patientMap, "Gender", userInput)) {
              response = userInput;
              valid = true;
            } else {
              MessageUI.searchValueNotFound("Gender", userInput);
              valid = false;
            }
          } else {
            MessageUI.notValidOption(inputOptions);
            valid = false;
          }
        }
      } else {
        MessageUI.notSingleChar();
        valid = false;
      }
    }

    return new String[] { String.valueOf(valid), response };
  }

  public static String[] validateBirthDateSearch(RedBlackTreeMap<String, Patient> patientMap, String userInput,
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
        userInput = userInput.replaceAll("\\s-", "");
        if (Validation.isNumeric(userInput)) {
          if (Validation.isValidDate(userInput)) {
            userInput = String.valueOf(Utilities.formatDate(userInput));
            if (validSearch(patientMap, "Birth Date", userInput)) {
              response = userInput;
              valid = true;
            } else {
              MessageUI.searchValueNotFound("Birth Date", userInput);
              valid = false;
            }
          } else {
            MessageUI.notValidDate();
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

  public static String[] validateAgeSearch(RedBlackTreeMap<String, Patient> patientMap, String userInput,
      char[] inputOptions) {

    boolean valid = false;
    String response = userInput;

    if (!userInput.equals("")) {
      if (Validation.isNumeric(userInput)) {
        if (!userInput.equals("0")) {
          if (validSearch(patientMap, "Age", userInput)) {
            response = userInput;
            valid = true;
          } else {
            MessageUI.searchValueNotFound("Age", userInput);
            valid = false;
          }
        } else {
          response = userInput;
          valid = true;
        }
      } else {
        MessageUI.notNumeric();
        valid = false;
      }
    }

    return new String[] { String.valueOf(valid), response };
  }

  public static String[] validateContactNumberSearch(RedBlackTreeMap<String, Patient> patientMap, String userInput,
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
        userInput = userInput.replaceAll("[\\s-]", "");
        if (Validation.isNumeric(userInput)) {
          if (Validation.isContactNumberFormat(userInput)) {
            userInput = String.valueOf(Utilities.formatContactNumber(userInput));
            if (validSearch(patientMap, "Contact Number", userInput)) {
              response = userInput;
              valid = true;
            } else {
              MessageUI.searchValueNotFound("Contact Number", userInput);
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
  
  public static String[] validateEmailSearch(RedBlackTreeMap<String, Patient> patientMap, String userInput,
      char[] inputOptions, int maxLength) {

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
          if (Validation.isEmailFormat(userInput)) {
            if (validSearch(patientMap, "Email", userInput)) {
              response = userInput;
              valid = true;
            } else {
              MessageUI.searchValueNotFound("Email", userInput);
              valid = false;
            }
          } else {
            MessageUI.notEmailFormat();
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
  
  public static String[] validateAddressSearch(RedBlackTreeMap<String, Patient> patientMap, String userInput,
      char[] inputOptions, int maxLength) {

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
          if (validSearch(patientMap, "Address", userInput)) {
            response = userInput;
            valid = true;
          } else {
            MessageUI.searchValueNotFound("Address", userInput);
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

  public static String[] validateRegistrationDateSearch(RedBlackTreeMap<String, Patient> patientMap, String userInput,
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
        userInput = userInput.replaceAll("\\s-", "");
        if (Validation.isNumeric(userInput)) {
          if (Validation.isValidDate(userInput)) {
            userInput = String.valueOf(Utilities.formatDate(userInput));
            if (validSearch(patientMap, "Registration Date", userInput)) {
              response = userInput;
              valid = true;
            } else {
              MessageUI.searchValueNotFound("Registration Date", userInput);
              valid = false;
            }
          } else {
            MessageUI.notValidDate();
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
  
  public static String[] validateBloodTypeSearch(RedBlackTreeMap<String, Patient> patientMap, String userInput,
      char[] inputOptions) {

    boolean valid = false;
    String response = userInput;

    if (!userInput.equals("")) {
      if (Validation.isSingleChar(userInput)) {
        char option = Character.toUpperCase(userInput.charAt(0));

        if (Validation.isValidOption(option, inputOptions)) {
          if (option != '0') {
            switch (option) {
              case '1':
                userInput = "A";
                break;
              case '2':
                userInput = "B";
                break;
              case '3':
                userInput = "AB";
                break;
              case '4':
                userInput = "O";
                break;
            }
            if (validSearch(patientMap, "Blood Type", userInput)) {
              response = userInput;
              valid = true;
            } else {
              MessageUI.searchValueNotFound("Blood Type", userInput);
              valid = false;
            }
          } else {
            response = userInput;
            valid = true;
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

  public static String[] validatePatientSearchMenu(String userInput, char[] inputOptions) {

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

  public static boolean validSearch(RedBlackTreeMap<String, Patient> patientMap, String searchCategory, String userInput) {
    ArrayList<String> keys = patientMap.getKeys();
    for (int i = 1; i <= keys.size(); i++) {
      Patient patient = patientMap.get(keys.get(i));
      if (searchCategory.equals("Patient ID")) {
        if (patient.getPatientId().equals(userInput)) {
          return true;
        }
      } else if (searchCategory.equals("IC Number")) {
        if (patient.getIcNumber().equals(userInput)) {
          return true;
        }
      } else if (searchCategory.equals("Name")) {
        if (patient.getName().equals(userInput)) {
          return true;
        }
      } else if (searchCategory.equals("Gender")) {
        if (patient.getGender().equals(Gender.valueOf(userInput))) {
          return true;
        }
      } else if (searchCategory.equals("Birth Date")) {
        if (patient.getBirthDate().toString().equals(userInput)) {
          return true;
        }
      } else if (searchCategory.equals("Age")) {
        if (patient.getAge() == Integer.parseInt(userInput)) {
          return true;
        }
      } else if (searchCategory.equals("Contact Number")) {
        if (patient.getContactNumber().equals(userInput)) {
          return true;
        }
      } else if (searchCategory.equals("Email")) {
        if (patient.getEmail().equals(userInput)) {
          return true;
        }
      } else if (searchCategory.equals("Address")) {
        if (patient.getAddress().equals(userInput)) {
          return true;
        }
      } else if (searchCategory.equals("Registration Date")) {
        if (patient.getRegistrationDate().equals(LocalDate.parse(userInput))) {
          return true;
        }
      } else if (searchCategory.equals("Blood Type")) {
        if (patient.getBloodType().equals(BloodType.valueOf(userInput))) {
          return true;
        }
      }
    }
    return false;
  }
}

