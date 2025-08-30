package control;

import utility.MessageUI;
import utility.Validation;

/**
 *
 * @author Tan Kai Hao
 */
public class QueueValidation {
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
}
