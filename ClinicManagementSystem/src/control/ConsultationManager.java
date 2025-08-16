package control;

import utility.*;
import adt.ListInterface;
import adt.ArrayList;
import entity.Appointment;
import entity.Consultation;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Iterator;

/**
 *
 * @author User
 */
public class ConsultationManager {

    private ListInterface<Consultation> consultationList;
    private static int lastId = 0;

    public ConsultationManager(ListInterface<Consultation> consultationList) {
        this.consultationList = consultationList;
    }

    public void addConsultation(Consultation consultation) {
        consultationList.add(consultation);
    }

    public Consultation getConsultationById(String consultationId) {
        for (int i = 1; i <= consultationList.size(); i++) {
            if (consultationList.getEntry(i).getConsultationId().equals(consultationId)) {
                return consultationList.getEntry(i);
            }
        }
        return null;
    }

    public ListInterface<Consultation> getConsultationsByPatientId(String patientId) {
        ListInterface<Consultation> result = new ArrayList<>();
        for (int i = 1; i <= consultationList.size(); i++) {
            if (consultationList.getEntry(i).getPatient().getPatientId().equals(patientId)) {
                result.add(consultationList.getEntry(i));
            }
        }
        return result;
    }

    public ListInterface<Consultation> getConsultationsByDoctorId(String doctorId) {
        ListInterface<Consultation> result = new ArrayList<>();
        for (int i = 1; i <= consultationList.size(); i++) {
            if (consultationList.getEntry(i).getDoctor().getDoctorId().equals(doctorId)) {
                result.add(consultationList.getEntry(i));
            }
        }
        return result;
    }

    public void updateConsultation(Consultation consultation) {
        for (int i = 1; i <= consultationList.size(); i++) {
            if (consultationList.getEntry(i).getConsultationId().equals(consultation.getConsultationId())) {
                consultationList.replace(i, consultation);
                break;
            }
        }
    }

    public void deleteConsultation(String consultationId) {
        for (int i = 1; i <= consultationList.size(); i++) {
            if (consultationList.getEntry(i).getConsultationId().equals(consultationId)) {
                consultationList.remove(i);
                break;
            }
        }
    }

    public String generateNewConsultationId() {
        return "C" + String.format("%04d", ++lastId);
    }

    public Appointment scheduleFollowUp(String consultationId, LocalDate appointmentDate, LocalTime appointmentTime) {
        Consultation consultation = getConsultationById(consultationId);
        if (consultation != null) {
            Appointment followUp = new Appointment(
                consultation.getPatient(),
                consultation.getDoctor(),
                appointmentDate,
                appointmentTime,
                "Follow-up",
                utility.Enum.Status.Pending
            );
            // Here you would typically add the appointment to an appointment list/manager
            // For now, we just return the created appointment
            return followUp;
        }
        return null;
    }

    public String generateConsultationsPerDoctorReport() {
        // This is a simplified report. A more complex implementation might use a Map.
        StringBuilder report = new StringBuilder("Consultations per Doctor Report:\n");
        ListInterface<String> doctorIds = new ArrayList<>();
        for (int i = 1; i <= consultationList.size(); i++) {
            String doctorId = consultationList.getEntry(i).getDoctor().getDoctorId();
            if (!isDoctorIdInList(doctorIds, doctorId)) {
                doctorIds.add(doctorId);
            }
        }

        for (int i = 1; i <= doctorIds.size(); i++) {
            String doctorId = doctorIds.getEntry(i);
            int count = 0;
            for (int j = 1; j <= consultationList.size(); j++) {
                if (consultationList.getEntry(j).getDoctor().getDoctorId().equals(doctorId)) {
                    count++;
                }
            }
            report.append("Doctor ID: ").append(doctorId).append(" - ").append(count).append(" consultations\n");
        }
        return report.toString();
    }
    
    private boolean isDoctorIdInList(ListInterface<String> doctorIds, String doctorId) {
        for (int i = 1; i <= doctorIds.size(); i++) {
            if (doctorIds.getEntry(i).equals(doctorId)) {
                return true;
            }
        }
        return false;
    }

    public String generateCommonSymptomsReport() {
        // This requires a more complex data structure to do properly, like a HashMap to count symptoms.
        // For now, this is a placeholder implementation.
        return "Common Symptoms Report: Not yet implemented.";
    }
}