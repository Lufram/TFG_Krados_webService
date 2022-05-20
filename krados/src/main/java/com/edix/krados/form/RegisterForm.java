package com.edix.krados.form;

import lombok.Data;

@Data
public class RegisterForm {
    private String username;
    private String password;

    private String name;
    private String lastname;

    private String roadName;
    private String city;
    private String state;
    private String postalCode;
}
