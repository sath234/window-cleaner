package org.example.service;

import org.example.model.CustomerBooking;
import org.example.model.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;

public class WindowCleaningServiceImplTest {
    private WindowCleaningServiceImpl windowCleaningService;

    @BeforeEach
    public void setUp() {
        windowCleaningService = new WindowCleaningServiceImpl();

        windowCleaningService.addCustomer(new Customer(1, "John", 10));
        windowCleaningService.addCustomer(new Customer(2, "Paul", 5));
        windowCleaningService.addCustomer(new Customer(3, "Ringo", 12));
        windowCleaningService.addCustomer(new Customer(4, "George", 4));

        windowCleaningService.addBooking(new CustomerBooking(1, 4, LocalDate.of(2025, 10, 1), LocalTime.of(6, 0)));
        windowCleaningService.addBooking(new CustomerBooking(2, 2, LocalDate.of(2026, 1, 10), LocalTime.of(8, 0)));
        windowCleaningService.addBooking(new CustomerBooking(3, 1, LocalDate.of(2025, 10, 1), LocalTime.of(10, 0)));
        windowCleaningService.addBooking(new CustomerBooking(4, 3, LocalDate.of(2025, 10, 1), LocalTime.of(12, 0)));
    }

    @Test
    public void addCustomerAddsCustomerToList() {
        Customer customer = new Customer(5, "Test", 10);
        windowCleaningService.addCustomer(customer);

        Assertions.assertTrue(windowCleaningService.retrieveCustomers().contains(customer));
    }

    @Test
    public void addCustomerThrowsIllegalArgumentExceptionForDuplicateCustomer() {
        Customer customer = new Customer(1, "John", 10);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            windowCleaningService.addCustomer(customer);
        });

        Assertions.assertEquals("Duplicate Customer not allowed", exception.getMessage());
    }

    @Test
    public void addCustomerThrowsIllegalArgumentExceptionForNullCustomer() {
        NullPointerException exception = Assertions.assertThrows(NullPointerException.class, () -> {
            windowCleaningService.addCustomer(null);
        });

        Assertions.assertEquals("Customer cannot be null", exception.getMessage());
    }

    @Test
    public void addBookingAddsBookingToList() {
        CustomerBooking customerBooking = new CustomerBooking(5, 1, LocalDate.now(), LocalTime.of(12, 0));
        windowCleaningService.addBooking(customerBooking);

        Assertions.assertTrue(windowCleaningService.retrieveCustomerBookings().contains(customerBooking));
    }

    @Test
    public void addBookingThrowsIllegalArgumentExceptionForDuplicateBooking() {
        CustomerBooking customerBooking = new CustomerBooking(1, 4, LocalDate.of(2025, 10, 1), LocalTime.of(6, 0));

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            windowCleaningService.addBooking(customerBooking);
        });

        Assertions.assertEquals("Duplicate CustomerBooking not allowed", exception.getMessage());
    }

    @Test
    public void addBookingThrowsIllegalArgumentExceptionForBookingInPast() {
        CustomerBooking customerBooking = new CustomerBooking(5, 1, LocalDate.of(2020, 1, 1), LocalTime.of(12, 0));

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            windowCleaningService.addBooking(customerBooking);
        });

        Assertions.assertEquals("Booking date cannot be in the past", exception.getMessage());
    }

    @Test
    public void addBookingThrowsIllegalArgumentExceptionForNullBooking() {
        NullPointerException exception = Assertions.assertThrows(NullPointerException.class, () -> {
            windowCleaningService.addBooking(null);
        });

        Assertions.assertEquals("Booking cannot be null", exception.getMessage());
    }

    @Test
    public void retrieveCustomersReturnsExpectedList() {
        Assertions.assertEquals(4, windowCleaningService.retrieveCustomers().size());
    }

    @Test
    public void retrieveCustomerBookingsReturnsExpectedList() {
        Assertions.assertEquals(4, windowCleaningService.retrieveCustomerBookings().size());
    }

    @Test
    public void calculateWindowsCleanedReturnsExpectedCount(){
        Assertions.assertEquals(26, windowCleaningService.calculateWindowsCleanedOnSpecificDate(LocalDate.of(2025, 10, 1)));
        Assertions.assertEquals(5, windowCleaningService.calculateWindowsCleanedOnSpecificDate(LocalDate.of(2026, 1, 10)));
        Assertions.assertEquals(0, windowCleaningService.calculateWindowsCleanedOnSpecificDate(LocalDate.of(2027, 1, 10)));
    }

    @Test
    public void calculateWindowsCleanedThrowsIllegalArgumentException(){
        NullPointerException nullPointerException = Assertions.assertThrows(NullPointerException.class, () -> {
            windowCleaningService.calculateWindowsCleanedOnSpecificDate(null);
        });

        Assertions.assertEquals("LocalDate cannot be null", nullPointerException.getMessage());
    }

    @Test
    public void calculateBookingCostReturnsExpectedCost(){
        Assertions.assertEquals(9, windowCleaningService.calculateTotalCostForBooking(1));
        Assertions.assertEquals(10, windowCleaningService.calculateTotalCostForBooking(2));
        Assertions.assertEquals(15, windowCleaningService.calculateTotalCostForBooking(3));
        Assertions.assertEquals(17, windowCleaningService.calculateTotalCostForBooking(4));
    }

    @Test
    public void calculateBookingCostThrowsIllegalArgumentException(){
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            windowCleaningService.calculateTotalCostForBooking(5);
        });

        Assertions.assertEquals("Booking number not found", illegalArgumentException.getMessage());
    }
}
