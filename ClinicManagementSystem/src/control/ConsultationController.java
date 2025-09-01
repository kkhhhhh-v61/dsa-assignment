package control;

// Author - Jason Tye

import dao.AppointmentDAO;
import dao.ConsultationDAO;
import entity.Appointment;
import entity.Consultation;
import adt.ArrayList;
import adt.RedBlackTreeMap;
import java.time.LocalDate;
import utility.Enum.Status;

public class ConsultationController {
    private ConsultationDAO consultationDAO;
    private AppointmentDAO appointmentDAO;
    ArrayList<Consultation> consultations = new ArrayList<Consultation>();
    public ConsultationController() {
        this.consultationDAO = new ConsultationDAO();
        this.appointmentDAO = new AppointmentDAO();
    }

    // Sync All Consultations with Appointments
    public void syncConsultationsWithAppointments() {
    RedBlackTreeMap<String, Appointment> apptMap = appointmentDAO.readFromFile();
    ArrayList<String> keys = apptMap.getKeys(); 

    for (int i = 1; i <= keys.size(); i++) {
        String apptId = keys.get(i);
        Appointment appt = apptMap.get(apptId);
        if (appt == null) continue;

        if (appt.getStatus() == Status.Approved) {
            ArrayList<Consultation> consList = consultationDAO.getAllConsultations();
            Consultation existing = null;

            for (int j = 1; j <= consList.size(); j++) {
                Consultation c = consList.get(j);
                if (c.getAppointmentId().equalsIgnoreCase(appt.getAppointmentId())) {
                    existing = c;
                    break;
                }
            }

            if (existing == null) {
                int lastId = consultationDAO.getLastId();
                String newId = String.format("C%04d", lastId + 1);

                ArrayList<String> symptoms = new ArrayList<String>();
                symptoms.add("none");
                ArrayList<String> diagnosis = new ArrayList<String>();
                diagnosis.add("none");

                Consultation newC = new Consultation(
                    appt.getAppointmentId(),
                    appt.getDoctorId(),
                    appt.getPatientId(),
                    appt.getAppointmentDate(),
                    appt.getAppointmentTime(),
                    symptoms,
                    diagnosis,
                    "",
                    Status.Unknown
                );
                newC.setConsultationId(newId);

                consultationDAO.addConsultation(newC);
                System.out.println("✅ Created new Consultation " + newId + " for Appointment " + apptId);

            } else {
                boolean updated = false;
                if (!existing.getConsultationDate().equals(appt.getAppointmentDate())) {
                    existing.setConsultationDate(appt.getAppointmentDate());
                    updated = true;
                }
                if (!existing.getConsuLocalTime().equals(appt.getAppointmentTime())) {
                    existing.setConsultationTime(appt.getAppointmentTime());
                    updated = true;
                }

                if (updated) {
                    System.out.println("🔄 Updated Consultation " + existing.getConsultationId()
                                       + " to new date/time from Appointment " + apptId);
                }
            }
        }
    }
    consultationDAO.saveAll();
}



    // ===== Add Consultations =====
    public void addConsultation(Consultation consultation) {
        consultationDAO.addConsultation(consultation);
    }

    // ===== View Consultations By ID =====
    public Consultation viewConsultationById(String id) {
        return consultationDAO.getConsultationById(id);
    }

    // ===== View All Consultations =====
    public ArrayList<Consultation> viewAllConsultations() {
        consultationDAO.reload();
        return consultationDAO.getAllConsultations();
    }

    // ===== Edit Consultations =====
    public void editConsultation(Consultation consultation) {
        consultationDAO.updateConsultation(consultation);
    }

    // ===== Delete Consultations =====
    public void deleteConsultation(String id) {
        consultationDAO.deleteConsultation(id);
    }

    // ===== View Consultations By User ID =====
    public ArrayList<Consultation> viewConsultationsByUserId(String userId) {
        return consultationDAO.getConsultationsByUserId(userId);
    }

    // ===== Create Consultation by Appointment =====
    public void createConsultationForAppointment(Appointment appt) {
    if (appt.getStatus() == Status.Approved) {
        if (!consultationDAO.existsForAppointment(appt)) {
            int lastId = consultationDAO.getLastId();
            String newId = String.format("C%04d", lastId + 1);

            ArrayList<String> symptoms = new ArrayList<>();
            symptoms.add("none");
            ArrayList<String> diagnosis = new ArrayList<>();
            diagnosis.add("none");

            Consultation newC = new Consultation(
                appt.getAppointmentId(), 
                appt.getPatientId(),
                appt.getDoctorId(),
                appt.getAppointmentDate(),
                appt.getAppointmentTime(),
                symptoms,
                diagnosis,
                "",
                Status.Unknown
            );
            newC.setConsultationId(newId);

            consultationDAO.addConsultation(newC);
        }
    }
}

    // Check if Consultation exist for appointments
    public boolean existsForAppointment(Appointment appt) {
        for (int i = 1; i <= consultations.size(); i++) {
            Consultation c = consultations.get(i);
            if (c.getAppointmentId().equalsIgnoreCase(appt.getAppointmentId())) {
                return true;
            }
        }
        return false;
    }

    // Save all Consultations
    public void saveAllConsultations() {
    consultationDAO.saveAll();
}

    // Generate Yearly Report
    public void generateYearlyReport(int year) {
        ArrayList<Consultation> consultations = consultationDAO.getAllConsultations();
        int[] monthlyCounts = new int[12];

        for (int i = 1; i <= consultations.size(); i++) { 
            Consultation c = consultations.get(i);
            if (c != null) {
                LocalDate date = c.getConsultationDate();
                if (date.getYear() == year) {
                    monthlyCounts[date.getMonthValue() - 1]++;
                }
            }
        }

        String[] months = {"January","February","March","April","May","June","July","August","September","October","November","December"};

        int total = 0;
        System.out.println("\n=== Yearly Consultation Report for " + year + " ===");
        for (int m = 0; m < 12; m++) {
            System.out.println(months[m] + " = " + monthlyCounts[m]);
            total += monthlyCounts[m];
        }
        System.out.println("Total consultations in " + year + " = " + total);
    }

    // Generate year to year report
    public void generateYearToYearReport(int startYear, int endYear) {
        ArrayList<Consultation> consultations = consultationDAO.getAllConsultations();

        ArrayList<Integer> years = new ArrayList<>();
        ArrayList<Integer> counts = new ArrayList<>();

        for (int year = startYear; year <= endYear; year++) {
            years.add(year);
            counts.add(0);
        }

        for (int i = 1; i <= consultations.size(); i++) {
            Consultation c = consultations.get(i);
            if (c != null) {
                LocalDate date = c.getConsultationDate();
                int year = date.getYear();

                if (year >= startYear && year <= endYear) {
                    for (int j = 1; j <= years.size(); j++) {
                        if (years.get(j).equals(year)) {
                            counts.add(j, counts.get(j) + 1);
                            break;
                        }
                    }
                }
            }
        }
        System.out.println("\n=== Year-to-Year Consultation Report (" + startYear + " - " + endYear + ") ===");
        int total = 0;
        for (int j = 1; j <= years.size(); j++) {
            int count = counts.get(j);
            System.out.println(years.get(j) + " = " + count);
            total += count;
        }
        System.out.println("\nTotal consultations between " + startYear + " and " + endYear + " = " + total);
    }




}
