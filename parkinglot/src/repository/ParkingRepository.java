package parkinglot.src.repository;

import parkinglot.src.model.Parking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingRepository {
  private static final Map<String, Parking> PARKING_LOT_MAP = new HashMap<>();

  public boolean isParkingLotExists(String parkingLotName) {
    return PARKING_LOT_MAP.containsKey(parkingLotName);
  }

  public void addParkingLot(String parkingLotName, Parking parking) {
    PARKING_LOT_MAP.put(parkingLotName, parking);
  }

  public Parking getParkingLot(String parkingLotName) {
    return PARKING_LOT_MAP.get(parkingLotName);
  }

  public List<Parking> getAllParkingLots() {
    return new ArrayList<>(PARKING_LOT_MAP.values());
  }

  public void removeParkingLot(String parkingLotName) {
    PARKING_LOT_MAP.remove(parkingLotName);
  }

}
