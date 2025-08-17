package control;

import adt.ArrayList;
import boundary.MainMenuUI;
import utility.MessageUI;
import utility.Validation;
import boundary.BoundaryAppointment;
import boundary.BoundaryConsultation;
import entity.Doctor;
import entity.Patient;
public class MainMenuController {
  public boolean runMainMenu(char input) {
    ArrayList<Patient> patients = new ArrayList<>();
    ArrayList<Doctor> doctors = new ArrayList<>();
    MainMenuUI mainMenuUI = new MainMenuUI();
    PatientMaintenance patientMaintenance = new PatientMaintenance();
    MessageUI messageUI = new MessageUI();
    Validation validation = new Validation();
    String[] response;
    char[] options;
    boolean exit = false;
    
    switch (input) {
      case '1':
        do {
            response = mainMenuUI.displayStudentUI();
            input = response[0].charAt(0);
            options = response[1].toCharArray();

            if (validation.checkInputOptionsMatch(options, input)) { 
                if (input == '0') {
                    exit = true;
                    break;   // ✅ break immediately, don't wait for next loop check
                }

                switch (input) {
                    case '1':
                        BoundaryAppointment boundaryAdd = new BoundaryAppointment(patients, doctors, "STUDENTADD");
                        boundaryAdd.run();
                        break;
                    case '2':
                        BoundaryAppointment boundaryView = new BoundaryAppointment(patients, doctors, "STUDENTVIEW");
                        boundaryView.run();
                        break;
                    case '3':
                        break;
                    case '4':
                        break;
                    case '5':
                        break;
                    case '6':
                        break;
                    default:
                        break;
                }
            } else {
                messageUI.displayInputOptionErrorMessage(options);
            }
        } while (!exit);
        break;
      case '2':
        do {
            response = mainMenuUI.displayStaffUI();
            input = response[0].charAt(0);
            options = response[1].toCharArray();

            if (validation.checkInputOptionsMatch(options, input)) {
                if (input == '0') {
                    exit = true;
                    break; 
                }

                switch (input) {
                    case '1':
                        patientMaintenance.runPatientMaintenance();
                        break;
                    case '2':
                        break;
                    case '3':
                        BoundaryAppointment boundaryAppointment = new BoundaryAppointment(patients, doctors, "STAFF");
                        boundaryAppointment.run();
                        break;
                    case '4':
                        BoundaryConsultation boundaryConsultation = new BoundaryConsultation(patients, doctors);
                        boundaryConsultation.run();
                        break;
                    case '5':
                        break;
                    case '6':
                        break;
                    case '7':
                        break;
                    default:
                        break;
                }
            } else {
                messageUI.displayInputOptionErrorMessage(options);
            }
        } while (!exit);
        break;
        case '0':
            mainMenuUI.displayGoodbyeMessage();
            return true;
        default:
            break;
      }
    return false;
  }
}
