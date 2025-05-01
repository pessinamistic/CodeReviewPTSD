package parkinglot.src.service.impl;

import java.util.BitSet;
import parkinglot.src.model.Booking;
import parkinglot.src.model.Parking;
import parkinglot.src.repository.BookingRepository;
import parkinglot.src.repository.ParkingRepository;

public abstract class ParkingService {
  protected ParkingRepository parkingRepository = new ParkingRepository();
  protected BookingRepository bookingRepository = new BookingRepository();

  public void createParkingLot(String parkingLotName, int floors, int slotsPerFloor) {
    if (parkingRepository.isParkingLotExists(parkingLotName)) {
      System.out.println("Parking lot already exists");
      return;
    }

    if (floors <= 0 || slotsPerFloor <= 0) {
      System.out.println("Invalid parking lot dimensions");
      return;
    }

    Parking parking = new Parking(parkingLotName, floors, slotsPerFloor);
    parkingRepository.addParkingLot(parkingLotName, parking);
    System.out.println(
        "Created a parking lot "
            + parkingLotName
            + " with "
            + floors
            + " floors and "
            + slotsPerFloor
            + " slots per floor");
  }

  public String generateTicketNumber(String parkingLotName, String floor, String slot) {
    return String.format("%s_%s_%s", parkingLotName, floor, slot);
  }

  public void bookParkingSlot(
      String parkingLotName, String vehicleNumber, int floor, int slot, String color) {
    Parking parking = parkingRepository.getParkingLot(parkingLotName);
    String ticketNumber =
        generateTicketNumber(parkingLotName, String.valueOf(floor), String.valueOf(slot));

    floor--;
    BitSet floorSlots = parking.getAvailableSlots().get(floor);
    floorSlots.set(slot);
    Booking booking = new Booking(ticketNumber, vehicleNumber, parking, floor, slot, color);
    bookingRepository.addBooking(ticketNumber, booking);
    System.out.println("Parked vehicle. Ticket ID: " + ticketNumber);
  }

  public void releaseParkingSlot(String ticketNumber) {

    Booking booking = bookingRepository.getBooking(ticketNumber);
    Parking parking = booking.getParking();
    int floor = booking.getFloor();
    int slot = booking.getSlot();

    BitSet floorSlots = parking.getAvailableSlots().get(floor);
    floorSlots.clear(slot);

    bookingRepository.removeBooking(ticketNumber);
    System.out.println(
        "Unparked vehicle with Registration Number: "
            + booking.getVehicleNumber()
            + " and Color: "
            + booking.getColor());
  }

  public abstract String checkParkingSlotAvailability(String parkingLotName);
}
