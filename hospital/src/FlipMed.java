package hospital.src;

import enums.Commands;
import service.AppointmentService;
import service.DoctorService;
import exception.InvalidCommandException;
import service.PatientService;

import java.util.Scanner;

public class FlipMed {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    DoctorService doctorService = new DoctorService();
    PatientService patientService = new PatientService();
    AppointmentService appointmentService = new AppointmentService();

    System.out.println("Welcome to the Hospital Management System");
    while (true) {
      try {
        String command = scanner.nextLine();
        if (command.equalsIgnoreCase("exit")) {
          System.out.println("Exiting the system. Goodbye!");
          break;
        }

        String[] userInput = command.split(" ");

        Commands commandEnum = Commands.getCommand(userInput[0]);
        if (commandEnum == null) {
          throw new InvalidCommandException("Invalid command. Please try again." + commandEnum);
        }
        switch (commandEnum) {
          case REGISTER_DOC -> {
            doctorService.addDoctor(userInput);
          }
          case MARK_DOC_AVAIL -> {
            doctorService.addSlots(userInput);
          }
          case REGISTER_PATIENT -> {
            patientService.addPatient(userInput[1]);
          }
          case SHOW_AVAIL_BY_SPECIALITY -> {
            doctorService.showAvailableDoctors(userInput);
          }
          case BOOK_APPOINTMENT -> {
            appointmentService.bookAppointment(userInput);
          }
          case GET_WAIT_LIST -> {
            doctorService.getWaitList(userInput);
          }
          case CANCEL_BOOKING_ID -> {
            appointmentService.cancelAppointment(userInput);
          }
          case SHOW_APPOINTMENTS_BOOKED -> {
            appointmentService.showAppointmentsBooked(userInput);
          }
          default -> throw new InvalidCommandException("Unexpected value: " + commandEnum);
        }
      } catch (Exception e) {
        System.out.println("Exception Caught : " + e.getMessage());
      }
    }
  }
}