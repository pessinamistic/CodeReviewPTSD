package parkinglot.src.service;

import java.util.BitSet;
import java.util.List;
import parkinglot.src.model.Parking;
import parkinglot.src.repository.ParkingRepository;
import parkinglot.src.service.impl.ParkingService;

public class TruckParkingService extends ParkingService {

  @Override
  public String checkParkingSlotAvailability(String parkingLotName) {
    if (!parkingRepository.isParkingLotExists(parkingLotName)) return "";

    Parking parking = parkingRepository.getParkingLot(parkingLotName);

    List<BitSet> floors = parking.getAvailableSlots();
    int floorLevel = 1;
    for (BitSet floor : floors) {
      if (!floor.get(1)) return floorLevel + " " + 1;
    }
    return "";
  }

}
