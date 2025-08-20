package client;

import adt.ArrayList;
import adt.ListInterface;
import entity.Consultation;
import control.ConsultationManager;
import boundary.ConsultationUI;
import control.PharmacyController; // Import PharmacyController
import boundary.PharmacyUI;       // Import PharmacyUI
import utility.MessageUI;
import utility.Validation;
import java.util.Scanner;

public class ClinicManagementSystem {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    // Load data from file for Consultation Module
    ListInterface<Consultation> consultationList = utility.DataManager.loadConsultations();
    ConsultationManager consultationManager = new ConsultationManager(consultationList);
    ConsultationUI consultationUI = new ConsultationUI(consultationManager);

    // Initialize Pharmacy Module
    PharmacyController pharmacyController = new PharmacyController();
    PharmacyUI pharmacyUI = new PharmacyUI(pharmacyController);

    int choice;
    do {
      System.out.println("\n--- Clinic Management System Main Menu ---");
      System.out.println("1. Consultation Management Module");
      System.out.println("2. Pharmacy Management Module");
      System.out.println("0. Exit Application");
      System.out.print("Enter your choice: ");
      choice = Validation.getIntegerInput();

      switch (choice) {
        case 1:
          consultationUI.run(); // Assuming ConsultationUI has a run method
          break;
        case 2:
          pharmacyUI.displayPharmacyMenu(); // Call the PharmacyUI menu
          break;
        case 0:
          MessageUI.displayExitMessage("Exiting Clinic Management System. Goodbye!");
          break;
        default:
          MessageUI.displayInvalidChoice();
      }
    } while (choice != 0);

    scanner.close();
  }
}
