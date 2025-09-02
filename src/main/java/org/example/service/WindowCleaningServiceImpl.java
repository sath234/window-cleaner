package org.example.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.example.model.CustomerBooking;
import org.example.model.Customer;
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
        this.customerBookings = new HashMap<Integer, CustomerBooking>();
        this.customers = new HashMap<Integer, Customer>();
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


        customerBookings.put(customerBooking.getBookingNumber(), customerBooking);
    }

    @Override
    public List<CustomerBooking> retrieveCustomerBookings() {
        return customerBookings.values().stream().toList();
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
    public int calculateTotalCostForBooking(final int bookingNumber) {
        if (!customerBookings.containsKey(bookingNumber)) {
            throw new IllegalArgumentException("Booking number not found");
        }

        return customerBookings.values()
                .stream()
                .filter(b -> b.getBookingNumber() == bookingNumber)
                .mapToInt(b -> customers.get(b.getCustomerNumber()).getWindows() + COST_PER_PROPERTY)
                .sum();
    }
}
