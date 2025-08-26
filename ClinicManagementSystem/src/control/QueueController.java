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
    String[] response;
    String[] vResponse;

    do {
      response = queueUI.runStudentQueueMenu();
      vResponse = QueueValidation.validateQueueMenu(response[0], response[1].toCharArray());

      if (Boolean.parseBoolean(vResponse[0])) {
        if (!vResponse[1].equals("0")) {
          
        }
      } else {
        
      }
    } while (!exit);
  }
}
