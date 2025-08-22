package utility;

import java.util.Scanner;

/**
 *
 * @author Tan Kai Hao
 */
public class Utilities {

  public static void wait(int milliseconds) {
    try {
      Thread.sleep(milliseconds);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  
  public static void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  public static Scanner getUserInput() {
    return new Scanner(System.in);
  }

  public static String formatIcNumber(String icNumber) {
    if (!icNumber.isEmpty()) {
      icNumber = icNumber.replaceAll("[\\s-]", "");
      icNumber = icNumber.replaceAll("(\\d{6})(\\d{2})(\\d{4})", "$1-$2-$3");
    }
    return icNumber;
  }

  public static String formatName(String name) {
    if (!name.isEmpty()) {
      StringBuilder capitalizeName = new StringBuilder();
      String[] words = name.split("\\s+");
      for (String word : words) {
        if (!word.isEmpty()) {
          capitalizeName.append(Character.toUpperCase(word.charAt(0)) + word.substring(1));
        }
      }
      return capitalizeName.toString().replaceAll("(?<!^)(?=[A-Z])", " ");
    }
    return name;
  }

  public static String formatContactNumber(String contactNumber) {
    if (!contactNumber.isEmpty()) {
      contactNumber = contactNumber.replaceAll("[\\s-]", "");
      if (contactNumber.length() == 10) {
        contactNumber = contactNumber.replaceAll("(\\d{3})(\\d{3})", "$1-$2 ");
      } else if (contactNumber.length() == 11) {
        contactNumber = contactNumber.replaceAll("(\\d{3})(\\d{4})", "$1-$2 ");
      }
    }
    return contactNumber;
  }
}
