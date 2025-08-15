package entity;

import java.time.LocalTime;
import utility.Enum.DayOfWeek;
import utility.Format;

public class Schedule {
  private DayOfWeek dayOfWeek;
  private LocalTime startTime;
  private LocalTime endTime;
  private boolean onShift;

  public Schedule() {
  }

  public Schedule(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime, boolean onShift) {
    this.dayOfWeek = dayOfWeek;
    this.startTime = startTime;
    this.endTime = endTime;
    this.onShift = onShift;
  }

  public DayOfWeek getDayOfWeek() {
    return dayOfWeek;
  }

  public void setDayOfWeek(DayOfWeek dayOfWeek) {
    this.dayOfWeek = dayOfWeek;
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

  public boolean isOnShift() {
    return onShift;
  }

  public void toggleOnShift() {
    this.onShift = !this.onShift;
  }

  public String toString() {
    return String.format("\n[%s | %s - %s | %s]",
      dayOfWeek,
      Format.formatTime(startTime),
      Format.formatTime(endTime),
      onShift ? "ON" : "OFF"
    );
  }
}
