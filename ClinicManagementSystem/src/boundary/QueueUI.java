package boundary;

import utility.Utilities;

/**
 *
 * @author Tan Kai Hao
 */
public class QueueUI {
  
  public String[] runStudentQueueMenu() {

    final char[] INPUT_OPTIONS = { '1', '2', '0' };

    System.out.println();

    Utilities.clearScreen();

    System.out.println("+--------------------+");
    System.out.println("| QUEUE REGISTRATION |");
    System.out.println("+--------------------+");
    System.out.println("| [1] Get Number     |");
    System.out.println("| [2] View Queue     |");
    System.out.println("| [0] Cancel         |");
    System.out.println("+--------------------+");

    System.out.println();

    System.out.print("> ");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS) };
  }

  public String[] runQueueNumberDisplay(int queueNumber) {

    final char[] INPUT_OPTIONS = { '0' };

    System.out.println();

    Utilities.clearScreen();

    System.out.println("| ");

    System.out.println();

    System.out.println("+------------------+");
    System.out.println("|   QUEUE NUMBER   |");
    System.out.println("+------------------+");
    System.out.printf("|       %04d       |\n", queueNumber);
    System.out.println("+------------------+");
    
    System.out.println();
    
    System.out.print("> ");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS) };
  }
}
