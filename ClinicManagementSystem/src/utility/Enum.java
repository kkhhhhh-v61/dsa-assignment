package utility;

/**
 *
 * @author Tan Kai Hao
 */
public class Enum {

  public enum BloodType {
    A, B, AB, O, Unknown
  }

  public enum DayOfWeek {
    Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday
  }

  public enum Gender {
    Male, Female, Unknown
  }

  public enum MedicineCategory {
    Tablet, Capsule, Liquid, Syringe, Inhaler, Topical, Other, Unknown
  }
  
  public enum Specialization {
    Cardiology, Dermatology, Neurology, Pediatrics, Surgery, Unknown
  }

  public enum Status {
    Pending, Approved, Rejected, Completed, Cancelled, Waiting, Serving, Unknown
  }
}
