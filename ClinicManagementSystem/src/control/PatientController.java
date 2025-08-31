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

/**
 *
 * @author Tan Kai Hao
 */
public class PatientController {
  private static final int ID_LENGTH = 4;
  private static final int NAME_LENGTH = 35;
  private static final int EMAIL_LENGTH = 35;
  private static final int ADDRESS_LENGTH = 45;

  PatientUI patientUI;
  PatientDAO patientDAO;
  PatientInitializer patientInitializer;
  private RedBlackTreeMap<String, Patient> patientMap;

  public PatientController() {
    patientUI = new PatientUI();
    patientDAO = new PatientDAO();
    patientInitializer = new PatientInitializer();
    loadAllData();
  }

  public void loadAllData() {
    patientMap = patientDAO.readFromFile();
  }

  public void runPatientManagement() {
    do {
      Utilities.clearScreen();
      char[] inputOptions = patientUI.displayPatientManagement();
      String userInput = patientUI.getGeneralInput();
      boolean valid = PatientValidation.validateCharInput(userInput, inputOptions);
      
      if (valid) {
        switch (userInput.charAt(0)) {
          case '1':
            runPatientList();
            continue;
          case '2':
            runPatientRegistration();
            continue;
          case '3':
            runPatientEdit();
            continue;
          case '4':
            runPatientDelete();
            continue;
          case '5':
            runPatientSearch();
            continue;
          case '6':
            runPatientClearance();
            continue;
          case '7':
            runPatientInitialization();
            continue;
          case '8':
            runReportGeneration();
            continue;
          case '0':
            return;
        }
      }
    } while (true);
  }

  public void runPatientList() {
    int part = 1, page = 1;
    do {
      Utilities.clearScreen();
      char[] inputOptions = new char[] {};
      if (part == 1) {
        inputOptions = patientUI.displayPatientListPart1(patientMap, page);
      } else if (part == 2) {
        inputOptions = patientUI.displayPatientListPart2(patientMap, page);
      }
      String userInput = patientUI.getGeneralInput();
      boolean valid = PatientValidation.validateCharInput(userInput, inputOptions);
        
      if (valid) {
        switch (Character.toUpperCase(userInput.charAt(0))) {
          case 'Q':
            page = Math.max(1, page - 1);
            continue;
          case 'E':
            page = Math.min((patientMap.size() > 0) ? (patientMap.size() + 9) / 10 : 1, page + 1);
            continue;
          case 'W':
            part = (part == 1) ? 2 : 1;
            continue;
          case '0':
            return;
        }
      }
    } while (true);
  }

  public void runPatientRegistration() {
    if (!patientMap.isEmpty()) {
      ArrayList<String> keys = patientMap.getKeys();
      String lastKey = keys.get(keys.size());
      Patient lastPatient = patientMap.get(lastKey);
      String lastId = lastPatient.getPatientId().substring(1);
      Patient.setLastId(Integer.parseInt(lastId));
    } else {
      Patient.setLastId(0);
    }
    
    Patient patient = new Patient();

    String icNumber = null;
    do {
      icNumber = runIcNumberInput("");
      if (icNumber != null && icNumber.equals("0")) {
        return;
      }
    } while (icNumber == null);
    patient.setIcNumber(Utilities.formatIcNumber(icNumber));
    patient.setGender(patient.identifyGender());
    patient.setBirthDate(patient.identifyBirthDate());
    patient.setAge(patient.calculateAge());

    String name = null;
    do {
      name = runPatientNameInput("");
      if (name != null && name.equals("0")) {
        return;
      }
    } while (name == null);
    patient.setName(Utilities.formatName(name));

    String contactNumber = null;
    do {
      contactNumber = runContactNumberInput("");
      if (contactNumber != null && contactNumber.equals("0")) {
        return;
      }
    } while (contactNumber == null);
    patient.setContactNumber(Utilities.formatContactNumber(contactNumber));

    String email = null;
    do {
      email = runEmailInput("");
      if (email != null && email.equals("0")) {
        return;
      }
    } while (email == null);
    patient.setEmail(email);

    String address = null;
    do {
      address = runAddressInput("");
      if (address != null && address.equals("0")) {
        return;
      }
    } while (address == null);
    patient.setAddress(address);

    String bloodType = null;
    do {
      bloodType = runBloodTypeInput("");
      if (bloodType != null && bloodType.equals("0")) {
        return;
      }
    } while (bloodType == null);
    switch (bloodType.charAt(0)) {
      case '1':
        patient.setBloodType(BloodType.A);
        break;
      case '2':
        patient.setBloodType(BloodType.B);
        break;
      case '3':
        patient.setBloodType(BloodType.AB);
        break;
      case '4':
        patient.setBloodType(BloodType.O);
        break;
    }

    patientMap.put(patient.getPatientId(), patient);

    boolean confirm = false;
    do {
      String action = runRegistrationConfirmation(patient);
      if (action != null && action.equals("0")) {
        patientMap.remove(patient.getPatientId());
        return;
      }

      if (action.equals("1")) {
        patientDAO.writeToFile(patientMap);
        MessageUI.registrationSuccess();
        confirm = true;
      } else if (action.equals("2")) {
        runRegistrationEdit(patient);
      }
    } while (!confirm);
  }

  public String runPatientIdInput() {
    Utilities.clearScreen();
    char[] inputOptions = patientUI.displayPatientIdInput();
    String userInput = patientUI.getSpecificInput("P");
    return (PatientValidation.validatePatientIdInput(userInput, inputOptions, ID_LENGTH)) ? userInput : null;
  }

  public String runIcNumberInput(String icNumber) {
    Utilities.clearScreen();
    char[] inputOptions = patientUI.displayIcNumberInput();
    if (!icNumber.isEmpty()) patientUI.displayCurrentValue("IC Number", icNumber);
    String userInput = patientUI.getGeneralInput();
    return (PatientValidation.validateIcNumberInput(userInput, inputOptions)) ? userInput : null;
  }

  public String runPatientNameInput(String name) {
    Utilities.clearScreen();
    char[] inputOptions = patientUI.displayPatientNameInput();
    if (!name.isEmpty()) patientUI.displayCurrentValue("Name", name);
    String userInput = patientUI.getGeneralInput();
    return (PatientValidation.validateNameInput(userInput, inputOptions, NAME_LENGTH)) ? userInput : null;
  }

  public String runGenderInput() {
    Utilities.clearScreen();
    char[] inputOptions = patientUI.displayGenderInput();
    String userInput = patientUI.getGeneralInput();
    return (PatientValidation.validateCharInput(userInput, inputOptions)) ? userInput : null;
  }

  public String runBirthDateInput() {
    Utilities.clearScreen();
    char[] inputOptions = patientUI.displayBirthDateInput();
    String userInput = patientUI.getGeneralInput();
    return (PatientValidation.validateBirthDateInput(userInput, inputOptions)) ? userInput : null;
  }

  public String runAgeInput() {
    Utilities.clearScreen();
    char[] inputOptions = patientUI.displayAgeInput();
    String userInput = patientUI.getGeneralInput();
    return (PatientValidation.validateAgeInput(userInput, inputOptions)) ? userInput : null;
  }

  public String runContactNumberInput(String contactNumber) {
    Utilities.clearScreen();
    char[] inputOptions = patientUI.displayContactNumberInput();
    if (!contactNumber.isEmpty()) patientUI.displayCurrentValue("Contact Number", contactNumber);
    String userInput = patientUI.getGeneralInput();
    return (PatientValidation.validateContactNumberInput(userInput, inputOptions)) ? userInput : null;
  }

  public String runEmailInput(String email) {
    Utilities.clearScreen();
    char[] inputOptions = patientUI.displayEmailInput();
    if (!email.isEmpty()) patientUI.displayCurrentValue("Email", email);
    String userInput = patientUI.getGeneralInput();
    return (PatientValidation.validateEmailInput(userInput, inputOptions, EMAIL_LENGTH)) ? userInput : null;
  }

  public String runAddressInput(String address) {
    Utilities.clearScreen();
    char[] inputOptions = patientUI.displayAddressInput();
    if (!address.isEmpty()) patientUI.displayCurrentValue("Address", address);
    String userInput = patientUI.getGeneralInput();
    return (PatientValidation.validateAddressInput(userInput, inputOptions, ADDRESS_LENGTH)) ? userInput : null;
  }

  public String runRegistrationDateInput() {
    Utilities.clearScreen();
    char[] inputOptions = patientUI.displayRegistrationDateInput();
    String userInput = patientUI.getGeneralInput();
    return (PatientValidation.validateDateInput(userInput, inputOptions)) ? userInput : null;
  }

  public String runBloodTypeInput(String bloodType) {
    Utilities.clearScreen();
    char[] inputOptions = patientUI.displayBloodTypeInput();
    if (!bloodType.isEmpty()) patientUI.displayCurrentValue("Blood Type", bloodType);
    String userInput = patientUI.getGeneralInput();
    return (PatientValidation.validateCharInput(userInput, inputOptions)) ? userInput : null;
  }

  public String runRegistrationConfirmation(Patient patient) {
    Utilities.clearScreen();
    patientUI.displayRegistrationConfirmation(patient);
    char[] inputOptions = patientUI.displayRegistrationConfirmationMenu();
    String userInput = patientUI.getGeneralInput();
    return (PatientValidation.validateCharInput(userInput, inputOptions)) ? userInput : null;
  }

  public void runRegistrationEdit(Patient patient) {
    do {
      Utilities.clearScreen();
      patientUI.displayRegistrationConfirmation(patient);
      char[] inputOptions = patientUI.displayPatientEditMenu();
      String userInput = patientUI.getGeneralInput();
      boolean valid = PatientValidation.validateCharInput(userInput, inputOptions);
      
      String patientId = patient.getPatientId();
      if (valid) {
        switch (userInput.charAt(0)) {
          case '1':
            runIcNumberEdit(patientId);
            continue;
          case '2':
            runNameEdit(patientId);
            continue;
          case '3':
            runContactNumberEdit(patientId);
            continue;
          case '4':
            runEmailEdit(patientId);
            continue;
          case '5':
            runAddressEdit(patientId);
            continue;
          case '6':
            runBloodTypeEdit(patientId);
            continue;
          case '0':
            return;
        }
      }
    } while (true);
  }

  public void runPatientEdit() {
    if (patientMap.isEmpty()) {
      System.out.println();
      System.out.println("Error! Edit can only be performed when there are existing Patient records.");
      Utilities.pressAnyKey();
      return;
    }

    int part = 1, page = 1;
    do {
      Utilities.clearScreen();
      char[] inputOptions = new char[] {};
      if (part == 1) {
        inputOptions = patientUI.displayPatientListPart1(patientMap, page);
      } else if (part == 2) {
        inputOptions = patientUI.displayPatientListPart2(patientMap, page);
      }
      patientUI.displayPatientIdInput();
      String userInput = patientUI.getSpecificInput("P");
      boolean valid = PatientValidation.validatePatientIdInput(userInput, inputOptions, ID_LENGTH);
      
      if (valid) {
        switch (userInput.toUpperCase()) {
          case "Q":
            page = Math.max(1, page - 1);
            continue;
          case "E":
            page = Math.min((patientMap.size() + 9) / 10, page + 1);
            continue;
          case "W":
            part = (part == 1) ? 2 : 1;
            continue;
          case "0":
            return;
          default:
            runPatientEditOptions("P" + userInput);
            break;
        }
        break;
      }
    } while (true);
  }

  public void runPatientEditOptions(String patientId) {
    Patient patient = patientMap.get(patientId);
    if (patient == null) {
      return;
    }

    int part = 1;
    do {
      Utilities.clearScreen();
      char[] inputOptions1;
      if (part == 1) {
        inputOptions1 = patientUI.displaySinglePatientPart1(patient);
      } else {
        inputOptions1 = patientUI.displaySinglePatientPart2(patient);
      }
      char[] inputOptions2 = patientUI.displayPatientEditMenu();
      String userInput = patientUI.getGeneralInput();
      char[] inputOptions = new char[inputOptions1.length + inputOptions2.length];
      System.arraycopy(inputOptions1, 0, inputOptions, 0, inputOptions1.length);
      System.arraycopy(inputOptions2, 0, inputOptions, inputOptions1.length, inputOptions2.length);
      boolean valid = PatientValidation.validateCharInput(userInput, inputOptions);
      
      if (valid) {
        switch (Character.toUpperCase(userInput.charAt(0))) {
          case 'W':
            part = (part == 1) ? 2 : 1;
            continue;
          case '1':
            runIcNumberEdit(patientId);
            break;
          case '2':
            runNameEdit(patientId);
            break;
          case '3':
            runContactNumberEdit(patientId);
            break;
          case '4':
            runEmailEdit(patientId);
            break;
          case '5':
            runAddressEdit(patientId);
            break;
          case '6':
            runBloodTypeEdit(patientId);
            break;
          case '0':
            return;
        }
      }
    } while (true);
  }

  public void runIcNumberEdit(String patientId) {
    String icNumber = null;
    do {
      icNumber = runIcNumberInput(patientMap.get(patientId).getIcNumber());
      if (icNumber != null && icNumber.equals("0")) {
        return;
      }
    } while (icNumber == null);
    
    String confirm = null;
    do {
      confirm = runEditConfirmation(patientMap.get(patientId), "IC Number", patientMap.get(patientId).getIcNumber(), Utilities.formatIcNumber(icNumber));
    } while (confirm == null);

    if (confirm.equals("0")) {
      return;
    } else if (confirm.equals("1")) {
      patientMap.get(patientId).setIcNumber(Utilities.formatIcNumber(icNumber));
      patientMap.get(patientId).setGender(patientMap.get(patientId).identifyGender());
      patientMap.get(patientId).setBirthDate(patientMap.get(patientId).identifyBirthDate());
      patientMap.get(patientId).setAge(patientMap.get(patientId).calculateAge());
      patientDAO.writeToFile(patientMap);
      MessageUI.editSuccess();
    }
  }

  public void runNameEdit(String patientId) {
    String name = null;
    do {
      name = runPatientNameInput(patientMap.get(patientId).getName());
      if (name != null && name.equals("0")) {
        return;
      }
    } while (name == null);
    
    String confirm = null;
    do {
      confirm = runEditConfirmation(patientMap.get(patientId), "Name", patientMap.get(patientId).getName(), Utilities.formatName(name));
    } while (confirm == null);

    if (confirm.equals("0")) {
      return;
    } else if (confirm.equals("1")) {
      patientMap.get(patientId).setName(Utilities.formatName(name));
      patientDAO.writeToFile(patientMap);
      MessageUI.editSuccess();
    }
  }

  public void runContactNumberEdit(String patientId) {
    String contactNumber = null;
    do {
      contactNumber = runContactNumberInput(patientMap.get(patientId).getContactNumber());
      if (contactNumber != null && contactNumber.equals("0")) {
        return;
      }
    } while (contactNumber == null);
    
    String confirm = null;
    do {
      confirm = runEditConfirmation(patientMap.get(patientId), "Contact Number", patientMap.get(patientId).getContactNumber(), Utilities.formatContactNumber(contactNumber));
    } while (confirm == null);

    if (confirm.equals("0")) {
      return;
    } else if (confirm.equals("1")) {
      patientMap.get(patientId).setContactNumber(Utilities.formatContactNumber(contactNumber));
      patientDAO.writeToFile(patientMap);
      MessageUI.editSuccess();
    }
  }

  public void runEmailEdit(String patientId) {
    String email = null;
    do {
      email = runEmailInput(patientMap.get(patientId).getEmail());
      if (email != null && email.equals("0")) {
        return;
      }
    } while (email == null);
    
    String confirm = null;
    do {
      confirm = runEditConfirmation(patientMap.get(patientId), "Email", patientMap.get(patientId).getEmail(), email);
    } while (confirm == null);

    if (confirm.equals("0")) {
      return;
    } else if (confirm.equals("1")) {
      patientMap.get(patientId).setEmail(email);
      patientDAO.writeToFile(patientMap);
      MessageUI.editSuccess();
    }
  }

  public void runAddressEdit(String patientId) {
    String address = null;
    do {
      address = runAddressInput(patientMap.get(patientId).getAddress());
      if (address != null && address.equals("0")) {
        return;
      }
    } while (address == null);
    
    String confirm = null;
    do {
      confirm = runEditConfirmation(patientMap.get(patientId), "Address", patientMap.get(patientId).getAddress(), address);
    } while (confirm == null);

    if (confirm.equals("0")) {
      return;
    } else if (confirm.equals("1")) {
      patientMap.get(patientId).setAddress(address);
      patientDAO.writeToFile(patientMap);
      MessageUI.editSuccess();
    }
  }

  public void runBloodTypeEdit(String patientId) {
    String bloodType = null;
    do {
      bloodType = runBloodTypeInput(patientMap.get(patientId).getBloodType().toString());
      if (bloodType != null && bloodType.equals("0")) {
        return;
      }
    } while (bloodType == null);

    switch (bloodType.charAt(0)) {
      case '1':
        bloodType = "A";
        break;
      case '2':
        bloodType = "B";
        break;
      case '3':
        bloodType = "AB";
        break;
      case '4':
        bloodType = "O";
        break;
    }
    
    String confirm = null;
    do {
      confirm = runEditConfirmation(patientMap.get(patientId), "Blood Type", patientMap.get(patientId).getBloodType().toString(), bloodType);
    } while (confirm == null);

    if (confirm.equals("0")) {
      return;
    } else if (confirm.equals("1")) {
      patientMap.get(patientId).setBloodType(BloodType.valueOf(bloodType));
      patientDAO.writeToFile(patientMap);
      MessageUI.editSuccess();
    }
  }
  
  public String runEditConfirmation(Patient patient, String field, String currentValue, String newValue) {
    Utilities.clearScreen();
    char[] inputOptions = patientUI.displayEditConfirmationMenu(field, currentValue, newValue);
    String userInput = patientUI.getGeneralInput();
    return (PatientValidation.validateCharInput(userInput, inputOptions)) ? userInput : null;
  }

  public void runPatientDelete() {
    if (patientMap.isEmpty()) {
      System.out.println();
      System.out.println("Error! Delete can only be performed when there are existing Patient records.");
      Utilities.pressAnyKey();
      return;
    }

    int part = 1, page = 1;
    do {
      Utilities.clearScreen();
      char[] inputOptions = new char[] {};
      if (part == 1) {
        inputOptions = patientUI.displayPatientListPart1(patientMap, page);
      } else {
        inputOptions = patientUI.displayPatientListPart2(patientMap, page);
      }
      patientUI.displayPatientIdInput();
      String userInput = patientUI.getSpecificInput("P");
      boolean valid = PatientValidation.validatePatientIdInput(userInput, inputOptions, ID_LENGTH);
      
      if (valid) {
        switch (userInput.toUpperCase()) {
          case "Q":
            page = Math.max(1, page - 1);
            continue;
          case "E":
            page = Math.min((patientMap.size() + 9) / 10, page + 1);
            continue;
          case "W":
            part = (part == 1) ? 2 : 1;
            continue;
          case "0":
            return;
          default:
            runDeleteConfirmation("P" + userInput);
            break;
        }
        break;
      }
    } while (true);
  }

  public void runDeleteConfirmation(String patientId) {
    Patient patient = patientMap.get(patientId);
    if (patient == null) {
      return;
    }

    int part = 1;
    do {
      Utilities.clearScreen();
      char[] inputOptions1;
      if (part == 1) {
        inputOptions1 = patientUI.displaySinglePatientPart1(patient);
      } else {
        inputOptions1 = patientUI.displaySinglePatientPart2(patient);
      }
      char[] inputOptions2 = patientUI.displayDeleteConfirmationMenu();
      String userInput = patientUI.getGeneralInput();
      char[] inputOptions = new char[inputOptions1.length + inputOptions2.length];
      System.arraycopy(inputOptions1, 0, inputOptions, 0, inputOptions1.length);
      System.arraycopy(inputOptions2, 0, inputOptions, inputOptions1.length, inputOptions2.length);
      boolean valid = PatientValidation.validateCharInput(userInput, inputOptions);
      
      if (valid) {
        switch (Character.toUpperCase(userInput.charAt(0))) {
          case 'W':
            part = (part == 1) ? 2 : 1;
            continue;
          case '1':
            patientMap.remove(patientId);
            patientDAO.writeToFile(patientMap);
            MessageUI.deleteSuccess();
            return;
          case '0':
            return;
        }
      }
    } while (true);
  }

  public void runPatientSearch() {
    if (patientMap.isEmpty()) {
      System.out.println();
      System.out.println("Error! Search can only be performed when there are existing Patient records.");
      Utilities.pressAnyKey();
      return;
    }
    
    do {
      String searchOption = runSearchOptionInput();
      if (searchOption == null) {
        continue;
      }
      if (searchOption.equals("0")) {
        return;
      }

      RedBlackTreeMap<String, Patient> resultsMap = new RedBlackTreeMap<>();
      switch (Character.toUpperCase(searchOption.charAt(0))) {
        case '1':
          String patientId = runPatientIdSearch();
          if (patientId.equals("0")) {
            continue;
          }
          resultsMap = getPatientIdSearchResult(patientId);
          break;
        case '2':
          String icNumber = runIcNumberSearch();
          if (icNumber.equals("0")) {
            continue;
          }
          resultsMap = getIcNumberSearchResult(icNumber);
          break;
        case '3':
          String name = runNameSearch();
          if (name.equals("0")) {
            continue;
          }
          resultsMap = getNameSearchResult(name);
          break;
        case '4':
          String gender = runGenderSearch();
          if (gender.equals("0")) {
            continue;
          }
          resultsMap = getGenderSearchResult(gender);
          break;
        case '5':
          String birthDate = runBirthDateSearch();
          if (birthDate.equals("0")) {
            continue;
          }
          resultsMap = getBirthDateSearchResult(birthDate);
          break;
        case '6':
          String age = runAgeSearch();
          if (age.equals("0")) {
            continue;
          }
          resultsMap = getAgeSearchResult(age);
          break;
        case '7':
          String contactNumber = runContactNumberSearch();
          if (contactNumber.equals("0")) {
            continue;
          }
          resultsMap = getContactNumberSearchResult(contactNumber);
          break;
        case '8':
          String email = runEmailSearch();
          if (email.equals("0")) {
            continue;
          }
          resultsMap = getEmailSearchResult(email);
          break;
        case '9':
          String address = runAddressSearch();
          if (address.equals("0")) {
            continue;
          }
          resultsMap = getAddressSearchResult(address);
          break;
        case 'A':
          String registrationDate = runRegistrationDateSearch();
          if (registrationDate.equals("0")) {
            continue;
          }
          resultsMap = getRegistrationDateSearchResult(registrationDate);
          break;
        case 'B':
          String bloodType = runBloodTypeSearch();
          if (bloodType.equals("0")) {
            continue;
          }
          resultsMap = getBloodTypeSearchResult(bloodType);
          break;
      }

      if (resultsMap == null) {
        MessageUI.noSearchResult();
        continue;
      }

      int part = 1, page = 1;
      do {
        String action = runSearchResultDisplay(resultsMap, part, page);
        if (action == null) {
          continue;
        }

        switch (Character.toUpperCase(action.charAt(0))) {
          case 'Q':
            page = Math.max(1, page - 1);
            break;
          case 'E':
            page = Math.min((resultsMap.size() > 0) ? (resultsMap.size() + 9) / 10 : 1, page + 1);
            break;
          case 'W':
            part = (part == 1) ? 2 : 1;
            break;
          case '0':
            return;
        }
      } while (true);
    } while (true);
  }

  public String runSearchOptionInput() {
    Utilities.clearScreen();
    char[] inputOptions = patientUI.displaySearchMenu();
    String userInput = patientUI.getGeneralInput();
    return (PatientValidation.validateCharInput(userInput, inputOptions)) ? userInput : null;
  }

  public String runSearchResultDisplay(RedBlackTreeMap<String, Patient> patientMap, int part, int page) {
    char[] inputOptions;
    Utilities.clearScreen();
    if (part == 1) {
      inputOptions = patientUI.displayPatientListPart1(patientMap, page);
    } else {
      inputOptions = patientUI.displayPatientListPart2(patientMap, page);
    }
    String userInput = patientUI.getGeneralInput();
    return (PatientValidation.validateCharInput(userInput, inputOptions)) ? userInput : null;
  }

  public String runPatientIdSearch() {
    String patientId = null;
    do {
      patientId = runPatientIdInput();
      if (patientId != null && patientId.equals("0")) {
        return patientId;
      }
    } while (patientId == null);

    return "P" + patientId;
  }

  public RedBlackTreeMap<String, Patient> getPatientIdSearchResult(String patientId) {
    RedBlackTreeMap<String, Patient> resultsMap = new RedBlackTreeMap<>();
    Patient patient = patientMap.get(patientId);
    if (patient == null) {
      return null;
    }
    resultsMap.put(patient.getPatientId(), patient);
    return resultsMap;
  }
  
  public String runIcNumberSearch() {
    String icNumber = null;
    do {
      icNumber = runIcNumberInput("");
      if (icNumber != null && icNumber.equals("0")) {
        return icNumber;
      }
    } while (icNumber == null);

    return Utilities.formatIcNumber(icNumber);
  }

  public RedBlackTreeMap<String, Patient> getIcNumberSearchResult(String icNumber) {
    RedBlackTreeMap<String, Patient> resultsMap = new RedBlackTreeMap<>();
    ArrayList<String> keys = patientMap.getKeys();
    for (String key : keys) {
      if (patientMap.get(key).getIcNumber().equals(icNumber)) {
        resultsMap.put(patientMap.get(key).getPatientId(), patientMap.get(key));
      }
    }
    return resultsMap;
  }

  public String runNameSearch() {
    String name = null;
    do {
      name = runPatientNameInput("");
      if (name != null && name.equals("0")) {
        return name;
      }
    } while (name == null);

    return Utilities.formatName(name);
  }

  public RedBlackTreeMap<String, Patient> getNameSearchResult(String name) {
    RedBlackTreeMap<String, Patient> resultsMap = new RedBlackTreeMap<>();
    ArrayList<String> keys = patientMap.getKeys();
    for (String key : keys) {
      if (patientMap.get(key).getName().toLowerCase().contains(name.toLowerCase())) {
        resultsMap.put(patientMap.get(key).getPatientId(), patientMap.get(key));
      }
    }
    return resultsMap;
  }

  public String runGenderSearch() {
    String gender = null;
    do {
      gender = runGenderInput();
      if (gender != null && gender.equals("0")) {
        return gender;
      }
    } while (gender == null);
    
    switch (gender.charAt(0)) {
      case '1':
        gender = "Male";
        break;
      case '2':
        gender = "Female";
        break;
    }

    return gender;
  }

  public RedBlackTreeMap<String, Patient> getGenderSearchResult(String gender) {
    RedBlackTreeMap<String, Patient> resultsMap = new RedBlackTreeMap<>();
    ArrayList<String> keys = patientMap.getKeys();
    for (String key : keys) {
      if (patientMap.get(key).getGender().equals(Gender.valueOf(gender))) {
        resultsMap.put(patientMap.get(key).getPatientId(), patientMap.get(key));
      }
    }
    return resultsMap;
  }

  public String runBirthDateSearch() {
    String birthDate = null;
    do {
      birthDate = runBirthDateInput();
      if (birthDate != null && birthDate.equals("0")) {
        return birthDate;
      }
    } while (birthDate == null);

    return birthDate;
  }

  public RedBlackTreeMap<String, Patient> getBirthDateSearchResult(String birthDate) {
    RedBlackTreeMap<String, Patient> resultsMap = new RedBlackTreeMap<>();
    ArrayList<String> keys = patientMap.getKeys();
    for (String key : keys) {
      if (patientMap.get(key).getBirthDate().equals(Utilities.formatDate(birthDate))) {
        resultsMap.put(patientMap.get(key).getPatientId(), patientMap.get(key));
      }
    }
    return resultsMap;
  }

  public String runAgeSearch() {
    String age = null;
    do {
      age = runAgeInput();
      if (age != null && age.equals("0")) {
        return age;
      }
    } while (age == null);

    return age;
  }

  public RedBlackTreeMap<String, Patient> getAgeSearchResult(String age) {
    RedBlackTreeMap<String, Patient> resultsMap = new RedBlackTreeMap<>();
    ArrayList<String> keys = patientMap.getKeys();
    for (String key : keys) {
      if (patientMap.get(key).getAge() == Integer.parseInt(age)) {
        resultsMap.put(patientMap.get(key).getPatientId(), patientMap.get(key));
      }
    }
    return resultsMap;
  }

  public String runContactNumberSearch() {
    String contactNumber = null;
    do {
      contactNumber = runContactNumberInput("");
      if (contactNumber != null && contactNumber.equals("0")) {
        return contactNumber;
      }
    } while (contactNumber == null);

    return Utilities.formatContactNumber(contactNumber);
  }

  public RedBlackTreeMap<String, Patient> getContactNumberSearchResult(String contactNumber) {
    RedBlackTreeMap<String, Patient> resultsMap = new RedBlackTreeMap<>();
    ArrayList<String> keys = patientMap.getKeys();
    for (String key : keys) {
      if (patientMap.get(key).getContactNumber().equals(contactNumber)) {
        resultsMap.put(patientMap.get(key).getPatientId(), patientMap.get(key));
      }
    }
    return resultsMap;
  }

  public String runEmailSearch() {
    String email = null;
    do {
      email = runEmailInput("");
      if (email != null && email.equals("0")) {
        return email;
      }
    } while (email == null);

    return email;
  }

  public RedBlackTreeMap<String, Patient> getEmailSearchResult(String email) {
    RedBlackTreeMap<String, Patient> resultsMap = new RedBlackTreeMap<>();
    ArrayList<String> keys = patientMap.getKeys();
    for (String key : keys) {
      if (patientMap.get(key).getEmail().equals(email)) {
        resultsMap.put(patientMap.get(key).getPatientId(), patientMap.get(key));
      }
    }
    return resultsMap;
  }

  public String runAddressSearch() {
    String address = null;
    do {
      address = runAddressInput("");
      if (address != null && address.equals("0")) {
        return address;
      }
    } while (address == null);

    return address;
  }

  public RedBlackTreeMap<String, Patient> getAddressSearchResult(String address) {
    RedBlackTreeMap<String, Patient> resultsMap = new RedBlackTreeMap<>();
    ArrayList<String> keys = patientMap.getKeys();
    for (String key : keys) {
      if (patientMap.get(key).getAddress().toLowerCase().contains(address.toLowerCase())) {
        resultsMap.put(patientMap.get(key).getPatientId(), patientMap.get(key));
      }
    }
    return resultsMap;
  }

  public String runRegistrationDateSearch() {
    String registrationDate = null;
    do {
      registrationDate = runRegistrationDateInput();
      if (registrationDate != null && registrationDate.equals("0")) {
        return registrationDate;
      }
    } while (registrationDate == null);

    return registrationDate;
  }

  public RedBlackTreeMap<String, Patient> getRegistrationDateSearchResult(String registrationDate) {
    RedBlackTreeMap<String, Patient> resultsMap = new RedBlackTreeMap<>();
    ArrayList<String> keys = patientMap.getKeys();
    for (String key : keys) {
      if (patientMap.get(key).getRegistrationDate().equals(Utilities.formatDate(registrationDate))) {
        resultsMap.put(patientMap.get(key).getPatientId(), patientMap.get(key));
      }
    }
    return resultsMap;
  }

  public String runBloodTypeSearch() {
    String bloodType = null;
    do {
      bloodType = runBloodTypeInput("");
      if (bloodType != null && bloodType.equals("0")) {
        return bloodType;
      }
    } while (bloodType == null);

    switch (bloodType.charAt(0)) {
      case '1':
        bloodType = "A";
        break;
      case '2':
        bloodType = "B";
        break;
      case '3':
        bloodType = "AB";
        break;
      case '4':
        bloodType = "O";
        break;
    }

    return bloodType;
  }

  public RedBlackTreeMap<String, Patient> getBloodTypeSearchResult(String bloodType) {
    RedBlackTreeMap<String, Patient> resultsMap = new RedBlackTreeMap<>();
    ArrayList<String> keys = patientMap.getKeys();
    for (String key : keys) {
      if (patientMap.get(key).getBloodType().equals(BloodType.valueOf(bloodType))) {
        resultsMap.put(patientMap.get(key).getPatientId(), patientMap.get(key));
      }
    }
    return resultsMap;
  }

  public void runPatientClearance() {
    if (patientMap.isEmpty()) {
      System.out.println();
      System.out.println("Error! Clearance can only be performed when there are existing Patient records.");
      Utilities.pressAnyKey();
      return;
    }

    String action = null;
    do {
      Utilities.clearScreen();
      char[] inputOptions = patientUI.displayRecordClearanceMenu();
      String userInput = patientUI.getGeneralInput();
      action = (PatientValidation.validateCharInput(userInput, inputOptions)) ? userInput : null;
    } while (action == null);

    if (action.equals("0")) {
      return;
    }

    System.out.println();
    patientMap.clear();
    patientDAO.clearFile();
    patientDAO.writeToFile(patientMap);
    Patient.setLastId(0);
    System.out.println("Patient Record Clearance DONE!");
    Utilities.pressAnyKey();
  }

  public void runPatientInitialization() {
    if (!patientMap.isEmpty()) {
      System.out.println();
      System.out.println("Error! Initialization can only be performed when there are no existing Patient records.");
      Utilities.pressAnyKey();
      return;
    }

    String action = null;
    do {
      Utilities.clearScreen();
      char[] inputOptions = patientUI.displayInitializationSizeInput();
      String userInput = patientUI.getGeneralInput();
      action = (PatientValidation.validateInitializationSizeInput(userInput, inputOptions)) ? userInput : null;
    } while (action == null);

    if (action.equals("0")) {
      return;
    }

    String confirm = null;
    do {
      Utilities.clearScreen();
      char[] inputOptions = patientUI.displayInitializationConfirmationMenu();
      String userInput = patientUI.getGeneralInput();
      confirm = (PatientValidation.validateCharInput(userInput, inputOptions)) ? userInput : null;
    } while (confirm == null);

    if (confirm.equals("0")) {
      return;
    }

    System.out.println();
    System.out.println("Initiating Patient Initialization...");
    patientMap = patientInitializer.initializeSamplePatients(Integer.parseInt(action));
    patientDAO.writeToFile(patientMap);
    System.out.println("Patient Initialization DONE!");
    Utilities.pressAnyKey();
  }

  public void runReportGeneration() {
    do {
      Utilities.clearScreen();
      char[] inputOptions = patientUI.displayReportGenerationMenu();
      String userInput = patientUI.getGeneralInput();
      boolean valid = PatientValidation.validateCharInput(userInput, inputOptions);

      if (valid) {
        switch (Character.toUpperCase(userInput.charAt(0))) {
          case '1':
            runReport1();
            continue;
          case '2':
            runReport2();
            continue;
          case '0':
            return;
        }
      }
    } while (true);
  }

  public void runReport1() {
    loadAllData();
    if (patientMap.isEmpty()) {
      System.out.println();
      System.out.println("Error! Report can only be generated when there are existing Patient records.");
      Utilities.pressAnyKey();
      return;
    }

    Utilities.clearScreen();
    RedBlackTreeMap<String, int[]> ageGroupMap = calculateTotalPatientByAgeGroup();
    patientUI.displayReport1(ageGroupMap);
    Utilities.pressAnyKey();
  }
  
  public RedBlackTreeMap<String, int[]> calculateTotalPatientByAgeGroup() {
    RedBlackTreeMap<String, int[]> ageGroupMap = new RedBlackTreeMap<>();
    ArrayList<String> keys = patientMap.getKeys();
    // [0] male, [1] female
    int childCount[] = { 0, 0 };
    int teenagerCount[] = { 0, 0 };
    int youngAdultCount[] = { 0, 0 };
    int adultCount[] = { 0, 0 };
    int middleAdultCount[] = { 0, 0 };
    int seniorCount[] = { 0, 0 };
    int elderlyCount[] = { 0, 0 };
    for (String key : keys) {
      Patient patient = patientMap.get(key);
      int age = patient.getAge();
      if (age >= 0 && age <= 10) {
        childCount[patient.getGender().equals(Gender.Male) ? 0 : 1]++;
      } else if (age > 10 && age <= 20) {
        teenagerCount[patient.getGender().equals(Gender.Male) ? 0 : 1]++;
      } else if (age > 20 && age <= 30) {
        youngAdultCount[patient.getGender().equals(Gender.Male) ? 0 : 1]++;
      } else if (age > 30 && age <= 40) {
        adultCount[patient.getGender().equals(Gender.Male) ? 0 : 1]++;
      } else if (age > 40 && age <= 50) {
        middleAdultCount[patient.getGender().equals(Gender.Male) ? 0 : 1]++;
      } else if (age > 50 && age <= 60) {
        seniorCount[patient.getGender().equals(Gender.Male) ? 0 : 1]++;
      } else if (age > 60) {
        elderlyCount[patient.getGender().equals(Gender.Male) ? 0 : 1]++;
      }
    }
    ageGroupMap.put("Infant/Child", childCount);
    ageGroupMap.put("Teenager", teenagerCount);
    ageGroupMap.put("Young Adult", youngAdultCount);
    ageGroupMap.put("Adult", adultCount);
    ageGroupMap.put("Middle-Aged Adult", middleAdultCount);
    ageGroupMap.put("Senior", seniorCount);
    ageGroupMap.put("Elderly", elderlyCount);
    return ageGroupMap;
  }

  public void runReport2() {
    
  }
}
