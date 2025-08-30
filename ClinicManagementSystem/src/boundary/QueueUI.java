package boundary;

import adt.Queue;
import utility.Utilities;
import java.util.Iterator;

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

  public char[] displayQueueManagement() {
    System.out.println();
    System.out.println("+------------------+");
    System.out.println("| QUEUE MANAGEMENT |");
    System.out.println("+------------------+");
    System.out.println("| [1] View Queue   |");
    System.out.println("| [2] Call Next    |");
    System.out.println("| [3] Clear Queue  |");
    System.out.println("| [0] Return       |");
    System.out.println("+------------------+");

    return new char[] { '1', '2', '3', '0' };
  }
  
  public void displayQueueList(Queue<String[]> waitingQueue, String[] servingQueue) {
    System.out.println();
    System.out.println("+===================+===================+");
    System.out.println("|    NOW SERVING    |      NEXT UP      |");
    System.out.println("+-------------------+-------------------+");
    String servingQid = (servingQueue.length == 0) ? " N/A " : String.format("Q%04d", Integer.parseInt(servingQueue[0]));
    String servingPid = (servingQueue.length == 0) ? " N/A " : servingQueue[1];
    Iterator<String[]> iterator = waitingQueue.iterator();
    String[] nextUp = (iterator.hasNext()) ? iterator.next() : new String[] {};
    String nextUpQid = (nextUp.length == 0) ? " N/A " : String.format("Q%04d", Integer.parseInt(nextUp[0]));
    String nextUpPid = (nextUp.length == 0) ? " N/A " : nextUp[1];
    System.out.printf("|   %5s : %5s   |   %5s : %5s   |\n", servingQid, servingPid, nextUpQid, nextUpPid);
    System.out.println("+===================+===================+");
    System.out.println("|             WAITING QUEUE             |");
    if (!iterator.hasNext()) {
      System.out.println("+---------------------------------------+");
      System.out.println("|   Waiting Queue is currently empty.   |");
      System.out.println("+=======================================+");
      return;
    }
    while (iterator.hasNext()) {
      String[] waiting = iterator.next();
      String waiting1Qid = String.format("Q%04d", Integer.parseInt(waiting[0]));
      String waiting1Pid = waiting[1];
      String waiting2Qid = " N/A ";
      String waiting2Pid = " N/A ";
      if (iterator.hasNext()) {
        waiting = iterator.next();
        waiting2Qid = String.format("Q%04d", Integer.parseInt(waiting[0]));
        waiting2Pid = waiting[1];
      }
      System.out.println("+-------------------+-------------------+");
      System.out.printf("|   %5s : %5s   |   %5s : %5s   |\n", waiting1Qid, waiting1Pid, waiting2Qid, waiting2Pid);
    }
    System.out.println("+===================+===================+");
  }

  public char[] displayQueueClearance() {
    System.out.println();
    System.out.println("+-------------------+");
    System.out.println("|  QUEUE CLEARANCE  |");
    System.out.println("+-------------------+");

    displayOptionsInfo(new String[] { "[1] Confirm", "[0] Cancel" });

    return new char[] { '1', '0' };
  }
  
  public char[] displayStudentQueueMenu() {
    System.out.println();
    System.out.println("+-------------------+");
    System.out.println("|   PATIENT QUEUE   |");
    System.out.println("+-------------------+");
    System.out.println("| [1] Get Number    |");
    System.out.println("| [2] View Queue    |");
    System.out.println("| [0] Return        |");
    System.out.println("+-------------------+");

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
    String serving, waiting, id;
    System.out.println();
    System.out.println("+===================+===================+");
    System.out.println("|    NOW SERVING    |   WAITING QUEUE   |");
    System.out.println("+-------------------+-------------------+");
    serving = (servingQueue.length == 0) ? " N/A " : String.format("Q%04d", Integer.parseInt(servingQueue[0]));
    Iterator<String[]> iterator = waitingQueue.iterator();
    waiting = (iterator.hasNext()) ? String.format("Q%04d", Integer.parseInt(iterator.next()[0])) : " N/A ";
    System.out.printf("|       %5s       |       %5s       |\n", serving, waiting);
    waiting = (iterator.hasNext()) ? String.format("Q%04d", Integer.parseInt(iterator.next()[0])) : " N/A ";
    System.out.printf("+===================+       %5s       |\n", waiting);
    waiting = (iterator.hasNext()) ? String.format("Q%04d", Integer.parseInt(iterator.next()[0])) : " N/A ";
    System.out.printf("|    YOUR NUMBER    |       %5s       |\n", waiting);
    waiting = (iterator.hasNext()) ? String.format("Q%04d", Integer.parseInt(iterator.next()[0])) : " N/A ";
    System.out.printf("+-------------------+       %5s       |\n", waiting);
    id = (idQueue.length == 0) ? " N/A " : String.format("Q%04d", Integer.parseInt(idQueue[0]));
    System.out.printf("|       %5s       |    %3d more...    |\n", id, ((waitingQueue.size() - 4) < 0) ? 0 : (waitingQueue.size() - 4));
    System.out.println("+===================+===================+");
  }
}
