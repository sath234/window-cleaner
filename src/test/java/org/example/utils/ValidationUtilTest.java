package org.example.utils;

import org.example.model.CustomerBooking;
import org.example.model.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public class ValidationUtilTest {

    static Stream<Arguments> duplicateObjectTestCases() {
        Customer duplicateCustomer = new Customer(1, "Nathan", 5);
        CustomerBooking duplicateCustomerBooking = new CustomerBooking(1, 1, LocalDate.now());

        return Stream.of(
                Arguments.of(
                        List.of(new Customer(1, "Nathan", 5)),
                        duplicateCustomer,
                        "Duplicate Customer not allowed"
                ),
                Arguments.of(
                        List.of(new CustomerBooking(1, 1, LocalDate.now())),
                        duplicateCustomerBooking,
                        "Duplicate CustomerBooking not allowed"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("duplicateObjectTestCases")
    public void checkDuplicateObjectInListThrowsException(List list, Object object, String expectedMessage) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ValidationUtil.checkDuplicateObjectInList(list, object);
        });
        
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    static Stream<Arguments> uniqueObjectTestCases() {
        return Stream.of(
                Arguments.of(
                        List.of(new Customer(1, "Nathan", 5)),
                        new Customer(2, "John", 3)
                ),
                Arguments.of(
                        List.of(new CustomerBooking(1, 1, LocalDate.now())),
                        new CustomerBooking(2, 2, LocalDate.now().plusDays(1))
                ),
                Arguments.of(
                        List.of(),
                        new Customer(1, "Nathan", 5) // Empty list
                )
        );
    }

    @ParameterizedTest
    @MethodSource("uniqueObjectTestCases")
    public void checkDuplicateObjectInListDoesNotThrow(List list, Object object) {
        Assertions.assertDoesNotThrow(() -> {
            ValidationUtil.checkDuplicateObjectInList(list, object);
        });
    }

    static Stream<Arguments> nullObjectTestCases() {
        return Stream.of(
                Arguments.of(null, "Customer", "Customer cannot be null"),
                Arguments.of(null, "Booking", "Booking cannot be null")
        );
    }

    @ParameterizedTest
    @MethodSource("nullObjectTestCases")
    public void checkObjectIsNotNullThrowsException(Object object, String objectType, String expectedMessage) {
        NullPointerException exception = Assertions.assertThrows(NullPointerException.class, () -> {
            ValidationUtil.checkObjectIsNotNull(object, objectType);
        });
        
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    static Stream<Arguments> validObjectTestCases() {
        return Stream.of(
                Arguments.of(new Customer(1, "Nathan", 5), "Customer"),
                Arguments.of(new CustomerBooking(1, 1, LocalDate.now()), "Booking")
        );
    }

    @ParameterizedTest
    @MethodSource("validObjectTestCases")
    public void checkObjectIsNotNullDoesNotThrow(Object object, String objectType) {
        Assertions.assertDoesNotThrow(() -> {
            ValidationUtil.checkObjectIsNotNull(object, objectType);
        });
    }
}
