package org.example.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.example.model.Booking;
import org.example.model.Customer;
import org.example.utils.ValidationUtil;

/**
 * Implementation of BookingService.
 */
public final class BookingServiceImpl implements BookingService {

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
     * List of customers.
     */
    // TODO: just have customer map might be easier
    private final List<Customer> customerList;
    /**
     * List of bookings.
     */
    // TODO: just have bookings map might be easier
    private final List<Booking> bookingList;
    /**
     * Map of customers by number.
     */
    private final Map<Integer, Customer> customerMap;

    /**
     * Constructor for BookingServiceImpl.
     */
    public BookingServiceImpl() {
        this.customerList = new ArrayList<>();
        this.bookingList = new ArrayList<>();
        this.customerMap = new HashMap<>();
    }

    @Override
    public void addCustomer(final Customer customer) {
        ValidationUtil.checkDuplicateObjectInList(customerList, customer);
        ValidationUtil.checkObjectIsNotNull(customer, CUSTOMER_OBJECT_NAME);

        customerList.add(customer);
        customerMap.put(customer.getCustomerNumber(), customer);
    }

    @Override
    public void addBooking(Booking booking) {
        ValidationUtil.checkDuplicateObjectInList(bookingList, booking);
        ValidationUtil.checkObjectIsNotNull(booking, BOOKING_OBJECT_NAME);
        ValidationUtil.checkDateNotInPast(booking.getBookingDate());

        bookingList.add(booking);
    }

    @Override
    public int calculateWindowsCleanedOnSpecificDate(final LocalDate date) {
        ValidationUtil.checkObjectIsNotNull(date, LOCAL_DATE_OBJECT_NAME);

        return bookingList.stream()
                .filter(booking -> booking.getBookingDate().equals(date))
                .mapToInt(booking -> customerMap.get(
                        booking.getCustomerNumber()).getWindows())
                .sum();
    }

    @Override
    public int calculateTotalCostForBooking(final int bookingNumber) {
        Booking booking = bookingList.stream()
                .filter(b -> b.getBookingNumber() == bookingNumber)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Booking number not found"));

        return customerMap.get(booking.getCustomerNumber()).getWindows()
                + COST_PER_PROPERTY;
    }
}
