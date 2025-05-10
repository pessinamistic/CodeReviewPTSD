package parkinglot.src.model;

import java.util.Arrays;

public enum ParkingCommands {
  CREATE_PARKING_LOT("create_parking_lot"),
  PARK_VEHICLE("park_vehicle"),
  UNPARK_VEHICLE("unpark_vehicle"),
  DISPLAY("display");

  private final String name;

  ParkingCommands(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public static boolean isDisplayCommand(String command) {
    return Arrays.stream(Display.values())
        .anyMatch(display -> display.getName().equalsIgnoreCase(command));
  }
}

enum Display {
  FREE_COUNT("free_count"),
  FREE_SLOTS("free_slots"),
  OCCUPIED_SLOTS("occupied_slots");

  private final String name;

  Display(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
