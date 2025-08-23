package utility;

// import java.awt.AWTException;
// import java.awt.Robot;
// import java.awt.event.KeyEvent;
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

  // Clear screen using escape sequence (for full system consoles)
  public static void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  // Clear screen using Robot class to handle key press (for NetBeans console)
  // public static void clearScreen() {
  //   try {
  //     Robot rob = new Robot();
  //     try {
  //       rob.keyPress(KeyEvent.VK_CONTROL); // press "CTRL"
  //       rob.keyPress(KeyEvent.VK_L); // press "L"
  //       rob.keyRelease(KeyEvent.VK_L); // unpress "L"
  //       rob.keyRelease(KeyEvent.VK_CONTROL); // unpress "CTRL"
  //       Thread.sleep(100); // add delay to prevent system from automatically stopping after clear
  //     } catch (InterruptedException e) {
  //       e.printStackTrace();
  //     }
  //   } catch (AWTException e) {
  //     e.printStackTrace();
  //   }
  // }

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
