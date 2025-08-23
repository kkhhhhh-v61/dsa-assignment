package control;

import adt.RedBlackTreeMap;
import entity.Patient;
import utility.MessageUI;
import utility.Validation;

/**
 *
 * @author Tan Kai Hao
 */
public class AppointmentValidation {
  public static String[] validatePatientIdExistence(RedBlackTreeMap<String, Patient> patientMap, String input, char[] inputOptions) {
    boolean valid = false;
    String response = input;

    if (input != "") {
      if (Validation.isSingleChar(input)) {
        char option = input.charAt(0);

        if (Validation.isValidOption(option, inputOptions)) {
          response = String.valueOf(option);
          valid = true;
        } else {
          MessageUI.notValidOption(inputOptions);
          valid = false;
        }
      } else {
        if (Validation.isValidId(patientMap, "P" + input)) {
          response = "P" + input;
          valid = true;
        } else {
          MessageUI.notValidId("P" + input);
          valid = false;
        }
      }
    }

    return new String[] { String.valueOf(valid), response };
  }
}
