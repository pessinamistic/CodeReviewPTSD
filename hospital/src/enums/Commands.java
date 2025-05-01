package hospital.src.enums;

public enum Commands {
  REGISTER_DOC("registerDoc"),
  MARK_DOC_AVAIL("markDocAvail"),
  SHOW_AVAIL_BY_SPECIALITY("showAvailBySpeciality"),
  GET_WAIT_LIST("getWaitList"),
  REGISTER_PATIENT("registerPatient"),
  BOOK_APPOINTMENT("bookAppointment"),
  CANCEL_BOOKING_ID("cancelBookingId"),
  SHOW_APPOINTMENTS_BOOKED("showAppointmentsBooked");

  private String name;

  Commands(String name) {
    this.name = name;
  }

  public static Commands getCommand(String command) {
    if (command.contains(":")) {
      command = command.substring(0, command.indexOf(":"));
    }
    if (command.startsWith("showAppointmentsBooked") || command.startsWith("getWaitList")) {
      command = command.substring(0, command.indexOf("("));
    }
    for (Commands cmd : Commands.values()) {
      if (cmd.name.equalsIgnoreCase(command)) {
        return cmd;
      }
    }
    return null; // or throw an exception if command is not found
  }

  public String getName() {
    return name;
  }
}
