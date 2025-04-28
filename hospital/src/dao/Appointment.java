package dao;

import java.util.UUID;

public class Appointment {
  String bookingId;
  Slot slot;
  Doctor doctor;
  Patient patient;

  public Appointment() {
  }

  public Appointment(Slot slot, Doctor doctor, Patient patient) {
    this.slot = slot;
    this.doctor = doctor;
    this.patient = patient;
    this.bookingId = UUID.randomUUID().toString();
  }

  public String getBookingId() {
    return bookingId;
  }

  public void setBookingId(String bookingId) {
    this.bookingId = bookingId;
  }

  public Slot getSlot() {
    return slot;
  }

  public void setSlot(Slot slot) {
    this.slot = slot;
  }

  public Doctor getDoctor() {
    return doctor;
  }

  public void setDoctor(Doctor doctor) {
    this.doctor = doctor;
  }

  public Patient getPatient() {
    return patient;
  }

  public void setPatient(Patient patient) {
    this.patient = patient;
  }

  @Override
  public String toString() {
    return "Appointment{" +
            "slot=" + slot +
            ", doctor=" + doctor +
            ", patient=" + patient +
            '}';
  }
}
