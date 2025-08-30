package boundary;

import adt.Queue;
import utility.Utilities;

/**
 *
 * @author Tan Kai Hao
 */
public class QueueUI {
  public String getGeneralInput() {
    System.out.println();
    System.out.print("> ");
    return Utilities.getUserInput().nextLine().trim();
  }

  public void displayOptionsInfo(String[] options) {
    System.out.println();
    for (String option : options) {
      System.out.println(option);
    }
  }
  
  public char[] displayStudentQueueMenu() {
    System.out.println();
    System.out.println("+--------------------+");
    System.out.println("| QUEUE REGISTRATION |");
    System.out.println("+--------------------+");
    System.out.println("| [1] Get Number     |");
    System.out.println("| [2] View Queue     |");
    System.out.println("| [0] Cancel         |");
    System.out.println("+--------------------+");

    return new char[] { '1', '2', '0' };
  }

  public char[] displayQueueNumberRegistrationMenu() {
    System.out.println();
    System.out.println("+--------------------+");
    System.out.println("| QUEUE REGISTRATION |");
    System.out.println("+--------------------+");

    displayOptionsInfo(new String[] { "[1] Get Number", "[0] Cancel" });

    return new char[] { '1', '0' };
  }
  
  public void displayQueueNumber(int queueNumber) {
    System.out.println();
    System.out.println("+------------------+");
    System.out.println("|   QUEUE NUMBER   |");
    System.out.println("+------------------+");
    System.out.printf("|      Q%04d       |\n", queueNumber);
    System.out.println("+------------------+");
  }

  public void displayCurrentQueue(Queue<String[]> waitingQueue, String[] servingQueue, String[] idQueue) {
    Queue<String[]> tempQueue = waitingQueue;
    String current;
    String waiting;
    System.out.println();
    System.out.println("+===================+===================+");
    System.out.println("|    NOW SERVING    |   WAITING QUEUE   |");
    System.out.println("+-------------------+-------------------+");
    current = (servingQueue.length == 0) ? " N/A " : String.format("Q%04d", Integer.parseInt(servingQueue[0]));
    waiting = (tempQueue.isEmpty()) ? " N/A " : String.format("Q%04d", Integer.parseInt(tempQueue.remove()[0]));
    System.out.printf("|       %5s       |       %5s       |\n", current, waiting);
    waiting = (tempQueue.isEmpty()) ? " N/A " : String.format("Q%04d", Integer.parseInt(tempQueue.remove()[0]));
    System.out.printf("+===================+       %5s       |\n", waiting);
    waiting = (tempQueue.isEmpty()) ? " N/A " : String.format("Q%04d", Integer.parseInt(tempQueue.remove()[0]));
    System.out.printf("|    YOUR NUMBER    |       %5s       |\n", waiting);
    waiting = (tempQueue.isEmpty()) ? " N/A " : String.format("Q%04d", Integer.parseInt(tempQueue.remove()[0]));
    System.out.printf("+-------------------+       %5s       |\n", waiting);
    if (idQueue.length == 0) idQueue = new String[] { " N/A ", "", "" };
    System.out.printf("|       %5s       |    %3d more...    |\n", String.format("Q%04d", Integer.parseInt(idQueue[0])), tempQueue.size());
    System.out.println("+===================+===================+");
  }
}
