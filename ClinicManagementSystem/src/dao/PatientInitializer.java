package dao;

import adt.RedBlackTreeMap;
import entity.Patient;
import utility.Enum.BloodType;
import utility.Utilities;

/**
 *
 * @author Tan Kai Hao
 */
public class PatientInitializer {
  
  public RedBlackTreeMap<String, Patient> initializeSamplePatients(int sampleSize) {
    RedBlackTreeMap<String, Patient> patientMap = new RedBlackTreeMap<>();

    Patient.setLastId(0);
    
    for (int i = 1; i <= sampleSize; i++) {
      Patient newPatient = new Patient();

      int year = (int) (Math.random() * 99);
      int month = 1 + (int) (Math.random() * 12);
      int day = 1 + (int) (Math.random() * (month == 2 ? (year % 4 == 0 ? 29 : 28) : (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) ? 31 : 30));

      StringBuilder sb = new StringBuilder();
      sb.append(String.format("%02d", year));
      sb.append(String.format("%02d", month));
      sb.append(String.format("%02d", day));
      sb.append(String.format("%02d", (int) (Math.random() * 99)));
      sb.append(String.format("%04d", (int) (Math.random() * 9999)));
      newPatient.setIcNumber(Utilities.formatIcNumber(sb.toString()));

      newPatient.setName("Patient " + String.format("%04d", i));
      newPatient.setGender(newPatient.identifyGender());
      newPatient.setBirthDate(newPatient.identifyBirthDate());
      newPatient.setAge(newPatient.calculateAge());
      newPatient.setContactNumber(Utilities.formatContactNumber("01" + (Math.random() < 0.5 ? String.format("%08d", (int) (Math.random() * 99999999)) : String.format("%09d", (int) (Math.random() * 999999999)))));
      newPatient.setEmail(newPatient.getPatientId() + "@clinic.com");
      newPatient.setAddress(newPatient.getPatientId() + " Uni Care Clinic");
      newPatient.setBloodType(Math.random() < 0.5 ? BloodType.A : Math.random() < 0.5 ? BloodType.B : Math.random() < 0.5 ? BloodType.AB : BloodType.O);
      
      patientMap.put(newPatient.getPatientId(), newPatient);
    }
    return patientMap;
  }
}
