package org.example.service;

import org.example.model.Booking;
import org.example.model.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;

public class BookingServiceImplTest {
    private BookingServiceImpl bookingService;

    @BeforeEach
    public void setUp() {
        bookingService = new BookingServiceImpl();

        bookingService.addCustomer(new Customer(1, "John", 10));
        bookingService.addCustomer(new Customer(2, "Paul", 5));
        bookingService.addCustomer(new Customer(3, "Ringo", 12));
        bookingService.addCustomer(new Customer(4, "George", 4));

        bookingService.addBooking(new Booking(1, 4, LocalDate.of(2025, 10, 1)));
        bookingService.addBooking(new Booking(2, 2, LocalDate.of(2026, 1, 10)));
        bookingService.addBooking(new Booking(3, 1, LocalDate.of(2025, 10, 1)));
        bookingService.addBooking(new Booking(4, 3, LocalDate.of(2025, 10, 1)));
    }

    @Test
    public void calculateWindowsCleanedReturnsExpectedCount(){
        Assertions.assertEquals(26, bookingService.calculateWindowsCleanedOnSpecificDate(LocalDate.of(2025, 10, 1)));
        Assertions.assertEquals(5, bookingService.calculateWindowsCleanedOnSpecificDate(LocalDate.of(2026, 1, 10)));
        Assertions.assertEquals(0, bookingService.calculateWindowsCleanedOnSpecificDate(LocalDate.of(2027, 1, 10)));
    }

    @Test
    public void calculateWindowsCleanedThrowsIllegalArgumentException(){
        NullPointerException nullPointerException = Assertions.assertThrows(NullPointerException.class, () -> {
            bookingService.calculateWindowsCleanedOnSpecificDate(null);
        });

        Assertions.assertEquals("LocalDate cannot be null", nullPointerException.getMessage());
    }

    @Test
    public void calculateBookingCostReturnsExpectedCost(){
        Assertions.assertEquals(9, bookingService.calculateTotalCostForBooking(1));
        Assertions.assertEquals(10, bookingService.calculateTotalCostForBooking(2));
        Assertions.assertEquals(15, bookingService.calculateTotalCostForBooking(3));
        Assertions.assertEquals(17, bookingService.calculateTotalCostForBooking(4));
    }

    @Test
    public void calculateBookingCostThrowsIllegalArgumentException(){
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            bookingService.calculateTotalCostForBooking(5);
        });

        Assertions.assertEquals("Booking number not found", illegalArgumentException.getMessage());
    }
}
