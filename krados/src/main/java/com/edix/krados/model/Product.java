package com.edix.krados.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    private String info;
    @Column(name = "unity_price", nullable = false)
    private double uPrice;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<ProductInPurchase> pInPurchase;
    
    @Override
    public String toString() {
    	return "Producto [id=" + id + ", nombre=" + name + ", info=" + info + ", uPrice=" + uPrice + ", categoria="+ category +"]";
    }
}
