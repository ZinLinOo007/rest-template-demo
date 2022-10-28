package com.example.frontend.ds;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Customer {

    private Integer id;

    private String code;
    private String firstName;
    private String lastName;

    public Customer() {
    }
}
