package org.example.service;

import java.time.LocalDateTime;
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
     * Time taken clean a window.
     */
    private static final long TIME_CLEAN_WINDOW = 5;
    /**
     * LocalDateTime object name.
     */
    private static final String LOCAL_DATE_TIME_OBJECT_NAME = "LocalDateTime";
    /**
     * Booking object name.
     */
    private static final String BOOKING_OBJECT_NAME = "Booking";
    /**
     * Customer object name.
     */
    private static final String CUSTOMER_OBJECT_NAME = "Customer";
    /**
     * List of customers.
     */
    private final List<Customer> customerList;
    /**
     * List of bookings.
     */
    private final List<CustomerBooking> customerBookingList;
    /**
     * Map of customers by number.
     */
    private final Map<Integer, Customer> customerMap;

    /**
     * Constructor for BookingServiceImpl.
     */
    public WindowCleaningServiceImpl() {
        this.customerList = new ArrayList<>();
        this.customerBookingList = new ArrayList<>();
        this.customerMap = new HashMap<>();
    }

    @Override
    public void addCustomer(Customer customer) {
        ValidationUtil.checkDuplicateObjectInList(customerList, customer);
        ValidationUtil.checkObjectIsNotNull(customer, CUSTOMER_OBJECT_NAME);

        customerList.add(customer);
        customerMap.put(customer.getCustomerNumber(), customer);
    }

    @Override
    public List<Customer> retrieveCustomers() {
        return customerList;
    }

    @Override
    public void addBooking(CustomerBooking customerBooking) {
        ValidationUtil.checkObjectIsNotNull(customerBooking, BOOKING_OBJECT_NAME);
        ValidationUtil.checkDateTimeNotInPast(customerBooking.getScheduledStart());

        // Need to add calculate endTime first otherwise duplicate check will always pass
        calculateEndTimeBooking(customerBooking);

        ValidationUtil.checkDuplicateObjectInList(customerBookingList, customerBooking);

        customerBookingList.add(customerBooking);
    }

    @Override
    public List<CustomerBooking> retrieveCustomerBookings() {
        return customerBookingList;
    }

    @Override
    public int calculateWindowsCleanedOnSpecificDate(LocalDateTime dateTime) {
        ValidationUtil.checkObjectIsNotNull(dateTime, LOCAL_DATE_TIME_OBJECT_NAME);

        return customerBookingList.stream()
                .filter(booking -> booking.getScheduledStart().toLocalDate().equals(dateTime.toLocalDate()))
                .mapToInt(booking -> customerMap.get(
                        booking.getCustomerNumber()).getWindows())
                .sum();
    }

    @Override
    public int calculateTotalCostForBooking(int bookingNumber) {
        CustomerBooking customerBooking = customerBookingList.stream()
                .filter(b -> b.getBookingNumber() == bookingNumber)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Booking number not found"));

        return customerMap.get(customerBooking.getCustomerNumber()).getWindows()
                + COST_PER_PROPERTY;
    }

    private void calculateEndTimeBooking(CustomerBooking customerBooking) {
        int windows = customerMap.get(customerBooking.getCustomerNumber())
                .getWindows();

        LocalDateTime endTime = customerBooking.getScheduledStart().plusMinutes(TIME_CLEAN_WINDOW * windows);
        customerBooking.setScheduledEnd(endTime);
    }
}
