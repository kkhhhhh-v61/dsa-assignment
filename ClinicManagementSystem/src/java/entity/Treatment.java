package entity;

import java.time.LocalDate;
import entity.Medicine;

public class Treatment {
  private int lastID = 1000;

  private String treatmentID;
  private String consultationID;
  private Patient patient;
  private LocalDate prescriptionDate;
  private Medicine medicine;
  private String instructions;
  private LocalDate treatmentDuration;
}
