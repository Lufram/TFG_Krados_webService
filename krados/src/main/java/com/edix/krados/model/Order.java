package com.edix.krados.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;
    private String status;

    /*@ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;*/

    @OneToMany(mappedBy = "order")
    private List<ProductInOrder> pInOrder;
}