package service;

import dao.Appointment;
import dao.Doctor;
import dao.Slot;
import enums.Speciality;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DoctorService {
  static final Map<String, Doctor> DOCTORS_MAP = new HashMap<>();

  public static Doctor getDoctor(String name) {
    return DOCTORS_MAP.get(name.replace("Dr.",""));
  }

  public void addDoctor(String[] input) {
    String name = input[2];
    String speciality = input[4];
    Doctor doctor = Doctor.builder()
            .name(name)
            .speciality(Speciality.valueOf(speciality.toUpperCase()))
            .build();
    DOCTORS_MAP.put(name, doctor);
    welcomeMessage(doctor);
  }

  private void welcomeMessage(Doctor doctor) {
    System.out.println("Welcome Dr." + doctor.getName() + "!!");
  }

  public void addSlots(String[] userInput) {
    String name = userInput[1];
    if (!doesDoctorExists(name)) {
      return;
    }
    List<Slot> slots = getSlots(userInput);
    if (!verifySlots(name, slots)) {
      return;
    }
    Doctor doctor = DOCTORS_MAP.get(name);
    doctor.setAvailableSlots(slots);
    System.out.println("Done Doc!");
  }

  private boolean doesDoctorExists(String name) {
    if (DOCTORS_MAP.containsKey(name)) {
      return true;
    }
    System.out.println("Doctor not found");
    return false;
  }

  private List<Slot> getSlots(String[] userInput) {
    List<Slot> slots = new ArrayList<>();
    for (int i = 2; i < userInput.length; i++) {
      String slot = userInput[i];
      String[] split = slot.split("-");
      int startTimeHour = Integer.parseInt(split[0].split(":")[0]);
      int startTimeMinute = Integer.parseInt(split[0].split(":")[1]);
      LocalTime start = LocalTime.of(startTimeHour, startTimeMinute);

      int endTimeHour = Integer.parseInt(split[1].split(":")[0]);
      String s = split[1].split(":")[1].contains(",") ? split[1].split(":")[1].split(",")[0] : split[1].split(":")[1];
      int endTimeMinute = Integer.parseInt(s);
      LocalTime end = LocalTime.of(endTimeHour, endTimeMinute);
      slots.add(Slot.builder().startTime(start).endTime(end).isAvailable(true).build());
    }
    return slots;
  }

  // TODO this should be done while input
  private boolean verifySlots(String name, List<Slot> slots) {
    for (Slot slot : slots) {
      if (!slot.isAvailable()) {
        System.out.println("Slot is not available");
        return false;
      }
      if (slot.getStartTime().isAfter(slot.getEndTime())) {
        System.out.println("Invalid slot: " + slot);
        return false;
      }
      if (Duration.between(slot.getStartTime(), slot.getEndTime()).toMinutes() > 30L) {
        System.out.println("Sorry Dr. " + name + " slots are 30 mins only");
        return false;
      }
    }
    return true;
  }

  public void showAvailableDoctors(String[] userInput) {
    Speciality speciality = Speciality.valueOf(userInput[1].toUpperCase());
    Set<Doctor> availableDoctors = DOCTORS_MAP.values()
            .stream()
            .filter(doctor -> doctor.getSpeciality().equals(speciality))
            .collect(Collectors.toSet());
    if (availableDoctors.isEmpty()) {
      System.out.println("No doctors available for this speciality : " + speciality.getName());
    }
    for (Doctor doctor : availableDoctors) {
      List<Slot> slots = doctor.getAvailableSlots();
      for (Slot slot : slots) {
        if (slot.isAvailable()) {
          System.out.println("Dr." + doctor.getName() + " (" + slot.getStartTime() + "-" + slot.getEndTime() + ")");
        }
      }
    }
  }

  public void getWaitList(String[] userInput) {
    String command = userInput[0];
    String doctorName = command.substring(command.indexOf("(") + 1, command.indexOf(")"));
    Doctor doctor = DoctorService.getDoctor(doctorName);
    if (doctor == null) {
      System.out.println("Doctor not found");
      return;
    }
    List<Appointment> waitList = doctor.getWaitList();
    for (Appointment appointment : waitList){
      System.out.println("WaitList Booking id: " + appointment.getBookingId() + ", patient " + appointment.getPatient().getName() + " " + appointment.getSlot().getStartTime());
    }
  }
}
