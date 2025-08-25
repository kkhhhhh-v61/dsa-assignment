package entity;

import utility.Enum.DayOfWeek;
import java.time.LocalTime;

/**
 *
 * @author Tan Kai Hao
 */
public class Schedule {
  private static int lastId = 0;

  private String scheduleId;
  private String doctorId;
  private DayOfWeek day;
  private LocalTime startTime;
  private LocalTime endTime;
  private boolean isAvailable;

  public Schedule() {
    this.scheduleId = "S" + String.format("%04d", ++lastId);
    this.doctorId = "";
    this.day = null;
    this.startTime = LocalTime.now();
    this.endTime = LocalTime.now();
    this.isAvailable = false;
  }

  public Schedule(String doctorId, DayOfWeek day, LocalTime startTime, LocalTime endTime,
      boolean isAvailable) {
    this.scheduleId = "S" + String.format("%04d", ++lastId);
    this.doctorId = doctorId;
    this.day = day;
    this.startTime = startTime;
    this.endTime = endTime;
    this.isAvailable = isAvailable;
  }
  
  public String getScheduleId() {
    return scheduleId;
  }

  public void setScheduleId(String scheduleId) { /* Not recommended for use */
    this.scheduleId = scheduleId;
  }

  public String getDoctorId() {
    return doctorId;
  }

  public void setDoctorId(String doctorId) {
    this.doctorId = doctorId;
  }

  public DayOfWeek getDay() {
    return day;
  }

  public void setDay(DayOfWeek day) {
    this.day = day;
  }

  public LocalTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalTime startTime) {
    this.startTime = startTime;
  }

  public LocalTime getEndTime() {
    return endTime;
  }

  public void setEndTime(LocalTime endTime) {
    this.endTime = endTime;
  }

  public boolean getIsAvailable() {
    return isAvailable;
  }

  public void setIsAvailable(boolean available) {
    isAvailable = available;
  }

  public String toString() {
    return "Schedule ID: " + scheduleId + "\nDoctor ID: " + doctorId + "\nDay: " + day
        + "\nStart Time: " + startTime + "\nEnd Time: " + endTime + "\nIs Available: " + isAvailable;
  }
  
  public String toFileString() {
    return scheduleId + "|" + doctorId + "|" + day + "|" + startTime + "|" + endTime + "|" + isAvailable;
  }
}
