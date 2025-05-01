package hospital.src.dao;

import hospital.src.enums.Speciality;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Doctor extends User {
  private long doctorId;
  private Speciality speciality;
  private List<Slot> availableSlots = new ArrayList<>();
  private List<Slot> bookedSlots = new ArrayList<>();
  private List<Appointment> waitList = new ArrayList<>();

  public Doctor(String name, Speciality speciality) {
    super(name);
    this.speciality = speciality;
    this.doctorId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
  }

  public static DoctorBuilder builder() {
    return new DoctorBuilder();
  }

  public Speciality getSpeciality() {
    return speciality;
  }

  public void setSpeciality(Speciality speciality) {
    this.speciality = speciality;
  }

  public List<Slot> getAvailableSlots() {
    return availableSlots;
  }

  public void setAvailableSlots(List<Slot> availableSlots) {
    this.availableSlots = availableSlots;
    sortArray(availableSlots);
  }

  public void addAvailableSlot(Slot slot) {
    this.availableSlots.add(slot);
    sortArray(availableSlots);
  }

  private void sortArray(List<Slot> availableSlots) {
    Arrays.sort(
        availableSlots.toArray(),
        (a, b) -> {
          Slot slot1 = (Slot) a;
          Slot slot2 = (Slot) b;
          return slot1.getStartTime().compareTo(slot2.getStartTime());
        });
  }

  public List<Slot> getBookedSlots() {
    return bookedSlots;
  }

  public void setBookedSlots(List<Slot> bookedSlots) {
    this.bookedSlots = bookedSlots;
  }

  public long getDoctorId() {
    return doctorId;
  }

  public void setDoctorId(long doctorId) {
    this.doctorId = doctorId;
  }

  public List<Appointment> getWaitList() {
    return waitList;
  }

  public void setWaitList(List<Appointment> waitList) {
    this.waitList = waitList;
  }

  @Override
  public String toString() {
    return "Doctor{"
        + "doctorId="
        + doctorId
        + ", name="
        + getName()
        + ", speciality="
        + speciality.getName()
        + ", availableSlots="
        + availableSlots
        + ", bookedSlots="
        + bookedSlots
        + ", waitList="
        + waitList
        + ", email="
        + getEmail()
        + ", phoneNumber="
        + getPhoneNumber()
        + ", address="
        + getAddress()
        + '}';
  }

  public static class DoctorBuilder {
    private String name;
    private Speciality speciality;

    public DoctorBuilder() {}

    public DoctorBuilder name(String name) {
      this.name = name;
      return this;
    }

    public DoctorBuilder speciality(Speciality speciality) {
      this.speciality = speciality;
      return this;
    }

    public Doctor build() {
      return new Doctor(this.name, this.speciality);
    }

    @Override
    public String toString() {
      return "DoctorBuilder{" + "name='" + name + '\'' + ", speciality='" + speciality + '\'' + '}';
    }
  }
}
