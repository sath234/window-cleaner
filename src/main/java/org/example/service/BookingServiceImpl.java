package org.example.service;

import java.time.LocalDate;
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

    /** Cost per property. */
    public static final int COST_PER_PROPERTY = 5;
    /** LocalDate object name. */
    public static final String LOCAL_DATE_OBJECT_NAME = "LocalDate";
    /** Booking object name. */
    public static final String BOOKING_OBJECT_NAME = "Booking";
    /** Customer object name. */
    public static final String CUSTOMER_OBJECT_NAME = "Customer";
    /** List of customers. */
    private final List<Customer> customerList;
    /** List of bookings. */
    private final List<Booking> bookingList;
    /** Map of customers by number. */
    private final Map<Integer, Customer> customerMap;

    /**
     * Constructor for BookingServiceImpl.
     * @param customers list of customers
     * @param bookings list of bookings
     */
    public BookingServiceImpl(final List<Customer> customers,
                              final List<Booking> bookings) {
        this.customerList = customers;
        this.bookingList = bookings;
        this.customerMap = new HashMap<>();
        customers.forEach(customer -> customerMap.put(
            customer.getCustomerNumber(), customer));
    }

    /**
     * Adds a customer to the service.
     * @param customer the customer to add
     */
    public void addCustomer(final Customer customer) {
        ValidationUtil.checkDuplicateObjectInList(customerList, customer);
        ValidationUtil.checkObjectIsNotNull(customer, CUSTOMER_OBJECT_NAME);

        customerList.add(customer);
        customerMap.put(customer.getCustomerNumber(), customer);
    }

    /**
     * Adds a booking to the service.
     * @param booking the booking to add
     */
    public void addBooking(final Booking booking) {
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
