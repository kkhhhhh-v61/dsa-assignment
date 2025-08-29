package control;

import adt.ArrayList;
import adt.RedBlackTreeMap;
import dao.AppointmentDAO;
import dao.DoctorScheduleDAO;
import dao.PatientDAO;
import entity.Appointment;
import entity.Doctor;
import entity.Patient;
import entity.Schedule;
import utility.MessageUI;
import utility.Validation;

/**
 *
 * @author Tan Kai Hao
 */
public class AppointmentValidation {
  static AppointmentDAO appointmentDAO = new AppointmentDAO();
  static DoctorScheduleDAO doctorScheduleDAO = new DoctorScheduleDAO();
  static PatientDAO patientDAO = new PatientDAO();
  static ArrayList<Doctor> doctorList;
  static ArrayList<Schedule> scheduleList;
  static RedBlackTreeMap<String, Appointment> appointmentMap;
  static RedBlackTreeMap<String, Doctor> doctorMap;
  static RedBlackTreeMap<String, Patient> patientMap;
  static RedBlackTreeMap<String, Schedule> scheduleMap;

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
  }
  
  public static boolean validateCharInput(String input, char[] inputOptions) {
    if (input.equals("") || inputOptions == null) {
      return false;
    }

    if (!Validation.isChar(input)) {
      MessageUI.notSingleChar();
      return false;
    }

    if (!Validation.isValidOption(Character.toUpperCase(input.charAt(0)), inputOptions)) {
      MessageUI.invalidOption(inputOptions);
      return false;
    }

    return true;
  }

  public static boolean isIdExists(char prefix, String id) {
    loadAllData();
    switch (prefix) {
      case 'A':
        return appointmentMap.containsKey("A" + id);
      case 'P':
        return patientMap.containsKey("P" + id);
      case 'D':
        return doctorMap.containsKey("D" + id);
    }

    return false;
  }

  public static boolean validateIdInput(char prefix, String input, char[] inputOptions, int length) {
    if (input.equals("") || inputOptions == null || length == 0) {
      return false;
    }

    if (!Validation.isChar(input)) {
      if (!Validation.withinLength(input, length)) {
        MessageUI.exceedLength(length);
        return false;
      }

      if (!Validation.isNumeric(input)) {
        MessageUI.notNumeric();
        return false;
      }

      if (!isIdExists(prefix, input)) {
        MessageUI.idDoesNotExist();
        return false;
      }

      return true;
    }

    if (!Validation.isValidOption(Character.toUpperCase(input.charAt(0)), inputOptions)) {
      MessageUI.invalidOption(inputOptions);
      return false;
    }

    return true;
  }

  public static boolean validateDoctorScheduleIdInput(ArrayList<String[]> list, String input, char[] inputOptions, int length) {
    if (input.equals("") || inputOptions == null || length == 0) {
      return false;
    }

    if (!Validation.isChar(input)) {
      if (!Validation.withinLength(input, length)) {
        MessageUI.exceedLength(length);
        return false;
      }

      if (!Validation.isNumeric(input)) {
        MessageUI.notNumeric();
        return false;
      }

      for (String[] item : list) {
        if (item[0].equals("D" + input)) {
          return true;
        }
      }

      MessageUI.idDoesNotExist();
      return false;
    }

    if (!Validation.isValidOption(Character.toUpperCase(input.charAt(0)), inputOptions)) {
      MessageUI.invalidOption(inputOptions);
      return false;
    }

    return true;
  }

  public static boolean validateDateInput(String input, char[] inputOptions) {
    if (input.equals("")) {
      return false;
    }

    if (!Validation.isChar(input)) {
      if (!Validation.isValidDate(input)) {
        MessageUI.invalidDate();
        return false;
      }

      if (!Validation.isFutureDate(input)) {
        MessageUI.mustBeFutureDate();
        return false;
      }

      if (!Validation.isWithinOneYear(input)) {
        MessageUI.notWithinOneYear();
        return false;
      }

      return true;
    }

    if (!Validation.isValidOption(Character.toUpperCase(input.charAt(0)), inputOptions)) {
      MessageUI.invalidOption(inputOptions);
      return false;
    }

    return true;
  }

  public static boolean validateTimeInput(String input, char[] inputOptions, String startTime, String endTime) {
    if (input.equals("")) {
      return false;
    }

    if (!Validation.isChar(input)) {
      if (!Validation.isValidTime(input)) {
        MessageUI.invalidTime();
        return false;
      }

      if (!Validation.withinRange(Integer.parseInt(input), Integer.parseInt(startTime.replace(":", "")), Integer.parseInt(endTime.replace(":", "")))) {
        MessageUI.withinTimeRange(startTime, endTime);
        return false;
      }

      return true;
    }

    if (!Validation.isValidOption(Character.toUpperCase(input.charAt(0)), inputOptions)) {
      MessageUI.invalidOption(inputOptions);
      return false;
    }

    return true;
  }

  public static boolean validateReasonInput(String input, char[] inputOptions, int length) {
    if (input.equals("")) {
      return false;
    }

    if (!Validation.isChar(input)) {
      if (!Validation.withinLength(input, length)) {
        MessageUI.exceedLength(length);
        return false;
      }

      return true;
    }

    if (!Validation.isValidOption(Character.toUpperCase(input.charAt(0)), inputOptions)) {
      MessageUI.invalidOption(inputOptions);
      return false;
    }

    return true;
  }
}
