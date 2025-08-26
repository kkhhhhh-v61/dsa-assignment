package control;

import utility.MessageUI;
import utility.Validation;

/**
 *
 * @author Tan Kai Hao
 */
public class QueueValidation {
  
  public static String[] validateQueueMenu(String userInput, char[] inputOptions) {
    
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
