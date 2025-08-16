package client;
import adt.ArrayList;
import adt.ListInterface;
import entity.Consultation;
import control.ConsultationManager;
import boundary.ConsultationUI;

public class ClinicManagementSystem {
  public static void main(String[] args) {
    // Initialize ADT and data
    ListInterface<Consultation> consultationList = new ArrayList<>();
    // You can populate this list with initial data from a file or hardcode it here
    
    // Initialize the manager
    ConsultationManager consultationManager = new ConsultationManager(consultationList);
    
    // Initialize and run the UI
    ConsultationUI consultationUI = new ConsultationUI(consultationManager);
    consultationUI.run();
  }
}
