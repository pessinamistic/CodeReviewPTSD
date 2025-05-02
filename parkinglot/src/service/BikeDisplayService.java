package parkinglot.src.service;

import java.util.BitSet;
import java.util.List;
import parkinglot.src.model.Parking;
import parkinglot.src.repository.ParkingRepository;
import parkinglot.src.service.impl.DisplayService;

public class BikeDisplayService implements DisplayService {
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
      for (int i = 2; i <= 3; i++) {
        if (!bitSet.get(i)) {
          freeCount++;
        }
      }
      System.out.println("No of free slots for BIKE on floor " + (floor++) + ": " + freeCount);
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
      for (int i = 2; i <= 3; i++) {
        if (!bitSet.get(i)) {
          returnString += i + " ";
        }
      }
      System.out.println(
          "Free slots for BIKE on Floor "
              + (floor++)
              + ": "
              + returnString.trim().replace(" ", ","));
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
      for (int i = 2; i <= 3; i++) {
        if (bitSet.get(i)) {
          returnString += i + " ";
        }
      }
      System.out.println(
          "Occupied Occupied slots for BIKE on Floor "
              + (floor++)
              + ": "
              + returnString.trim().replace(" ", ","));
    }
  }
}
