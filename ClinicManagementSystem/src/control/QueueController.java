package control;

import adt.Queue;
import adt.QueueInterface;
import dao.QueueDAO;
import boundary.QueueUI;

/**
 *
 * @author Tan Kai Hao
 */
public class QueueController {
  
  QueueUI queueUI = new QueueUI();

  QueueDAO queueDAO = new QueueDAO();

  QueueInterface<String[]> patientQueue;
  
  public QueueController() {
    patientQueue = new Queue<String[]>();

    patientQueue = queueDAO.retrieveFromFile();
  }

  public void runStudentQueue(String patientId) {
    
    boolean exit = false;

    do {
      String[] response = queueUI.runStudentQueueMenu();
      String[] vResponse = QueueValidation.validateQueueMenu(response[0], response[1].toCharArray());

      if (!Boolean.parseBoolean(vResponse[0])) {
        continue;
      }

      String choice = vResponse[1];
      if (choice.equals("0")) {
        exit = true;
        continue;
      }
    } while (!exit);
  }
}
