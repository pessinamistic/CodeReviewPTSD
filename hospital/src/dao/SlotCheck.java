package dao;

public class SlotCheck{
  Slot slot;
  boolean isAvailable = false;
  boolean isBooked = true;

  public SlotCheck() {
  }

  public SlotCheck(Slot slot, boolean isAvailable) {
    this.slot = slot;
    this.isAvailable = isAvailable;
  }

  public Slot getSlot() {
    return slot;
  }

  public void setSlot(Slot slot) {
    this.slot = slot;
  }

  public boolean isAvailable() {
    return isAvailable;
  }

  public void setAvailable(boolean available) {
    isAvailable = available;
  }

  public boolean isBooked() {
    return isBooked;
  }

  public void setBooked(boolean booked) {
    isBooked = booked;
  }
}
