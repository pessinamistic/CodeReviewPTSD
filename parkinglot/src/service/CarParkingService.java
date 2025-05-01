package parkinglot.src.service;

import java.util.BitSet;
import java.util.List;
import parkinglot.src.model.Parking;
import parkinglot.src.service.impl.ParkingService;

public class CarParkingService extends ParkingService {

  @Override
  public String checkParkingSlotAvailability(String parkingLotName) {
    if (!parkingRepository.isParkingLotExists(parkingLotName)) return "";

    Parking parking = parkingRepository.getParkingLot(parkingLotName);

    List<BitSet> floors = parking.getAvailableSlots();
    int floorLevel = 1;
    for (BitSet floor : floors) {
      for (int i = 4; i <= parking.getSlotsPerFloor(); i++) {
        if (!floor.get(i)) return (floorLevel) + " " + i;
      }
      floorLevel++;
    }
    return "";
  }
}
