package parkinglot.src.repository;

import java.util.HashMap;
import java.util.Map;
import parkinglot.src.model.Booking;

public class BookingRepository {
  private static final Map<String, Booking> BOOKING_MAP = new HashMap<>();

  public void addBooking(String ticketNumber, Booking booking) {
    BOOKING_MAP.put(ticketNumber, booking);
  }

  public Booking getBooking(String ticketNumber) {
    return BOOKING_MAP.get(ticketNumber);
  }

  public void removeBooking(String ticketNumber) {
    BOOKING_MAP.remove(ticketNumber);
  }

  public boolean isBookingExists(String ticketNumber) {
    return BOOKING_MAP.containsKey(ticketNumber);
  }
}
