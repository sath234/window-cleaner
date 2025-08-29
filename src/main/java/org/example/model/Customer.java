package org.example.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Customer model class.
 */
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class Customer {
    /** Customer number. */
    private final int customerNumber;
    /** Customer name. */
    private String name;
    /** Number of windows. */
    private int windows;
}
