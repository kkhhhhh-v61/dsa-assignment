package utility;

public class Format {

  public static String formatName(String name) {
    if (name == null || name.isEmpty()) {
      return "";
    }
    // Capitalize the first letter of each word and make the rest lowercase
    StringBuilder formattedName = new StringBuilder();
    String[] words = name.split(" ");
    for (String word : words) {
      if (!word.isEmpty()) {
        formattedName.append(Character.toUpperCase(word.charAt(0)))
                     .append(word.substring(1).toLowerCase())
                     .append(" ");
      }
    }
    return formattedName.toString().trim();
  }

  public static String removeSpaces(String input) {
    if (input == null) {
      return "";
    }
    return input.replaceAll("\\s+", "");
  }

  public static String formatContactNumber(String contactNumber) {
    // Remove all non-digit characters
    String cleanedNumber = contactNumber.replaceAll("[^0-9]", "");

    // Apply formatting based on common Malaysian mobile number patterns
    if (cleanedNumber.startsWith("01") && cleanedNumber.length() >= 9 && cleanedNumber.length() <= 10) {
      if (cleanedNumber.length() == 9) { // e.g., 01X-XXXXXXX
        return cleanedNumber.substring(0, 3) + "-" + cleanedNumber.substring(3);
      } else if (cleanedNumber.length() == 10) { // e.g., 01X-XXXXXXXX
        return cleanedNumber.substring(0, 3) + "-" + cleanedNumber.substring(3);
      }
    }
    // Return original if not a recognized format or already formatted
    return contactNumber;
  }
}
