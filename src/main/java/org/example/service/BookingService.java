package org.example.service;

import java.time.LocalDate;

/**
 * Booking service interface.
 */
public interface BookingService {
    /**
     * Calculates windows cleaned on specific date.
     * @param date the date to check
     * @return number of windows cleaned
     */
    int calculateWindowsCleanedOnSpecificDate(LocalDate date);

    /**
     * Calculates total cost for booking.
     * @param bookingNumber the booking number
     * @return total cost
     */
    int calculateTotalCostForBooking(int bookingNumber);
}
