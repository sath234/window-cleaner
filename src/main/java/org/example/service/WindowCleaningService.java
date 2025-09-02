package org.example.service;

import org.example.model.CustomerBooking;
import org.example.model.Customer;
import org.example.model.Status;

import java.time.LocalDate;
import java.util.List;

/**
 * Booking service interface.
 */
public interface WindowCleaningService {
    /**
     * Adds a customer to the service
     *
     * @param customer the customer to add
     */
    void addCustomer(Customer customer);

    /**
     * Retrieves all customers.
     *
     * @return list of customers
     */
    List<Customer> retrieveCustomers();

    /**
     * Adds a booking to the service.
     *
     * @param customerBooking the booking to add
     */
    void addBooking(CustomerBooking customerBooking);

    /**
     * Retrieves all bookings.
     *
     * @return list of CustomerBookings
     */
    List<CustomerBooking> retrieveCustomerBookings();

    /**
     * Retrieves all bookings for a specific date.
     *
     * @param date the date to check
     * @return list of CustomerBookings
     */
    List<CustomerBooking> retrieveCustomerBookings(LocalDate date);

    /**
     * Calculates windows cleaned on specific date.
     *
     * @param date the date to check
     * @return number of windows cleaned
     */
    int calculateWindowsCleanedOnSpecificDate(LocalDate date);

    /**
     * Calculates windows cleaned on specific date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return number of windows cleaned
     */
    int calculateWindowsCleanedSpecificDateRange(LocalDate startDate, LocalDate endDate);

    /**
     * Calculates total cost for booking.
     *
     * @param bookingNumber the booking number
     * @return total cost
     */
    int calculateTotalCostForBooking(int bookingNumber);

    /**
     *
     * @param date the date to check
     * @return total revenue for date
     */
    int calculateTotalRevenueForDate(LocalDate date);

    /**
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return total revenue for date range
     */
    int calculateTotalRevenueForDateRange(LocalDate startDate, LocalDate endDate);

    /**
     * Updates booking status.
     *
     * @param bookingNumber the booking number
     * @param status the status to update
     */
    void updateBookingStatus(int bookingNumber, Status status);
}
