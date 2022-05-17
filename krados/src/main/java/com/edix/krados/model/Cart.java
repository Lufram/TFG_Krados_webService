package com.edix.krados.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "cart")
    private List<ProductInCart> pInCart;
    @OneToOne
    private Client client;

    public Cart(Client client) {
        this.client = client;
        this.pInCart = new ArrayList<>();
    }
}
