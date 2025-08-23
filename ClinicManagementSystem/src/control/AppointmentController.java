package control;

import adt.ArrayList;
import adt.ListInterface;
import adt.RedBlackTreeMap;
import boundary.AppointmentUI;
import dao.PatientDAO;
import entity.Doctor;
import entity.Patient;
import entity.Schedule;
import utility.MessageUI;
import utility.Validation;

/**
 *
 * @author Tan Kai Hao
 */
public class AppointmentController {

  AppointmentUI appointmentUI = new AppointmentUI();

  DoctorController doctorController = new DoctorController();
  
  PatientDAO patientDAO = new PatientDAO();

  RedBlackTreeMap<String, Patient> patientMap = new RedBlackTreeMap<>();
  ListInterface<Doctor> doctorList = new ArrayList<>();
  ListInterface<Schedule> scheduleList = new ArrayList<>();
  ArrayList<String[]> doctorData = new ArrayList<>();
  
  public void runStudentAppointment() {

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
                runAppointmentBooking();
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
  
  public void runAppointmentBooking() {

    boolean exit = false;
    String[] response;
    String[] vResponse;
    int page = 1;

    if(doctorData.isEmpty()) {
      doctorData = getRequiredDoctorRecordListData();
    }

    do {
      appointmentUI.runDoctorRecordListMenu(doctorData, page);
      response = appointmentUI.runAppointmentBookingMenu();
      vResponse = AppointmentValidation.validateDoctorIdExistence(doctorList, response[0], response[1].toCharArray());

      if (Boolean.parseBoolean(vResponse[0])) {
        if (vResponse[1].equals("0")) {
          exit = true;
        } else {
          // TODO: implement appointment booking
        }
      }
    } while (!exit);
  }
  
  public ArrayList<String[]> getRequiredDoctorRecordListData() {
    
    doctorList = doctorController.getAllDoctors();
    
    for (int i = 1; i <= doctorList.size(); i++) {
      Doctor doctor = doctorList.get(i);
      ListInterface<Schedule> schedules = doctorController.getSchedulesForDoctor(doctor.getDoctorId());
      
      if (schedules.size() > 0) {
        for (int j = 1; j <= schedules.size(); j++) {
          Schedule schedule = schedules.get(j);
          String[] entry = {
            doctor.getDoctorId(),
            doctor.getName(),
            schedule.getDay().toString(),
            schedule.getStartTime().toString(),
            schedule.getEndTime().toString(),
            String.valueOf(schedule.getIsAvailable() ? "Available" : "Unavailable")
          };
          doctorData.add(entry);
        }
      }
    }
    
    return doctorData;
  }
}
