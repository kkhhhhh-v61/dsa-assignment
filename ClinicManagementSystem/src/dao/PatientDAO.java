package dao;

import adt.RedBlackTreeMap;
import entity.Patient;
// import utility.MessageUI;
// import utility.Utilities;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 *
 * @author Tan Kai Hao
 */
public class PatientDAO { 

  static File dataFile = new File("./data/patient.dat");
  
  public PatientDAO() {
    try {
      if (!dataFile.exists()) {
        // System.out.println("File: 'patient.dat' doesn't exist. Creating it now...");
        dataFile.getParentFile().mkdirs();
        dataFile.createNewFile();
        // System.out.println("File: 'patient.dat' created successfully.");
        // Utilities.wait(1000);
      }
    } catch (IOException e) {
      // System.out.println("File: 'patient.dat' creation failed.");
      // Utilities.wait(1000);
      e.printStackTrace();
      // MessageUI.pressAnyKey();
    }
  }


  public void writeToFile(RedBlackTreeMap<String, Patient> patientMap) {
    try {
      ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataFile));
      oos.writeObject(patientMap);
      oos.close();
    } catch (FileNotFoundException e) {
      // System.out.println("File: 'patient.dat' not found.");
      // Utilities.wait(1000);
      e.printStackTrace();
      // MessageUI.pressAnyKey();
    } catch (IOException e) {
      // System.out.println("File: 'patient.dat' writing failed.");
      // Utilities.wait(1000);
      e.printStackTrace();
      // MessageUI.pressAnyKey();
    }
  }

  @SuppressWarnings("unchecked")
  public RedBlackTreeMap<String, Patient> readFromFile() {

    RedBlackTreeMap<String, Patient> patientMap = new RedBlackTreeMap<>();

    try {
      ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataFile));
      patientMap = (RedBlackTreeMap<String, Patient>) ois.readObject();
      ois.close();
    } catch (FileNotFoundException e) {
      // System.out.println("File: 'patient.dat' not found.");
      // Utilities.wait(1000);
      e.printStackTrace();
      // MessageUI.pressAnyKey();
    } catch (IOException e) {
      // System.out.println("File: 'patient.dat' reading failed.");
      // Utilities.wait(1000);
      e.printStackTrace();
      // MessageUI.pressAnyKey();
    } catch (ClassNotFoundException e) {
      // System.out.println("File: 'patient.dat' reading failed.");
      // Utilities.wait(1000);
      e.printStackTrace();
      // MessageUI.pressAnyKey();
    }
    return patientMap;
  }
  
  public void clearFile() {
    try {
      dataFile.delete();
      dataFile.getParentFile().mkdirs();
      dataFile.createNewFile();
      // System.out.println("File: 'patient.dat' cleared successfully.");
      // Utilities.wait(1000);
    } catch (Exception e) {
      // System.out.println("File: 'patient.dat' clearing failed.");
      // Utilities.wait(1000);
      e.printStackTrace();
      // MessageUI.pressAnyKey();
    }
  }
}
