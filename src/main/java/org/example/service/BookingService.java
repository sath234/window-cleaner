package org.example.service;

import org.example.model.CustomerBooking;
import org.example.model.Customer;

import java.time.LocalDate;
import java.util.List;

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
