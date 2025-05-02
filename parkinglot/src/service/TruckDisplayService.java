package parkinglot.src.service;

import java.util.BitSet;
import java.util.List;
import parkinglot.src.model.Parking;
import parkinglot.src.repository.ParkingRepository;
import parkinglot.src.service.impl.DisplayService;

public class TruckDisplayService implements DisplayService {
  ParkingRepository parkingRepository = new ParkingRepository();

  public void getFreeCount(String parkingLotName) {
    Parking parking = parkingRepository.getParkingLot(parkingLotName);
    if (parking == null) {
      System.out.println("Parking lot not found");
      return;
    }

    int freeCount = 0;
    List<BitSet> availableSlots = parking.getAvailableSlots();
    int floor = 1;
    for (BitSet bitSet : availableSlots) {
      if (!bitSet.get(1)) {
        freeCount++;
      }
      System.out.println("No of free slots for TRUCK on floor " + (floor++) + ": " + freeCount);
      freeCount = 0;
    }
  }

  @Override
  public void getFreeSlots(String parkingLotName) {
    Parking parking = parkingRepository.getParkingLot(parkingLotName);
    if (parking == null) {
      System.out.println("Parking lot not found");
      return;
    }

    int floor = 1;
    List<BitSet> availableSlots = parking.getAvailableSlots();
    for (BitSet bitSet : availableSlots) {
      String returnString = "";
      if (!bitSet.get(1)) {
        returnString += "1 ";
      }
      System.out.println(
              "Free slots for TRUCK on Floor " + (floor++) + ": " + returnString.trim().replace(" ", ","));
    }
  }

  @Override
  public void getOccupiedSlots(String parkingLotName) {
    Parking parking = parkingRepository.getParkingLot(parkingLotName);
    if (parking == null) {
      System.out.println("Parking lot not found");
      return;
    }

    int floor = 1;
    List<BitSet> availableSlots = parking.getAvailableSlots();
    for (BitSet bitSet : availableSlots) {
      String returnString = "";
      if (bitSet.get(1)) {
        returnString += "1 ";
      }
      System.out.println(
              "Occupied Occupied  slots for TRUCK on Floor "
                      + (floor++)
                      + ": "
                      + returnString.trim().replace(" ", ","));
    }
  }
}
