package control;

import adt.ArrayList;
import adt.RedBlackTreeMap;
import dao.PatientDAO;
import entity.Patient;
import utility.MessageUI;
import utility.Validation;
import utility.Utilities;

/**
 *
 * @author Tan Kai Hao
 */
public class PatientValidation {
  static PatientDAO patientDAO = new PatientDAO();
  static RedBlackTreeMap<String, Patient> patientMap;

  public static void loadAllData() {
    patientMap = patientDAO.readFromFile();
  }
  
  public static boolean validateCharInput(String input, char[] inputOptions) {
    if (input.equals("") || inputOptions == null) {
      return false;
    }

    if (!Validation.isChar(input)) {
      MessageUI.notSingleChar();
      return false;
    }

    if (!Validation.isValidOption(Character.toUpperCase(input.charAt(0)), inputOptions)) {
      MessageUI.invalidOption(inputOptions);
      return false;
    }

    return true;
  }

  public static boolean isIdExists(String id) {
    loadAllData();
    patientMap = patientDAO.readFromFile();
    if (patientMap == null) {
      return false;
    }

    return patientMap.containsKey("P" + id);
  }

  public static boolean validatePatientIdInput(String input, char[] inputOptions, int length) {
    if (input.equals("") || inputOptions == null || length == 0) {
      return false;
    }

    if (!Validation.isChar(input)) {
      if (!Validation.withinLength(input, length)) {
        MessageUI.exceedLength(length);
        return false;
      }

      if (!Validation.isNumeric(input)) {
        MessageUI.notNumeric();
        return false;
      }

      if (!isIdExists(input)) {
        MessageUI.idDoesNotExist();
        return false;
      }

      return true;
    }

    if (!Validation.isValidOption(Character.toUpperCase(input.charAt(0)), inputOptions)) {
      MessageUI.invalidOption(inputOptions);
      return false;
    }

    return true;
  }

  public static boolean isUniqueIcNumber(String icNumber) {
    if (patientMap == null) {
      return true;
    }

    ArrayList<String> keys = patientMap.getKeys();
    for (String key : keys) {
      if (patientMap.get(key).getIcNumber().equals(icNumber)) {
        return false;
      }
    }
    return true;
  }

  public static boolean validateIcNumberInput(String input, char[] inputOptions) {
    if (input.equals("") || inputOptions == null) {
      return false;
    }

    if (!Validation.isChar(input)) {
      input = input.replaceAll("[\\s-]", "");

      if (!Validation.isNumeric(input)) {
        MessageUI.notNumeric();
        return false;
      }

      if (!Validation.isValidIcNumber(Utilities.formatIcNumber(input))) {
        return false;
      }

      if (!isUniqueIcNumber(input)) {
        MessageUI.icNumberExists();
        return false;
      }

      return true;
    }

    if (!Validation.isValidOption(Character.toUpperCase(input.charAt(0)), inputOptions)) {
      MessageUI.invalidOption(inputOptions);
      return false;
    }

    return true;
  }

  public static boolean validateNameInput(String input, char[] inputOptions, int length) {
    if (input.equals("") || inputOptions == null || length == 0) {
      return false;
    }

    if (!Validation.isChar(input)) {
      if (!Validation.withinLength(input, length)) {
        MessageUI.exceedLength(length);
        return false;
      }

      if (!Validation.isValidName(input)) {
        MessageUI.invalidName();
        return false;
      }

      return true;
    }

    if (!Validation.isValidOption(Character.toUpperCase(input.charAt(0)), inputOptions)) {
      MessageUI.invalidOption(inputOptions);
      return false;
    }

    return true;
  }

  public static boolean validateBirthDateInput(String input, char[] inputOptions) {
    if (input.equals("") || inputOptions == null) {
      return false;
    }

    if (!Validation.isChar(input)) {
      if (!Validation.isValidBirthDate(input)) {
        MessageUI.invalidBirthDate();
        return false;
      }

      return true;
    }

    if (!Validation.isValidOption(Character.toUpperCase(input.charAt(0)), inputOptions)) {
      MessageUI.invalidOption(inputOptions);
      return false;
    }

    return true;
  }

  public static boolean validateAgeInput(String input, char[] inputOptions) {
    if (input.equals("") || inputOptions == null) {
      return false;
    }

    if (Integer.parseInt(input) > 0) {
      if (!Validation.isNumeric(input)) {
        MessageUI.notNumeric();
        return false;
      }

      if (!Validation.withinRange(Integer.parseInt(input), 0, 99)) {
        MessageUI.invalidRange(0, 99);
        return false;
      }

      return true;
    }

    if (!Validation.isChar(input) && !Validation.isValidOption(input.charAt(0), inputOptions)) {
      MessageUI.invalidOption(inputOptions);
      return false;
    }

    return true;
  }

  public static boolean validateContactNumberInput(String input, char[] inputOptions) {
    if (input.equals("") || inputOptions == null) {
      return false;
    }

    if (!Validation.isChar(input)) {
      if (!Validation.isNumeric(input)) {
        MessageUI.notNumeric();
        return false;
      }

      input = Utilities.formatContactNumber(input.replaceAll("[\\s-]", ""));
      if (!Validation.isValidContactNumber(input)) {
        MessageUI.invalidContactNumber();
        return false;
      }

      return true;
    }

    if (!Validation.isValidOption(Character.toUpperCase(input.charAt(0)), inputOptions)) {
      MessageUI.invalidOption(inputOptions);
      return false;
    }

    return true;
  }

  public static boolean validateEmailInput(String input, char[] inputOptions, int length) {
    if (input.equals("") || inputOptions == null || length == 0) {
      return false;
    }

    if (!Validation.isChar(input)) {
      if (!Validation.withinLength(input, length)) {
        MessageUI.exceedLength(length);
        return false;
      }

      if (!Validation.isValidEmail(input)) {
        MessageUI.invalidEmail();
        return false;
      }

      return true;
    }

    if (!Validation.isValidOption(Character.toUpperCase(input.charAt(0)), inputOptions)) {
      MessageUI.invalidOption(inputOptions);
      return false;
    }

    return true;
  }

  public static boolean validateAddressInput(String input, char[] inputOptions, int length) {
    if (input.equals("") || inputOptions == null || length == 0) {
      return false;
    }

    if (!Validation.isChar(input)) {
      if (!Validation.withinLength(input, length)) {
        MessageUI.exceedLength(length);
        return false;
      }

      return true;
    }

    if (!Validation.isValidOption(Character.toUpperCase(input.charAt(0)), inputOptions)) {
      MessageUI.invalidOption(inputOptions);
      return false;
    }

    return true;
  }

  public static boolean validateDateInput(String input, char[] inputOptions) {
    if (input.equals("")) {
      return false;
    }

    if (!Validation.isChar(input)) {
      input = input.replaceAll("\\s-", "");
      if (!Validation.isValidDate(input)) {
        MessageUI.invalidDate();
        return false;
      }

      if (Validation.isFutureDate(input)) {
        MessageUI.notFutureDate();
        return false;
      }

      return true;
    }

    if (!Validation.isValidOption(Character.toUpperCase(input.charAt(0)), inputOptions)) {
      MessageUI.invalidOption(inputOptions);
      return false;
    }

    return true;
  }

  public static boolean validateInitializationSizeInput(String input, char[] inputOptions) {
    if (input.equals("")) {
      return false;
    }

    if (!Validation.isChar(input)) {
      if (!Validation.isNumeric(input)) {
        MessageUI.notNumeric();
        return false;
      }

      if (!Validation.withinRange(Integer.parseInt(input), 1, 981)) {
        MessageUI.invalidRange(1, 981);
        return false;
      }

      return true;
    }

    if (!Validation.isValidOption(Character.toUpperCase(input.charAt(0)), inputOptions)) {
      MessageUI.invalidOption(inputOptions);
      return false;
    }

    return true;
  }
}

