package org.example.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class Booking {
    private final int bookingNumber;
    private final int customerNumber;
    private LocalDate bookingDate;
}
