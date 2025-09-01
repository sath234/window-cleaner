package org.example.model;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Booking model class.
 */
@Getter
@Setter
@EqualsAndHashCode
public class CustomerBooking {

    /**
     * Constructor for CustomerBooking.
     *
     * @param bookingNumber  the booking number
     * @param customerNumber the customer number
     * @param bookingDate    the booking date
     * @param startTime      the start time
     */
    public CustomerBooking(int bookingNumber, int customerNumber, LocalDate bookingDate,
                           LocalTime startTime) {
        this.bookingNumber = bookingNumber;
        this.customerNumber = customerNumber;
        this.bookingDate = bookingDate;
        this.startTime = startTime;
    }

    /**
     * Booking number.
     */
    private final int bookingNumber;
    /**
     * Customer number.
     */
    private final int customerNumber;
    /**
     * Booking date.
     */
    private LocalDate bookingDate;
    /**
     * Booking time.
     */
    private LocalTime startTime;
    /**
     * Duration of the booking.
     */
    private LocalTime endTime;
}
