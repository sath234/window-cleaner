package org.example.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class BookingTest {

    @Test
    public void canFetchBookingFields(){
        LocalDate date = LocalDate.of(2020, 1, 1);
        Booking booking = new Booking(1, 1, date);

        Assertions.assertEquals(1, booking.getBookingNumber());
        Assertions.assertEquals(1, booking.getCustomerNumber());
        Assertions.assertEquals(date, booking.getBookingDate());
    }

    @Test
    public void canSetNonFinalBookingFields(){
        LocalDate date = LocalDate.of(2020, 1, 1);
        Booking booking = new Booking(1, 1, date);

        LocalDate date2 = LocalDate.of(2020, 1, 2);

        booking.setBookingDate(date2);

        Assertions.assertEquals(1, booking.getBookingNumber());
        Assertions.assertEquals(1, booking.getCustomerNumber());
        Assertions.assertEquals(date2, booking.getBookingDate());
    }
}
