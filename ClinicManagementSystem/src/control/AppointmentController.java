package control;

import adt.ArrayList;
import adt.RedBlackTreeMap;
import boundary.AppointmentUI;
import dao.AppointmentDAO;
import dao.DoctorScheduleDAO;
import dao.PatientDAO;
import entity.Appointment;
import entity.Doctor;
import entity.Patient;
import entity.Schedule;
import utility.Enum.Status;
import utility.MessageUI;
import utility.Utilities;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author Tan Kai Hao
 */
public class AppointmentController {
  private static final int ID_LENGTH = 4;
  private static final int REASON_LENGTH = 35;

  static AppointmentUI appointmentUI;
  static AppointmentDAO appointmentDAO = new AppointmentDAO();
  static DoctorScheduleDAO doctorScheduleDAO = new DoctorScheduleDAO();
  static PatientDAO patientDAO = new PatientDAO();
  static RedBlackTreeMap<String, Appointment> appointmentMap;
  static RedBlackTreeMap<String, Doctor> doctorMap;
  static RedBlackTreeMap<String, Patient> patientMap;
  static RedBlackTreeMap<String, Schedule> scheduleMap;
  static ArrayList<Doctor> doctorList;
  static ArrayList<Schedule> scheduleList;
  static ArrayList<String[]> doctorScheduleList;

  @SuppressWarnings("unchecked")
  public static void loadAllData() {
    appointmentMap = appointmentDAO.readFromFile();
    doctorList = (ArrayList<Doctor>) doctorScheduleDAO.readFromFile("doctor");
    doctorMap = new RedBlackTreeMap<>();
    for (Doctor doctor : doctorList) {
      doctorMap.put(doctor.getDoctorId(), doctor);
    }
    patientMap = patientDAO.readFromFile();
    scheduleList = (ArrayList<Schedule>) doctorScheduleDAO.readFromFile("schedule");
    scheduleMap = new RedBlackTreeMap<>();
    for (Schedule schedule : scheduleList) {
      scheduleMap.put(schedule.getScheduleId(), schedule);
    }
    doctorScheduleList = new ArrayList<>();
    if (doctorList != null && scheduleList != null) {
      for (Doctor doctor : doctorList) {
        for (Schedule schedule : scheduleList) {
          if (doctor.getDoctorId().equals(schedule.getDoctorId())) {
            doctorScheduleList.add(new String[] { doctor.getDoctorId(), doctor.getName(), schedule.getDay().toString(),
                schedule.getStartTime().toString(), schedule.getEndTime().toString(),
                schedule.getIsAvailable() ? "Available" : "Not Available" });
          }
        }
      }
    }
  }

  public AppointmentController() {
    appointmentUI = new AppointmentUI();
    appointmentDAO = new AppointmentDAO();
    appointmentMap = appointmentDAO.readFromFile();
    if (appointmentMap == null) appointmentMap = new RedBlackTreeMap<>();
    loadAllData();
  }

  public void runAppointmentManagement() {
    do {
      loadAllData();
      Utilities.clearScreen();
      char[] inputOptions = appointmentUI.displayAppointmentManagement();
      String userInput = appointmentUI.getGeneralInput();
      boolean valid = AppointmentValidation.validateCharInput(userInput, inputOptions);

      if (valid) {
        switch (userInput.charAt(0)) {
          case '1':
            runAppointmentList(null);
            continue;
          case '2':
            runAppointmentStatusUpdate();
            continue;
          case '3':
            runAppointmentEdit();
            continue;
          case '4':
            runAppointmentDelete();
            continue;
          case '5':
            runAppointmentSearch();
            continue;
          case '6':
            runAppointmentClearance();
            continue;
          case '0':
            return;
        }
      }
    } while (true);
  }

  public String runAppointmentIdInput() {
    char[] inputOptions = appointmentUI.displayAppointmentIdInput();
    String userInput = appointmentUI.getSpecificInput("A");
    return (AppointmentValidation.validateIdInput('A', userInput, inputOptions, ID_LENGTH)) ? userInput : null;
  }

  public String runPatientIdInput() {
    char[] inputOptions = appointmentUI.displayPatientIdInput();
    String userInput = appointmentUI.getSpecificInput("P");
    return (AppointmentValidation.validateIdInput('P', userInput, inputOptions, ID_LENGTH)) ? userInput : null;
  }

  public String runDoctorIdInput(String doctorId) {
    char[] inputOptions = appointmentUI.displayDoctorIdInput();
    if (!doctorId.isEmpty()) appointmentUI.displayCurrentValue("Doctor ID", doctorId);
    String userInput = appointmentUI.getSpecificInput("D");
    return (AppointmentValidation.validateIdInput('D', userInput, inputOptions, ID_LENGTH)) ? userInput : null;
  }

  public String runAppointmentDateInput(String date) {
    char[] inputOptions = appointmentUI.displayDateInput();
    if (!date.isEmpty()) appointmentUI.displayCurrentValue("Date", date);
    String userInput = appointmentUI.getGeneralInput();
    userInput = userInput.replace("-", "");
    return (AppointmentValidation.validateDateInput(userInput, inputOptions)) ? userInput : null;
  }

  public String runAppointmentTimeInput(String time, String startTime, String endTime) {
    char[] inputOptions = appointmentUI.displayTimeInput();
    if (!time.isEmpty()) appointmentUI.displayCurrentValue("Time", time);
    String userInput = appointmentUI.getGeneralInput();
    userInput = userInput.replaceAll(":", "");
    return (AppointmentValidation.validateTimeInput(userInput, inputOptions, startTime, endTime)) ? userInput : null;
  }

  public String runReasonInput(String reason) {
    char[] inputOptions = appointmentUI.displayReasonInput();
    if (!reason.isEmpty()) appointmentUI.displayCurrentValue("Reason", reason);
    String userInput = appointmentUI.getGeneralInput();
    return (AppointmentValidation.validateReasonInput(userInput, inputOptions, REASON_LENGTH)) ? userInput : null;
  }

  public String runStatusInput(String status) {
    char[] inputOptions = appointmentUI.displayStatusInput();
    if (!status.isEmpty()) appointmentUI.displayCurrentValue("Status", status);
    String userInput = appointmentUI.getGeneralInput();
    return (AppointmentValidation.validateCharInput(userInput, inputOptions)) ? userInput : null;
  }

  public void runAppointmentList(RedBlackTreeMap<String, Appointment> filteredMap) {
    int page = 1;
    do {
      loadAllData();
      if (filteredMap != null && !filteredMap.isEmpty()) appointmentMap = filteredMap;
      Utilities.clearScreen();
      char[] inputOptions = appointmentUI.displayAppointmentList(appointmentMap, page);
      String userInput = appointmentUI.getGeneralInput();
      boolean valid = AppointmentValidation.validateCharInput(userInput, inputOptions);

      if (valid) {
        switch (Character.toUpperCase(userInput.charAt(0))) {
          case 'Q':
            page = Math.max(1, page - 1);
            continue;
          case 'E':
            page = Math.min((appointmentMap.size() > 0) ? (appointmentMap.size() + 9) / 10 : 1, page + 1);
            continue;
          case '0':
            return;
        }
      }
    } while (true);
  }

  public void runAppointmentStatusUpdate() {
    if (appointmentMap.isEmpty()) {
      System.out.println();
      System.out.println("Error! Status Update can only be performed when there are existing Appointment records.");
      Utilities.pressAnyKey();
      return;
    }

    int page = 1;
    do {
      loadAllData();
      Utilities.clearScreen();
      char[] inputOptions = appointmentUI.displayAppointmentList(appointmentMap, page);
      appointmentUI.displayAppointmentIdInput();
      String userInput = appointmentUI.getSpecificInput("A");
      boolean valid = AppointmentValidation.validateIdInput('A', userInput, inputOptions, ID_LENGTH);

      if (valid) {
        switch (Character.toUpperCase(userInput.charAt(0))) {
          case 'Q':
            page = Math.max(1, page - 1);
            continue;
          case 'E':
            page = Math.min((appointmentMap.size() > 0) ? (appointmentMap.size() + 9) / 10 : 1, page + 1);
            continue;
          case '0':
            return;
          default:
            runStatusEdit("A" + userInput);
            break;
        }
      }
    } while (true);
  }

  public void runAppointmentEdit() {
    if (appointmentMap.isEmpty()) {
      System.out.println();
      System.out.println("Error! Edit can only be performed when there are existing Appointment records.");
      Utilities.pressAnyKey();
      return;
    }

    int page = 1;
    do {
      loadAllData();
      Utilities.clearScreen();
      char[] inputOptions = appointmentUI.displayAppointmentList(appointmentMap, page);
      appointmentUI.displayAppointmentIdInput();
      String userInput = appointmentUI.getSpecificInput("A");
      boolean valid = AppointmentValidation.validateIdInput('A', userInput, inputOptions, ID_LENGTH);

      if (valid) {
        switch (Character.toUpperCase(userInput.charAt(0))) {
          case 'Q':
            page = Math.max(1, page - 1);
            continue;
          case 'E':
            page = Math.min((appointmentMap.size() > 0) ? (appointmentMap.size() + 9) / 10 : 1, page + 1);
            continue;
          case '0':
            return;
          default:
            runAppointmentEditOptions("A" + userInput);
            break;
        }
      }
    } while (true);
  }

  public void runAppointmentEditOptions(String appointmentId) {
    Appointment appointment = appointmentMap.get(appointmentId);
    if (appointment == null) {
      return;
    }

    do {
      loadAllData();
      Utilities.clearScreen();
      appointmentUI.displaySingleAppointment(appointment);
      char[] inputOptions = appointmentUI.displayAppointmentEditMenu();
      String userInput = appointmentUI.getGeneralInput();
      boolean valid = AppointmentValidation.validateCharInput(userInput, inputOptions);

      if (valid) {
        switch (Character.toUpperCase(userInput.charAt(0))) {
          case '1':
            Utilities.clearScreen();
            runDoctorIdEdit(appointmentId);
            break;
          case '2':
            Utilities.clearScreen();
            runAppointmentDateEdit(appointmentId);
            break;
          case '3':
            Utilities.clearScreen();
            runAppointmentTimeEdit(appointmentId);
            break;
          case '4':
            Utilities.clearScreen();
            runStatusEdit(appointmentId);
            break;
          case '0':
            return;
        }
      }
    } while (true);
  }

  public void runDoctorIdEdit(String appointmentId) {
    Appointment appointment = appointmentMap.get(appointmentId);
    if (appointment == null) {
      return;
    }

    String doctorId = null;
    do {
      doctorId = runDoctorIdInput(appointment.getDoctorId());
      if (doctorId != null && doctorId.equals("0")) {
        return;
      }
    } while (doctorId == null);
    
    String confirm = null;
    do {
      confirm = runEditConfirmation(appointment, "Doctor ID", appointment.getDoctorId(), doctorId);
    } while (confirm == null);

    if (confirm.equals("0")) {
      return;
    } else if (confirm.equals("1")) {
      appointment.setDoctorId(doctorId);
      appointmentDAO.writeToFile(appointmentMap);
      MessageUI.editSuccess();
    }
  }

  public void runAppointmentDateEdit(String appointmentId) {
    Appointment appointment = appointmentMap.get(appointmentId);
    if (appointment == null) {
      return;
    }

    String date = null;
    do {
      date = runAppointmentDateInput(appointment.getAppointmentDate().toString());
      if (date != null && date.equals("0")) {
        return;
      }
    } while (date == null);
    
    String confirm = null;
    do {
      confirm = runEditConfirmation(appointment, "Date", appointment.getAppointmentDate().toString(), date);
    } while (confirm == null);

    if (confirm.equals("0")) {
      return;
    } else if (confirm.equals("1")) {
      appointment.setAppointmentDate(LocalDate.parse(date));
      appointmentDAO.writeToFile(appointmentMap);
      MessageUI.editSuccess();
      
      Utilities.clearScreen();
      System.out.println("Detected that the appointment date has been changed. Please update the appointment time accordingly.");
      Utilities.pressAnyKey();
      runAppointmentTimeEdit(appointmentId);
    }
  }

  public void runAppointmentTimeEdit(String appointmentId) {
    Appointment appointment = appointmentMap.get(appointmentId);
    if (appointment == null) {
      return;
    }

    String doctorId = appointment.getDoctorId();
    String appointmentDay = appointment.getAppointmentDate().getDayOfWeek().toString();
    String startTime = "";
    String endTime = "";
    for (Schedule schedule : scheduleList) {
      if (schedule.getDoctorId().equals(doctorId) && schedule.getDay().toString().equalsIgnoreCase(appointmentDay)) {
        startTime = schedule.getStartTime().toString();
        endTime = schedule.getEndTime().toString();
      }
    }

    if (startTime.isEmpty() || endTime.isEmpty()) {
      MessageUI.noScheduleAvailable(appointment.getAppointmentDate().toString(), appointmentDay);
      return;
    }

    String time = null;
    do {
      Utilities.clearScreen();
      time = runAppointmentTimeInput(appointment.getAppointmentTime().toString(), startTime, endTime);
      if (time != null && time.equals("0")) {
        return;
      }
    } while (time == null);
    
    String confirm = null;
    do {
      confirm = runEditConfirmation(appointment, "Time", appointment.getAppointmentTime().toString(), time);
    } while (confirm == null);

    if (confirm.equals("0")) {
      return;
    } else if (confirm.equals("1")) {
      appointment.setAppointmentTime(LocalTime.parse(time));
      appointmentDAO.writeToFile(appointmentMap);
      MessageUI.editSuccess();
    }
  }

  public void runStatusEdit(String appointmentId) {
    Appointment appointment = appointmentMap.get(appointmentId);
    if (appointment == null) {
      return;
    }

    String status = null;
    do {
      status = runStatusInput(appointment.getStatus().toString());
      if (status != null && status.equals("0")) {
        return;
      }

      switch (Character.toUpperCase(status.charAt(0))) {
        case '1':
          status = "Approved";
          break;
        case '2':
          status = "Rejected";
          break;
        case '3':
          status = "Pending";
          break;
        case '4':
          status = "Cancelled";
          break;
        default:
          break;
      }
    } while (status == null);
    
    String confirm = null;
    do {
      confirm = runEditConfirmation(appointment, "Status", appointment.getStatus().toString(), status);
    } while (confirm == null);

    if (confirm.equals("0")) {
      return;
    } else if (confirm.equals("1")) {
      appointment.setStatus(Status.valueOf(status));
      appointmentDAO.writeToFile(appointmentMap);
      MessageUI.editSuccess();
    }
  }

  public String runEditConfirmation(Appointment appointment, String field, String currentValue, String newValue) {
    Utilities.clearScreen();
    char[] inputOptions = appointmentUI.displayEditConfirmationMenu(field, currentValue, newValue);
    String userInput = appointmentUI.getGeneralInput();
    return (AppointmentValidation.validateCharInput(userInput, inputOptions)) ? userInput : null;
  }

  public void runAppointmentDelete() {
    if (appointmentMap.isEmpty()) {
      System.out.println();
      System.out.println("Error! Delete can only be performed when there are existing Appointment records.");
      Utilities.pressAnyKey();
      return;
    }
    
    int page = 1;
    do {
      loadAllData();
      Utilities.clearScreen();
      char[] inputOptions = appointmentUI.displayAppointmentList(appointmentMap, page);
      appointmentUI.displayAppointmentIdInput();
      String userInput = appointmentUI.getSpecificInput("A");
      boolean valid = AppointmentValidation.validateIdInput('A', userInput, inputOptions, ID_LENGTH);

      if (valid) {
        switch (Character.toUpperCase(userInput.charAt(0))) {
          case 'Q':
            page = Math.max(1, page - 1);
            continue;
          case 'E':
            page = Math.min((appointmentMap.size() > 0) ? (appointmentMap.size() + 9) / 10 : 1, page + 1);
            continue;
          case '0':
            return;
          default:
            runDeleteConfirmation("A" + userInput);
            break;
        }
      }
    } while (true);
  }

  public void runDeleteConfirmation(String appointmentId) {
    Appointment appointment = appointmentMap.get(appointmentId);
    if (appointment == null) {
      return;
    }

    do {
      Utilities.clearScreen();
      char[] inputOptions = appointmentUI.displayDeleteConfirmationMenu();
      String userInput = appointmentUI.getGeneralInput();
      boolean valid = AppointmentValidation.validateCharInput(userInput, inputOptions);

      if (valid) {
        switch (Character.toUpperCase(userInput.charAt(0))) {
          case '1':
            appointmentMap.remove(appointmentId);
            appointmentDAO.writeToFile(appointmentMap);
            MessageUI.deleteSuccess();
            return;
          case '0':
            return;
        }
      }
    } while (true);
  }
  
  public void runAppointmentSearch() {
    if (appointmentMap.isEmpty()) {
      System.out.println();
      System.out.println("Error! Search can only be performed when there are existing Appointment records.");
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

      RedBlackTreeMap<String, Appointment> resultsMap = new RedBlackTreeMap<>();
      switch (Character.toUpperCase(searchOption.charAt(0))) {
        case '1':
          String appointmentId = runAppointmentIdSearch();
          if (appointmentId.equals("0")) {
            continue;
          }
          resultsMap = getAppointmentIdSearchResult(appointmentId);
          break;
        case '2':
          String patientId = runPatientIdSearch();
          if (patientId.equals("0")) {
            continue;
          }
          resultsMap = getPatientIdSearchResult(patientId);
          break;
        case '3':
          String doctorId = runDoctorIdSearch();
          if (doctorId.equals("0")) {
            continue;
          }
          resultsMap = getDoctorIdSearchResult(doctorId);
          break;
        case '4':
          String appointmentDate = runAppointmentDateSearch();
          if (appointmentDate.equals("0")) {
            continue;
          }
          resultsMap = getAppointmentDateSearchResult(appointmentDate);
          break;
        case '5':
          String status = runStatusSearch();
          if (status.equals("0")) {
            continue;
          }
          resultsMap = getStatusSearchResult(status);
          break;
      }

      if (resultsMap == null) {
        MessageUI.noSearchResult();
        continue;
      }

      int page = 1;
      do {
        String action = runSearchResultDisplay(resultsMap, page);
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
          case '0':
            return;
        }
      } while (true);
    } while (true);
  }

  public String runSearchOptionInput() {
    Utilities.clearScreen();
    char[] inputOptions = appointmentUI.displaySearchMenu();
    String userInput = appointmentUI.getGeneralInput();
    return (AppointmentValidation.validateCharInput(userInput, inputOptions)) ? userInput : null;
  }

  public String runSearchResultDisplay(RedBlackTreeMap<String, Appointment> appointmentMap, int page) {
    char[] inputOptions;
    Utilities.clearScreen();
    inputOptions = appointmentUI.displayAppointmentList(appointmentMap, page);
    String userInput = appointmentUI.getGeneralInput();
    return (AppointmentValidation.validateCharInput(userInput, inputOptions)) ? userInput : null;
  }

  public String runAppointmentIdSearch() {
    String appointmentId = null;
    do {
      appointmentId = runAppointmentIdInput();
      if (appointmentId != null && appointmentId.equals("0")) {
        return appointmentId;
      }
    } while (appointmentId == null);

    return "A" + appointmentId;
  }

  public RedBlackTreeMap<String, Appointment> getAppointmentIdSearchResult(String appointmentId) {
    RedBlackTreeMap<String, Appointment> resultsMap = new RedBlackTreeMap<>();
    Appointment appointment = appointmentMap.get(appointmentId);
    if (appointment == null) {
      return null;
    }
    resultsMap.put(appointment.getAppointmentId(), appointment);
    return resultsMap;
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

  public RedBlackTreeMap<String, Appointment> getPatientIdSearchResult(String patientId) {
    RedBlackTreeMap<String, Appointment> resultsMap = new RedBlackTreeMap<>();
    ArrayList<String> keys = appointmentMap.getKeys();
    for (String key : keys) {
      if (appointmentMap.get(key).getPatientId().equals(patientId)) {
        resultsMap.put(appointmentMap.get(key).getAppointmentId(), appointmentMap.get(key));
      }
    }
    return resultsMap;
  }

  public String runDoctorIdSearch() {
    String doctorId = null;
    do {
      doctorId = runDoctorIdInput("");
      if (doctorId != null && doctorId.equals("0")) {
        return doctorId;
      }
    } while (doctorId == null);

    return "D" + doctorId;
  }

  public RedBlackTreeMap<String, Appointment> getDoctorIdSearchResult(String doctorId) {
    RedBlackTreeMap<String, Appointment> resultsMap = new RedBlackTreeMap<>();
    ArrayList<String> keys = appointmentMap.getKeys();
    for (String key : keys) {
      if (appointmentMap.get(key).getDoctorId().equals(doctorId)) {
        resultsMap.put(appointmentMap.get(key).getAppointmentId(), appointmentMap.get(key));
      }
    }
    return resultsMap;
  }

  public String runAppointmentDateSearch() {
    String appointmentDate = null;
    do {
      appointmentDate = runAppointmentDateInput("");
      if (appointmentDate != null && appointmentDate.equals("0")) {
        return appointmentDate;
      }
    } while (appointmentDate == null);

    return appointmentDate;
  }

  public RedBlackTreeMap<String, Appointment> getAppointmentDateSearchResult(String appointmentDate) {
    RedBlackTreeMap<String, Appointment> resultsMap = new RedBlackTreeMap<>();
    ArrayList<String> keys = appointmentMap.getKeys();
    for (String key : keys) {
      if (appointmentMap.get(key).getAppointmentDate().equals(LocalDate.parse(appointmentDate))) {
        resultsMap.put(appointmentMap.get(key).getAppointmentId(), appointmentMap.get(key));
      }
    }
    return resultsMap;
  }

  public String runStatusSearch() {
    String status = null;
    do {
      status = runStatusInput("");
      if (status != null && status.equals("0")) {
        return status;
      }
    } while (status == null);

    switch (status.charAt(0)) {
      case '1':
        status = "Approved";
        break;
      case '2':
        status = "Rejected";
        break;
      case '3':
        status = "Pending";
        break;
      case '4':
        status = "Cancelled";
        break;
    }

    return status;
  }

  public RedBlackTreeMap<String, Appointment> getStatusSearchResult(String status) {
    RedBlackTreeMap<String, Appointment> resultsMap = new RedBlackTreeMap<>();
    ArrayList<String> keys = appointmentMap.getKeys();
    for (String key : keys) {
      if (appointmentMap.get(key).getStatus().equals(Status.valueOf(status))) {
        resultsMap.put(appointmentMap.get(key).getAppointmentId(), appointmentMap.get(key));
      }
    }
    return resultsMap;
  }
  
  public void runAppointmentClearance() {
    if (appointmentMap.isEmpty()) {
      System.out.println();
      System.out.println("Error! Clearance can only be performed when there are existing Appointment records.");
      Utilities.pressAnyKey();
      return;
    }

    String action = null;
    do {
      Utilities.clearScreen();
      char[] inputOptions = appointmentUI.displayRecordClearanceMenu();
      String userInput = appointmentUI.getGeneralInput();
      action = (AppointmentValidation.validateCharInput(userInput, inputOptions)) ? userInput : null;
    } while (action == null);

    if (action.equals("0")) {
      return;
    }

    System.out.println();
    appointmentMap.clear();
    appointmentDAO.clearFile();
    appointmentDAO.writeToFile(appointmentMap);
    Appointment.setLastId(0);
    System.out.println("Appointment Record Clearance DONE!");
    Utilities.pressAnyKey();
  }

  public void runStudentAppointment(String patientId) {
    do {
      Utilities.clearScreen();
      char[] inputOptions = appointmentUI.displayStudentAppointment();
      String userInput = appointmentUI.getGeneralInput();
      boolean valid = AppointmentValidation.validateCharInput(userInput, inputOptions);

      if (valid) {
        switch (userInput.charAt(0)) {
          case '1':
            runStudentAppointmentList(patientId);
            continue;
          case '2':
            runStudentAppointmentBooking(patientId);
            continue;
          case '3':
            runStudentAppointmentCancellation(patientId);
            continue;
          case '0':
            return;
        }
      }
    } while (true);
  }

  public void runStudentAppointmentList(String patientId) {
    RedBlackTreeMap<String, Appointment> filteredMap = new RedBlackTreeMap<>();
    ArrayList<String> keys = appointmentMap.getKeys();
    for (String key : keys) {
      if (appointmentMap.get(key).getPatientId().equals(patientId)) {
        filteredMap.put(appointmentMap.get(key).getAppointmentId(), appointmentMap.get(key));
      }
    }
    runAppointmentList(filteredMap);
  }

  public void runStudentAppointmentBooking(String patientId) {
    loadAllData();
    if (!appointmentMap.isEmpty()) {
      ArrayList<String> keys = appointmentMap.getKeys();
      String lastKey = keys.get(keys.size());
      Appointment lastAppointment = appointmentMap.get(lastKey);
      String lastId = lastAppointment.getAppointmentId().substring(1);
      Appointment.setLastId(Integer.parseInt(lastId));
    } else {
      Appointment.setLastId(0);
    }
    
    Appointment appointment = new Appointment();
    appointment.setPatientId(patientId);

    String date = null;
    do {
      Utilities.clearScreen();
      date = runAppointmentDateInput("");
      if (date != null && date.equals("0")) {
        return;
      }
    } while (date == null);
    appointment.setAppointmentDate(Utilities.formatDate(date));

    ArrayList<String[]> filteredList = new ArrayList<>();
    String appointmentDay = appointment.getAppointmentDate().getDayOfWeek().toString();
    for (String[] doctorSchedule : doctorScheduleList) {
      if (doctorSchedule[2].equalsIgnoreCase(appointmentDay)) {
        filteredList.add(doctorSchedule);
      }
    }

    int page = 1;
    String doctorId = null;
    do {
      if (doctorScheduleList.isEmpty()) {
        System.out.println();
        System.out.println("Booking Failed! No available Doctor Schedule on the selected date.");
        Utilities.pressAnyKey();
        return;
      }
      Utilities.clearScreen();
      char[] inputOptions = appointmentUI.displayDoctorScheduleList(filteredList, page);
      appointmentUI.displayDoctorIdInput();
      String userInput = appointmentUI.getSpecificInput("D");
      boolean valid = AppointmentValidation.validateDoctorScheduleIdInput(filteredList, userInput, inputOptions, ID_LENGTH);

      if (valid) {
        switch (userInput.toUpperCase()) {
          case "Q":
            page = Math.max(1, page - 1);
            continue;
          case "E":
            page = Math.min((filteredList.size() > 0) ? (filteredList.size() + 9) / 10 : 1, page + 1);
            continue;
          case "0":
            return;
          default:
            doctorId = "D" + userInput;
            break;
        }
      }
    } while (doctorId == null);
    appointment.setDoctorId(doctorId);

    String startTime = "";
    String endTime = "";
    String[] filteredDoctorSchedule = new String[] {};
    for (String[] doctorSchedule : doctorScheduleList) {
      if (doctorSchedule[0].equals(doctorId) && doctorSchedule[2].equalsIgnoreCase(appointment.getAppointmentDate().getDayOfWeek().toString())) {
        filteredDoctorSchedule = doctorSchedule;
        startTime = doctorSchedule[3];
        endTime = doctorSchedule[4];
      }
    }

    String time = null;
    do {
      Utilities.clearScreen();
      if (filteredDoctorSchedule.length == 0) {
        System.out.println();
        System.out.println("Booking Failed! No available Schedule on the selected Doctor.");
        Utilities.pressAnyKey();
        return;
      }
      appointmentUI.displaySingleDoctorSchedule(filteredDoctorSchedule);
      time = runAppointmentTimeInput("", startTime, endTime);
      if (time != null && time.equals("0")) {
        return;
      }
    } while (time == null);
    appointment.setAppointmentTime(Utilities.formatTime(time));

    String reason = null;
    do {
      Utilities.clearScreen();
      reason = runReasonInput("");
      if (reason != null && reason.equals("0")) {
        return;
      }
    } while (reason == null);
    appointment.setReason(reason);
    appointment.setStatus(Status.Pending);

    appointmentMap.put(appointment.getAppointmentId(), appointment);
    
    do {
      String action = runBookingConfirmation(appointment);
      if (action != null && action.equals("0")) {
        appointmentMap.remove(appointment.getAppointmentId());
        return;
      }

      if (action.equals("1")) {
        appointmentDAO.writeToFile(appointmentMap);
        MessageUI.bookingSuccess();
        return;
      }
      /* REMOVED BUGGED FEATURE THAT I DON'T HAVE TIME TO FIX :) */
      // else if (action.equals("2")) {
      //   runBookingEdit(appointment, startTime, endTime);
      //   continue;
      // }
    } while (true);
  }

  public String runBookingConfirmation(Appointment appointment) {
    Utilities.clearScreen();
    appointmentUI.displayAppointmentConfirmation(appointment);
    char[] inputOptions = appointmentUI.displayAppointmentConfirmationMenu();
    String userInput = appointmentUI.getGeneralInput();
    return (AppointmentValidation.validateCharInput(userInput, inputOptions)) ? userInput : null;
  }

  /* REMOVED BUGGED FEATURE THAT I DON'T HAVE TIME TO FIX :) */
  // public void runBookingEdit(Appointment appointment, String startTime, String endTime) {
  //   do {
  //     Utilities.clearScreen();
  //     appointmentUI.displayAppointmentConfirmation(appointment);
  //     char[] inputOptions = appointmentUI.displayAppointmentEditMenu();
  //     String userInput = appointmentUI.getGeneralInput();
  //     boolean valid = AppointmentValidation.validateCharInput(userInput, inputOptions);
      
  //     String appointmentId = appointment.getAppointmentId();
  //     if (valid) {
  //       switch (userInput.charAt(0)) {
  //         case '1':
  //           runDoctorIdInput(appointmentMap.get(appointmentId).getDoctorId());
  //           continue;
  //         case '2':
  //           runAppointmentDateInput(appointmentMap.get(appointmentId).getAppointmentDate().toString());
  //           continue;
  //         case '3':
  //           runAppointmentTimeInput(appointmentMap.get(appointmentId).getAppointmentTime().toString(), startTime, endTime);
  //           continue;
  //         case '4':
  //           runReasonInput(appointmentMap.get(appointmentId).getReason());
  //           continue;
  //         case '0':
  //           return;
  //       }
  //     }
  //   } while (true);
  // }

  public void runStudentAppointmentCancellation(String patientId) {
    Appointment appointment = appointmentMap.get(patientId);
    RedBlackTreeMap<String, Appointment> filteredMap = new RedBlackTreeMap<>();
    ArrayList<String> keys = appointmentMap.getKeys();
    for (String key : keys) {
      if (appointmentMap.get(key).getPatientId().equals(patientId)) {
        filteredMap.put(appointmentMap.get(key).getAppointmentId(), appointmentMap.get(key));
      }
    }

    int page = 1;
    do {
      Utilities.clearScreen();
      char[] inputOptions = appointmentUI.displayAppointmentList(filteredMap, page);
      appointmentUI.displayAppointmentIdInput();
      String userInput = appointmentUI.getSpecificInput("A");
      boolean valid = AppointmentValidation.validateIdInput('A', userInput, inputOptions, ID_LENGTH);

      if (valid) {
        switch (userInput.toUpperCase()) {
          case "Q":
            page = Math.max(1, page - 1);
            break;
          case "E":
            page = Math.min((filteredMap.size() > 0) ? (filteredMap.size() + 9) / 10 : 1, page + 1);
            break;
          case "1":
            appointment.setStatus(Status.Cancelled);
            appointmentMap.put(appointment.getAppointmentId(), appointment);
            appointmentDAO.writeToFile(appointmentMap);
            MessageUI.cancellationSuccess();
            return;
          case "0":
            return;
          default:
            runAppointmentCancellationConirmation("A" + userInput);
            break;
        }
      }
    } while (true);
  }
  
  public void runAppointmentCancellationConirmation(String appointmentId) {
    Appointment appointment = appointmentMap.get(appointmentId);
    Utilities.clearScreen();
    appointmentUI.displaySingleAppointment(appointment);
    char[] inputOptions = appointmentUI.displayAppointmentCancellationMenu();
    String userInput = appointmentUI.getGeneralInput();
    boolean valid = AppointmentValidation.validateCharInput(userInput, inputOptions);

    if (valid) {
      switch (userInput.charAt(0)) {
        case '1':
          appointment.setStatus(Status.Cancelled);
          appointmentMap.put(appointmentId, appointment);
          appointmentDAO.writeToFile(appointmentMap);
          MessageUI.cancellationSuccess();
          return;
        case '0':
          return;
      }
    }
  }
}
