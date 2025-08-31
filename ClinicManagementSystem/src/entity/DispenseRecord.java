package entity;

import java.io.Serializable;
import java.time.LocalDate;
/**
 *
 * @author Lai Chee Kit
 */
public class DispenseRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int lastId = 0;

    private String dispenseId;
    private Medicine medicine;
    private int dispensedQuantity;
    private LocalDate dispenseDate;
    private String treatmentId; // Link to the treatment that caused this dispense
    private String patientId;
    private String doctorId;

    public DispenseRecord() {
        this.dispenseId = "D" + String.format("%04d", ++lastId);
        this.medicine = null;
        this.dispensedQuantity = 0;
        this.dispenseDate = LocalDate.now();
        this.treatmentId = "";
        this.patientId = "";
        this.doctorId = "";
    }

    public DispenseRecord(Medicine medicine, int dispensedQuantity, String treatmentId, String patientId, String doctorId) {
        this.dispenseId = "D" + String.format("%04d", ++lastId);
        this.medicine = medicine;
        this.dispensedQuantity = dispensedQuantity;
        this.dispenseDate = LocalDate.now();
        this.treatmentId = treatmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
    }

    public String getDispenseId() {
        return dispenseId;
    }

    public void setDispenseId(String dispenseId) {
        this.dispenseId = dispenseId;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public int getDispensedQuantity() {
        return dispensedQuantity;
    }

    public void setDispensedQuantity(int dispensedQuantity) {
        this.dispensedQuantity = dispensedQuantity;
    }

    public LocalDate getDispenseDate() {
        return dispenseDate;
    }

    public void setDispenseDate(LocalDate dispenseDate) {
        this.dispenseDate = dispenseDate;
    }

    public String getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(String treatmentId) {
        this.treatmentId = treatmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public static void setLastId(int lastId) {
        DispenseRecord.lastId = lastId;
    }

    @Override
    public String toString() {
        return "DispenseRecord{" +
               "dispenseId='" + dispenseId + '\'' +
               ", medicine=" + medicine.getName() +
               ", dispensedQuantity=" + dispensedQuantity +
               ", dispenseDate=" + dispenseDate +
               ", treatmentId='" + treatmentId + '\'' +
               ", patientId='" + patientId + '\'' +
               ", doctorId='" + doctorId + '\'' +
               '}';
    }
}
