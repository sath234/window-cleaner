package org.example.utils;

import org.example.model.CustomerBooking;
import org.example.model.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

public class ValidationUtilTest {

    static Stream<Arguments> duplicateObjectTestCases() {
        Customer duplicateCustomer = new Customer(1, "Nathan", 5);
        LocalDateTime testDateTime = LocalDateTime.of(2025, 1, 1, 13, 0);
        CustomerBooking duplicateCustomerBooking = new CustomerBooking(1, 1, testDateTime);

        return Stream.of(
                Arguments.of(
                        List.of(new Customer(1, "Nathan", 5)),
                        duplicateCustomer,
                        "Duplicate Customer not allowed",
                        "Customer with same number already exists"
                ),
                Arguments.of(
                        List.of(new CustomerBooking(1, 1, testDateTime)),
                        duplicateCustomerBooking,
                        "Duplicate CustomerBooking not allowed",
                        "Booking with same details already exists"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("duplicateObjectTestCases")
    public void checkDuplicateObjectInListThrowsException(List list, Object object, String expectedMessage, String scenario) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ValidationUtil.checkDuplicateObjectInList(list, object);
        }, "Failed scenario: " + scenario);

        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    static Stream<Arguments> uniqueObjectTestCases() {
        return Stream.of(
                Arguments.of(
                        List.of(new Customer(1, "Nathan", 5)),
                        new Customer(2, "John", 3),
                        "Different customer numbers should be allowed"
                ),
                Arguments.of(
                        List.of(new CustomerBooking(1, 1, LocalDateTime.of(2025, 1, 1, 10, 0))),
                        new CustomerBooking(2, 2, LocalDateTime.of(2025, 1, 2, 10, 0)),
                        "Different booking numbers should be allowed"
                ),
                Arguments.of(
                        List.of(),
                        new Customer(1, "Nathan", 5),
                        "Empty list should allow any object"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("uniqueObjectTestCases")
    public void checkDuplicateObjectInListDoesNotThrow(List list, Object object, String scenario) {
        Assertions.assertDoesNotThrow(() -> {
            ValidationUtil.checkDuplicateObjectInList(list, object);
        }, "Failed scenario: " + scenario);
    }

    static Stream<Arguments> nullObjectTestCases() {
        return Stream.of(
                Arguments.of(null, "Customer", "Customer cannot be null", "Null customer should be rejected"),
                Arguments.of(null, "Booking", "Booking cannot be null", "Null booking should be rejected")
        );
    }

    @ParameterizedTest
    @MethodSource("nullObjectTestCases")
    public void checkObjectIsNotNullThrowsException(Object object, String objectType, String expectedMessage, String scenario) {
        NullPointerException exception = Assertions.assertThrows(NullPointerException.class, () -> {
            ValidationUtil.checkObjectIsNotNull(object, objectType);
        }, "Failed scenario: " + scenario);

        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    static Stream<Arguments> validObjectTestCases() {
        return Stream.of(
                Arguments.of(new Customer(1, "Nathan", 5), "Customer", "Valid customer should pass validation"),
                Arguments.of(new CustomerBooking(1, 1, LocalDateTime.of(2025, 1, 1, 10, 0)), "Booking", "Valid booking should pass validation")
        );
    }

    @ParameterizedTest
    @MethodSource("validObjectTestCases")
    public void checkObjectIsNotNullDoesNotThrow(Object object, String objectType, String scenario) {
        Assertions.assertDoesNotThrow(() -> {
            ValidationUtil.checkObjectIsNotNull(object, objectType);
        }, "Failed scenario: " + scenario);
    }
}
