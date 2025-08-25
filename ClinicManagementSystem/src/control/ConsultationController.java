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

    public ConsultationController() {
        this.consultationDAO = new ConsultationDAO();
        this.appointmentDAO = new AppointmentDAO();
        syncConsultationsWithAppointments();
    }

    void syncConsultationsWithAppointments() {
        RedBlackTreeMap<String, Appointment> appointments = appointmentDAO.retrieveFromFile();
        ArrayList<Consultation> consultations = consultationDAO.getAllConsultations();

        // Get all keys in sorted order
        ArrayList<String> keys = appointments.getKeys();

        for (int i = 1; i <= keys.size(); i++) {  // if adt.ArrayList is 1-indexed
            String key = keys.get(i);
            Appointment appt = appointments.get(key);

            if (appt.getStatus() == Status.Approved) {
                boolean exists = false;
                for (int j = 1; j <= consultations.size(); j++) {
                    Consultation c = consultations.get(j);
                    if (c.getPatientId().equals(appt.getPatientId()) &&
                        c.getDoctorId().equals(appt.getDoctorId()) &&
                        c.getConsultationDate().equals(appt.getAppointmentDate()) &&
                        c.getConsuLocalTime().equals(appt.getAppointmentTime())) {   // ✅ fixed typo
                        exists = true;
                        break;
                    }
                }

                if (!exists) {
                    // Generate new ID
                    int lastId = consultationDAO.getLastId();
                    String newId = String.format("C%04d", lastId + 1);

                    ArrayList<String> symptoms = new ArrayList<>();
                    symptoms.add("none");   // ✅ default placeholder
                    ArrayList<String> diagnosis = new ArrayList<>();
                    diagnosis.add("none");  // ✅ default placeholder

                    Consultation newC = new Consultation(
                        appt.getPatientId(),     // ✅ correct order
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
    }

    public void addConsultation(Consultation consultation) {
        consultationDAO.addConsultation(consultation);
    }

    public Consultation viewConsultationById(String id) {
        return consultationDAO.getConsultationById(id);
    }

    public ArrayList<Consultation> viewAllConsultations() {
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
}
