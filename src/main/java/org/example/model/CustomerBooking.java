package org.example.model;

import java.time.LocalDateTime;

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
     * @param scheduledStart the scheduled start date and time
     */
    public CustomerBooking(int bookingNumber, int customerNumber, LocalDateTime scheduledStart) {
        this.bookingNumber = bookingNumber;
        this.customerNumber = customerNumber;
        this.scheduledStart = scheduledStart;
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
     * Scheduled start date and time.
     */
    private LocalDateTime scheduledStart;
    /**
     * Scheduled end date and time.
     */
    private LocalDateTime scheduledEnd;
}
