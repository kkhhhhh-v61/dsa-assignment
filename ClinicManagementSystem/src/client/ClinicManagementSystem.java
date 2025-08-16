package client;
import adt.ArrayList;
import adt.ListInterface;
import entity.Consultation;
import control.ConsultationManager;
import boundary.ConsultationUI;

public class ClinicManagementSystem {
  public static void main(String[] args) {
    // Load data from file
    ListInterface<Consultation> consultationList = utility.DataManager.loadConsultations();
    
    // Initialize the manager
    ConsultationManager consultationManager = new ConsultationManager(consultationList);
    
    // Initialize and run the UI
    ConsultationUI consultationUI = new ConsultationUI(consultationManager);
    consultationUI.run();
  }
}
