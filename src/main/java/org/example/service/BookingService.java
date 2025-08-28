package org.example.service;

import java.time.LocalDate;
import java.util.Date;

public interface BookingService {
    int calculateWindowsCleanedOnSpecificDate(LocalDate date);
    int calculateTotalCostForBooking(int bookingNumber);
}
