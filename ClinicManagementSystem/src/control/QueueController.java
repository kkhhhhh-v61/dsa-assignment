package control;

import adt.ArrayList;
import adt.Queue;
import adt.RedBlackTreeMap;
import boundary.QueueUI;
import dao.AppointmentDAO;
import dao.DoctorScheduleDAO;
import dao.QueueDAO;
import entity.Appointment;
import entity.Schedule;
import utility.Enum.Status;
import utility.MessageUI;
import utility.Utilities;
import java.util.Iterator;
import java.time.LocalTime;

/**
 *
 * @author Tan Kai Hao
 */
public class QueueController {
  AppointmentDAO appointmentDAO = new AppointmentDAO();
  DoctorScheduleDAO doctorScheduleDAO = new DoctorScheduleDAO();
  QueueDAO queueDAO = new QueueDAO();
  QueueUI queueUI = new QueueUI();

  RedBlackTreeMap<String, Appointment> appointmentMap;
  Queue<String[]> patientQueue;
  ArrayList<Schedule> scheduleList;

  public QueueController() {
    loadAllData();
  }
  
  @SuppressWarnings("unchecked")
  public void loadAllData() {
    appointmentMap = appointmentDAO.readFromFile();
    if (appointmentMap == null) appointmentMap = new RedBlackTreeMap<>();
    if (!appointmentMap.isEmpty()) {
      ArrayList<String> keys = appointmentMap.getKeys();
      String lastKey = keys.get(keys.size());
      Appointment lastAppointment = appointmentMap.get(lastKey);
      String lastId = lastAppointment.getAppointmentId().substring(1);
      Appointment.setLastId(Integer.parseInt(lastId));
    } else {
      Appointment.setLastId(0);
    }

    patientQueue = queueDAO.readFromFile();
    if (patientQueue == null) patientQueue = new Queue<>();

    scheduleList = (ArrayList<Schedule>) doctorScheduleDAO.readFromFile("schedule");
    if (scheduleList == null) scheduleList = new ArrayList<>();
  }

  public void runQueueManagement() {
    do {
      Utilities.clearScreen();
      char[] inputOptions = queueUI.displayQueueManagement();
      String userInput = queueUI.getGeneralInput();
      boolean valid = QueueValidation.validateCharInput(userInput, inputOptions);

      if (valid) {
        switch (userInput.charAt(0)) {
          case '1':
            runQueueList();
            continue;
          case '2':
            runCallNext();
            continue;
          case '3':
            runClearQueue();
            continue;
          case '0':
            return;
        }
      }
    } while (true);
  }

  public void runQueueList() {
    loadAllData();
    if (patientQueue.isEmpty()) {
      MessageUI.emptyQueue();
      return;
    }

    Iterator<String[]> iterator = patientQueue.iterator();
    String[] servingQueue = new String[]{};
    while (iterator.hasNext()) {
      String[] entry = iterator.next();
      if (entry[2].equals(Status.Serving.toString())) {
        servingQueue = entry;
      }
    }

    iterator = patientQueue.iterator();
    Queue<String[]> waitingQueue = new Queue<>();
    while (iterator.hasNext()) {
      String[] entry = iterator.next();
      if (entry[2].equals(Status.Waiting.toString())) {
        waitingQueue.add(entry);
      }
    }
    
    Utilities.clearScreen();
    queueUI.displayQueueList(waitingQueue, servingQueue);
    Utilities.pressAnyKey();
  }

  public void runCallNext() {
    loadAllData();
    if (patientQueue.isEmpty()) {
      MessageUI.emptyQueue();
      return;
    }

    Queue<String[]> tempQueue = new Queue<>();

    String[] lastServingEntry = null;
    if (patientQueue.peek()[2].equals(Status.Serving.toString())) {
      lastServingEntry = patientQueue.poll();
    }
    
    if (!patientQueue.isEmpty()) {
      String[] nextEntry = patientQueue.poll();
      nextEntry[2] = Status.Serving.toString();
      tempQueue.add(nextEntry);

      while (!patientQueue.isEmpty()) {
        tempQueue.add(patientQueue.poll());
      }
    }
    
    patientQueue = tempQueue;
    queueDAO.writeToFile(patientQueue);
    if (lastServingEntry != null) {
      Appointment appointment = new Appointment();
      appointment.setPatientId(lastServingEntry[1]);
      LocalTime time = appointment.getAppointmentTime();

      time = Utilities.formatTime(String.format("%02d%02d", time.getHour(), time.getMinute()));
      appointment.setAppointmentTime(time);

      ArrayList<String> availableDoctorIds = new ArrayList<>();
      ArrayList<String> allDoctorIds = new ArrayList<>();
      for (Schedule schedule : scheduleList) {
        allDoctorIds.add(schedule.getDoctorId());
        if (schedule.getDay().toString().equalsIgnoreCase(appointment.getAppointmentDate().getDayOfWeek().toString())
            && schedule.getStartTime().isBefore(time) && schedule.getEndTime().isAfter(time)
            && schedule.getIsAvailable()) {
          availableDoctorIds.add(schedule.getDoctorId());
        }
      }

      if (availableDoctorIds.isEmpty()) {
        MessageUI.noAvailableDoctor();
        appointment.setDoctorId(allDoctorIds.get((int) (Math.random() * allDoctorIds.size())));
      } else {
        appointment.setDoctorId(availableDoctorIds.get((int) (Math.random() * availableDoctorIds.size())));
      }

      appointment.setStatus(Status.Approved);
      appointmentMap.put(appointment.getAppointmentId(), appointment);
      appointmentDAO.writeToFile(appointmentMap);
    }

    Utilities.clearScreen();
    MessageUI.patientCalled(patientQueue.peek());
  }

  public void runClearQueue() {
    loadAllData();
    if (patientQueue.isEmpty()) {
      MessageUI.emptyQueue();
      return;
    }

    Utilities.clearScreen();
    char[] inputOptions = queueUI.displayQueueClearance();
    String userInput = queueUI.getGeneralInput();
    boolean valid = QueueValidation.validateCharInput(userInput, inputOptions);

    if (valid) {
      switch (userInput.charAt(0)) {
        case '1':
          patientQueue.clear();
          queueDAO.writeToFile(patientQueue);
          System.out.println();
          System.out.println("Waiting Queue has been cleared.");
          Utilities.pressAnyKey();
          return;
        case '0':
          return;
      }
    }
  }

  public void runStudentQueue(String patientId) {
    do {
      Utilities.clearScreen();
      char[] inputOptions = queueUI.displayStudentQueueMenu();
      String userInput = queueUI.getGeneralInput();
      boolean valid = QueueValidation.validateCharInput(userInput, inputOptions);

      if (valid) {
        switch (userInput.charAt(0)) {
          case '1':
            runGetQueueNumber(patientId);
            continue;
          case '2':
            runViewQueue(patientId);
            continue;
          case '0':
            return;
        }
      }
    } while (true);
  }
  
  public void runGetQueueNumber(String patientId) {
    do {
      Utilities.clearScreen();
      char[] inputOptions = queueUI.displayQueueNumberRegistrationMenu();
      String userInput = queueUI.getGeneralInput();
      boolean valid = QueueValidation.validateCharInput(userInput, inputOptions);

      if (valid) {
        switch (userInput.charAt(0)) {
          case '1':
            String[] lastEntry = patientQueue.peekLast();
            int queueNumber;
            if (lastEntry == null) {
              queueNumber = 0;
            } else {
              queueNumber = Integer.parseInt(lastEntry[0]);
            }
            String[] newEntry = new String[] { String.valueOf(queueNumber + 1), patientId, Status.Waiting.toString() };
            patientQueue.add(newEntry);
            Utilities.clearScreen();
            queueUI.displayQueueNumber(queueNumber + 1);
            queueDAO.writeToFile(patientQueue);
            Utilities.pressAnyKey();
            return;
          case '0':
            return;
        }
      }
    } while (true);
  }

  public void runViewQueue(String patientId) {
    loadAllData();
    if (patientQueue.isEmpty()) {
      MessageUI.emptyQueue();
      return;
    }

    Iterator<String[]> iterator = patientQueue.iterator();
    String[] idQueue = new String[]{};
    String[] servingQueue = new String[]{};
    while (iterator.hasNext()) {
      String[] entry = iterator.next();
      if (entry[2].equals(Status.Serving.toString())) {
        servingQueue = entry;
      }
      if (entry[1].equals(patientId)) {
        idQueue = entry;
        break;
      }
    }

    iterator = patientQueue.iterator();
    Queue<String[]> waitingQueue = new Queue<>();
    while (iterator.hasNext()) {
      String[] entry = iterator.next();
      if (entry[2].equals(Status.Waiting.toString())) {
        waitingQueue.add(entry);
      }
    }
    
    Utilities.clearScreen();
    queueUI.displayCurrentQueue(waitingQueue, servingQueue, idQueue);
    Utilities.pressAnyKey();
  }
}
