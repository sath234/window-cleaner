package org.example.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
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

    public CustomerBooking(int bookingNumber, int customerNumber, LocalDate bookingDate) {
        this.bookingNumber = bookingNumber;
        this.customerNumber = customerNumber;
        this.bookingDate = bookingDate;
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
     * Booking status.
     */
    private Status status;
}
