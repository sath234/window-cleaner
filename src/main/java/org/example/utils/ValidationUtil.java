package org.example.utils;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public final class ValidationUtil {

    private ValidationUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static <T> void checkDuplicateObjectInList(List<T> list, T object) {
        if (list.contains(object)) {
            throw new IllegalArgumentException("Duplicate " + object.getClass().getSimpleName() + " not allowed");
        }
    }

    public static <T> void checkObjectIsNotNull(T object, String objectName) {
        Objects.requireNonNull(object, objectName + " cannot be null");
    }

    public static void checkDateNotInPast(LocalDate date) {
        if (date != null && date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Booking date cannot be in the past");
        }
    }
}
