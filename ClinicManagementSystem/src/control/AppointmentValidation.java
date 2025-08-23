package control;

import adt.ListInterface;
import adt.RedBlackTreeMap;
import entity.Doctor;
import entity.Patient;
import utility.MessageUI;
import utility.Validation;

/**
 *
 * @author Tan Kai Hao
 */
public class AppointmentValidation {
  public static String[] validatePatientIdExistence(RedBlackTreeMap<String, Patient> patientMap, String input,
      char[] inputOptions) {
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
  
  public static String[] validateDoctorIdExistence(ListInterface<Doctor> doctorList, String input, char[] inputOptions) {
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
        if (Validation.isValidId(doctorList, "D" + input)) {
          response = "D" + input;
          valid = true;
        } else {
          MessageUI.notValidId("D" + input);
          valid = false;
        }
      }
    }

    return new String[] { String.valueOf(valid), response };
  }
}
