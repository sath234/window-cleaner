package org.example.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingTest {

    @Test
    public void canFetchBookingFields(){
        LocalDate date = LocalDate.of(2020, 1, 1);
        LocalTime time = LocalTime.of(12, 0);
        CustomerBooking customerBooking = new CustomerBooking(1, 1, date, time);

        Assertions.assertEquals(1, customerBooking.getBookingNumber());
        Assertions.assertEquals(1, customerBooking.getCustomerNumber());
        Assertions.assertEquals(date, customerBooking.getBookingDate());
    }

    @Test
    public void canSetNonFinalBookingFields(){
        LocalDate date = LocalDate.of(2020, 1, 1);
        LocalTime time = LocalTime.of(12, 0);
        CustomerBooking customerBooking = new CustomerBooking(1, 1, date, time);

        LocalDate date2 = LocalDate.of(2020, 1, 2);

        customerBooking.setBookingDate(date2);

        Assertions.assertEquals(1, customerBooking.getBookingNumber());
        Assertions.assertEquals(1, customerBooking.getCustomerNumber());
        Assertions.assertEquals(date2, customerBooking.getBookingDate());
    }
}
