package service;

import dao.Appointment;
import dao.Doctor;
import dao.Patient;
import dao.Slot;
import dao.SlotCheck;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppointmentService {
  static final Map<String, Appointment> APPOINTMENT_MAP = new HashMap<>();
  PatientService patientService = new PatientService();

  // If patient is null appointment should not handle
  public void bookAppointment(String[] userInput) {
    String patientId = userInput[1].substring(1, userInput[1].length() - 1);
    String doctorId = userInput[2].substring(0, userInput[2].length() - 1);
    LocalTime slotTime = LocalTime.parse(userInput[3].substring(0, userInput[3].length() - 1));
    Doctor doctor = DoctorService.getDoctor(doctorId);
    Patient patient = PatientService.getPatient(patientId);
    if (patient == null){
      patientService.addPatient(patientId);
      patient = PatientService.getPatient(patientId);
    }
    if (!checkPatientAvailability(patient, slotTime)){
      System.out.println("Slot not available at Patient's end");
      return;
    }
    SlotCheck slotCheck = isSlotAvailable(doctor, slotTime);
    Slot slot = slotCheck.getSlot();
    Appointment appointment = new Appointment(slot, doctor, patient);
    if (slotCheck.isBooked()){
      if (userInput.length == 5){
        String[] split = userInput[4].substring(0, userInput[4].length() - 1).split("=");
        boolean isWaitList = Boolean.parseBoolean(split[1]);
        if (isWaitList){
          doctor.getWaitList().add(appointment);
          System.out.println("Added to the waitlist. Booking id: " + appointment.getBookingId());
          return;
        }
      }
      System.out.println("Slot not available at Doctor's end");
      return;
    }
    slot.setAvailable(false);
    doctor.getAvailableSlots().remove(slot);
    doctor.getBookedSlots().add(slot);
    patient.getBookedSlots().add(appointment);
    APPOINTMENT_MAP.put(appointment.getBookingId(), appointment);
    System.out.println("Appointment booked successfully! Booking ID: " + appointment.getBookingId());
  }

  private boolean checkPatientAvailability(Patient patient, LocalTime slotTime) {
    List<Appointment> bookedSlots = patient.getBookedSlots();
    for (Appointment entry : bookedSlots) {
      Slot slot = entry.getSlot();
      LocalTime startTime = slot.getStartTime();
      if (slotTime.equals(startTime) && !slot.isAvailable()){
        return false;
      }
    }
    return true;
  }

  private SlotCheck isSlotAvailable(Doctor doctor, LocalTime slotTime) {
    SlotCheck slotCheck = new SlotCheck();

    for (Slot slot : doctor.getAvailableSlots()){
      LocalTime startTime = slot.getStartTime();
      if (slotTime.equals(startTime)){
        slotCheck.setSlot(slot);
        slotCheck.setAvailable(true);
        slotCheck.setBooked(false);
        return slotCheck;
      }
    }

    for (Slot slot : doctor.getBookedSlots()){
      LocalTime startTime = slot.getStartTime();
      if (slotTime.equals(startTime)){
        slotCheck.setSlot(slot);
        slotCheck.setAvailable(false);
        slotCheck.setBooked(true);
        return slotCheck;
      }
    }
    return slotCheck;
  }

  public void cancelAppointment(String[] userInput) {
    String bookingId = userInput[1];
    Appointment appointmentDetails = getAppointmentDetails(bookingId);
    if (appointmentDetails == null) {
      System.out.println("Booking ID not found");
      return;
    }
    Slot slot = appointmentDetails.getSlot();
    Doctor doctor = appointmentDetails.getDoctor();
    Patient patient = appointmentDetails.getPatient();
    if (doctor.getBookedSlots().contains(slot)){
      doctor.getBookedSlots().remove(slot);
      doctor.addAvailableSlot(slot);
      slot.setAvailable(true);
    }
    patient.getBookedSlots().remove(appointmentDetails);
    APPOINTMENT_MAP.remove(bookingId);
    System.out.println("Booking Cancelled :" + bookingId);
    checkWaitList(doctor, slot);
  }

  private void checkWaitList(Doctor doctor, Slot slot) {
    List<Appointment> waitList = doctor.getWaitList();
    if (waitList.isEmpty()){
      System.out.println("No one in the waitlist");
      return;
    }
    for (Appointment appointment : waitList){
      if (appointment.getSlot().equals(slot) && slot.isAvailable()){
        doctor.getBookedSlots().add(slot);
        doctor.getAvailableSlots().remove(slot);
        Patient patient = appointment.getPatient();
        patient.getBookedSlots().add(appointment);
        doctor.getWaitList().remove(appointment);
        System.out.println("Booking ID: " + appointment.getBookingId() + " moved from waitlist to booked");
        return;
      }
    }
  }


  public static Appointment getAppointmentDetails(String bookingId){
    if (APPOINTMENT_MAP.containsKey(bookingId)){
      return APPOINTMENT_MAP.get(bookingId);
    }
    return null;
  }



  public void showAppointmentsBooked(String[] userInput) {
    String command = userInput[0];
    String patientName = command.substring(command.indexOf("(") + 1, command.indexOf(")"));
    Patient patient = PatientService.getPatient(patientName);
    if (patient == null) {
      System.out.println("Patient not found");
      return;
    }
    List<Appointment> bookedSlots = patient.getBookedSlots();
    if (bookedSlots.isEmpty()) {
      System.out.println("No appointments booked");
      return;
    }
    for (Appointment appointment : bookedSlots){
      System.out.println("Booking id: " + appointment.getBookingId() + ", Dr " + appointment.getDoctor().getName() + " " + appointment.getSlot().getStartTime());
    }
  }

}
