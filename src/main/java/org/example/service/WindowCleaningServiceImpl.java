package org.example.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void addCustomer(final Customer customer) {
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
    public void addBooking(final CustomerBooking customerBooking) {
        ValidationUtil.checkObjectIsNotNull(customerBooking, BOOKING_OBJECT_NAME);
        ValidationUtil.checkDateNotInPast(customerBooking.getBookingDate());

        // set the schedule here so consistent status for all new bookings
        customerBooking.setStatus(Status.SCHEDULED);

        // Have to check for duplicates after set status otherwise
        // equals and hashcode will not work as expected
        ValidationUtil.checkDuplicateObjectInList(customerBookingList, customerBooking);

        customerBookingList.add(customerBooking);
    }

    @Override
    public List<CustomerBooking> retrieveCustomerBookings() {
        return customerBookingList;
    }

    @Override
    public List<CustomerBooking> retrieveCustomerBookings(LocalDate date) {
        ValidationUtil.checkObjectIsNotNull(date, LOCAL_DATE_OBJECT_NAME);

        return customerBookingList.stream()
                .filter(b -> b.getBookingDate().equals(date))
                .toList();
    }

    @Override
    public int calculateWindowsCleanedOnSpecificDate(final LocalDate date) {
        ValidationUtil.checkObjectIsNotNull(date, LOCAL_DATE_OBJECT_NAME);

        return customerBookingList.stream()
                .filter(booking -> booking.getBookingDate().equals(date))
                .mapToInt(booking -> customerMap.get(
                        booking.getCustomerNumber()).getWindows())
                .sum();
    }

    @Override
    public int calculateWindowsCleanedSpecificDateRange(LocalDate startDate, LocalDate endDate) {
        ValidationUtil.checkObjectIsNotNull(startDate, "Start " + LOCAL_DATE_OBJECT_NAME);
        ValidationUtil.checkObjectIsNotNull(endDate, "End " + LOCAL_DATE_OBJECT_NAME);

        return customerBookingList.stream()
                .filter(b -> (!b.getBookingDate().isAfter(endDate) &&
                        !b.getBookingDate().isBefore(startDate)))
                .mapToInt(b -> customerMap.get(b.getCustomerNumber()).getWindows())
                .sum();
    }

    @Override
    public int calculateTotalCostForBooking(final int bookingNumber) {
        CustomerBooking customerBooking = customerBookingList.stream()
                .filter(b -> b.getBookingNumber() == bookingNumber)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Booking number not found"));

        return customerMap.get(customerBooking.getCustomerNumber()).getWindows()
                + COST_PER_PROPERTY;
    }

    @Override
    public int calculateTotalRevenueForDate(LocalDate date) {
        ValidationUtil.checkObjectIsNotNull(date, LOCAL_DATE_OBJECT_NAME);

        return customerBookingList.stream()
                .filter(b -> b.getBookingDate().equals(date))
                .mapToInt(b -> calculateTotalCostForBooking(b.getBookingNumber()))
                .sum();
    }

    @Override
    public int calculateTotalRevenueForDateRange(LocalDate startDate, LocalDate endDate) {
        ValidationUtil.checkObjectIsNotNull(startDate, "Start " + LOCAL_DATE_OBJECT_NAME);
        ValidationUtil.checkObjectIsNotNull(endDate, "End " + LOCAL_DATE_OBJECT_NAME);

        return customerBookingList.stream()
                .filter(b -> (!b.getBookingDate().isAfter(endDate) &&
                        !b.getBookingDate().isBefore(startDate)))
                .mapToInt(b -> customerMap.get(b.getCustomerNumber())
                        .getWindows() + COST_PER_PROPERTY)
                .sum();
    }

    @Override
    public void updateBookingStatus(int bookingNumber, Status status) {
        ValidationUtil.checkObjectIsNotNull(status, "Status");

        CustomerBooking customerBooking = customerBookingList.stream()
                .filter(b -> b.getBookingNumber() == bookingNumber)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Booking number not found"));

        if (customerBooking.getBookingDate().isAfter(LocalDate.now()) && status == Status.COMPLETED) {
            throw new IllegalArgumentException("Cannot complete future booking");
        }
        if (customerBooking.getStatus() == status) {
            throw new IllegalArgumentException("Status is already " + status);
        }
        if (customerBooking.getStatus() == Status.COMPLETED) {
            throw new IllegalArgumentException("Cannot modify completed booking");
        }
        customerBooking.setStatus(status);
    }
}
