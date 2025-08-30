package control;

import adt.RedBlackTreeMap;
import boundary.ConsultationUI;
import boundary.DoctorUI;
import boundary.PharmacyUI;
import boundary.SystemUI;
import boundary.TreatmentUI;
import dao.PatientDAO;
import entity.Patient;
import utility.Utilities;

/**
 *
 * @author Tan Kai Hao
 */
public class SystemController {
  private final static int ID_LENGTH = 4;

  ConsultationUI consultationUI = new ConsultationUI();
  DoctorUI doctorUI = new DoctorUI();
  PharmacyUI pharmacyUI = new PharmacyUI();
  TreatmentUI treatmentUI = new TreatmentUI();
  SystemUI systemUI = new SystemUI();
  
  AppointmentController appointmentController = new AppointmentController();
  ConsultationController consultationController = new ConsultationController();
  PatientController patientController = new PatientController();
  QueueController queueController = new QueueController();

  PatientDAO patientDAO = new PatientDAO();

  RedBlackTreeMap<String, Patient> patientMap;

  public SystemController() {
    patientMap = patientDAO.readFromFile();
  }
  
  public void runClinicStaffPortal() {
    do {
      Utilities.clearScreen();
      char[] inputOptions = systemUI.displayClinicStaffPortalMenu();
      String userInput = systemUI.getGeneralInput();
      boolean valid = SystemValidation.validateCharInput(userInput, inputOptions);

      if (valid) {
        switch (userInput.charAt(0)) {
          case '1':
            queueController.runQueueManagement();
            continue;
          case '2':
            patientController.runPatientManagement();
            continue;
          case '3':
            doctorUI.showDoctorMenu();
            continue;
          case '4':
            appointmentController.runAppointmentManagement();
            continue;
          case '5':
            consultationController.syncConsultationsWithAppointments();
            consultationUI.runMenu();
            continue;
          case '6':
            treatmentUI.runMenu();
            continue;
          case '7':
            pharmacyUI.displayPharmacyMenu();
            continue;
          case '0':
            return;
        }
      }
    } while (true);
  }
  
  public void runStudentPortal() {
    String[] response = runPatientIdLogin();
    String patientId = response[1];
    
    if (!Boolean.parseBoolean(response[0])) {
      return;
    }

    do {
      Utilities.clearScreen();
      char[] inputOptions = systemUI.displayStudentPortalMenu();
      String userInput = systemUI.getGeneralInput();
      boolean valid = SystemValidation.validateCharInput(userInput, inputOptions);

      if (valid) {
        patientId = "P" + response[1];
        switch (userInput.charAt(0)) {
          case '1':
            queueController.runStudentQueue(patientId);
            continue;
          case '2':
            appointmentController.runStudentAppointment(patientId);
            continue;
          case '3':
            consultationUI.runStudent(patientId);
            continue;
          case '4':
            treatmentUI.runStudentMenu(patientId);
            continue;
          case '0':
            return;
        }
      }
    } while (true);
  }

  public String[] runPatientIdLogin() {
    boolean valid = false;
    String userInput;
    do {
      Utilities.clearScreen();
      char[] inputOptions = systemUI.displayPatientIdLoginInput();
      userInput = systemUI.getSpecificInput("P");
      valid = SystemValidation.validatePatientIdLogin(userInput, inputOptions, ID_LENGTH);
    } while (!valid);

    if (userInput.equals("0")) {
      return new String[] { "false", userInput };
    }

    Utilities.clearScreen();
    systemUI.displayLoginSuccess(patientMap.get("P" + userInput));
    Utilities.pressAnyKey();
    return new String[] { "true", userInput };
  }
}
