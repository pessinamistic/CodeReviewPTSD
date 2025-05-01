package hospital.src.dao;

import java.time.LocalTime;
import java.util.ArrayDeque;
import java.util.Queue;

public class Slot {
  private LocalTime startTime;
  private LocalTime endTime;
  private boolean isAvailable = true;
  Queue<Appointment> waitingQueue = new ArrayDeque<>();

  public Slot() {}

  public Slot(LocalTime startTime, LocalTime endTime, boolean isAvailable) {
    this.startTime = startTime;
    this.endTime = endTime;
    this.isAvailable = isAvailable;
  }

  public static SlotBuilder builder() {
    return new SlotBuilder();
  }

  public LocalTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalTime startTime) {
    this.startTime = startTime;
  }

  public LocalTime getEndTime() {
    return endTime;
  }

  public void setEndTime(LocalTime endTime) {
    this.endTime = endTime;
  }

  public boolean isAvailable() {
    return isAvailable;
  }

  public void setAvailable(boolean available) {
    isAvailable = available;
  }

  public static class SlotBuilder {
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isAvailable = true;

    public SlotBuilder startTime(LocalTime startTime) {
      this.startTime = startTime;
      return this;
    }

    public SlotBuilder endTime(LocalTime endTime) {
      this.endTime = endTime;
      return this;
    }

    public SlotBuilder isAvailable(boolean isAvailable) {
      this.isAvailable = isAvailable;
      return this;
    }

    public Slot build() {
      return new Slot(startTime, endTime, isAvailable);
    }

    @Override
    public String toString() {
      return "SlotBuilder{"
          + "startTime="
          + startTime
          + ", endTime="
          + endTime
          + ", isAvailable="
          + isAvailable
          + '}';
    }
  }
}
