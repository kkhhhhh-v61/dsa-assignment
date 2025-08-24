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
    doctorData = new ArrayList<>();
    appointmentMap = new RedBlackTreeMap<>();
    doctorList = new ArrayList<>();
    scheduleList = new ArrayList<>();

    doctorData = getRequiredDoctorRecordListData();
    appointmentMap = appointmentDAO.retrieveFromFile();
  }

  @SuppressWarnings("unchecked")
  public void loadDoctorMap() {

    doctorList = (ArrayList<Doctor>) doctorScheduleDAO.retrieveFromFile("doctor");

    for (int i = 1; i <= doctorList.size(); i++) {
      Doctor doctor = doctorList.get(i);
      doctorMap.put(doctor.getDoctorId(), doctor);
    }
  }

  @SuppressWarnings("unchecked")
  public void loadScheduleMap() {

    scheduleList = (ArrayList<Schedule>) doctorScheduleDAO.retrieveFromFile("schedule");

    for (int i = 1; i <= scheduleList.size(); i++) {
      Schedule schedule = scheduleList.get(i);
      scheduleMap.put(schedule.getScheduleId(), schedule);
    }
  }

  public void runAppointmentManagement() {

    boolean exit = false;
    String[] response;
    String[] vResponse;

    do {
      response = appointmentUI.runAppointmentMenu();
      vResponse = AppointmentValidation.validateAppointmentMenu(response[0], response[1].toCharArray());

      if (Boolean.parseBoolean(vResponse[0])) {
        switch (vResponse[1]) {
          case "1":
            runAppointmentList();
            break;
          case "2":
            runAppointmentSearch();
            break;
          case "3":
            runAppointmentClearance();
            break;
          case "4":
            runAppointmentInitialization();
            break;
          case "0":
            exit = true;
            break;
        }
      }

    } while (!exit);

  }
  
  public void runAppointmentList() {

    boolean exit = false;
    String[] response;
    String[] vResponse;
    int page = 1;

    do {
      appointmentUI.runAppointmentListMenu(appointmentMap, "", page);
      response = appointmentUI.inputAppointmentListMenu();
      vResponse = AppointmentValidation.validateAppointmentListMenu(response[0], response[1].toCharArray());

      if (Boolean.parseBoolean(vResponse[0])) {
        switch (vResponse[1]) {
          case "Q":
            if (page > 1) {
              page--;
            }
            break;
          case "E":
            if (page < (appointmentMap.size() + 9) / 10) {
              page++;
            }
            break;
          case "1":
            runAppointmentAction("edit");
            break;
          case "2":
            runAppointmentAction("delete");
            break;
          case "0":
            exit = true;
            break;
        }
      }
    } while (!exit);
  }
  
  public void runAppointmentAction(String action) {

    boolean exit = false;
    String[] response;
    String[] vResponse;
    int page = 1;

    do {
      appointmentUI.runAppointmentListMenu(appointmentMap, "", page);
      response = appointmentUI.inputAppointmentSearch();
      vResponse = AppointmentValidation.validateAppointmentSearch(appointmentMap, response[0],
          response[1].toCharArray());

      if (Boolean.parseBoolean(vResponse[0])) {
        switch (vResponse[1]) {
          case "Q":
            if (page > 1) {
              page--;
            }
            break;
          case "E":
            if (page < (appointmentMap.size() + 9) / 10) {
              page++;
            }
            break;
          case "0":
            exit = true;
            break;
        }

        if (action.equals("edit")) {
          runAppointmentEdit(vResponse[1]);
          exit = true;
        } else if (action.equals("delete")) {
          runAppointmentDelete(vResponse[1]);
          exit = true;
        } else {
          System.out.println("Invalid action! Please try again.");
          Utilities.wait(1000);
        }
      }
    } while (!exit);
  }
  
  public void runAppointmentEdit(String appointmentId) {

    boolean exit = false;
    String[] response;
    String[] vResponse;
    int page = 1;
    
    do {
      System.out.println();

      Utilities.clearScreen();

      response = appointmentUI.runAppointmentEditMenu(appointmentMap.get(appointmentId));
      vResponse = AppointmentValidation.validateAppointmentEditMenu(response[0], response[1].toCharArray());

      if (Boolean.parseBoolean(vResponse[0])) {
        switch (vResponse[1]) {
          case "1":
            page = 1;
            do {
              response = appointmentUI.editDoctorId(doctorData, appointmentMap.get(appointmentId).getDoctorId(), page);
              vResponse = AppointmentValidation.validateEditDoctorId(doctorData, response[0], response[1].toCharArray());
              
              if (!vResponse[1].equals("0") || vResponse[1].equals("Q") || vResponse[1].equals("E")) {
                switch (vResponse[1]) {
                  case "Q":
                    if (page > 1) {
                      page--;
                    }
                    break;
                  case "E":
                    if (page < (doctorMap.size() + 9) / 10) {
                      page++;
                    }
                    break;
                }
              }
            } while (!Boolean.parseBoolean(vResponse[0]));

            if (!vResponse[1].equals("0")) {
              do {
                response = appointmentUI.confirmEdit("Doctor ID", appointmentMap.get(appointmentId).getDoctorId(), vResponse[1]);
                vResponse = AppointmentValidation.validateConfirmEdit(response[0], response[1].toCharArray());
              } while (!Boolean.parseBoolean(vResponse[0]));

              if (Boolean.parseBoolean(vResponse[0])) {
                switch (vResponse[1]) {
                  case "1":
                    appointmentMap.get(appointmentId).setDoctorId(vResponse[1]);
                    break;
                  case "0":
                    break;
                }
              } 
            }
            break;
          case "2":
            do {
              response = appointmentUI.editAppointmentDate(appointmentMap.get(appointmentId).getAppointmentDate().toString());
              vResponse = AppointmentValidation.validateEditAppointmentDate(response[0], response[1].toCharArray());
            } while (!Boolean.parseBoolean(vResponse[0]));

            if (!vResponse[1].equals("0")) {
              String userInput = vResponse[1];
              do {
                response = appointmentUI.confirmEdit("Appointment Date", appointmentMap.get(appointmentId).getAppointmentDate().toString(), userInput);
                vResponse = AppointmentValidation.validateConfirmEdit(response[0], response[1].toCharArray());
              } while (!Boolean.parseBoolean(vResponse[0]));

              if (Boolean.parseBoolean(vResponse[0])) {
                if (vResponse[1].equals("1")) {
                  appointmentMap.get(appointmentId).setAppointmentDate(LocalDate.parse(userInput));
                }
              } 
            }
            break;
          case "3":
            page = 1;
            String appointmentDay = appointmentMap.get(appointmentId).getAppointmentDate().getDayOfWeek().toString();
            do {
              appointmentUI.runDoctorRecordListMenu(doctorData, appointmentMap.get(appointmentId).getDoctorId(), appointmentDay, page);
              response = appointmentUI.editAppointmentTime(appointmentMap.get(appointmentId).getAppointmentTime().toString());
              vResponse = AppointmentValidation.validateEditAppointmentTime(scheduleMap, appointmentMap.get(appointmentId).getDoctorId(), appointmentMap.get(appointmentId).getAppointmentDate(), response[0], response[1].toCharArray(), Integer.parseInt(response[2]));
            } while (!Boolean.parseBoolean(vResponse[0]));

            if (!vResponse[1].equals("0")) {
              String userInput = vResponse[1];
              do {
                response = appointmentUI.confirmEdit("Appointment Time", appointmentMap.get(appointmentId).getAppointmentTime().toString(), userInput);
                vResponse = AppointmentValidation.validateConfirmEdit(response[0], response[1].toCharArray());
              } while (!Boolean.parseBoolean(vResponse[0]));

              if (Boolean.parseBoolean(vResponse[0])) {
                if (vResponse[1].equals("1")) {
                  appointmentMap.get(appointmentId).setAppointmentTime(LocalTime.parse(userInput));
                }
              } 
            }
            break;
          case "4":
            do {
              response = appointmentUI.editStatus(appointmentMap.get(appointmentId).getStatus().toString());
              vResponse = AppointmentValidation.validateEditStatus(response[0], response[1].toCharArray());
            } while (!Boolean.parseBoolean(vResponse[0]));
            
            if (!vResponse[1].equals("0")) {
              String userInput = vResponse[1];
              switch (userInput) {
                case "1":
                  userInput = Status.Approved.toString();
                  break;
                case "2":
                  userInput = Status.Rejected.toString();
                  break;
                case "3":
                  userInput = Status.Pending.toString();
                  break;
                case "0":
                  break;
              }

              do {
                response = appointmentUI.confirmEdit("Status", appointmentMap.get(appointmentId).getStatus().toString(), userInput);
                vResponse = AppointmentValidation.validateConfirmEdit(response[0], response[1].toCharArray());
              } while (!Boolean.parseBoolean(vResponse[0]));

              if (Boolean.parseBoolean(vResponse[0])) {
                if (vResponse[1].equals("1")) {
                  appointmentMap.get(appointmentId).setStatus(Status.valueOf(userInput));
                }
              }
            }
            break;
          case "0":
            exit = true;
            break;
        }
      }
      
    } while (!exit);
  }

  public void runAppointmentDelete(String appointmentId) {
    
  }

  public void runAppointmentSearch() {
    
  }

  public void runAppointmentClearance() {
    
  }

  public void runAppointmentInitialization() {
    
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
                runAppointmentRecords(patientId);
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
      response = appointmentUI.inputAppointmentDate();
      vResponse = AppointmentValidation.validateAppointmentDate(response[0], response[1].toCharArray());

      if (Boolean.parseBoolean(vResponse[0])) {
        if (vResponse[1].equals("0")) {
          exit = true;
        } else {
          String appointmentDay = LocalDate.parse(vResponse[1]).getDayOfWeek().toString();
          newAppointment.setAppointmentDate(LocalDate.parse(vResponse[1]));

          boolean exit1 = false;
          do {
            do {
              appointmentUI.runDoctorRecordListMenu(doctorData, "", appointmentDay, page);
              response = appointmentUI.inputDoctorId();
              vResponse = AppointmentValidation.validateDoctorIdExistence(doctorMap, doctorData, appointmentDay, response[0], response[1].toCharArray());
            } while (!Boolean.parseBoolean(vResponse[0]));

            if (vResponse[1].equals("0") || vResponse[1].equals("Q") || vResponse[1].equals("E")) {
              switch (vResponse[1]) {
                case "Q":
                  if (page > 1) {
                    page--;
                  }
                  break;
                case "E":
                  if (page < (doctorData.size() + 9) / 10) {
                    page++;
                  }
                  break;
                case "0":
                  exit1 = true;
                  break;
              }
            } else {
              exit1 = true;
            }
          } while (!exit1);

          newAppointment.setDoctorId(vResponse[1]);

          boolean exit2 = false;
          do {
            do {
              appointmentUI.runDoctorRecordListMenu(doctorData, newAppointment.getDoctorId(), appointmentDay, page);
              response = appointmentUI.inputAppointmentTime();
              vResponse = AppointmentValidation.validateAppointmentTime(scheduleMap, newAppointment.getDoctorId(),
                  newAppointment.getAppointmentDate(), response[0], response[1].toCharArray(), Integer.parseInt(response[2]));
            } while (!Boolean.parseBoolean(vResponse[0]));

            if (vResponse[1].equals("0") || vResponse[1].equals("Q") || vResponse[1].equals("E")) {
              switch (vResponse[1]) {
                case "Q":
                  if (page > 1) {
                    page--;
                  }
                  break;
                case "E":
                  if (page < (doctorData.size() + 9) / 10) {
                    page++;
                  }
                  break;
                case "0":
                  exit2 = true;
                  break;
              }
            } else {
              exit2 = true;
            }
          } while (!exit2);

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
  
  public ArrayList<String[]> getRequiredDoctorRecordListData() {

    doctorData = new ArrayList<>();

    loadDoctorMap();
    loadScheduleMap();

    ArrayList<String> doctorKeys = doctorMap.getKeys();

    for (int i = 1; i <= doctorKeys.size(); i++) {
      for (int j = 1; j <= scheduleList.size(); j++) {
        if (doctorKeys.get(i).equals(scheduleList.get(j).getDoctorId()) && scheduleList.get(j).getIsAvailable()) {
          String[] entry = {
              doctorKeys.get(i),
              doctorMap.get(doctorKeys.get(i)).getName(),
              scheduleList.get(j).getDay().toString(),
              scheduleList.get(j).getStartTime().toString(),
              scheduleList.get(j).getEndTime().toString(),
              "Available"
          };
          doctorData.add(entry);
        }
      }
    }

    return doctorData;
  }

  public void runAppointmentRecords(String patientId) {

    boolean exit = false;
    String[] response;
    String[] vResponse;
    int page = 1;

    do {

      System.out.println();

      Utilities.clearScreen();

    } while (!exit);
  }
}
