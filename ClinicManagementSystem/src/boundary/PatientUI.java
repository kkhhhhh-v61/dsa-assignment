package boundary;

import adt.HashMap;
import entity.Patient;
import utility.MessageUI;
import utility.Validation;
import java.util.Scanner;

public class PatientUI {

    private Scanner scanner = new Scanner(System.in);

    public String[] displayPatientManagementUI() {
        int choice;
        do {
            MessageUI.clearScreen();
            MessageUI.displayBorder();
            System.out.println("|                                       PATIENT MANAGEMENT                                         |");
            MessageUI.displayBorder();
            System.out.println("| [1] Register New Patient                                                                         |");
            System.out.println("| [2] View All Patients                                                                            |");
            System.out.println("| [0] Back to Main Menu                                                                            |");
            MessageUI.displayBorder();
            MessageUI.displayContinueMessage();
            System.out.print("Enter your choice: ");
            choice = Validation.getIntegerInput();

            switch (choice) {
                case 1:
                case 2:
                case 0:
                    return new String[]{String.valueOf(choice), "120"}; // Return choice and valid options
                default:
                    MessageUI.displayInvalidChoice();
            }
        } while (true);
    }

    public String inputPatientIcNumber() {
        System.out.print("Enter Patient IC Number (e.g., 000000-00-0000) or '0' to cancel: ");
        return scanner.nextLine();
    }

    public String inputPatientName() {
        System.out.print("Enter Patient Name or '0' to cancel: ");
        return scanner.nextLine();
    }

    public char inputPatientGender() {
        char choice;
        do {
            System.out.println("Select Gender:");
            System.out.println("1. Male");
            System.out.println("2. Female");
            System.out.print("Enter choice (1/2) or '0' to cancel: ");
            String input = scanner.nextLine();
            if (input.length() == 1 && (input.charAt(0) == '1' || input.charAt(0) == '2' || input.charAt(0) == '0')) {
                choice = input.charAt(0);
                break;
            } else {
                MessageUI.displayInvalidChoice();
            }
        } while (true);
        return choice;
    }

    public String inputPatientBirthDate() {
        System.out.print("Enter Patient Birth Date (YYYY-MM-DD) or '0' to cancel: ");
        return scanner.nextLine();
    }

    public String inputPatientContactNumber() {
        System.out.print("Enter Patient Contact Number or '0' to cancel: ");
        return scanner.nextLine();
    }

    public String inputPatientEmail() {
        System.out.print("Enter Patient Email or '0' to cancel: ");
        return scanner.nextLine();
    }

    public String inputPatientAddress() {
        System.out.print("Enter Patient Address or '0' to cancel: ");
        return scanner.nextLine();
    }

    public char inputPatientBloodType() {
        char choice;
        do {
            System.out.println("Select Blood Type:");
            System.out.println("1. A");
            System.out.println("2. B");
            System.out.println("3. AB");
            System.out.println("4. O");
            System.out.print("Enter choice (1/2/3/4) or '0' to cancel: ");
            String input = scanner.nextLine();
            if (input.length() == 1 && (input.charAt(0) == '1' || input.charAt(0) == '2' || input.charAt(0) == '3' || input.charAt(0) == '4' || input.charAt(0) == '0')) {
                choice = input.charAt(0);
                break;
            } else {
                MessageUI.displayInvalidChoice();
            }
        } while (true);
        return choice;
    }

    public String[] displayPatientListUI(HashMap<String, Patient> patientMap, String ui, int page) {
        MessageUI.clearScreen();
        MessageUI.displayBorder();
        System.out.println("|                                          PATIENT LIST                                            |");
        MessageUI.displayBorder();

        if (patientMap.isEmpty()) {
            System.out.println("No patients registered yet.");
            MessageUI.displayBorder();
            MessageUI.displayContinueMessage();
            return new String[]{"0", "0"}; // Indicate no options, just exit
        }

        // Display patients (simplified for now, assuming 10 patients per page)
        int startIndex = (page - 1) * 10;
        int endIndex = Math.min(startIndex + 10, patientMap.size());

        System.out.printf("%-10s %-20s %-10s %-15s\n", "ID", "Name", "Gender", "Contact");
        MessageUI.displayBorder();
        for (int i = startIndex; i < endIndex; i++) {
            // This part needs to be adapted as HashMap doesn't have direct index access like List
            // For now, a placeholder. Actual implementation would iterate through values or use a List.
            // Assuming patientMap.getEntry(i) is a placeholder for getting the i-th patient
            // This will require a change in PatientController to pass a List or iterate differently.
            // For now, I'll just print a placeholder.
            System.out.println("Patient data placeholder...");
        }
        MessageUI.displayBorder();
        System.out.println("Page " + page + " of " + ((patientMap.size() + 9) / 10));
        MessageUI.displayBorder();
        System.out.println("| [E] Previous Page | [Q] Next Page | [W] Toggle View | [0] Back                               |");
        MessageUI.displayBorder();
        MessageUI.displayContinueMessage();
        System.out.print("Enter your choice: ");
        String input = scanner.nextLine();
        return new String[]{input, "EQW0"}; // Return choice and valid options
    }
}
