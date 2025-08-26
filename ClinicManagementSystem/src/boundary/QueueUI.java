package boundary;

import adt.Queue;
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

  public String[] runGetQueueNumber() {

    final char[] INPUT_OPTIONS = { '1', '0' };

    System.out.println();

    Utilities.clearScreen();

    System.out.println("+--------------------+");
    System.out.println("| QUEUE REGISTRATION |");
    System.out.println("+--------------------+");

    System.out.println();

    System.out.println("[1] Get Number");
    System.out.println("[0] Cancel");

    System.out.println();

    System.out.print("> ");
    return new String[] { Utilities.getUserInput().nextLine().trim(), String.valueOf(INPUT_OPTIONS) };
  }
  
  public void runQueueNumberDisplay(int queueNumber) {
    
    System.out.println();

    Utilities.clearScreen();

    System.out.println("+------------------+");
    System.out.println("|   QUEUE NUMBER   |");
    System.out.println("+------------------+");
    System.out.println();

    System.out.printf("|       %04d       |\n", queueNumber);
    System.out.println("+------------------+");

    System.out.println();
    Utilities.pressAnyKey();
  }

  public String[] runQueueNumberDisplay(Queue<String[]> queue, String patientId, int queueNumber, int numberOfCounters) {

    Queue<String[]> tempQueue = queue;

    final char[] INPUT_OPTIONS = { '0' };

    System.out.println();

    Utilities.clearScreen();

    System.out.println("+==========================================+");
    System.out.println("|              CURRENT NUMBER              |");
    System.out.println("+==========================================+");

    
    for (int i = 0; i < numberOfCounters; i += 2) {
      String[] currentQueue = tempQueue.remove();
      System.out.printf("| Counter %03d | %04d |", i + 1, currentQueue[0]);
      currentQueue = tempQueue.remove();
      if ((((numberOfCounters - 1) - i) % 2) == 0) {
        System.out.printf("| Counter %03d | %04d |", i + 2, currentQueue[0]);
      } else {
        System.out.printf("|   Counter Closed!   |");
      }
      System.out.println("+==========================================+");
    }

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
