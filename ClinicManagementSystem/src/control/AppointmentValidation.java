package control;

import adt.ArrayList;
import adt.RedBlackTreeMap;
import entity.Doctor;
import entity.Patient;
import entity.Schedule;
import utility.MessageUI;
import utility.Utilities;
import utility.Validation;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author Tan Kai Hao
 */
public class AppointmentValidation {
  public static String[] validatePatientIdExistence(RedBlackTreeMap<String, Patient> patientMap, String input,
      char[] inputOptions) {
    boolean valid = false;
    String response = input;

    if (!input.equals("")) {
      if (Validation.isSingleChar(input)) {
        char option = input.charAt(0);

        if (Validation.isValidOption(option, inputOptions)) {
          response = String.valueOf(option);
          valid = true;
        } else {
          MessageUI.notValidOption(inputOptions);
          valid = false;
        }
      } else {
        if (Validation.isValidId(patientMap, "P" + input)) {
          response = "P" + input;
          valid = true;
        } else {
          MessageUI.notValidId("P" + input);
          valid = false;
        }
      }
    }

    return new String[] { String.valueOf(valid), response };
  }
  
  public static String[] validateDoctorIdExistence(RedBlackTreeMap<String, Doctor> doctorMap, String input,
      char[] inputOptions) {
    boolean valid = false;
    String response = input;

    if (!input.equals("")) {
      if (Validation.isSingleChar(input)) {
        char option = Character.toUpperCase(input.charAt(0));

        if (Validation.isValidOption(option, inputOptions)) {
          response = String.valueOf(option);
          valid = true;
        } else {
          MessageUI.notValidOption(inputOptions);
          valid = false;
        }
      } else {
        if (Validation.isValidId(doctorMap, "D" + input)) {
          response = "D" + input;
          valid = true;
        } else {
          MessageUI.notValidId("D" + input);
          valid = false;
        }
      }
    }

    return new String[] { String.valueOf(valid), response };
  }
  
  public static String[] validateAppointmentDate(RedBlackTreeMap<String, Doctor> doctorMap, String doctorId, String input, char[] inputOptions) {
    boolean valid = false;
    String response = input;

    if (!input.equals("")) {
      if (Validation.isSingleChar(input)) {
        char option = Character.toUpperCase(input.charAt(0));

        if (Validation.isValidOption(option, inputOptions)) {
          response = String.valueOf(option);
          valid = true;
        } else {
          MessageUI.notValidOption(inputOptions);
          valid = false;
        }
      } else {
        if (Validation.isValidId(doctorMap, doctorId)) {
          input = input.replaceAll("\\s-", "");
          if (Validation.isNumeric(input)) {
            if (isValidDate(input)) {
              if (isNotPastDate(input)) {
                if (isWithinTimeFrame(input)) {
                  response = String.valueOf(Utilities.formatDate(input));
                  valid = true;
                } else {
                  MessageUI.notWithinTimeFrame();
                  valid = false;
                }
              } else {
                MessageUI.notFutureDate();
                valid = false;
              }
            } else {
              MessageUI.notValidDate();
              valid = false;
            }
          } else {
            MessageUI.notNumeric();
            valid = false;
          }
        } else {
          MessageUI.notValidId(doctorId);
          valid = false;
        }
      }
    }

    return new String[] { String.valueOf(valid), response };
  }
  
  public static boolean isValidDate(String input) {
    try {
      int year = Integer.parseInt(input.substring(0, 4));
      int month = Integer.parseInt(input.substring(4, 6));
      int day = Integer.parseInt(input.substring(6, 8));

      if (month < 1 || month > 12) {
        return false;
      }

      int daysInMonth = LocalDate.of(year, month, 1).lengthOfMonth();
      if (day < 1 || day > daysInMonth) {
        return false;
      }

      return true;
    } catch (Exception e) {
      return false;
    }
  }
  
  public static boolean isNotPastDate(String input) {
    try {
      int year = Integer.parseInt(input.substring(0, 4));
      int month = Integer.parseInt(input.substring(4, 6));
      int day = Integer.parseInt(input.substring(6, 8));

      if (LocalDate.of(year, month, day).isBefore(LocalDate.now())) {
        return false;
      }

      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public static boolean isWithinTimeFrame(String input) {
    try {
      int year = Integer.parseInt(input.substring(0, 4));
      int month = Integer.parseInt(input.substring(4, 6));
      int day = Integer.parseInt(input.substring(6, 8));

      if (LocalDate.of(year, month, day).isAfter(LocalDate.now().plusYears(1))) {
        return false;
      }

      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public static String[] validateAppointmentTime(RedBlackTreeMap<String, Schedule> scheduleMap, String doctorId,
      LocalDate appointmentDate, String input, char[] inputOptions, int maxLength) {
    boolean valid = false;
    String response = input;

    String startTime = null;
    String endTime = null;
    String appointmentDay = appointmentDate.getDayOfWeek().name();

    // this possibly null
    if (scheduleMap != null) {
      ArrayList<String> keys = scheduleMap.getKeys();
      for (int i = 1; i <= keys.size(); i++) {
        Schedule schedule = scheduleMap.get(keys.get(i));
        if (schedule.getDoctorId().equals(doctorId) && schedule.getDay().name().equalsIgnoreCase(appointmentDay)) {
          startTime = schedule.getStartTime().toString();
          endTime = schedule.getEndTime().toString();
          break;
        }
      }

      if (!input.equals("")) {
        if (Validation.isSingleChar(input)) {
          char option = input.charAt(0);

          if (Validation.isValidOption(option, inputOptions)) {
            response = String.valueOf(option);
            valid = true;
          } else {
            MessageUI.notValidOption(inputOptions);
            valid = false;
          }
        } else {
          input = input.replaceAll("[\\s:]", "");
          if (Validation.isNumeric(input)) {
            if (Validation.isEqualsLength(input, maxLength)) {
              input = Utilities.formatTime(input).toString();
              if (isWithinTimeRange(LocalTime.parse(input), LocalTime.parse(startTime), LocalTime.parse(endTime))) {
                response = input;
                valid = true;
              } else {
                MessageUI.notValidTime(LocalTime.parse(startTime), LocalTime.parse(endTime));
                valid = false;
              }
            } else {
              MessageUI.mustBeLength(maxLength);
              valid = false;
            }
          } else {
            MessageUI.notNumeric();
            valid = false;
          }
        }
      }
    }
    
    return new String[] { String.valueOf(valid), response };
  }
  
  public static boolean isWithinTimeRange(LocalTime input, LocalTime startTime, LocalTime endTime) {
    if (input == null || startTime == null || endTime == null) {
      return false;
    }
    return !input.isBefore(startTime) && input.isBefore(endTime);
  }

  public static String[] validateReason(String input, char[] inputOptions, int maxLength) {
    boolean valid = false;
    String response = input;

    if (!input.equals("")) {
      if (Validation.isSingleChar(input)) {
        char option = input.charAt(0);

        if (Validation.isValidOption(option, inputOptions)) {
          response = String.valueOf(option);
          valid = true;
        } else {
          MessageUI.notValidOption(inputOptions);
          valid = false;
        }
      } else {
        if (Validation.isExceedLength(input, maxLength)) {
          response = input;
          valid = true;
        } else {
          MessageUI.exceedLength(maxLength);
          valid = false;
        }
      }
    }

    return new String[] { String.valueOf(valid), response };
  }

  public static String[] validateConfirmAppointment(String input, char[] inputOptions) {
    boolean valid = false;
    String response = input;

    if (!input.equals("")) {
      if (Validation.isSingleChar(input)) {
        char option = input.charAt(0);

        if (Validation.isValidOption(option, inputOptions)) {
          response = String.valueOf(option);
          valid = true;
        } else {
          MessageUI.notValidOption(inputOptions);
          valid = false;
        }
      }
    }

    return new String[] { String.valueOf(valid), response };
  }
}
