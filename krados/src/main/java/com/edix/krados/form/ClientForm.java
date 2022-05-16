package com.edix.krados.form;

import com.edix.krados.model.Address;
import lombok.Data;

@Data
public class ClientForm {
    private Long id;
    private String name;
    private String surname;
    private Address address;
    private Long cartId;
}


