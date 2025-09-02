package org.example.service;

import org.example.model.CustomerBooking;
import org.example.model.Customer;
import org.example.model.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;

public class WindowCleaningServiceImplTest {
    private WindowCleaningServiceImpl windowCleaningService;

    @BeforeEach
    public void setUp() {
        windowCleaningService = new WindowCleaningServiceImpl();

        windowCleaningService.addCustomer(new Customer(1, "John", 10));
        windowCleaningService.addCustomer(new Customer(2, "Paul", 5));
        windowCleaningService.addCustomer(new Customer(3, "Ringo", 12));
        windowCleaningService.addCustomer(new Customer(4, "George", 4));

        windowCleaningService.addBooking(new CustomerBooking(1, 4, LocalDate.of(2025, 10, 1)));
        windowCleaningService.addBooking(new CustomerBooking(2, 2, LocalDate.of(2026, 1, 10)));
        windowCleaningService.addBooking(new CustomerBooking(3, 1, LocalDate.of(2025, 10, 1)));
        windowCleaningService.addBooking(new CustomerBooking(4, 3, LocalDate.of(2025, 10, 1)));
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
        CustomerBooking customerBooking = new CustomerBooking(5, 1, LocalDate.now());
        windowCleaningService.addBooking(customerBooking);

        Assertions.assertTrue(windowCleaningService.retrieveCustomerBookings().contains(customerBooking));
    }

    @Test
    public void addBookingThrowsIllegalArgumentExceptionForDuplicateBooking() {
        CustomerBooking customerBooking = new CustomerBooking(1, 4, LocalDate.of(2025, 10, 1));

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            windowCleaningService.addBooking(customerBooking);
        });

        Assertions.assertEquals("Duplicate CustomerBooking not allowed", exception.getMessage());
    }

    @Test
    public void addBookingThrowsIllegalArgumentExceptionForBookingInPast() {
        CustomerBooking customerBooking = new CustomerBooking(5, 1, LocalDate.of(2020, 1, 1));

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
        Assertions.assertEquals(9, windowCleaningService.calculateCost(1));
        Assertions.assertEquals(10, windowCleaningService.calculateCost(2));
        Assertions.assertEquals(15, windowCleaningService.calculateCost(3));
        Assertions.assertEquals(17, windowCleaningService.calculateCost(4));
    }

    @Test
    public void calculateBookingCostThrowsIllegalArgumentException(){
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            windowCleaningService.calculateCost(5);
        });

        Assertions.assertEquals("Booking number not found", illegalArgumentException.getMessage());
    }

    @Test
    public void retrieveCustomerBookingsWithCustomerNumberReturnsExpectedList() {
        List<CustomerBooking> customerBookings = List.of(new CustomerBooking(5, 5, LocalDate.of(2025, 12, 1)));
        windowCleaningService.addBooking(customerBookings.get(0));

        Assertions.assertEquals(customerBookings, windowCleaningService.retrieveCustomerBookings(5));
    }

    @Test
    public void retrieveCustomerBookingsWithDateRangeReturnsExpectedList() {
        Assertions.assertEquals(3, windowCleaningService.retrieveCustomerBookings(LocalDate.of(2025, 9, 1), LocalDate.of(2025, 10, 3)).size());
        Assertions.assertEquals(3, windowCleaningService.retrieveCustomerBookings(LocalDate.of(2025, 10, 1), LocalDate.of(2025, 10, 3)).size());
        Assertions.assertEquals(3, windowCleaningService.retrieveCustomerBookings(LocalDate.of(2025, 9, 1), LocalDate.of(2025, 10, 1)).size());
        Assertions.assertEquals(4, windowCleaningService.retrieveCustomerBookings(LocalDate.of(2025, 9, 1), LocalDate.of(2026, 1, 10)).size());
        Assertions.assertEquals(1, windowCleaningService.retrieveCustomerBookings(LocalDate.of(2026, 1, 10), LocalDate.of(2026, 5, 10)).size());
        Assertions.assertEquals(0, windowCleaningService.retrieveCustomerBookings(LocalDate.of(2026, 2, 10), LocalDate.of(2026, 5, 10)).size());
    }

    @Test
    public void retrieveCustomerBookingsWithDateRangeThrowsIllegalArgumentException() {
        NullPointerException nullPointerException1 = Assertions.assertThrows(NullPointerException.class, () -> {
            windowCleaningService.retrieveCustomerBookings(null, LocalDate.of(2025, 10, 3));
        });

        NullPointerException nullPointerException2 = Assertions.assertThrows(NullPointerException.class, () -> {
            windowCleaningService.retrieveCustomerBookings(LocalDate.of(2025, 10, 3), null);
        });

        NullPointerException nullPointerException3 = Assertions.assertThrows(NullPointerException.class, () -> {
            windowCleaningService.retrieveCustomerBookings(null, null);
        });

        Assertions.assertEquals("LocalDate start cannot be null", nullPointerException1.getMessage());
        Assertions.assertEquals("LocalDate end cannot be null", nullPointerException2.getMessage());
        Assertions.assertEquals("LocalDate start cannot be null", nullPointerException3.getMessage());
    }

    @Test
    public void calculateCostWithDateRangeReturnsExpectedValue() {
        Assertions.assertEquals(41, windowCleaningService.calculateCost(LocalDate.of(2025, 9, 1), LocalDate.of(2025, 10, 3)));
        Assertions.assertEquals(41, windowCleaningService.calculateCost(LocalDate.of(2025, 10, 1), LocalDate.of(2025, 10, 3)));
        Assertions.assertEquals(41, windowCleaningService.calculateCost(LocalDate.of(2025, 9, 1), LocalDate.of(2025, 10, 1)));
        Assertions.assertEquals(51, windowCleaningService.calculateCost(LocalDate.of(2025, 9, 1), LocalDate.of(2026, 1, 10)));
        Assertions.assertEquals(10, windowCleaningService.calculateCost(LocalDate.of(2026, 1, 10), LocalDate.of(2026, 5, 10)));
        Assertions.assertEquals(0, windowCleaningService.calculateCost(LocalDate.of(2026, 2, 10), LocalDate.of(2026, 5, 10)));

    }
    @Test
    public void calculateCostWithDateRangeThrowsIllegalArgumentException() {
        NullPointerException nullPointerException1 = Assertions.assertThrows(NullPointerException.class, () -> {
            windowCleaningService.calculateCost(null, LocalDate.of(2025, 10, 3));
        });

        NullPointerException nullPointerException2 = Assertions.assertThrows(NullPointerException.class, () -> {
            windowCleaningService.calculateCost(LocalDate.of(2025, 10, 3), null);
        });

        NullPointerException nullPointerException3 = Assertions.assertThrows(NullPointerException.class, () -> {
            windowCleaningService.calculateCost(null, null);
        });

        Assertions.assertEquals("LocalDate start cannot be null", nullPointerException1.getMessage());
        Assertions.assertEquals("LocalDate end cannot be null", nullPointerException2.getMessage());
        Assertions.assertEquals("LocalDate start cannot be null", nullPointerException3.getMessage());
    }

    @Test
    public void calculateCostWithDateReturnsExpectedValue() {
        Assertions.assertEquals(41, windowCleaningService.calculateCost(LocalDate.of(2025, 10, 1)));
        Assertions.assertEquals(10, windowCleaningService.calculateCost(LocalDate.of(2026, 1, 10)));
        Assertions.assertEquals(0, windowCleaningService.calculateCost(LocalDate.of(2026, 2, 10)));
    }

    @Test
    public void calculateCostWithDateThrowsIllegalArgumentException() {
        NullPointerException nullPointerException = Assertions.assertThrows(NullPointerException.class, () -> {
            windowCleaningService.calculateCost(null);
        });

        Assertions.assertEquals("LocalDate cannot be null", nullPointerException.getMessage());
    }

    @Test
    public void updateBookingStatusSuccessfullyUpdatesStatusCompleted() {
        CustomerBooking customerBooking = new CustomerBooking(5, 5, LocalDate.of(2025, 12, 1));
        windowCleaningService.addBooking(customerBooking);
        windowCleaningService.updateBookingStatus(5, Status.COMPLETED);

        Assertions.assertEquals(Status.COMPLETED, customerBooking.getStatus());
    }

    @Test
    public void updateBookingStatusSuccessfullyUpdatesStatusCancelled() {
        CustomerBooking customerBooking = new CustomerBooking(5, 5, LocalDate.of(2025, 12, 1));
        windowCleaningService.addBooking(customerBooking);
        windowCleaningService.updateBookingStatus(5, Status.CANCELLED);

        Assertions.assertEquals(Status.CANCELLED, customerBooking.getStatus());
    }


    @Test
    public void updateBookingStatusThrowsIllegalArgumentExceptionForInvalidBookingNumber() {
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            windowCleaningService.updateBookingStatus(5, Status.CANCELLED);
        });

        Assertions.assertEquals("Booking number not found", illegalArgumentException.getMessage());
    }

    @Test
    public void updateBookingStatusThrowsIllegalArgumentExceptionForInvalidStatus() {
        CustomerBooking customerBooking = new CustomerBooking(5, 5, LocalDate.of(2025, 12, 1));
        windowCleaningService.addBooking(customerBooking);

        IllegalArgumentException illegalArgumentException1 = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            windowCleaningService.updateBookingStatus(5, Status.SCHEDULED);
        });

        IllegalArgumentException illegalArgumentException2 = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            windowCleaningService.updateBookingStatus(5, Status.COMPLETED);

            windowCleaningService.updateBookingStatus(5, Status.CANCELLED);
        });

        Assertions.assertEquals("Booking status is already SCHEDULED", illegalArgumentException1.getMessage());
        Assertions.assertEquals("Booking already completed", illegalArgumentException2.getMessage());
    }
}
