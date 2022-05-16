package com.edix.krados.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "surname", nullable = false)
    private String surname;
    @Column(name = "telNumber", nullable = false)
    private String telNumber;
    @Temporal(TemporalType.DATE)
    private Date burnDate;
    @Embedded
    private Address address;
    @OneToMany(mappedBy = "client")
    private List<Purchase> purchaseList;
    @OneToOne(mappedBy = "client")
    private Cart cart;
    @OneToOne
    private User user;

    public Client(String name, String surname, String telNumber, Date burnDate, Address address, User user) {

        this.name = name;
        this.surname = surname;
        this.telNumber = telNumber;
        this.burnDate = burnDate;
        this.address = address;
        this.purchaseList = new ArrayList<>();
        this.cart = new Cart(Client.this);
        this.user = user;
    }
}
