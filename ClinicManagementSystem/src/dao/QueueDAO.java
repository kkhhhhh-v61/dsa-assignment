package dao;

import adt.Queue;
import adt.QueueInterface;
import utility.MessageUI;
// import utility.Utilities;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author Tan Kai Hao
 */
public class QueueDAO {

  static File dataFile = new File("./data/queue.dat");
  
  public QueueDAO() {
    try {
      if (!dataFile.exists()) {
        // System.out.println("File: 'queue.dat' doesn't exist. Creating it now...");
        dataFile.getParentFile().mkdirs();
        dataFile.createNewFile();
        // System.out.println("File: 'queue.dat' created successfully.");
        // Utilities.wait(1000);
      }
    } catch (IOException e) {
      // System.out.println("File: 'queue.dat' creation failed.");
      // Utilities.wait(1000);
      e.printStackTrace();
      MessageUI.pressAnyKey();
    }
  }


  public void writeToFile(Queue<String[]> queue) {
    try {
      ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataFile));
      oos.writeObject(queue);
      oos.close();
    } catch (FileNotFoundException e) {
      // System.out.println("File: 'queue.dat' not found.");
      // Utilities.wait(1000);
      e.printStackTrace();
      MessageUI.pressAnyKey();
    } catch (IOException e) {
      // System.out.println("File: 'queue.dat' writing failed.");
      // Utilities.wait(1000);
      e.printStackTrace();
      MessageUI.pressAnyKey();
    }
  }

  @SuppressWarnings("unchecked")
  public QueueInterface<String[]> readFromFile() {

    QueueInterface<String[]> queue = new Queue<>();

    try {
      ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataFile));
      queue = (QueueInterface<String[]>) ois.readObject();
      ois.close();
    } catch (FileNotFoundException e) {
      // System.out.println("File: 'queue.dat' not found.");
      // Utilities.wait(1000);
      e.printStackTrace();
      MessageUI.pressAnyKey();
    } catch (IOException e) {
      // System.out.println("File: 'queue.dat' reading failed.");
      // Utilities.wait(1000);
      e.printStackTrace();
      MessageUI.pressAnyKey();
    } catch (ClassNotFoundException e) {
      // System.out.println("File: 'queue.dat' reading failed.");
      // Utilities.wait(1000);
      e.printStackTrace();
      MessageUI.pressAnyKey();
    }
    return queue;
  }
  
  public void clearFile() {
    try {
      dataFile.delete();
      dataFile.getParentFile().mkdirs();
      dataFile.createNewFile();
      // System.out.println("File: 'queue.dat' cleared successfully.");
      // Utilities.wait(1000);
    } catch (Exception e) {
      // System.out.println("File: 'queue.dat' clearing failed.");
      // Utilities.wait(1000);
      e.printStackTrace();
      MessageUI.pressAnyKey();
    }
  }
}
