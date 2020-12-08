package com.bigtree.orders.model;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address{
    private String firstName;
    private String lastName;
    private String lineNumber1;
    private String lineNumber2;
    private String city;
    private String country;
    private String postCode;
    private String notes;
    private String mobile;
}
