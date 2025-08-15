package utility;

public class Enum {
  public enum Status {
    Pending, Approved, Rejected, Completed, Cancelled, Ongoing, Unknown
  }

  /* Person */
  public enum Gender {
    Male, Female, Unknown
  }

  /* Patient */
  public enum Allergy {
    Dairy, Egg, Peanut, Shellfish, Strawberries, None
  }

  public enum BloodType {
    A, B, AB, O, Unknown
  }

  /* Doctor */
  public enum Specialization {
    Cardiology, Dermatology, Neurology, Pediatrics, Surgery, Unknown
  }

  /* Schedule */
  public enum DayOfWeek {
    Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday
  }

  /* Medicine */
  public enum MedicineCategory {
    Tablet, Capsule, Liquid, Syringe, Inhaler, Topical, Other, Unknown
  }
}
