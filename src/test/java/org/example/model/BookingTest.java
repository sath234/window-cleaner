package org.example.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class BookingTest {

    @Test
    public void canFetchBookingFields(){
        LocalDateTime dateTime = LocalDateTime.of(2020, 1, 1, 12, 0);
        CustomerBooking customerBooking = new CustomerBooking(1, 1, dateTime);

        Assertions.assertEquals(1, customerBooking.getBookingNumber());
        Assertions.assertEquals(1, customerBooking.getCustomerNumber());
        Assertions.assertEquals(dateTime, customerBooking.getScheduledStart());
    }

    @Test
    public void canSetNonFinalBookingFields(){
        LocalDateTime dateTime = LocalDateTime.of(2020, 1, 1, 12, 0);
        CustomerBooking customerBooking = new CustomerBooking(1, 1, dateTime);

        LocalDateTime dateTime2 = LocalDateTime.of(2020, 1, 2, 14, 30);

        customerBooking.setScheduledStart(dateTime2);

        Assertions.assertEquals(1, customerBooking.getBookingNumber());
        Assertions.assertEquals(1, customerBooking.getCustomerNumber());
        Assertions.assertEquals(dateTime2, customerBooking.getScheduledStart());
    }
}
