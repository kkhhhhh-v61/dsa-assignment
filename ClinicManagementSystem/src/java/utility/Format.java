package utility;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Format {
  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  public static String formatDate(LocalDate date) {
    return date.format(DATE_FORMATTER);
  }
  
  public static LocalDate parseDate(String dateString) {
    return LocalDate.parse(dateString, DATE_FORMATTER);
  }

  public static int getDifferenceInYears(LocalDate date1, LocalDate date2) {
    return Math.abs(Period.between(date1, date2).getYears());
  }

  private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

  public static String formatTime(LocalTime time) {
    return time.format(TIME_FORMATTER);
  }

  public static LocalTime parseTime(String timeString) {
    return LocalTime.parse(timeString, TIME_FORMATTER);
  }

  public static boolean checkDateFormat(String dateString) {
    try {
      parseDate(dateString);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

}
