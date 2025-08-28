package org.example.service;

import org.example.model.Booking;
import org.example.model.Customer;
import org.example.utils.ValidationUtil;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingServiceImpl implements BookingService {

    public static final int WINDOW_COST = 5;
    public static final String LOCAL_DATE_OBJECT_NAME = "LocalDate";
    public static final String BOOKING_OBJECT_NAME = "Booking";
    public static final String CUSTOMER_OBJECT_NAME = "Customer";
    private final List<Customer> customers;
    private final List<Booking> bookings;
    private final Map<Integer, Customer> customerMap;

    public BookingServiceImpl(List<Customer> customers, List<Booking> bookings) {
        this.customers = customers;
        this.bookings = bookings;
        this.customerMap = new HashMap<>();
        customers.forEach(customer -> customerMap.put(customer.getCustomerNumber(), customer));
    }

    public void addCustomer(Customer customer) {
        ValidationUtil.checkDuplicateObjectInList(customers, customer);
        ValidationUtil.checkObjectIsNotNull(customer, CUSTOMER_OBJECT_NAME);

        customers.add(customer);
        customerMap.put(customer.getCustomerNumber(), customer);
    }

    public void addBooking(Booking booking) {
        ValidationUtil.checkDuplicateObjectInList(bookings, booking);
        ValidationUtil.checkObjectIsNotNull(booking, BOOKING_OBJECT_NAME);
        ValidationUtil.checkDateNotInPast(booking.getBookingDate());

        bookings.add(booking);
    }

    @Override
    public int calculateWindowsCleanedOnSpecificDate(LocalDate date) {
        ValidationUtil.checkObjectIsNotNull(date, LOCAL_DATE_OBJECT_NAME);

        return bookings.stream()
                .filter(booking -> booking.getBookingDate().equals(date))
                .mapToInt(booking -> customerMap.get(booking.getCustomerNumber()).getWindows())
                .sum();
    }

    @Override
    public int calculateTotalCostForBooking(int bookingNumber) {
        Booking booking = bookings.stream()
                .filter(b -> b.getBookingNumber() == bookingNumber)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Booking number not found"));

        return customerMap.get(booking.getCustomerNumber()).getWindows() * WINDOW_COST;

    }
}
