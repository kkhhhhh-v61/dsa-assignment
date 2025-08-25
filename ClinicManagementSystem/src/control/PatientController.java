package control;

import adt.ArrayList;
import adt.RedBlackTreeMap;
import boundary.PatientUI;
import dao.PatientDAO;
import dao.PatientInitializer;
import entity.Patient;
import utility.Enum.BloodType;
import utility.Enum.Gender;
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

  private RedBlackTreeMap<String, Patient> patientMap;

  public PatientController() {
    patientMap = new RedBlackTreeMap<>();
    patientMap = patientDAO.retrieveFromFile();
  }
  
  public void runPatientManagement() {

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
                runPatientList();
                break;
              case '3':
                runPatientSearch();
                break;
              case '4':
                runPatientClearance();
                break;
              case '5':
                runPatientInitialization();
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
    ArrayList<String> keys = patientMap.getKeys();
    
    if (!keys.isEmpty()) {
      String lastKey = keys.get(keys.size());
      String lastId = lastKey.substring(1);
      Patient.setLastId(Integer.parseInt(lastId));
    }

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

          do {
            response = patientUI.inputPatientName();
            vResponse = PatientValidation.validateName(response[0], response[1].toCharArray(), Integer.parseInt(response[2]));
          } while (!Boolean.parseBoolean(vResponse[0]));

          if (Boolean.parseBoolean(vResponse[0])) {
            if (vResponse[1].equals("0")) {
              exit = true;
            } else {
              newPatient.setName(Utilities.formatName(vResponse[1]));

              do {
                response = patientUI.inputContactNumber();
                vResponse = PatientValidation.validateContactNumber(response[0], response[1].toCharArray());
              } while (!Boolean.parseBoolean(vResponse[0]));

              if (Boolean.parseBoolean(vResponse[0])) {
                if (vResponse[1].equals("0")) {
                  exit = true;
                } else {
                  newPatient.setContactNumber(Utilities.formatContactNumber(vResponse[1]));

                  do {
                    response = patientUI.inputEmail();
                    vResponse = PatientValidation.validateEmail(response[0], response[1].toCharArray(), Integer.parseInt(response[2]));
                  } while (!Boolean.parseBoolean(vResponse[0]));

                  if (Boolean.parseBoolean(vResponse[0])) {
                    if (vResponse[1].equals("0")) {
                      exit = true;
                    } else {
                      newPatient.setEmail(vResponse[1]);

                      do {
                        response = patientUI.inputAddress();
                        vResponse = PatientValidation.validateAddress(response[0], response[1].toCharArray(), Integer.parseInt(response[2]));
                      } while (!Boolean.parseBoolean(vResponse[0]));

                      if (Boolean.parseBoolean(vResponse[0])) {
                        if (vResponse[1].equals("0")) {
                          exit = true;
                        } else {
                          newPatient.setAddress(vResponse[1]);

                          do {
                            response = patientUI.inputBloodType();
                            vResponse = PatientValidation.validateBloodType(response[0], response[1].toCharArray());
                          } while (!Boolean.parseBoolean(vResponse[0]));

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
                System.out.println();
                System.out.println("Patient registered successfully.");
                Utilities.pressAnyKey();
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
      vResponse = PatientValidation.validateRegistrationEdit(response[0], response[1].toCharArray());

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
            do {
              response = patientUI.editIcNumber(patient.getIcNumber());
              vResponse = PatientValidation.validateEditIcNumber(response[0], response[1].toCharArray(), patient.getIcNumber());
            } while (!Boolean.parseBoolean(vResponse[0]));

            if (Boolean.parseBoolean(vResponse[0])) {
              if (vResponse[1].equals("0")) {
                exit = true;
              } else {
                String userInput = Utilities.formatIcNumber(vResponse[1]);
                do {
                  response = patientUI.confirmEdit("IC Number", patient.getIcNumber(), userInput);
                  vResponse = PatientValidation.validateConfirmEdit(response[0], response[1].toCharArray());
                } while (!Boolean.parseBoolean(vResponse[0]));

                if (Boolean.parseBoolean(vResponse[0])) {
                  switch (vResponse[1]) {
                    case "1":
                      patient.setIcNumber(userInput);
                      patient.identifyGender();
                      patient.identifyBirthDate();
                      patient.setAge(patient.calculateAge());
                      break;
                    case "0":
                      break;
                  }
                }
              }
            }
            break;
          case "2":
            do {
              response = patientUI.editPatientName(patient.getName());
              vResponse = PatientValidation.validateEditName(response[0], response[1].toCharArray(), Integer.parseInt(response[2]), patient.getName());
            } while (!Boolean.parseBoolean(vResponse[0]));
            
            if (Boolean.parseBoolean(vResponse[0])) {
              if (vResponse[1].equals("0")) {
                exit = true;
              } else {
                String userInput = Utilities.formatName(vResponse[1]);
                do {
                  response = patientUI.confirmEdit("Patient Name", patient.getName(), userInput);
                  vResponse = PatientValidation.validateConfirmEdit(response[0], response[1].toCharArray());
                } while (!Boolean.parseBoolean(vResponse[0]));

                if (Boolean.parseBoolean(vResponse[0])) {
                  switch (vResponse[1]) {
                    case "1":
                      patient.setName(userInput);
                      break;
                    case "0":
                      break;
                  }
                }
              }
            }
            break;
          case "3":
            do {
              response = patientUI.editContactNumber(patient.getContactNumber());
              vResponse = PatientValidation.validateEditContactNumber(response[0], response[1].toCharArray(), patient.getContactNumber());
            } while (!Boolean.parseBoolean(vResponse[0]));
            
            if (Boolean.parseBoolean(vResponse[0])) {
              if (vResponse[1].equals("0")) {
                exit = true;
              } else {
                String userInput = Utilities.formatContactNumber(vResponse[1]);
                do {
                  response = patientUI.confirmEdit("Contact Number", patient.getContactNumber(), userInput);
                  vResponse = PatientValidation.validateConfirmEdit(response[0], response[1].toCharArray());
                } while (!Boolean.parseBoolean(vResponse[0]));

                if (Boolean.parseBoolean(vResponse[0])) {
                  switch (vResponse[1]) {
                    case "1":
                      patient.setContactNumber(userInput);
                      break;
                    case "0":
                      break;
                  }
                }
              }
            }
            break;
          case "4":
            do {
              response = patientUI.editEmail(patient.getEmail());
              vResponse = PatientValidation.validateEditEmail(response[0], response[1].toCharArray(), Integer.parseInt(response[2]), patient.getEmail());
            } while (!Boolean.parseBoolean(vResponse[0]));
            
            if (Boolean.parseBoolean(vResponse[0])) {
              if (vResponse[1].equals("0")) {
                exit = true;
              } else {
                String userInput = vResponse[1];
                do {
                  response = patientUI.confirmEdit("Email", patient.getEmail(), userInput);
                  vResponse = PatientValidation.validateConfirmEdit(response[0], response[1].toCharArray());
                } while (!Boolean.parseBoolean(vResponse[0]));

                if (Boolean.parseBoolean(vResponse[0])) {
                  switch (vResponse[1]) {
                    case "1":
                      patient.setEmail(userInput);
                      break;
                    case "0":
                      break;
                  }
                }
              }
            }
            break;
          case "5":
            do {
              response = patientUI.editAddress(patient.getAddress());
              vResponse = PatientValidation.validateEditAddress(response[0], response[1].toCharArray(), Integer.parseInt(response[2]), patient.getAddress());
            } while (!Boolean.parseBoolean(vResponse[0]));
            
            if (Boolean.parseBoolean(vResponse[0])) {
              if (vResponse[1].equals("0")) {
                exit = true;
              } else {
                String userInput = vResponse[1];
                do {
                  response = patientUI.confirmEdit("Address", patient.getAddress(), userInput);
                  vResponse = PatientValidation.validateConfirmEdit(response[0], response[1].toCharArray());
                } while (!Boolean.parseBoolean(vResponse[0]));

                if (Boolean.parseBoolean(vResponse[0])) {
                  switch (vResponse[1]) {
                    case "1":
                      patient.setAddress(userInput);
                      break;
                    case "0":
                      break;
                  }
                }
              }
            }
            break;
          case "6":
            do {
              response = patientUI.editBloodType(patient.getBloodType());
              vResponse = PatientValidation.validateEditBloodType(response[0], response[1].toCharArray(), String.valueOf(patient.getBloodType()));
            } while (!Boolean.parseBoolean(vResponse[0]));

            if (Boolean.parseBoolean(vResponse[0])) {
              String userInput = "";
              switch (vResponse[1]) {
                case "1":
                  userInput = "A";
                  break;
                case "2":
                  userInput = "B";
                  break;
                case "3":
                  userInput = "AB";
                  break;
                case "4":
                  userInput = "O";
                  break;
              }

              do {
                response = patientUI.confirmEdit("Blood Type", String.valueOf(patient.getBloodType()), userInput);
                vResponse = PatientValidation.validateConfirmEdit(response[0], response[1].toCharArray());
              } while (!Boolean.parseBoolean(vResponse[0]));

              if (Boolean.parseBoolean(vResponse[0])) {
                switch (vResponse[1]) {
                  case "1":
                    patient.setBloodType(BloodType.valueOf(userInput));
                    break;
                  case "0":
                    break;
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

  public void runPatientList() {

    boolean exit = false;
    String[] response;
    String[] vResponse;
    String ui = "left";
    int page = 1;
    
    do {
      System.out.println();

      Utilities.clearScreen();

      response = patientUI.runPatientListMenu(patientMap, ui, page);
      vResponse = PatientValidation.validatePatientListMenu(response[0], response[1].toCharArray());

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
              runPatientAction("edit");
            } else {
              MessageUI.noPatientRecord();
            }
            break;
          case "2":
            if (!patientMap.isEmpty()) {
              runPatientAction("delete");
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

  public void runPatientAction(String action) {

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
            Utilities.pressAnyKey();
          }
        }
      }
    } while (!exit);
  }
  
  public void runPatientEdit(String patientId) {

    boolean exit = false;
    String[] response;
    String[] vResponse;
    String ui = "left";

    do {
      System.out.println();

      Utilities.clearScreen();

      response = patientUI.runPatientEditMenu(patientMap.get(patientId), ui);
      vResponse = PatientValidation.validatePatientEditMenu(response[0], response[1].toCharArray());

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
            do {
              response = patientUI.editIcNumber(patientMap.get(patientId).getIcNumber());
              vResponse = PatientValidation.validateEditIcNumber(response[0], response[1].toCharArray(), patientMap.get(patientId).getIcNumber());
            } while (!Boolean.parseBoolean(vResponse[0]));
            
            if (!vResponse[1].equals("0")) {
              String userInput = Utilities.formatIcNumber(vResponse[1]);
              do {
                response = patientUI.confirmEdit("IC Number", patientMap.get(patientId).getIcNumber(), userInput);
                vResponse = PatientValidation.validateConfirmEdit(response[0], response[1].toCharArray());
              } while (!Boolean.parseBoolean(vResponse[0]));

              if (Boolean.parseBoolean(vResponse[0])) {
                switch (vResponse[1]) {
                  case "1":
                    patientMap.get(patientId).setIcNumber(userInput);
                    patientMap.get(patientId).identifyGender();
                    patientMap.get(patientId).identifyBirthDate();
                    patientMap.get(patientId).setAge(patientMap.get(patientId).calculateAge());
                    patientDAO.saveToFile(patientMap);
                    break;
                  case "0":
                    break;
                }
              }
            }
            break;
          case "2":
            do {
              response = patientUI.editPatientName(patientMap.get(patientId).getName());
              vResponse = PatientValidation.validateEditName(response[0], response[1].toCharArray(), Integer.parseInt(response[2]), patientMap.get(patientId).getName());
            } while (!Boolean.parseBoolean(vResponse[0]));
            
            if (!vResponse[1].equals("0")) {
              String userInput = Utilities.formatName(vResponse[1]);
              do {
                response = patientUI.confirmEdit("Patient Name", patientMap.get(patientId).getName(), userInput);
                vResponse = PatientValidation.validateConfirmEdit(response[0], response[1].toCharArray());
              } while (!Boolean.parseBoolean(vResponse[0]));

              if (Boolean.parseBoolean(vResponse[0])) {
                switch (vResponse[1]) {
                  case "1":
                    patientMap.get(patientId).setName(userInput);
                    patientDAO.saveToFile(patientMap);
                    break;
                  case "0":
                    break;
                }
              }
            }
            break;
          case "3":
            do {
              response = patientUI.editContactNumber(patientMap.get(patientId).getContactNumber());
              vResponse = PatientValidation.validateEditContactNumber(response[0], response[1].toCharArray(), patientMap.get(patientId).getContactNumber());
            } while (!Boolean.parseBoolean(vResponse[0]));
            
            if (!vResponse[1].equals("0")) {
              String userInput = Utilities.formatContactNumber(vResponse[1]);
              do {
                response = patientUI.confirmEdit("Contact Number", patientMap.get(patientId).getContactNumber(), userInput);
                vResponse = PatientValidation.validateConfirmEdit(response[0], response[1].toCharArray());
              } while (!Boolean.parseBoolean(vResponse[0]));

              if (Boolean.parseBoolean(vResponse[0])) {
                switch (vResponse[1]) {
                  case "1":
                    patientMap.get(patientId).setContactNumber(userInput);
                    patientDAO.saveToFile(patientMap);
                    break;
                  case "0":
                    break;
                }
              }
            }
            break;
          case "4":
            do {
              response = patientUI.editEmail(patientMap.get(patientId).getEmail());
              vResponse = PatientValidation.validateEditEmail(response[0], response[1].toCharArray(), Integer.parseInt(response[2]), patientMap.get(patientId).getEmail());
            } while (!Boolean.parseBoolean(vResponse[0]));
            
            if (!vResponse[1].equals("0")) {
              String userInput = vResponse[1];
              do {
                response = patientUI.confirmEdit("Email", patientMap.get(patientId).getEmail(), userInput);
                vResponse = PatientValidation.validateConfirmEdit(response[0], response[1].toCharArray());
              } while (!Boolean.parseBoolean(vResponse[0]));

              if (Boolean.parseBoolean(vResponse[0])) {
                switch (vResponse[1]) {
                  case "1":
                    patientMap.get(patientId).setEmail(userInput);
                    patientDAO.saveToFile(patientMap);
                    break;
                  case "0":
                    break;
                }
              }
            }
            break;
          case "5":
            do {
              response = patientUI.editAddress(patientMap.get(patientId).getAddress());
              vResponse = PatientValidation.validateEditAddress(response[0], response[1].toCharArray(), Integer.parseInt(response[2]), patientMap.get(patientId).getAddress());
            } while (!Boolean.parseBoolean(vResponse[0]));
            
            if (!vResponse[1].equals("0")) {
              String userInput = vResponse[1];
              do {
                response = patientUI.confirmEdit("Address", patientMap.get(patientId).getAddress(), userInput);
                vResponse = PatientValidation.validateConfirmEdit(response[0], response[1].toCharArray());
              } while (!Boolean.parseBoolean(vResponse[0]));

              if (Boolean.parseBoolean(vResponse[0])) {
                switch (vResponse[1]) {
                  case "1":
                    patientMap.get(patientId).setAddress(userInput);
                    patientDAO.saveToFile(patientMap);
                    break;
                  case "0":
                    break;
                }
              }
            }
            break;
          case "6":
            do {
              response = patientUI.editBloodType(patientMap.get(patientId).getBloodType());
              vResponse = PatientValidation.validateEditBloodType(response[0], response[1].toCharArray(), String.valueOf(patientMap.get(patientId).getBloodType()));
            } while (!Boolean.parseBoolean(vResponse[0]));

            if (!vResponse[1].equals("0")) {
              String userInput = "";
              switch (vResponse[1]) {
                case "1":
                  userInput = "A";
                  break;
                case "2":
                  userInput = "B";
                  break;
                case "3":
                  userInput = "AB";
                  break;
                case "4":
                  userInput = "O";
                  break;
              }
              do {
                response = patientUI.confirmEdit("Blood Type", String.valueOf(patientMap.get(patientId).getBloodType()), userInput);
                vResponse = PatientValidation.validateConfirmEdit(response[0], response[1].toCharArray());
              } while (!Boolean.parseBoolean(vResponse[0]));

              if (Boolean.parseBoolean(vResponse[0])) {
                patientMap.get(patientId).setBloodType(BloodType.valueOf(userInput));
                patientDAO.saveToFile(patientMap);
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

  public void runPatientDelete(String patientId) {

    boolean exit = false;
    String[] response;
    String[] vResponse;
    String ui = "left";

    do {
      System.out.println();

      Utilities.clearScreen();

      response = patientUI.runPatientDeleteMenu(patientMap.get(patientId), ui);
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
            patientMap.remove(patientId);
            patientDAO.saveToFile(patientMap);
            System.out.println();
            System.out.println("Patient successfully deleted.");
            Utilities.pressAnyKey();
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

  public void runPatientSearch() {

    String[] response;
    String[] vResponse;

    do {
      response = patientUI.inputSearchCategory();
      vResponse = PatientValidation.validateSearchCategory(response[0], response[1].toCharArray());
    } while (!Boolean.parseBoolean(vResponse[0]));

    if (!vResponse[1].equals("0")) {
      String userInput = vResponse[1];
      String searchCategory = "Patient ID";
      switch (userInput) {
        case "1":
          searchCategory = "Patient ID";
          break;
        case "2":
          searchCategory = "IC Number";
          break;
        case "3":
          searchCategory = "Name";
          break;
        case "4":
          searchCategory = "Gender";
          break;
        case "5":
          searchCategory = "Birth Date";
          break;
        case "6":
          searchCategory = "Age";
          break;
        case "7":
          searchCategory = "Contact Number";
          break;
        case "8":
          searchCategory = "Email";
          break;
        case "9":
          searchCategory = "Address";
          break;
        case "A":
          searchCategory = "Registration Date";
          break;
        case "B":
          searchCategory = "Blood Type";
          break;
      }

      runSearchBy(searchCategory);
    }
  }

  public void runSearchBy(String searchCategory) {
    
    boolean exit = false;
    String[] response;
    String[] vResponse = new String[] {};
    String ui = "left";
    int page = 1;

    do {
      switch (searchCategory) {
        case "Patient ID":
          response = patientUI.inputPatientId();
          vResponse = PatientValidation.validatePatientIdSearch(patientMap, response[0], response[1].toCharArray());
          break;
        case "IC Number":
          response = patientUI.inputIcNumber();
          vResponse = PatientValidation.validateIcNumberSearch(patientMap, response[0], response[1].toCharArray());
          break;
        case "Name":
          response = patientUI.inputPatientName();
          vResponse = PatientValidation.validateNameSearch(patientMap, response[0], response[1].toCharArray(), Integer.parseInt(response[2]));
          break;
        case "Gender":
          response = patientUI.inputGender();
          vResponse = PatientValidation.validateGenderSearch(patientMap, response[0], response[1].toCharArray());
          break;
        case "Birth Date":
          response = patientUI.inputBirthDate();
          vResponse = PatientValidation.validateBirthDateSearch(patientMap, response[0], response[1].toCharArray());
          break;
        case "Age":
          response = patientUI.inputAge();
          vResponse = PatientValidation.validateAgeSearch(patientMap, response[0], response[1].toCharArray());
          break;
        case "Contact Number":
          response = patientUI.inputContactNumber();
          vResponse = PatientValidation.validateContactNumberSearch(patientMap, response[0], response[1].toCharArray());
          break;
        case "Email":
          response = patientUI.inputEmail();
          vResponse = PatientValidation.validateEmailSearch(patientMap, response[0], response[1].toCharArray(), Integer.parseInt(response[2]));
          break;
        case "Address":
          response = patientUI.inputAddress();
          vResponse = PatientValidation.validateAddressSearch(patientMap, response[0], response[1].toCharArray(), Integer.parseInt(response[2]));
          break;
        case "Registration Date":
          response = patientUI.inputRegistrationDate();
          vResponse = PatientValidation.validateRegistrationDateSearch(patientMap, response[0], response[1].toCharArray());
          break;
        case "Blood Type":
          response = patientUI.inputBloodType();
          vResponse = PatientValidation.validateBloodTypeSearch(patientMap, response[0], response[1].toCharArray());
          break;
      }
    } while (!Boolean.parseBoolean(vResponse[0]));

    RedBlackTreeMap<String, Patient> filteredPatientMap = new RedBlackTreeMap<>();
    if (!vResponse[1].equals("0")) {
      ArrayList<String> keys = patientMap.getKeys();
      String userInput = vResponse[1];
      for (int i = 1; i <= keys.size(); i++) {
        Patient patient = patientMap.get(keys.get(i));
        switch (searchCategory) {
          case "Patient ID":
            if (patient.getPatientId().equals(userInput)) {
              filteredPatientMap.put(patient.getPatientId(), patient);
            }
            break;
          case "IC Number":
            if (patient.getIcNumber().equals(userInput)) {
              filteredPatientMap.put(patient.getPatientId(), patient);
            }
            break;
          case "Name":
            if (patient.getName().equals(userInput)) {
              filteredPatientMap.put(patient.getPatientId(), patient);
            }
            break;
          case "Gender":
            if (patient.getGender().equals(Gender.valueOf(userInput))) {
              filteredPatientMap.put(patient.getPatientId(), patient);
            }
            break;
          case "Birth Date":
            if (patient.getBirthDate().toString().equals(userInput)) {
              filteredPatientMap.put(patient.getPatientId(), patient);
            }
            break;
          case "Age":
            if (patient.getAge() == Integer.parseInt(userInput)) {
              filteredPatientMap.put(patient.getPatientId(), patient);
            }
            break;
          case "Contact Number":
            if (patient.getContactNumber().equals(userInput)) {
              filteredPatientMap.put(patient.getPatientId(), patient);
            }
            break;
          case "Email":
            if (patient.getEmail().equals(userInput)) {
              filteredPatientMap.put(patient.getPatientId(), patient);
            }
            break;
          case "Address":
            if (patient.getAddress().equals(userInput)) {
              filteredPatientMap.put(patient.getPatientId(), patient);
            }
            break;
          case "Registration Date":
            if (patient.getRegistrationDate().toString().equals(userInput)) {
              filteredPatientMap.put(patient.getPatientId(), patient);
            }
            break;
          case "Blood Type":
            if (patient.getBloodType().equals(BloodType.valueOf(userInput))) {
              filteredPatientMap.put(patient.getPatientId(), patient);
            }
            break;
        }
      }

      do {
        do {
          response = patientUI.runPatientListMenu(filteredPatientMap, ui, page);
          vResponse = PatientValidation.validatePatientListMenu(response[0], response[1].toCharArray());
        } while (!Boolean.parseBoolean(vResponse[0]));

        if (!vResponse[1].equals("0")) {
          switch (vResponse[1]) {
            case "Q":
              if (page > 1) {
                page--;
              }
              break;
            case "E":
              if (page < (filteredPatientMap.size() + 9) / 10) {
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
              if (!filteredPatientMap.isEmpty()) {
                runPatientAction("edit");
              } else {
                MessageUI.noPatientRecord();
              }
              break;
            case "2":
              if (!filteredPatientMap.isEmpty()) {
                runPatientAction("delete");
              } else {
                MessageUI.noPatientRecord();
              }
              break;
          }
        } else {
          exit = true;
        }
      } while (!exit);
    }
  }

  public void runPatientClearance() {

    String[] response;
    String[] vResponse;
    
    if (!patientMap.isEmpty()) {
      response = patientUI.runPatientClearanceMenu();
      vResponse = PatientValidation.validatePatientClearance(response[0], response[1].toCharArray());
      if (Boolean.parseBoolean(vResponse[0])) {
        if (vResponse[1].equals("1")) {
          patientMap.clear();
          patientDAO.clearFile();
          patientDAO.saveToFile(patientMap);
          Patient.setLastId(0);
          System.out.println("Patient records cleared.");
          Utilities.pressAnyKey();
        }
      }
    } else {
      System.out.println("Error! Clearance can only be performed when there are existing Patient records.");
      Utilities.pressAnyKey();
    }
  }
  
  public void runPatientInitialization() {
    if (patientMap.isEmpty()) {
      int sampleSize = patientUI.inputSampleSize();
      if (sampleSize > 0) {
        System.out.println("Initializing new sample Patient records...");
        Utilities.wait(1000);
        patientMap = patientInitializer.initializeSamplePatients(sampleSize);
        patientDAO.saveToFile(patientMap);
        System.out.println("Initialization done.");
        Utilities.pressAnyKey();
      }
    } else {
      System.out.println("Error! Initialization can only be performed when there are no existing Patient records.");
      Utilities.pressAnyKey();
    }
  }
}
