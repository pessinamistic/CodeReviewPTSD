package parkinglot.src.model;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.stream.IntStream;

public class Parking {
  String name;
  int floors;
  int slotsPerFloor;
  List<BitSet> availableSlots = new ArrayList<>();

  public Parking() {}

  public Parking(String name, int floors, int slotsPerFloor) {
    this.name = name;
    this.floors = floors;
    this.slotsPerFloor = slotsPerFloor;
    IntStream.range(0, 2)
        .forEach(
            i -> {
              availableSlots.add(new BitSet(slotsPerFloor));
            });
  }

  public int getFloors() {
    return floors;
  }

  public Parking setFloors(int floors) {
    this.floors = floors;
    return this;
  }

  public int getSlotsPerFloor() {
    return slotsPerFloor;
  }

  public Parking setSlotsPerFloor(int slotsPerFloor) {
    this.slotsPerFloor = slotsPerFloor;
    return this;
  }

  public String getName() {
    return name;
  }

  public Parking setName(String name) {
    this.name = name;
    return this;
  }

  public List<BitSet> getAvailableSlots() {
    return availableSlots;
  }

  public Parking setAvailableSlots(List<BitSet> availableSlots) {
    this.availableSlots = availableSlots;
    return this;
  }

  @Override
  public String toString() {
    return "Parking{" + "floors=" + floors + ", slotsPerFloor=" + slotsPerFloor + '}';
  }
}
