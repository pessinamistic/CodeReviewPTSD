package parkinglot.src.model;

public class Booking {
  private String tickerNumber;
  private String vehicleNumber;
  private Parking parking;
  private int floor;
  private int slot;
  private String color;

  public Booking() {}

  public Booking(
      String tickerNumber,
      String vehicleNumber,
      Parking parking,
      int floor,
      int slot,
      String color) {
    this.tickerNumber = tickerNumber;
    this.vehicleNumber = vehicleNumber;
    this.parking = parking;
    this.floor = floor;
    this.slot = slot;
    this.color = color;
  }

  public String getTickerNumber() {
    return tickerNumber;
  }

  public Booking setTickerNumber(String tickerNumber) {
    this.tickerNumber = tickerNumber;
    return this;
  }

  public String getVehicleNumber() {
    return vehicleNumber;
  }

  public Booking setVehicleNumber(String vehicleNumber) {
    this.vehicleNumber = vehicleNumber;
    return this;
  }

  public Parking getParking() {
    return parking;
  }

  public Booking setParking(Parking parking) {
    this.parking = parking;
    return this;
  }

  public int getFloor() {
    return floor;
  }

  public Booking setFloor(int floor) {
    this.floor = floor;
    return this;
  }

  public int getSlot() {
    return slot;
  }

  public Booking setSlot(int slot) {
    this.slot = slot;
    return this;
  }

  public String getColor() {
    return color;
  }

  public Booking setColor(String color) {
    this.color = color;
    return this;
  }

  @Override
  public String toString() {
    return "Booking{"
        + "tickerNumber='"
        + tickerNumber
        + '\''
        + ", vehicleNumber='"
        + vehicleNumber
        + '\''
        + ", parking="
        + parking
        + ", floor="
        + floor
        + ", slot="
        + slot
        + ", color='"
        + color
        + '\''
        + '}';
  }
}
