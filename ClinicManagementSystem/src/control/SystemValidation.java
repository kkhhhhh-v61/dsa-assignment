package control;

import adt.RedBlackTreeMap;
import dao.PatientDAO;
import entity.Patient;
import utility.MessageUI;
import utility.Validation;

/**
 *
 * @author Tan Kai Hao
 */
public class SystemValidation {
  static PatientDAO patientDAO = new PatientDAO();
  static RedBlackTreeMap<String, Patient> patientMap;

  public SystemValidation() {
    patientMap = patientDAO.readFromFile();
  }
  
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
    if (patientMap == null) {
      return false;
    }

    return patientMap.containsKey("P" + id);
  }

  public static boolean validatePatientIdLogin(String input, char[] inputOptions, int length) {
    if (input.equals("")) {
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
}
