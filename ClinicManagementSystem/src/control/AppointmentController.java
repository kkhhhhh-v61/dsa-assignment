package control;

import adt.ArrayList;
import adt.RedBlackTreeMap;
import boundary.AppointmentUI;
import dao.AppointmentDAO;
import dao.DoctorScheduleDAO;
import entity.Appointment;
import entity.Doctor;
import entity.Schedule;
import utility.Enum.Status;
import utility.MessageUI;
import utility.Utilities;
import utility.Validation;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author Tan Kai Hao
 */
public class AppointmentController {

  AppointmentUI appointmentUI = new AppointmentUI();

  AppointmentDAO appointmentDAO = new AppointmentDAO();
  DoctorScheduleDAO doctorScheduleDAO = new DoctorScheduleDAO();

  private RedBlackTreeMap<String, Doctor> doctorMap;
  private RedBlackTreeMap<String, Schedule> scheduleMap;
  private RedBlackTreeMap<String, Appointment> appointmentMap;
  
  private ArrayList<Doctor> doctorList;
  private ArrayList<Schedule> scheduleList;
  private ArrayList<String[]> doctorData;
  
  public AppointmentController() {
    doctorMap = new RedBlackTreeMap<>();
    scheduleMap = new RedBlackTreeMap<>();
    appointmentMap = new RedBlackTreeMap<>();
    doctorList = new ArrayList<>();
    scheduleList = new ArrayList<>();
    doctorData = getRequiredDoctorRecordListData();
  }

  public void runStudentAppointment(String patientId) {

    boolean exit = false;
    String[] response;
    char userInput;
    char[] inputOptions;

    do {
      response = appointmentUI.runStudentAppointmentMenu();
      inputOptions = response[1].toCharArray();

      if (response[0] != "") {
        if (Validation.isSingleChar(response[0])) {
          userInput = response[0].charAt(0);

          if (Validation.isValidOption(userInput, inputOptions)) {
            switch (userInput) {
              case '1':
                runAppointmentBooking(patientId);
                break;
              case '2':
                break;
              case '3':
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
  
  @SuppressWarnings("unchecked")
  public void runAppointmentBooking(String patientId) {

    doctorList = (ArrayList<Doctor>) doctorScheduleDAO.retrieveFromFile("doctor");

    boolean exit = false;
    String[] response;
    String[] vResponse;
    int page = 1;
    boolean complete = false;

    appointmentMap = appointmentDAO.retrieveFromFile();
    ArrayList<String> keys = appointmentMap.getKeys();
    
    if (!keys.isEmpty()) {
      String lastKey = keys.get(keys.size());
      String lastId = lastKey.substring(1);
      Appointment.setLastId(Integer.parseInt(lastId));
    }

    Appointment newAppointment = new Appointment();

    if(doctorData.isEmpty()) {
      doctorData = getRequiredDoctorRecordListData();
    }

    do {
      newAppointment.setPatientId(patientId);
      appointmentUI.runDoctorRecordListMenu(doctorData, page);
      response = appointmentUI.inputDoctorId();
      vResponse = AppointmentValidation.validateDoctorIdExistence(doctorMap, response[0], response[1].toCharArray());

      if (Boolean.parseBoolean(vResponse[0])) {
        if (vResponse[1].equals("0")) {
          exit = true;
        } else {
          newAppointment.setDoctorId(vResponse[1]);

          do {
            response = appointmentUI.inputAppointmentDate();
            vResponse = AppointmentValidation.validateAppointmentDate(doctorMap, newAppointment.getDoctorId(),
                response[0], response[1].toCharArray());
          } while (!Boolean.parseBoolean(vResponse[0]));

          if (vResponse[1].equals("0")) {
            exit = true;
          } else {
            newAppointment.setAppointmentDate(LocalDate.parse(vResponse[1]));

            do {
              response = appointmentUI.inputAppointmentTime();
              vResponse = AppointmentValidation.validateAppointmentTime(scheduleMap, newAppointment.getDoctorId(),
                  newAppointment.getAppointmentDate(), response[0], response[1].toCharArray(), Integer.parseInt(response[2]));
            } while (!Boolean.parseBoolean(vResponse[0]));

            if (vResponse[1].equals("0")) {
              exit = true;
            } else {
              newAppointment.setAppointmentTime(LocalTime.parse(vResponse[1]));

              do {
                response = appointmentUI.inputReason();
                vResponse = AppointmentValidation.validateReason(response[0], response[1].toCharArray(), Integer.parseInt(response[2]));
              } while (!Boolean.parseBoolean(vResponse[0]));

              if (vResponse[1].equals("0")) {
                exit = true;
              } else {
                newAppointment.setReason(vResponse[1]);
                complete = true;
              }
            }
          }
        }
      }

      if (complete) {
        boolean exit1 = false;

        do {
          response = appointmentUI.confirmAppointment(newAppointment);
          vResponse = AppointmentValidation.validateConfirmAppointment(response[0], response[1].toCharArray());

          if (Boolean.parseBoolean(vResponse[0])) {
            switch (vResponse[1]) {
              case "1":
                newAppointment.setStatus(Status.Pending);
                appointmentMap.put(newAppointment.getAppointmentId(), newAppointment);
                appointmentDAO.saveToFile(appointmentMap);
                System.out.println();
                System.out.println("Appointment booked successfully.");
                Utilities.wait(1000);
                exit1 = true;
                exit = true;
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
  
  @SuppressWarnings("unchecked")
  public ArrayList<String[]> getRequiredDoctorRecordListData() {

    doctorData = new ArrayList<>();

    doctorList = (ArrayList<Doctor>) doctorScheduleDAO.retrieveFromFile("doctor");
    scheduleList = (ArrayList<Schedule>) doctorScheduleDAO.retrieveFromFile("schedule");

    for (int i = 1; i <= doctorList.size(); i++) {
      Doctor doctor = doctorList.get(i);
      doctorMap.put(doctor.getDoctorId(), doctor);
    }

    for (int i = 1; i <= scheduleList.size(); i++) {
      Schedule schedule = scheduleList.get(i);
      scheduleMap.put(schedule.getScheduleId(), schedule);
    }

    ArrayList<String> doctorKeys = doctorMap.getKeys();

    for (int i = 1; i <= doctorKeys.size(); i++) {
      for (int j = 1; j <= scheduleList.size(); j++) {
        if (doctorKeys.get(i).equals(scheduleList.get(j).getDoctorId())) {
          String[] entry = {
              doctorKeys.get(i),
              doctorMap.get(doctorKeys.get(i)).getName(),
              scheduleList.get(j).getDay().toString(),
              scheduleList.get(j).getStartTime().toString(),
              scheduleList.get(j).getEndTime().toString(),
              String.valueOf(scheduleList.get(j).getIsAvailable() ? "Available" : "Unavailable")
          };
          doctorData.add(entry);
        }
      }
    }

    return doctorData;
  }
}
