package parkinglot.src.service.impl;

public interface DisplayService {
  void getFreeCount(String parkingLotName);
  void getFreeSlots(String parkingLotName);
  void getOccupiedSlots(String parkingLotName);
}
