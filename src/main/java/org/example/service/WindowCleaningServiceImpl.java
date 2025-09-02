package org.example.service;

import java.time.LocalDate;
import java.util.*;

import org.example.model.CustomerBooking;
import org.example.model.Customer;
import org.example.model.Status;
import org.example.utils.ValidationUtil;

/**
 * Implementation of BookingService.
 */
public final class WindowCleaningServiceImpl implements WindowCleaningService {

    /**
     * Cost per property.
     */
    public static final int COST_PER_PROPERTY = 5;
    /**
     * LocalDate object name.
     */
    public static final String LOCAL_DATE_OBJECT_NAME = "LocalDate";
    /**
     * Booking object name.
     */
    public static final String BOOKING_OBJECT_NAME = "Booking";
    /**
     * Customer object name.
     */
    public static final String CUSTOMER_OBJECT_NAME = "Customer";
    /**
     * List of bookings.
     */
    private final Map<Integer, CustomerBooking> customerBookings;
    /**
     * Map of customers by number.
     */
    private final Map<Integer, Customer> customers;

    /**
     * Constructor.
     */
    public WindowCleaningServiceImpl() {
        this.customerBookings = new HashMap<>();
        this.customers = new HashMap<>();
    }

    @Override
    public void addCustomer(final Customer customer) {
        ValidationUtil.checkObjectIsNotNull(customer, CUSTOMER_OBJECT_NAME);
        ValidationUtil.checkDuplicateKeyInMap(customers, customer.getCustomerNumber(), "Customer");

        customers.put(customer.getCustomerNumber(), customer);
    }

    @Override
    public List<Customer> retrieveCustomers() {
        return customers.values().stream().toList();
    }

    @Override
    public void addBooking(final CustomerBooking customerBooking) {
        ValidationUtil.checkObjectIsNotNull(customerBooking, BOOKING_OBJECT_NAME);
        ValidationUtil.checkDateNotInPast(customerBooking.getBookingDate());
        ValidationUtil.checkDuplicateKeyInMap(customerBookings, customerBooking.getBookingNumber(), "CustomerBooking");

        customerBooking.setStatus(Status.SCHEDULED);

        customerBookings.put(customerBooking.getBookingNumber(), customerBooking);
    }

    @Override
    public List<CustomerBooking> retrieveCustomerBookings() {
        return customerBookings.values().stream().toList();
    }

    @Override
    public List<CustomerBooking> retrieveCustomerBookings(int customerNumber) {
        return customerBookings.values()
                .stream()
                .filter(b -> b.getCustomerNumber() == customerNumber)
                .toList();
    }

    @Override
    public List<CustomerBooking> retrieveCustomerBookings(LocalDate start, LocalDate end) {
        ValidationUtil.checkObjectIsNotNull(start, LOCAL_DATE_OBJECT_NAME + " start");
        ValidationUtil.checkObjectIsNotNull(end, LOCAL_DATE_OBJECT_NAME + " end");

        return customerBookings.values()
                .stream()
                .filter(b -> !b.getBookingDate().isBefore(start) && !b.getBookingDate().isAfter(end))
                .toList();
    }

    @Override
    public int calculateWindowsCleanedOnSpecificDate(final LocalDate date) {
        ValidationUtil.checkObjectIsNotNull(date, LOCAL_DATE_OBJECT_NAME);

       return customerBookings.values()
               .stream()
               .filter(b -> b.getBookingDate().equals(date))
               .mapToInt(b -> customers.get(b.getCustomerNumber()).getWindows())
               .sum();
    }

    @Override
    public int calculateCost(final int bookingNumber) {
        if (!customerBookings.containsKey(bookingNumber)) {
            throw new IllegalArgumentException("Booking number not found");
        }

        return customerBookings.values()
                .stream()
                .filter(b -> b.getBookingNumber() == bookingNumber)
                .mapToInt(b -> customers.get(b.getCustomerNumber()).getWindows() + COST_PER_PROPERTY)
                .sum();
    }

    @Override
    public int calculateCost(LocalDate start, LocalDate end) {
        ValidationUtil.checkObjectIsNotNull(start, LOCAL_DATE_OBJECT_NAME + " start");
        ValidationUtil.checkObjectIsNotNull(end, LOCAL_DATE_OBJECT_NAME + " end");
        return customerBookings.values()
                .stream()
                .filter(b -> !b.getBookingDate().isBefore(start) && !b.getBookingDate().isAfter(end))
                .mapToInt(b -> customers.get(b.getCustomerNumber()).getWindows() + COST_PER_PROPERTY)
                .sum();
    }

    @Override
    public int calculateCost(LocalDate date) {
        ValidationUtil.checkObjectIsNotNull(date, LOCAL_DATE_OBJECT_NAME);
        return customerBookings.values()
                .stream()
                .filter(b -> b.getBookingDate().equals(date))
                .mapToInt(b -> customers.get(b.getCustomerNumber()).getWindows() + COST_PER_PROPERTY)
                .sum();
    }

    @Override
    public void updateBookingStatus(int bookingNumber, Status status) {
        ValidationUtil.checkObjectIsNotNull(status, "Status");

        if (!customerBookings.containsKey(bookingNumber)) {
            throw new IllegalArgumentException("Booking number not found");
        }

        if (customerBookings.get(bookingNumber).getStatus().equals(status)) {
            throw new IllegalArgumentException("Booking status is already " + status);
        }

        if (customerBookings.get(bookingNumber).getStatus().equals(Status.COMPLETED)) {
            throw new IllegalArgumentException("Booking already completed");
        }

        customerBookings.get(bookingNumber).setStatus(status);
    }
}
