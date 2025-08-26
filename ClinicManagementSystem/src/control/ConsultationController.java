package control;

// Author - Jason Tye

import dao.AppointmentDAO;
import dao.ConsultationDAO;
import entity.Appointment;
import entity.Consultation;
import adt.ArrayList;
import adt.RedBlackTreeMap;
import utility.Enum.Status;

public class ConsultationController {
    private ConsultationDAO consultationDAO;
    private AppointmentDAO appointmentDAO;
    ArrayList<Consultation> consultations = new ArrayList<Consultation>();
    public ConsultationController() {
        this.consultationDAO = new ConsultationDAO();
        this.appointmentDAO = new AppointmentDAO();
    }

    public void syncConsultationsWithAppointments() {
    // Load all appointments from file
    RedBlackTreeMap<String, Appointment> apptMap = appointmentDAO.retrieveFromFile();
    ArrayList<String> keys = apptMap.getKeys(); // your adt.ArrayList (1-based)

    for (int i = 1; i <= keys.size(); i++) {
        String apptId = keys.get(i);
        Appointment appt = apptMap.get(apptId);
        if (appt == null) continue;

        if (appt.getStatus() == Status.Approved) {
            // Get all consultations
            ArrayList<Consultation> consList = consultationDAO.getAllConsultations();
            Consultation existing = null;

            // Look for consultation linked to this appointment
            for (int j = 1; j <= consList.size(); j++) {
                Consultation c = consList.get(j);
                if (c.getAppointmentId().equalsIgnoreCase(appt.getAppointmentId())) {
                    existing = c;
                    break;
                }
            }

            if (existing == null) {
                // --- No consultation yet → create ---
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
                // --- Consultation exists → check and update date/time if needed ---
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

    // Persist consultations after sync
    consultationDAO.saveAll();
}




    public void addConsultation(Consultation consultation) {
        consultationDAO.addConsultation(consultation);
    }

    public Consultation viewConsultationById(String id) {
        return consultationDAO.getConsultationById(id);
    }

    public ArrayList<Consultation> viewAllConsultations() {
        consultationDAO.reload();
        return consultationDAO.getAllConsultations();
    }

    public void editConsultation(Consultation consultation) {
        consultationDAO.updateConsultation(consultation);
    }

    public void deleteConsultation(String id) {
        consultationDAO.deleteConsultation(id);
    }
    
    public ArrayList<Consultation> viewConsultationsByUserId(String userId) {
        return consultationDAO.getConsultationsByUserId(userId);
    }
    
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
                appt.getAppointmentId(),      // ✅ Link to appointment
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
    
    public boolean existsForAppointment(Appointment appt) {
        for (int i = 1; i <= consultations.size(); i++) {
            Consultation c = consultations.get(i);
            if (c.getAppointmentId().equalsIgnoreCase(appt.getAppointmentId())) {
                return true;
            }
        }
        return false;
    }
    
    public void saveAllConsultations() {
    consultationDAO.saveAll();
}

}
