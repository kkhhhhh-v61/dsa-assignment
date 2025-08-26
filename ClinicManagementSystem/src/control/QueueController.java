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
  
  int lastQueueNumber;
  
  public QueueController() {
    patientQueue = new Queue<String[]>();

    patientQueue = queueDAO.retrieveFromFile();

    if (!patientQueue.isEmpty()) {
      // lastQueueNumber = Integer.parseInt(patientQueue.getLast()[0]);
    }
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
          switch (vResponse[1]) {
            case "1":
              runGetQueueNumber(patientId);
              break;
            case "2":
              runQueueNumberDisplay(patientId);
              break;
          }
        }
      } else {
        
      }
    } while (!exit);
  }

  public void runGetQueueNumber(String patientId) {
    
    boolean exit = false;
    String[] response;
    String[] vResponse;

    do {
      response = queueUI.runGetQueueNumber();
      vResponse = QueueValidation.validateQueueMenu(response[0], response[1].toCharArray());

      if (Boolean.parseBoolean(vResponse[0])) {
        if (!vResponse[1].equals("0")) {
          
        }
      } else {
        
      }
    } while (!exit);
  }

  public void runQueueNumberDisplay(String patientId) {
    
  }
}
