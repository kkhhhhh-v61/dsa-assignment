package control;

import boundary.DoctorUI;
import boundary.SystemUI;

/**
 *
 * @author Tan Kai Hao
 */
public class SystemController {

  SystemUI systemUI = new SystemUI();
  
  PatientController patientController = new PatientController();
  DoctorUI doctorUI = new DoctorUI();
  
  public void runClinicStaffPortal() {
    boolean exit = false;


    do {
      exit = false;

      char response = systemUI.runClinicStaffMenu();

      switch (response) {
        case '1':
          patientController.runPatientManagement();
          break;
        case '2':
          doctorUI.showDoctorMenu();
          break;
        case '3':
          break;
        case '4':
          break;
        case '5':
          break;
        case '0':
          exit = true;
          break;
      }
    } while (!exit);
  }
}
