package control;

import adt.ArrayList;
import adt.RedBlackTreeMap;
import dao.PatientDAO;
import dao.PatientInitializer;
import entity.Patient;
import boundary.PatientUI;
import utility.Enum.BloodType;
import utility.MessageUI;
import utility.Utilities;

/**
 *
 * @author Tan Kai Hao
 */
public class PatientController {
  
  PatientUI patientUI = new PatientUI();
  PatientDAO patientDAO = new PatientDAO();
  PatientInitializer patientInitializer = new PatientInitializer();

  RedBlackTreeMap<String, Patient> patientMap = new RedBlackTreeMap<>();
  
  public void runPatientManagement() {
    patientMap = patientDAO.retrieveFromFile();
    ArrayList<String> keys = patientMap.getKeys();
    
    if (!keys.isEmpty()) {
      String lastKey = keys.get(keys.size());
      String lastId = lastKey.substring(1);
      Patient.setLastId(Integer.parseInt(lastId));
    }

    boolean exit = false;

    do {
      char response = patientUI.runPatientMenu();

      switch (response) {
        case '1':
          runPatientRegistration();
          break;
        case '2':
          runPatientRecordList();
          break;
        case '3':
          if (patientUI.runClearPatientRecord().equals("1")) {
            patientMap.clear();
            patientDAO.clearFile();
            Patient.setLastId(0);
            System.out.println("Patient records cleared.");
            Utilities.wait(1000);
          }
          break;
        case '4':
          if (patientMap.isEmpty()) {
            System.out.println("Initializing new sample Patient records...");
            Utilities.wait(1000);
            patientMap = patientInitializer.initializeSamplePatients();
            patientDAO.saveToFile(patientMap);
            System.out.println("Initialization done.");
            Utilities.wait(1000);
          } else {
            System.out.println("Error! Initialization can only be done when there are no existing Patient records.");
            Utilities.wait(1000);
          }
          break;
        case '0':
          exit = true;
          break;
      }

    } while (!exit);
  }
  
  public void runPatientRegistration() {

    boolean exit = false;
    
    do {
      String response = runPatientRegistrationMenu();

      switch (response) {
        case "0":
          exit = true;
          break;
      }
    } while (!exit); 
  }

  public String runPatientRegistrationMenu() {

    Patient newPatient = new Patient();

    boolean exit = false;
    String response = "X";
    boolean complete = false;

    do {
      System.out.println();

      Utilities.clearScreen();

      response = patientUI.inputIcNumber(patientMap);
      if (response.equals("0")) {
        exit = true;
      } else {
        newPatient.setIcNumber(Utilities.formatIcNumber(response));
        response = patientUI.inputPatientName();
        if (response.equals("0")) {
          exit = true;
        } else {
          newPatient.setName(Utilities.formatName(response));
          newPatient.identifyGender();
          newPatient.identifyBirthDate();
          newPatient.setAge(newPatient.calculateAge());
          response = patientUI.inputContactNumber(patientMap);
          if (response.equals("0")) {
            exit = true;
          } else {
            newPatient.setContactNumber(Utilities.formatContactNumber(response));
            response = patientUI.inputEmail(patientMap);
            if (response.equals("0")) {
              exit = true;
            } else {
              newPatient.setEmail(response);
              response = patientUI.inputAddress();
              if (response.equals("0")) {
                exit = true;
              } else {
                newPatient.setAddress(response);
                response = patientUI.inputBloodType();
                switch (response) {
                  case "1":
                    newPatient.setBloodType(BloodType.A);
                    complete = true;
                    break;
                  case "2":
                    newPatient.setBloodType(BloodType.B);
                    complete = true;
                    break;
                  case "3":
                    newPatient.setBloodType(BloodType.AB);
                    complete = true;
                    break;
                  case "4":
                    newPatient.setBloodType(BloodType.O);
                    complete = true;
                    break;
                  case "0":
                    exit = true;
                    break;
                  default:
                    break;
                }
              }
            }
          }
        }
      }

      boolean exit1 = false;
      if (complete) {
        do {
          response = patientUI.confirmRegistration(newPatient);
          switch (response) {
            case "1":
              patientMap.put(newPatient.getPatientId(), newPatient);
              patientDAO.saveToFile(patientMap);
              System.out.println();
              System.out.println("Patient successfully registered.");
              Utilities.wait(1000);
              exit1 = true;
              exit = true;
              break;
            case "2":
              newPatient = runPatientRegistrationEdit(newPatient);
              break;
            case "0":
              exit1 = true;
              exit = true;
              break;
            default:
              break;
          }
        } while (!exit1);
      }

      response = "0";
    } while (!exit);

    return response;
  }

  public void runPatientRecordList() {
    
    boolean exit = false;
    char response = 'X';
    String ui = "left";
    int page = 1;
    
    do {
      System.out.println();

      Utilities.clearScreen();

      response = patientUI.runPatientRecordListMenu(patientMap, ui, page);
      switch (response) {
        case 'Q':
        if (page > 1) {
          page--;
        }
          break;
        case 'E':
          if (page < (patientMap.size() + 9) / 10) {
            page++;
          }
          break;
        case 'W':
          if (ui.equals("left")) {
            ui = "right";
          } else {
            ui = "left";
          }
          break;
        case '1':
          if (!patientMap.isEmpty()) {
            runPatientSearch("edit");
          } else {
            MessageUI.noPatientRecord();
          }
          break;
        case '2':
          if (!patientMap.isEmpty()) {
            runPatientSearch("delete");
          } else {
            MessageUI.noPatientRecord();
          }
          break;
        case '0':
          exit = true;
          break;
        default:
          break;
      }
    } while (!exit);
  }

  public void runPatientSearch(String action) {

    boolean exit = false;
    String response = "X";

    do {
      System.out.println();

      Utilities.clearScreen();

      response = patientUI.inputPatientId(patientMap);
      if (response.equals("0")) {
        exit = true;
      } else {
        if (action.equals("edit")) {
          runPatientEdit(response);
          exit = true;
        } else if (action.equals("delete")) {
          runPatientDelete(response);
          exit = true;
        } else {
          System.out.println("Invalid action! Please try again.");
          Utilities.wait(1000);
        }
      }
    } while (!exit);
  }
  
  public void runPatientEdit(String id) {

    boolean exit = false;
    String response = "X";
    String ui = "left";
    boolean confirm = false;

    do {
      System.out.println();

      Utilities.clearScreen();

      response = String.valueOf(patientUI.runPatientEditMenu(patientMap.get(id), ui));
      switch (response) {
        case "W":
          if (ui.equals("left")) {
            ui = "right";
          } else {
            ui = "left";
          }
          break;
        case "1":
          response = patientUI.editIcNumber(patientMap.get(id).getIcNumber());
          if (!response.equals("0")) {
            response = Utilities.formatIcNumber(response);
            confirm = patientUI.confirmEdit("IC Number", patientMap.get(id).getIcNumber(), response);
            if (confirm) {
              patientMap.get(id).setIcNumber(response);
              patientMap.get(id).identifyGender();
              patientMap.get(id).identifyBirthDate();
              patientMap.get(id).setAge(patientMap.get(id).calculateAge());
              patientDAO.saveToFile(patientMap);
            }
          }
          exit = true;
          break;
        case "2":
          response = patientUI.editPatientName(patientMap.get(id).getName());
          if (!response.equals("0")) {
            response = Utilities.formatName(response);
            confirm = patientUI.confirmEdit("Patient Name", patientMap.get(id).getName(), response);
            if (confirm) {
              patientMap.get(id).setName(response);
              patientDAO.saveToFile(patientMap);
            }
          }
          exit = true;
          break;
        case "3":
          response = patientUI.editContactNumber(patientMap.get(id).getContactNumber());
          if (!response.equals("0")) {
            response = Utilities.formatContactNumber(response);
            confirm = patientUI.confirmEdit("Contact Number", patientMap.get(id).getContactNumber(), response);
            if (confirm) {
              patientMap.get(id).setContactNumber(response);
              patientDAO.saveToFile(patientMap);
            }
          }
          exit = true;
          break;
        case "4":
          response = patientUI.editEmail(patientMap.get(id).getEmail());
          if (!response.equals("0")) {
            confirm = patientUI.confirmEdit("Email", patientMap.get(id).getEmail(), response);
            if (confirm) {
              patientMap.get(id).setEmail(response);
              patientDAO.saveToFile(patientMap);
            }
          }
          exit = true;
          break;
        case "5":
          response = patientUI.editAddress(patientMap.get(id).getAddress());
          if (!response.equals("0")) {
            confirm = patientUI.confirmEdit("Address", patientMap.get(id).getAddress(), response);
            if (confirm) {
              patientMap.get(id).setAddress(response);
              patientDAO.saveToFile(patientMap);
            }
          }
          exit = true;
          break;
        case "6":
          response = patientUI.editBloodType(patientMap.get(id).getBloodType());
          switch (response) {
            case "1":
              response = String.valueOf(BloodType.A);
              break;
            case "2":
              response = String.valueOf(BloodType.B);
              break;
            case "3":
              response = String.valueOf(BloodType.AB);
              break;
            case "4":
              response = String.valueOf(BloodType.O);
              break;
            case "0":
              exit = true;
              break;
            default:
              break;
          }
          if (!response.equals("0")) {
            confirm = patientUI.confirmEdit("Blood Type", String.valueOf(patientMap.get(id).getBloodType()), response);
            if (confirm) {
              patientMap.get(id).setBloodType(BloodType.valueOf(response));
              patientDAO.saveToFile(patientMap);
            }
          }
          exit = true;
          break;
        case "0":
          exit = true;
          break;
        default:
          break;
      }
    } while (!exit);
  }

  public Patient runPatientRegistrationEdit(Patient patient) {

    boolean exit = false;
    String response = "X";
    String ui = "left";
    boolean confirm = false;

    do {
      System.out.println();

      Utilities.clearScreen();

      response = String.valueOf(patientUI.runPatientEditMenu(patient, ui));
      switch (response) {
        case "W":
          if (ui.equals("left")) {
            ui = "right";
          } else {
            ui = "left";
          }
          break;
        case "1":
          response = patientUI.editIcNumber(patient.getIcNumber());
          if (!response.equals("0")) {
            response = Utilities.formatIcNumber(response);
            confirm = patientUI.confirmEdit("IC Number", patient.getIcNumber(), response);
            if (confirm) {
              patient.setIcNumber(response);
              patient.identifyGender();
              patient.identifyBirthDate();
              patient.setAge(patient.calculateAge());
            }
          }
          exit = true;
          break;
        case "2":
          response = patientUI.editPatientName(patient.getName());
          if (!response.equals("0")) {
            response = Utilities.formatName(response);
            confirm = patientUI.confirmEdit("Patient Name", patient.getName(), response);
            if (confirm) {
              patient.setName(response);
            }
          }
          exit = true;
          break;
        case "3":
          response = patientUI.editContactNumber(patient.getContactNumber());
          if (!response.equals("0")) {
            response = Utilities.formatContactNumber(response);
            confirm = patientUI.confirmEdit("Contact Number", patient.getContactNumber(), response);
            if (confirm) {
              patient.setContactNumber(response);
            }
          }
          exit = true;
          break;
        case "4":
          response = patientUI.editEmail(patient.getEmail());
          if (!response.equals("0")) {
            confirm = patientUI.confirmEdit("Email", patient.getEmail(), response);
            if (confirm) {
              patient.setEmail(response);
            }
          }
          exit = true;
          break;
        case "5":
          response = patientUI.editAddress(patient.getAddress());
          if (!response.equals("0")) {
            confirm = patientUI.confirmEdit("Address", patient.getAddress(), response);
            if (confirm) {
              patient.setAddress(response);
            }
          }
          exit = true;
          break;
        case "6":
          response = patientUI.editBloodType(patient.getBloodType());
          switch (response) {
            case "1":
              response = String.valueOf(BloodType.A);
              break;
            case "2":
              response = String.valueOf(BloodType.B);
              break;
            case "3":
              response = String.valueOf(BloodType.AB);
              break;
            case "4":
              response = String.valueOf(BloodType.O);
              break;
            case "0":
              exit = true;
              break;
            default:
              break;
          }
          if (!response.equals("0")) {
            confirm = patientUI.confirmEdit("Blood Type", String.valueOf(patient.getBloodType()), response);
            if (confirm) {
              patient.setBloodType(BloodType.valueOf(response));
            }
          }
          exit = true;
          break;
        case "0":
          exit = true;
          break;
        default:
          break;
      }
    } while (!exit);

    return patient;
  }
  
  public void runPatientDelete(String id) {

    boolean exit = false;
    char response = 'X';
    String ui = "left";

    do {
      System.out.println();

      Utilities.clearScreen();

      response = patientUI.runPatientDeleteMenu(patientMap, patientMap.get(id), ui);
      switch (response) {
        case 'W':
          if (ui.equals("left")) {
            ui = "right";
          } else {
            ui = "left";
          }
          break;
        case '1':
          patientMap.remove(id);
          patientDAO.saveToFile(patientMap);
          System.out.println();
          System.out.println("Patient successfully deleted.");
          Utilities.wait(1000);
          exit = true;
          break;
        case '0':
          exit = true;
          break;
        default:
          break;
      }
    } while (!exit);
  }
}
