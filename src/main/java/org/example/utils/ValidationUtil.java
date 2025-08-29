package org.example.utils;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * Utility class for validation operations.
 */
public final class ValidationUtil {

    private ValidationUtil() {
        throw new UnsupportedOperationException(
            "Utility class cannot be instantiated");
    }

    /**
     * Checks if an object is already present in the list.
     * @param <T> the type of object
     * @param list the list to check
     * @param object the object to check for
     */
    public static <T> void checkDuplicateObjectInList(final List<T> list,
                                                       final T object) {
        if (list.contains(object)) {
            throw new IllegalArgumentException("Duplicate "
                + object.getClass().getSimpleName() + " not allowed");
        }
    }

    /**
     * Checks if an object is not null.
     * @param <T> the type of object
     * @param object the object to check
     * @param objectName the name of the object for error message
     */
    public static <T> void checkObjectIsNotNull(final T object,
                                                 final String objectName) {
        Objects.requireNonNull(object, objectName + " cannot be null");
    }

    /**
     * Checks if the date is not in the past.
     * @param date the date to check
     */
    public static void checkDateNotInPast(final LocalDate date) {
        if (date != null && date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(
                "Booking date cannot be in the past");
        }
    }
}
