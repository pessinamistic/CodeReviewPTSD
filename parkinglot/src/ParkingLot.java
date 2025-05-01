package parkinglot.src;

import java.util.Scanner;

import parkinglot.src.model.Booking;
import parkinglot.src.model.ParkingCommands;
import parkinglot.src.model.Vehicle;
import parkinglot.src.repository.BookingRepository;
import parkinglot.src.service.BikeParkingService;
import parkinglot.src.service.CarParkingService;
import parkinglot.src.service.TruckParkingService;
import parkinglot.src.service.impl.DisplayService;
import parkinglot.src.service.impl.ParkingService;

public class ParkingLot {
  Scanner scanner = new Scanner(System.in);

  VehicleFactory vehicleFactory = new VehicleFactory();
  CarParkingService carParkingService = new CarParkingService();
  BookingRepository bookingRepository = new BookingRepository();

  public static void main(String[] args) {
    ParkingLot parkingLot = new ParkingLot();
    parkingLot.driveParkingService();
  }

  private void driveParkingService() {
    while (true) {
      String command = scanner.nextLine();

      if (command.startsWith("exit")) {
        System.out.println("Exiting the program.");
        break;
      }

      String[] userInput = command.split(" ");
      ParkingCommands parkingCommand = ParkingCommands.valueOf(userInput[0].toUpperCase());

      switch (parkingCommand) {
        case CREATE_PARKING_LOT ->
            carParkingService.createParkingLot(
                userInput[1], Integer.parseInt(userInput[2]), Integer.parseInt(userInput[3]));
        case DISPLAY -> this.handleDisplayCommand(userInput);
        case PARK_VEHICLE -> this.handleParkCommand(userInput);
        case UNPARK_VEHICLE -> this.handleUnparkCommand(userInput);
      }
    }
  }

  private void handleUnparkCommand(String[] userInput) {
    String ticketNumber = userInput[2];
    if (!bookingRepository.isBookingExists(ticketNumber)) {
      System.out.println("No booking found with the given ticket number");
      return;
    }
    carParkingService.releaseParkingSlot(ticketNumber);
  }

  private void handleParkCommand(String[] userInput) {
    String parkingLotName = userInput[1];
    String vehicleString = userInput[2];
    Vehicle vehicle = Vehicle.valueOf(vehicleString.toUpperCase());
    ParkingService parkingService = vehicleFactory.getParkingService(vehicle);
    String[] slotAvailability =
            parkingService.checkParkingSlotAvailability(parkingLotName).split(" ");
    if (slotAvailability.length == 2) {
      parkingService.bookParkingSlot(
              parkingLotName,
              userInput[3],
              Integer.parseInt(slotAvailability[0]),
              Integer.parseInt(slotAvailability[1]),
              userInput[4]);
    } else {
      System.out.println("No parking slots available for " + vehicleString);
    }
  }

  private void handleDisplayCommand(String[] userInput) {
    String displayCommand = userInput[1];
    if (ParkingCommands.isDisplayCommand(displayCommand)) {
      String vehicleString = userInput[3];
      Vehicle vehicle = Vehicle.valueOf(vehicleString.toUpperCase());

      DisplayService displayService = vehicleFactory.getDisplayService(vehicle);
      if (displayService != null) {
        executeDisplayCommand(displayService, displayCommand, userInput[2]);
      } else {
        System.out.println("Invalid vehicle type");
      }
    }
  }

  private void executeDisplayCommand(
      DisplayService displayService, String displayCommand, String parkingLotName) {
    switch (displayCommand) {
      case "free_count" -> displayService.getFreeCount(parkingLotName);
      case "free_slots" -> displayService.getFreeSlots(parkingLotName);
      case "occupied_slots" -> displayService.getOccupiedSlots(parkingLotName);
      default -> System.out.println("Invalid display command");
    }
  }
}
