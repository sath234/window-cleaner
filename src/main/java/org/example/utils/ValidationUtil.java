package org.example.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public final class ValidationUtil {

    private ValidationUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static <T> Object checkDuplicateObjectInList (List<T> list, T object) {
        if (list.contains(object)) {
            throw new IllegalArgumentException("Please provide a unique "+object.getClass().getSimpleName()+" object");
        }
        return null;
    }

    public static <T> void checkObjectIsNotNull(T object, String objectName){
        if (object == null){
            throw new IllegalArgumentException(objectName + " is null");
        }
    }

    public static void checkDateNotInPast(LocalDate date) {
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Booking date cannot be in the past");
        }
    }
}
