package dao;

import adt.RedBlackTreeMap;
import entity.Appointment;
// import utility.MessageUI;
import utility.Utilities;
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
public class AppointmentDAO {

  static File dataFile = new File("./data/appointment.dat");
  
  public AppointmentDAO() {
    try {
      if (!dataFile.exists()) {
        System.out.println("File: 'appointment.dat' doesn't exist. Creating it now...");
        dataFile.getParentFile().mkdirs();
        dataFile.createNewFile();
        System.out.println("File: 'appointment.dat' created successfully.");
        Utilities.wait(1000);
      }
    } catch (IOException e) {
      System.out.println("File: 'appointment.dat' creation failed.");
      Utilities.wait(1000);
      // e.printStackTrace();
      // MessageUI.pressAnyKey();
    }
  }


  public void saveToFile(RedBlackTreeMap<String, Appointment> appointmentMap) {
    try {
      ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataFile));
      oos.writeObject(appointmentMap);
      oos.close();
    } catch (FileNotFoundException e) {
      System.out.println("File: 'appointment.dat' not found.");
      Utilities.wait(1000);
      // e.printStackTrace();
      // MessageUI.pressAnyKey();
    } catch (IOException e) {
      System.out.println("File: 'appointment.dat' writing failed.");
      Utilities.wait(1000);
      // e.printStackTrace();
      // MessageUI.pressAnyKey();
    }
  }

  @SuppressWarnings("unchecked")
  public RedBlackTreeMap<String, Appointment> retrieveFromFile() {

    RedBlackTreeMap<String, Appointment> appointmentMap = new RedBlackTreeMap<>();

    try {
      ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataFile));
      appointmentMap = (RedBlackTreeMap<String, Appointment>) ois.readObject();
      ois.close();
    } catch (FileNotFoundException e) {
      System.out.println("File: 'appointment.dat' not found.");
      Utilities.wait(1000);
      // e.printStackTrace();
      // MessageUI.pressAnyKey();
    } catch (IOException e) {
      System.out.println("File: 'appointment.dat' reading failed.");
      Utilities.wait(1000);
      // e.printStackTrace();
      // MessageUI.pressAnyKey();
    } catch (ClassNotFoundException e) {
      System.out.println("File: 'appointment.dat' reading failed.");
      Utilities.wait(1000);
      // e.printStackTrace();
      // MessageUI.pressAnyKey();
    }
    return appointmentMap;
  }
  
  public void clearFile() {
    try {
      dataFile.delete();
      dataFile.getParentFile().mkdirs();
      dataFile.createNewFile();
      System.out.println("File: 'appointment.dat' cleared successfully.");
      Utilities.wait(1000);
    } catch (Exception e) {
      System.out.println("File: 'appointment.dat' clearing failed.");
      Utilities.wait(1000);
      // e.printStackTrace();
      // MessageUI.pressAnyKey();
    }
  }
}
