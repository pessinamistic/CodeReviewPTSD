package service;

import dao.Appointment;
import dao.Patient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static service.AppointmentService.APPOINTMENT_MAP;

public class PatientService {

  static final Map<String, Patient> PATIENT_MAP = new HashMap<>();

  public static Patient getPatient(String name){
    if (PATIENT_MAP.containsKey(name)){
      return PATIENT_MAP.get(name);
    }
    System.out.println("Patient not found");
    return null;
  }

  public void addPatient(String patientName) {
    PATIENT_MAP.put(patientName, Patient.builder().name(patientName).build());
    System.out.println(patientName + " registered successfully!");
  }
}
