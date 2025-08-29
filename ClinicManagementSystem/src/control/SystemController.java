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
    boolean exit = false;
    do {
      Utilities.clearScreen();
      char[] inputOptions = systemUI.displayClinicStaffPortalMenu();
      String userInput = systemUI.getGeneralInput();
      boolean valid = SystemValidation.validateCharInput(userInput, inputOptions);

      if (valid) {
        switch (userInput.charAt(0)) {
          case '1':
            patientController.runPatientManagement();
            break;
          case '2':
            doctorUI.showDoctorMenu();
            break;
          case '3':
            appointmentController.runAppointmentManagement();
            break;
          case '4':
            consultationController.syncConsultationsWithAppointments();
            consultationUI.runMenu();
            break;
          case '5':
            treatmentUI.runMenu();
            break;
          case '6':
            pharmacyUI.displayPharmacyMenu();
            break;
          case '7':
            break;
          case '0':
            exit = true;
            break;
        }
      }
    } while (!exit);
  }
  
  public void runStudentPortal() {
    String[] response = runPatientIdLogin();
    String patientId = response[1];
    
    if (!Boolean.parseBoolean(response[0])) {
      return;
    }

    boolean exit = false;
    do {
      Utilities.clearScreen();
      char[] inputOptions = systemUI.displayStudentPortalMenu();
      String userInput = systemUI.getGeneralInput();
      boolean valid = SystemValidation.validateCharInput(userInput, inputOptions);

      if (valid) {
        patientId = "P" + response[1];
        switch (userInput.charAt(0)) {
          case '1':
            // queueController.runStudentQueue(patientId);
            break;
          case '2':
            appointmentController.runStudentAppointment(patientId);
            break;
          case '3':
            consultationUI.runStudent(patientId);
            break;
          case '4':
            treatmentUI.runStudentMenu(patientId);
            break;
          case '0':
            exit = true;
            break;
        }
      }
    } while (!exit);
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
