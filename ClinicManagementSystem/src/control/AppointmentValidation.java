package control;

import adt.ArrayList;
import adt.ListInterface;
import adt.RedBlackTreeMap;
import entity.Appointment;
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

  public static String[] validateAppointmentMenu(String userInput, char[] userInputOptions) {

    boolean valid = false;
    String response = userInput;

    if (!userInput.equals("")) {
      if (Validation.isSingleChar(userInput)) {
        char option = Character.toUpperCase(userInput.charAt(0));

        if (Validation.isValidOption(option, userInputOptions)) {
          response = String.valueOf(option);
          valid = true;
        } else {
          MessageUI.notValidOption(userInputOptions);
          valid = false;
        }
      } else {
        MessageUI.notSingleChar();
        valid = false;
      }
    }

    return new String[] { String.valueOf(valid), response };
  }

  public static String[] validateAppointmentListMenu(String userInput, char[] userInputOptions) {

    boolean valid = false;
    String response = userInput;

    if (!userInput.equals("")) {
      if (Validation.isSingleChar(userInput)) {
        char option = Character.toUpperCase(userInput.charAt(0));

        if (Validation.isValidOption(option, userInputOptions)) {
          response = String.valueOf(option);
          valid = true;
        } else {
          MessageUI.notValidOption(userInputOptions);
          valid = false;
        }
      } else {
        MessageUI.notSingleChar();
        valid = false;
      }
    }

    return new String[] { String.valueOf(valid), response };
  }
  
  public static String[] validateAppointmentSearch(RedBlackTreeMap<String, Appointment> appointmentMap, String userInput, char[] userInputOptions) {

    boolean valid = false;
    String response = userInput;

    if (!userInput.equals("")) {
      if (Validation.isSingleChar(userInput)) {
        char option = Character.toUpperCase(userInput.charAt(0));

        if (Validation.isValidOption(option, userInputOptions)) {
          response = String.valueOf(option);
          valid = true;
        } else {
          MessageUI.notValidOption(userInputOptions);
          valid = false;
        }
      } else {
        if (Validation.isValidId(appointmentMap, "A" + userInput)) {
          response = "A" + userInput;
          valid = true;
        } else {
          MessageUI.notValidId("A" + userInput);
          valid = false;
        }
      }
    }

    return new String[] { String.valueOf(valid), response };
  }
  
  public static String[] validateAppointmentEditMenu(String userInput, char[] userInputOptions) {

    boolean valid = false;
    String response = userInput;

    if (!userInput.equals("")) {
      if (Validation.isSingleChar(userInput)) {
        char option = Character.toUpperCase(userInput.charAt(0));

        if (Validation.isValidOption(option, userInputOptions)) {
          response = String.valueOf(option);
          valid = true;
        } else {
          MessageUI.notValidOption(userInputOptions);
          valid = false;
        }
      } else {
        MessageUI.notSingleChar();
        valid = false;
      }
    }

    return new String[] { String.valueOf(valid), response };
  }

  public static String[] validateEditDoctorId(ArrayList<String[]> doctorData, String userInput, char[] userInputOptions) {

    boolean valid = false;
    String response = userInput;

    if (!userInput.equals("")) {
      if (Validation.isSingleChar(userInput)) {
        char option = Character.toUpperCase(userInput.charAt(0));

        if (Validation.isValidOption(option, userInputOptions)) {
          response = String.valueOf(option);
          valid = true;
        } else {
          MessageUI.notValidOption(userInputOptions);
          valid = false;
        }
      } else {
        ListInterface<String> doctorIds = new ArrayList<>();
        for (int i = 1; i <= doctorData.size(); i++) {
          String[] doctor = doctorData.get(i);
          if (doctor[0].equals("D" + userInput)) {
            if (!doctorIds.contains("D" + userInput)) {
              doctorIds.add("D" + userInput);
            }
          }
        }
        if (Validation.isValidId(doctorIds, "D" + userInput)) {
          response = "D" + userInput;
          valid = true;
        } else {
          MessageUI.notValidId("D" + userInput);
          valid = false;
        }
      }
    }

    return new String[] { String.valueOf(valid), response };
  }
  
  public static String[] validateEditAppointmentDate(String userInput, char[] userInputOptions) {
    
    boolean valid = false;
    String response = userInput;

    if (!userInput.equals("")) {
      if (Validation.isSingleChar(userInput)) {
        char option = Character.toUpperCase(userInput.charAt(0));

        if (Validation.isValidOption(option, userInputOptions)) {
          response = String.valueOf(option);
          valid = true;
        } else {
          MessageUI.notValidOption(userInputOptions);
          valid = false;
        }
      } else {
        userInput = userInput.replaceAll("\\s-", "");
        if (Validation.isNumeric(userInput)) {
          if (isValidDate(userInput)) {
            if (isNotPastDate(userInput)) {
              if (isWithinTimeFrame(userInput)) {
                response = String.valueOf(Utilities.formatDate(userInput));
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
      }
    }

    return new String[] { String.valueOf(valid), response };
  }

  public static String[] validateEditAppointmentTime(RedBlackTreeMap<String, Schedule> scheduleMap, String doctorId,
      LocalDate appointmentDate, String userInput, char[] userInputOptions, int maxLength) {

    boolean valid = false;
    String response = userInput;

    String startTime = null;
    String endTime = null;
    String appointmentDay = appointmentDate.getDayOfWeek().name();

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

      if (!userInput.equals("")) {
        if (Validation.isSingleChar(userInput)) {
          char option = Character.toUpperCase(userInput.charAt(0));

          if (Validation.isValidOption(option, userInputOptions)) {
            response = String.valueOf(option);
            valid = true;
          } else {
            MessageUI.notValidOption(userInputOptions);
            valid = false;
          }
        } else {
          userInput = userInput.replaceAll("[\\s:]", "");
          if (Validation.isNumeric(userInput)) {
            if (Validation.isEqualsLength(userInput, maxLength)) {
              userInput = Utilities.formatTime(userInput).toString();
              if (isWithinTimeRange(LocalTime.parse(userInput), LocalTime.parse(startTime), LocalTime.parse(endTime))) {
                response = userInput;
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
  
  public static String[] validateEditStatus(String userInput, char[] userInputOptions) {
    
    boolean valid = false;
    String response = userInput;

    if (!userInput.equals("")) {
      if (Validation.isSingleChar(userInput)) {
        char option = Character.toUpperCase(userInput.charAt(0));

        if (Validation.isValidOption(option, userInputOptions)) {
          response = String.valueOf(option);
          valid = true;
        } else {
          MessageUI.notValidOption(userInputOptions);
          valid = false;
        }
      } else {
        MessageUI.notSingleChar();
        valid = false;
      }
    }

    return new String[] { String.valueOf(valid), response };
  }
  
  public static String[] validateConfirmEdit(String userInput, char[] userInputOptions) {

    boolean valid = false;
    String response = userInput;

    if (!userInput.equals("")) {
      if (Validation.isSingleChar(userInput)) {
        char option = Character.toUpperCase(userInput.charAt(0));

        if (Validation.isValidOption(option, userInputOptions)) {
          response = String.valueOf(option);
          valid = true;
        } else {
          MessageUI.notValidOption(userInputOptions);
          valid = false;
        }
      } else {
        MessageUI.notSingleChar();
        valid = false;
      }
    }

    return new String[] { String.valueOf(valid), response };
  }
  
  public static String[] vaidateAppointmentDeleteMenu(String userInput, char[] userInputOptions) {

    boolean valid = false;
    String response = userInput;

    if (!userInput.equals("")) {
      if (Validation.isSingleChar(userInput)) {
        char option = Character.toUpperCase(userInput.charAt(0));

        if (Validation.isValidOption(option, userInputOptions)) {
          response = String.valueOf(option);
          valid = true;
        } else {
          MessageUI.notValidOption(userInputOptions);
          valid = false;
        }
      } else {
        MessageUI.notSingleChar();
        valid = false;
      }
    }

    return new String[] { String.valueOf(valid), response };
  }

  public static String[] validatePatientIdExistence(RedBlackTreeMap<String, Patient> patientMap, String userInput,
      char[] userInputOptions) {

    boolean valid = false;
    String response = userInput;

    if (!userInput.equals("")) {
      if (Validation.isSingleChar(userInput)) {
        char option = Character.toUpperCase(userInput.charAt(0));

        if (Validation.isValidOption(option, userInputOptions)) {
          response = String.valueOf(option);
          valid = true;
        } else {
          MessageUI.notValidOption(userInputOptions);
          valid = false;
        }
      } else {
        if (Validation.isValidId(patientMap, "P" + userInput)) {
          response = "P" + userInput;
          valid = true;
        } else {
          MessageUI.notValidId("P" + userInput);
          valid = false;
        }
      }
    }

    return new String[] { String.valueOf(valid), response };
  }
  
  public static String[] validateDoctorIdExistence(RedBlackTreeMap<String, Doctor> doctorMap,
      ArrayList<String[]> doctorData, String appointmentDay, String userInput, char[] userInputOptions) {
    
    boolean valid = false;
    String response = userInput;

    if (!userInput.equals("")) {
      if (Validation.isSingleChar(userInput)) {
        char option = Character.toUpperCase(userInput.charAt(0));

        if (Validation.isValidOption(option, userInputOptions)) {
          response = String.valueOf(option);
          valid = true;
        } else {
          MessageUI.notValidOption(userInputOptions);
          valid = false;
        }
      } else {
        if (Validation.isValidId(doctorMap, "D" + userInput)) {
          response = "D" + userInput;
          valid = true;
        } else {
          MessageUI.notValidId("D" + userInput);
          valid = false;
        }
      }
    }

    return new String[] { String.valueOf(valid), response };
  }
  
  public static String[] validateAppointmentDate(String userInput, char[] userInputOptions) {

    boolean valid = false;
    String response = userInput;

    if (!userInput.equals("")) {
      if (Validation.isSingleChar(userInput)) {
        char option = Character.toUpperCase(userInput.charAt(0));

        if (Validation.isValidOption(option, userInputOptions)) {
          response = String.valueOf(option);
          valid = true;
        } else {
          MessageUI.notValidOption(userInputOptions);
          valid = false;
        }
      } else {
        userInput = userInput.replaceAll("\\s-", "");
        if (Validation.isNumeric(userInput)) {
          if (isValidDate(userInput)) {
            if (isNotPastDate(userInput)) {
              if (isWithinTimeFrame(userInput)) {
                response = String.valueOf(Utilities.formatDate(userInput));
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
      }
    }

    return new String[] { String.valueOf(valid), response };
  }
  
  public static boolean isValidDate(String userInput) {

    try {
      int year = Integer.parseInt(userInput.substring(0, 4));
      int month = Integer.parseInt(userInput.substring(4, 6));
      int day = Integer.parseInt(userInput.substring(6, 8));

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
  
  public static boolean isNotPastDate(String userInput) {

    try {
      int year = Integer.parseInt(userInput.substring(0, 4));
      int month = Integer.parseInt(userInput.substring(4, 6));
      int day = Integer.parseInt(userInput.substring(6, 8));

      if (LocalDate.of(year, month, day).isBefore(LocalDate.now())) {
        return false;
      }

      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public static boolean isWithinTimeFrame(String userInput) {

    try {
      int year = Integer.parseInt(userInput.substring(0, 4));
      int month = Integer.parseInt(userInput.substring(4, 6));
      int day = Integer.parseInt(userInput.substring(6, 8));

      if (LocalDate.of(year, month, day).isAfter(LocalDate.now().plusYears(1))) {
        return false;
      }

      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public static String[] validateAppointmentTime(RedBlackTreeMap<String, Schedule> scheduleMap, String doctorId,
      LocalDate appointmentDate, String userInput, char[] userInputOptions, int maxLength) {

    boolean valid = false;
    String response = userInput;

    String startTime = null;
    String endTime = null;
    String appointmentDay = appointmentDate.getDayOfWeek().name();

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

      if (!userInput.equals("")) {
        if (Validation.isSingleChar(userInput)) {
          char option = Character.toUpperCase(userInput.charAt(0));

          if (Validation.isValidOption(option, userInputOptions)) {
            response = String.valueOf(option);
            valid = true;
          } else {
            MessageUI.notValidOption(userInputOptions);
            valid = false;
          }
        } else {
          userInput = userInput.replaceAll("[\\s:]", "");
          if (Validation.isNumeric(userInput)) {
            if (Validation.isEqualsLength(userInput, maxLength)) {
              userInput = Utilities.formatTime(userInput).toString();
              if (isWithinTimeRange(LocalTime.parse(userInput), LocalTime.parse(startTime), LocalTime.parse(endTime))) {
                response = userInput;
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
  
  public static boolean isWithinTimeRange(LocalTime userInput, LocalTime startTime, LocalTime endTime) {

    if (userInput == null || startTime == null || endTime == null) {
      return false;
    }
    return !userInput.isBefore(startTime) && userInput.isBefore(endTime);
  }

  public static String[] validateReason(String userInput, char[] userInputOptions, int maxLength) {

    boolean valid = false;
    String response = userInput;

    if (!userInput.equals("")) {
      if (Validation.isSingleChar(userInput)) {
        char option = Character.toUpperCase(userInput.charAt(0));

        if (Validation.isValidOption(option, userInputOptions)) {
          response = String.valueOf(option);
          valid = true;
        } else {
          MessageUI.notValidOption(userInputOptions);
          valid = false;
        }
      } else {
        if (Validation.isExceedLength(userInput, maxLength)) {
          response = userInput;
          valid = true;
        } else {
          MessageUI.exceedLength(maxLength);
          valid = false;
        }
      }
    }

    return new String[] { String.valueOf(valid), response };
  }

  public static String[] validateConfirmAppointment(String userInput, char[] userInputOptions) {

    boolean valid = false;
    String response = userInput;

    if (!userInput.equals("")) {
      if (Validation.isSingleChar(userInput)) {
        char option = Character.toUpperCase(userInput.charAt(0));

        if (Validation.isValidOption(option, userInputOptions)) {
          response = String.valueOf(option);
          valid = true;
        } else {
          MessageUI.notValidOption(userInputOptions);
          valid = false;
        }
      }
    }

    return new String[] { String.valueOf(valid), response };
  }
}
