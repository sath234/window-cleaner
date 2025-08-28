package org.example.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CustomerTest {

    @Test
    public void canFetchCustomerFields(){
        Customer customer = new Customer(1, "Nathan", 10);

        Assertions.assertEquals("Nathan", customer.getName());
        Assertions.assertEquals(10, customer.getWindows());
        Assertions.assertEquals(1, customer.getCustomerNumber());
    }

    @Test
    public void canSetNonFinalCustomerFields(){
        Customer customer = new Customer(1, "Nathan", 10);

        customer.setWindows(12);
        customer.setName("BOB");

        Assertions.assertEquals("BOB", customer.getName());
        Assertions.assertEquals(12, customer.getWindows());
        Assertions.assertEquals(1, customer.getCustomerNumber());
    }
}
