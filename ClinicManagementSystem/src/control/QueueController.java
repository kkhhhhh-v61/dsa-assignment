package control;

import adt.Queue;
import boundary.QueueUI;
import dao.QueueDAO;
import utility.Enum.Status;
import utility.MessageUI;
import utility.Utilities;
import java.util.Iterator;

/**
 *
 * @author Tan Kai Hao
 */
public class QueueController {
  QueueDAO queueDAO = new QueueDAO();
  QueueUI queueUI = new QueueUI();

  Queue<String[]> patientQueue;

  public QueueController() {
    patientQueue = queueDAO.readFromFile();
    if (patientQueue == null) patientQueue = new Queue<>();
  }
  
  public void loadAllData() {
    new QueueController();
  }

  public void runQueueManagement(String patientId) {
    do {
      Utilities.clearScreen();
      char[] inputOptions = queueUI.displayStudentQueueMenu();
      String userInput = queueUI.getGeneralInput();
      boolean valid = QueueValidation.validateCharInput(userInput, inputOptions);

      if (valid) {
        switch (userInput.charAt(0)) {
          case '1':
            runGetQueueNumber(patientId);
            continue;
          case '2':
            runViewQueue(patientId);
            continue;
          case '0':
            return;
        }
      }
    } while (true);
  }
  
  public void runGetQueueNumber(String patientId) {
    do {
      Utilities.clearScreen();
      char[] inputOptions = queueUI.displayQueueNumberRegistrationMenu();
      String userInput = queueUI.getGeneralInput();
      boolean valid = QueueValidation.validateCharInput(userInput, inputOptions);

      if (valid) {
        switch (userInput.charAt(0)) {
          case '1':
            String[] lastEntry = patientQueue.peekLast();
            int queueNumber;
            if (lastEntry == null) {
              queueNumber = 0;
            } else {
              queueNumber = Integer.parseInt(lastEntry[0]);
            }
            String[] newEntry = new String[] { String.valueOf(queueNumber + 1), patientId, Status.Waiting.toString() };
            patientQueue.add(newEntry);
            queueUI.displayQueueNumber(queueNumber + 1);
            queueDAO.writeToFile(patientQueue);
            Utilities.pressAnyKey();
            return;
          case '0':
            return;
        }
      }
    } while (true);
  }

  public void runViewQueue(String patientId) {
    loadAllData();
    if (patientQueue.isEmpty()) {
      MessageUI.emptyQueue();
      return;
    }

    Queue<String[]> filteredQueue = new Queue<>();
    for (String[] entry : patientQueue) {
      if (entry[2].equals(Status.Waiting.toString()) || entry[2].equals(Status.Serving.toString())) {
        filteredQueue.add(entry);
      }
    }

    Iterator<String[]> iterator = filteredQueue.iterator();
    String[] idQueue = new String[]{};
    String[] servingQueue = new String[]{};
    while (iterator.hasNext()) {
      String[] entry = iterator.next();
      if (entry[2].equals(Status.Serving.toString())) {
        servingQueue = entry;
      }
      if (entry[1].equals(patientId)) {
        idQueue = entry;
        break;
      }
    }
    
    Utilities.clearScreen();
    queueUI.displayCurrentQueue(filteredQueue, servingQueue, idQueue);
    Utilities.pressAnyKey();
  }
}
