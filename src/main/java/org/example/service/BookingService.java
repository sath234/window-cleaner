package org.example.service;

import org.example.model.Booking;
import org.example.model.Customer;

import java.time.LocalDate;

/**
 * Booking service interface.
 */
public interface BookingService {
    /**
     * Adds a customer to the service
     *
     * @param customer the customer to add
     */
    void addCustomer(Customer customer);

    /**
     * Adds a booking to the service.
     *
     * @param booking the booking to add
     */
    void addBooking(Booking booking);

    /**
     * Calculates windows cleaned on specific date.
     *
     * @param date the date to check
     * @return number of windows cleaned
     */
    int calculateWindowsCleanedOnSpecificDate(LocalDate date);

    /**
     * Calculates total cost for booking.
     *
     * @param bookingNumber the booking number
     * @return total cost
     */
    int calculateTotalCostForBooking(int bookingNumber);
}
