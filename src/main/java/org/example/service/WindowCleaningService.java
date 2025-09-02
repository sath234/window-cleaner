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
     * Retrieves all bookings for a specific customer.
     *
     * @param customerNumber the customer number
     * @return list of CustomerBookings
     */
    List<CustomerBooking> retrieveCustomerBookings(int customerNumber);

    /**
     * Retrieves all bookings for a specific date range.
     *
     * @param start the start date
     * @param end   the end date
     * @return list of CustomerBookings
     */
    List<CustomerBooking> retrieveCustomerBookings(LocalDate start, LocalDate end);

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
    int calculateCost(int bookingNumber);

    /**
     * Calculates total cost for booking.
     *
     * @param start the start date
     * @param end   the end date
     * @return total cost
     */
    int calculateCost(LocalDate start, LocalDate end);
    
    /**
     * Calculates total revenue for a specific date.
     *
     * @param date the date to check
     * @return total revenue
     */
    int calculateCost(LocalDate date);

    void updateBookingStatus(int bookingNumber, Status status);
}
