package dao;

import java.util.ArrayList;
import java.util.List;

public class Patient extends User {
  private int age;
  List<Appointment> bookedSlots;

  public Patient() {
  }

  public Patient(String name, int age) {
    super(name);
    this.age = age;
    this.bookedSlots = new ArrayList<>();
  }

  public static PatientBuilder builder() {
    return new PatientBuilder();
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public List<Appointment> getBookedSlots() {
    return bookedSlots;
  }

  public void setBookedSlots(List<Appointment> bookedSlots) {
    this.bookedSlots = bookedSlots;
  }

  @Override
  public String toString() {
    return "Patient{" +
            "name='" + getName() + '\'' +
            "age=" + age +
            "} ";
  }

  public static class PatientBuilder {
    private String name;
    private int age;

    public PatientBuilder() {
    }

    public PatientBuilder name(String name) {
      this.name = name;
      return this;
    }

    public PatientBuilder age(int age) {
      this.age = age;
      return this;
    }

    public Patient build() {
      return new Patient(name, age);
    }
  }
}
