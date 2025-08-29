package dao;

import adt.ArrayList;
import entity.Doctor;
import entity.Schedule;
import utility.Enum.DayOfWeek;
import utility.Enum.Specialization;
// import utility.MessageUI;
// import utility.Utilities;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

/** 
 *
 * @author Tan Kai Hao
 */
public class DoctorScheduleDAO {
  
  static File dataFileDoctor = new File("./data/doctors.txt");
  static File dataFileSchedule = new File("./data/schedules.txt");

  public DoctorScheduleDAO() {
    try {
      if (!dataFileDoctor.exists()) {
        // System.out.println("File: 'doctors.txt' doesn't exist. Creating it now...");
        dataFileDoctor.getParentFile().mkdirs();
        dataFileDoctor.createNewFile();
        // System.out.println("File: 'doctors.txt' created successfully.");
        // Utilities.wait(1000);
      }
    } catch (IOException e) {
      // System.out.println("File: 'doctors.txt' creation failed.");
      // Utilities.wait(1000);
      e.printStackTrace();
      // MessageUI.pressAnyKey();
    }
  }

  public ArrayList<?> readFromFile(String retrieveType) {

    if (retrieveType.equals("doctor")) {

      ArrayList<Doctor> doctorList = new ArrayList<>();

      try {
        FileReader fileReader = new FileReader(dataFileDoctor);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
          String[] dataArray = line.split("\\|");
          Doctor doctor = new Doctor();
          doctor.setDoctorId(dataArray[0]);
          doctor.setIcNumber(dataArray[1]);
          doctor.setName(dataArray[2]);
          doctor.setGender(doctor.identifyGender());
          doctor.setBirthDate(doctor.identifyBirthDate());
          doctor.setAge(doctor.calculateAge());
          doctor.setContactNumber(dataArray[6]);
          doctor.setEmail(dataArray[7]);
          doctor.setAddress(dataArray[8]);
          doctor.setRegistrationDate(LocalDate.parse(dataArray[9]));
          doctor.setSpecialization(Specialization.valueOf(dataArray[10]));
          doctor.setYearsOfExperience(Integer.parseInt(dataArray[11]));
          doctorList.add(doctor);
        }
        bufferedReader.close();
        fileReader.close();
        
      } catch (Exception e) {
        // System.out.println("File: 'doctors.txt' reading failed.");
        // Utilities.wait(1000);
        e.printStackTrace();
        // MessageUI.pressAnyKey();
      }
      
      return doctorList;
    } else if (retrieveType.equals("schedule")) {

      ArrayList<Schedule> scheduleList = new ArrayList<>();

      try {
        FileReader fileReader = new FileReader(dataFileSchedule);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
          String[] dataArray = line.split("\\|");
          Schedule schedule = new Schedule();
          schedule.setScheduleId(dataArray[0]);
          schedule.setDoctorId(dataArray[1]);
          schedule.setDay(DayOfWeek.valueOf(dataArray[2]));
          schedule.setStartTime(LocalTime.parse(dataArray[3]));
          schedule.setEndTime(LocalTime.parse(dataArray[4]));
          schedule.setIsAvailable(Boolean.parseBoolean(dataArray[5]));
          scheduleList.add(schedule);
        }
        bufferedReader.close();
        fileReader.close();
      } catch (Exception e) {
        // System.out.println("File: 'schedules.txt' reading failed.");
        // Utilities.wait(1000);
        e.printStackTrace();
        // MessageUI.pressAnyKey();
      }
      
      return scheduleList;
    }
    return null;
  }
}
