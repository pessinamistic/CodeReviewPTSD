package parkinglot.src;

import java.util.Map;
import parkinglot.src.model.Vehicle;
import parkinglot.src.service.BikeDisplayService;
import parkinglot.src.service.BikeParkingService;
import parkinglot.src.service.CarDisplayService;
import parkinglot.src.service.CarParkingService;
import parkinglot.src.service.impl.DisplayService;
import parkinglot.src.service.impl.ParkingService;
import parkinglot.src.service.TruckDisplayService;
import parkinglot.src.service.TruckParkingService;

public class VehicleFactory {

  CarDisplayService carDisplayService = new CarDisplayService();
  BikeDisplayService bikeDisplayService = new BikeDisplayService();
  TruckDisplayService truckDisplayService = new TruckDisplayService();

  CarParkingService carParkingService = new CarParkingService();
  BikeParkingService bikeParkingService = new BikeParkingService();
  TruckParkingService truckParkingService = new TruckParkingService();

  Map<Vehicle, DisplayService> vehicleDisplayServiceMap =
      Map.of(
          Vehicle.CAR, carDisplayService,
          Vehicle.BIKE, bikeDisplayService,
          Vehicle.TRUCK, truckDisplayService);

  Map<Vehicle, ParkingService> vehicleParkingServiceMap =
      Map.of(
          Vehicle.CAR, carParkingService,
          Vehicle.BIKE, bikeParkingService,
          Vehicle.TRUCK, truckParkingService);

  public DisplayService getDisplayService(Vehicle vehicle) {
    return vehicleDisplayServiceMap.get(vehicle);
  }

  public ParkingService getParkingService(Vehicle vehicle) {
    return vehicleParkingServiceMap.get(vehicle);
  }
}
