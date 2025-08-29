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
@AllArgsConstructor
@EqualsAndHashCode
public class Booking {
    /** Booking number. */
    private final int bookingNumber;
    /** Customer number. */
    private final int customerNumber;
    /** Booking date. */
    private LocalDate bookingDate;
}
