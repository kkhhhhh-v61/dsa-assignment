package control;

import adt.ArrayList;
import adt.RedBlackTreeMap;
import boundary.PatientUI;
import dao.PatientDAO;
import dao.PatientInitializer;
import entity.Patient;
import utility.Enum.BloodType;
import utility.MessageUI;
import utility.Utilities;
import utility.Validation;

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
    String[] response;
    char userInput;
    char[] inputOptions;

    do {
      response = patientUI.runPatientMenu();
      inputOptions = response[1].toCharArray();

      if (response[0] != "") {
        if (Validation.isSingleChar(response[0])) {
          userInput = response[0].charAt(0);

          if (Validation.isValidOption(userInput, inputOptions)) {
            switch (userInput) {
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
          } else {
            MessageUI.notValidOption(inputOptions);
          }
        } else {
          MessageUI.notSingleChar();
        }
      }
    } while (!exit);
  }

  public void runPatientRegistration() {

    Patient newPatient = new Patient();

    boolean exit = false;
    boolean complete = false;
    String[] response;
    String[] vResponse;

    do {
      System.out.println();

      Utilities.clearScreen();

      response = patientUI.inputIcNumber();
      vResponse = PatientValidation.validateIcNumber(patientMap, response[0], response[1].toCharArray());

      if (Boolean.parseBoolean(vResponse[0])) {
        if (vResponse[1].equals("0")) {
          exit = true;
        } else {
          newPatient.setIcNumber(Utilities.formatIcNumber(vResponse[1]));
          newPatient.identifyGender();
          newPatient.identifyBirthDate();
          newPatient.setAge(newPatient.calculateAge());

          response = patientUI.inputPatientName();
          vResponse = PatientValidation.validateName(response[0], response[1].toCharArray(),
              Integer.parseInt(response[2]));

          if (Boolean.parseBoolean(vResponse[0])) {
            if (vResponse[1].equals("0")) {
              exit = true;
            } else {
              newPatient.setName(Utilities.formatName(vResponse[1]));

              response = patientUI.inputContactNumber();
              vResponse = PatientValidation.validateContactNumber(response[0], response[1].toCharArray());

              if (Boolean.parseBoolean(vResponse[0])) {
                if (vResponse[1].equals("0")) {
                  exit = true;
                } else {
                  newPatient.setContactNumber(Utilities.formatContactNumber(vResponse[1]));

                  response = patientUI.inputEmail();
                  vResponse = PatientValidation.validateEmail(response[0], response[1].toCharArray(),
                      Integer.parseInt(response[2]));

                  if (Boolean.parseBoolean(vResponse[0])) {
                    if (vResponse[1].equals("0")) {
                      exit = true;
                    } else {
                      newPatient.setEmail(vResponse[1]);

                      response = patientUI.inputAddress();
                      vResponse = PatientValidation.validateAddress(response[0], response[1].toCharArray(),
                          Integer.parseInt(response[2]));

                      if (Boolean.parseBoolean(vResponse[0])) {
                        if (vResponse[1].equals("0")) {
                          exit = true;
                        } else {
                          newPatient.setAddress(vResponse[1]);

                          response = patientUI.inputBloodType();
                          vResponse = PatientValidation.validateBloodType(response[0], response[1].toCharArray());

                          if (Boolean.parseBoolean(vResponse[0])) {
                            switch (vResponse[1]) {
                              case "1":
                                newPatient.setBloodType(BloodType.A);
                                break;
                              case "2":
                                newPatient.setBloodType(BloodType.B);
                                break;
                              case "3":
                                newPatient.setBloodType(BloodType.AB);
                                break;
                              case "4":
                                newPatient.setBloodType(BloodType.O);
                                break;
                              case "0":
                                exit = true;
                                break;
                            }
                            complete = true;
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }

      if (complete) {
        boolean exit1 = false;

        do {
          response = patientUI.confirmRegistration(newPatient);
          vResponse = PatientValidation.validateConfirmRegistration(response[0], response[1].toCharArray());

          if (Boolean.parseBoolean(vResponse[0])) {
            switch (vResponse[1]) {
              case "1":
                patientMap.put(newPatient.getPatientId(), newPatient);
                patientDAO.saveToFile(patientMap);
                System.out.println("Patient registered successfully.");
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
            }
          }
        } while (!exit1);
      }
    } while (!exit);
  }
  
  public Patient runPatientRegistrationEdit(Patient patient) {

    boolean exit = false;
    String[] response;
    String[] vResponse;
    String ui = "left";

    do {
      System.out.println();

      Utilities.clearScreen();

      response = patientUI.runPatientEditMenu(patient, ui);
      vResponse = PatientValidation.validatePatientId(patientMap, response[0], response[1].toCharArray());

      if (Boolean.parseBoolean(vResponse[0])) {
        switch (vResponse[1]) {
          case "W":
            if (ui.equals("left")) {
              ui = "right";
            } else {
              ui = "left";
            }
            break;
          case "1":
            response = patientUI.editIcNumber(patient.getIcNumber());
            vResponse = PatientValidation.validateEditIcNumber(response[0], response[1].toCharArray(), patient.getIcNumber());
            
            if (Boolean.parseBoolean(vResponse[0])) {
              if (vResponse[1].equals("0")) {
                exit = true;
              } else {
                response = patientUI.confirmEdit("IC Number", patient.getIcNumber(), vResponse[1]);
                vResponse = PatientValidation.validateConfirmEdit(response[0], response[1].toCharArray());

                if (Boolean.parseBoolean(vResponse[0])) {
                  switch (vResponse[1]) {
                    case "1":
                      patient.setIcNumber(vResponse[1]);
                      break;
                    case "0":
                      exit = true;
                      break;
                  }
                }
              }
            }
            break;
          case "2":
            response = patientUI.editPatientName(patient.getName());
            vResponse = PatientValidation.validateEditName(response[0], response[1].toCharArray(), Integer.parseInt(response[2]), patient.getName());
            
            if (Boolean.parseBoolean(vResponse[0])) {
              if (vResponse[1].equals("0")) {
                exit = true;
              } else {
                response = patientUI.confirmEdit("Patient Name", patient.getName(), vResponse[1]);
                vResponse = PatientValidation.validateConfirmEdit(response[0], response[1].toCharArray());

                if (Boolean.parseBoolean(vResponse[0])) {
                  switch (vResponse[1]) {
                    case "1":
                      patient.setName(vResponse[1]);
                      break;
                    case "0":
                      exit = true;
                      break;
                  }
                }
              }
            }
            break;
          case "3":
            response = patientUI.editContactNumber(patient.getContactNumber());
            vResponse = PatientValidation.validateEditContactNumber(response[0], response[1].toCharArray(), patient.getContactNumber());
            
            if (Boolean.parseBoolean(vResponse[0])) {
              if (vResponse[1].equals("0")) {
                exit = true;
              } else {
                response = patientUI.confirmEdit("Contact Number", patient.getContactNumber(), vResponse[1]);
                vResponse = PatientValidation.validateConfirmEdit(response[0], response[1].toCharArray());

                if (Boolean.parseBoolean(vResponse[0])) {
                  switch (vResponse[1]) {
                    case "1":
                      patient.setContactNumber(vResponse[1]);
                      break;
                    case "0":
                      exit = true;
                      break;
                  }
                }
              }
            }
            break;
          case "4":
            response = patientUI.editEmail(patient.getEmail());
            vResponse = PatientValidation.validateEditEmail(response[0], response[1].toCharArray(), Integer.parseInt(response[2]), patient.getEmail());
            
            if (Boolean.parseBoolean(vResponse[0])) {
              if (vResponse[1].equals("0")) {
                exit = true;
              } else {
                response = patientUI.confirmEdit("Email", patient.getEmail(), vResponse[1]);
                vResponse = PatientValidation.validateConfirmEdit(response[0], response[1].toCharArray());

                if (Boolean.parseBoolean(vResponse[0])) {
                  switch (vResponse[1]) {
                    case "1":
                      patient.setEmail(vResponse[1]);
                      break;
                    case "0":
                      exit = true;
                      break;
                  }
                }
              }
            }
            break;
          case "5":
            response = patientUI.editAddress(patient.getAddress());
            vResponse = PatientValidation.validateEditAddress(response[0], response[1].toCharArray(), Integer.parseInt(response[2]), patient.getAddress());
            
            if (Boolean.parseBoolean(vResponse[0])) {
              if (vResponse[1].equals("0")) {
                exit = true;
              } else {
                response = patientUI.confirmEdit("Address", patient.getAddress(), vResponse[1]);
                vResponse = PatientValidation.validateConfirmEdit(response[0], response[1].toCharArray());

                if (Boolean.parseBoolean(vResponse[0])) {
                  switch (vResponse[1]) {
                    case "1":
                      patient.setAddress(vResponse[1]);
                      break;
                    case "0":
                      exit = true;
                      break;
                  }
                }
              }
            }
            break;
          case "6":
            response = patientUI.editBloodType(patient.getBloodType());
            vResponse = PatientValidation.validateEditBloodType(response[0], response[1].toCharArray(), String.valueOf(patient.getBloodType()));

            if (Boolean.parseBoolean(response[0])) {
              if (vResponse[1].equals("0")) {
                exit = true;
              } else {
                response = patientUI.confirmEdit("Blood Type", String.valueOf(patient.getBloodType()), vResponse[1]);
                vResponse = PatientValidation.validateConfirmEdit(response[0], response[1].toCharArray());

                if (Boolean.parseBoolean(vResponse[0])) {
                  switch (vResponse[1]) {
                    case "1":
                      patient.setBloodType(BloodType.A);
                      break;
                    case "2":
                      patient.setBloodType(BloodType.B);
                      break;
                    case "3":
                      patient.setBloodType(BloodType.AB);
                      break;
                    case "4":
                      patient.setBloodType(BloodType.O);
                      break;
                    case "0":
                      exit = true;
                      break;
                  }
                }
              }
            }
            break;
          case "0":
            exit = true;
            break;
          default:
            break;
        }
      }
    } while (!exit);

    return patient;
  }

  public void runPatientRecordList() {

    boolean exit = false;
    String[] response;
    String[] vResponse;
    String ui = "left";
    int page = 1;
    
    do {
      System.out.println();

      Utilities.clearScreen();

      response = patientUI.runPatientRecordListMenu(patientMap, ui, page);
      vResponse = PatientValidation.validateRecordList(response[0], response[1].toCharArray());

      if (Boolean.parseBoolean(vResponse[0])) {
        switch (vResponse[1]) {
          case "Q":
          if (page > 1) {
            page--;
          }
            break;
          case "E":
            if (page < (patientMap.size() + 9) / 10) {
              page++;
            }
            break;
          case "W":
            if (ui.equals("left")) {
              ui = "right";
            } else {
              ui = "left";
            }
            break;
          case "1":
            if (!patientMap.isEmpty()) {
              runPatientSearch("edit");
            } else {
              MessageUI.noPatientRecord();
            }
            break;
          case "2":
            if (!patientMap.isEmpty()) {
              runPatientSearch("delete");
            } else {
              MessageUI.noPatientRecord();
            }
            break;
          case "0":
            exit = true;
            break;
          default:
            break;
        }
      }
    } while (!exit);
  }

  public void runPatientSearch(String action) {

    boolean exit = false;
    String[] response;
    String[] vResponse;

    do {
      System.out.println();

      Utilities.clearScreen();

      response = patientUI.inputPatientId();
      vResponse = PatientValidation.validatePatientId(patientMap, response[0], response[1].toCharArray());

      if (Boolean.parseBoolean(vResponse[0])) {
        if (vResponse[1].equals("0")) {
          exit = true;
        } else {
          if (action.equals("edit")) {
            runPatientEdit(vResponse[1]);
            exit = true;
          } else if (action.equals("delete")) {
            runPatientDelete(vResponse[1]);
            exit = true;
          } else {
            System.out.println("Invalid action! Please try again.");
            Utilities.wait(1000);
          }
        }
      }
    } while (!exit);
  }
  
  public void runPatientEdit(String id) {

    boolean exit = false;
    String[] response;
    String[] vResponse;
    String ui = "left";

    do {
      System.out.println();

      Utilities.clearScreen();

      response = patientUI.runPatientEditMenu(patientMap.get(id), ui);
      vResponse = PatientValidation.validateEditMenu(response[0], response[1].toCharArray());

      if (Boolean.parseBoolean(vResponse[0])) {
        switch (vResponse[1]) {
          case "W":
            if (ui.equals("left")) {
              ui = "right";
            } else {
              ui = "left";
            }
            break;
          case "1":
            response = patientUI.editIcNumber(patientMap.get(id).getIcNumber());
            vResponse = PatientValidation.validateEditIcNumber(response[0], response[1].toCharArray(), patientMap.get(id).getIcNumber());
            
            if (Boolean.parseBoolean(vResponse[0])) {
              if (vResponse[1].equals("0")) {
                exit = true;
              } else {
                response = patientUI.confirmEdit("IC Number", patientMap.get(id).getIcNumber(), vResponse[1]);
                vResponse = PatientValidation.validateConfirmEdit(response[0], response[1].toCharArray());

                if (Boolean.parseBoolean(vResponse[0])) {
                  switch (vResponse[1]) {
                    case "1":
                      patientMap.get(id).setIcNumber(vResponse[1]);
                      break;
                    case "0":
                      exit = true;
                      break;
                  }
                }
              }
            }
            break;
          case "2":
            response = patientUI.editPatientName(patientMap.get(id).getName());
            vResponse = PatientValidation.validateEditName(response[0], response[1].toCharArray(), Integer.parseInt(response[2]), patientMap.get(id).getName());
            
            if (Boolean.parseBoolean(vResponse[0])) {
              if (vResponse[1].equals("0")) {
                exit = true;
              } else {
                response = patientUI.confirmEdit("Patient Name", patientMap.get(id).getName(), vResponse[1]);
                vResponse = PatientValidation.validateConfirmEdit(response[0], response[1].toCharArray());

                if (Boolean.parseBoolean(vResponse[0])) {
                  switch (vResponse[1]) {
                    case "1":
                      patientMap.get(id).setName(vResponse[1]);
                      break;
                    case "0":
                      exit = true;
                      break;
                  }
                }
              }
            }
            break;
          case "3":
            response = patientUI.editContactNumber(patientMap.get(id).getContactNumber());
            vResponse = PatientValidation.validateEditContactNumber(response[0], response[1].toCharArray(), patientMap.get(id).getContactNumber());
            
            if (Boolean.parseBoolean(vResponse[0])) {
              if (vResponse[1].equals("0")) {
                exit = true;
              } else {
                response = patientUI.confirmEdit("Contact Number", patientMap.get(id).getContactNumber(), vResponse[1]);
                vResponse = PatientValidation.validateConfirmEdit(response[0], response[1].toCharArray());

                if (Boolean.parseBoolean(vResponse[0])) {
                  switch (vResponse[1]) {
                    case "1":
                      patientMap.get(id).setContactNumber(vResponse[1]);
                      break;
                    case "0":
                      exit = true;
                      break;
                  }
                }
              }
            }
            break;
          case "4":
            response = patientUI.editEmail(patientMap.get(id).getEmail());
            vResponse = PatientValidation.validateEditEmail(response[0], response[1].toCharArray(), Integer.parseInt(response[2]), patientMap.get(id).getEmail());
            
            if (Boolean.parseBoolean(vResponse[0])) {
              if (vResponse[1].equals("0")) {
                exit = true;
              } else {
                response = patientUI.confirmEdit("Email", patientMap.get(id).getEmail(), vResponse[1]);
                vResponse = PatientValidation.validateConfirmEdit(response[0], response[1].toCharArray());

                if (Boolean.parseBoolean(vResponse[0])) {
                  switch (vResponse[1]) {
                    case "1":
                      patientMap.get(id).setEmail(vResponse[1]);
                      break;
                    case "0":
                      exit = true;
                      break;
                  }
                }
              }
            }
            break;
          case "5":
            response = patientUI.editAddress(patientMap.get(id).getAddress());
            vResponse = PatientValidation.validateEditAddress(response[0], response[1].toCharArray(), Integer.parseInt(response[2]), patientMap.get(id).getAddress());
            
            if (Boolean.parseBoolean(vResponse[0])) {
              if (vResponse[1].equals("0")) {
                exit = true;
              } else {
                response = patientUI.confirmEdit("Address", patientMap.get(id).getAddress(), vResponse[1]);
                vResponse = PatientValidation.validateConfirmEdit(response[0], response[1].toCharArray());

                if (Boolean.parseBoolean(vResponse[0])) {
                  switch (vResponse[1]) {
                    case "1":
                      patientMap.get(id).setAddress(vResponse[1]);
                      break;
                    case "0":
                      exit = true;
                      break;
                  }
                }
              }
            }
            break;
          case "6":
            response = patientUI.editBloodType(patientMap.get(id).getBloodType());
            vResponse = PatientValidation.validateEditBloodType(response[0], response[1].toCharArray(), String.valueOf(patientMap.get(id).getBloodType()));

            if (Boolean.parseBoolean(response[0])) {
              if (vResponse[1].equals("0")) {
                exit = true;
              } else {
                response = patientUI.confirmEdit("Blood Type", String.valueOf(patientMap.get(id).getBloodType()), vResponse[1]);
                vResponse = PatientValidation.validateConfirmEdit(response[0], response[1].toCharArray());

                if (Boolean.parseBoolean(vResponse[0])) {
                  switch (vResponse[1]) {
                    case "1":
                      patientMap.get(id).setBloodType(BloodType.A);
                      break;
                    case "2":
                      patientMap.get(id).setBloodType(BloodType.B);
                      break;
                    case "3":
                      patientMap.get(id).setBloodType(BloodType.AB);
                      break;
                    case "4":
                      patientMap.get(id).setBloodType(BloodType.O);
                      break;
                    case "0":
                      exit = true;
                      break;
                  }
                }
              }
            }
            break;
          case "0":
            exit = true;
            break;
          default:
            break;
        }
      }
    } while (!exit);
  }

  public void runPatientDelete(String id) {

    boolean exit = false;
    String[] response;
    String[] vResponse;
    String ui = "left";

    do {
      System.out.println();

      Utilities.clearScreen();

      response = patientUI.runPatientDeleteMenu(patientMap.get(id), ui);
      vResponse = PatientValidation.validateDeleteMenu(response[0], response[1].toCharArray());

      if (Boolean.parseBoolean(vResponse[0])) {
        switch (vResponse[1]) {
          case "W":
            if (ui.equals("left")) {
              ui = "right";
            } else {
              ui = "left";
            }
            break;
          case "1":
            patientMap.remove(id);
            patientDAO.saveToFile(patientMap);
            System.out.println();
            System.out.println("Patient successfully deleted.");
            Utilities.wait(1000);
            exit = true;
            break;
          case "0":
            exit = true;
            break;
          default:
            break;
        }
      }
    } while (!exit);
  }
}
