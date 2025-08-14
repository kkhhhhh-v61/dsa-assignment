package utility;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Format {
  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  public static String formatDate(LocalDate date) {
    return date.format(DATE_FORMATTER);
  }

  public static int getDifferenceInYears(LocalDate date1, LocalDate date2) {
    return Math.abs(Period.between(date1, date2).getYears());
  }
  
  public static LocalDate parseDate(String dateString) {
    return LocalDate.parse(dateString, DATE_FORMATTER);
  }
}
