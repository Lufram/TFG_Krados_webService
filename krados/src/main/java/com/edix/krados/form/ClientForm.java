package com.edix.krados.form;

import com.edix.krados.model.Address;
import lombok.Data;

import java.util.Date;
@Data
public class ClientForm {
    private Long id;
    private String name;
    private String surname;
    private String telNumber;
    private Date burnDate;
    private Address address;
    private Long cartId;
}


