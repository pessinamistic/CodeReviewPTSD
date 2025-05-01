package parkinglot.src.model;

public enum Vehicle {
  CAR("Car"),
  BIKE("Bike"),
  TRUCK("Truck");

  private String name;

  Vehicle(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
