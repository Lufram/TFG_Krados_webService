package com.edix.krados.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    private String info;
    private boolean inOffer = false;
    private String url;
    @Column(name = "unity_price", nullable = false)
    private double uPrice;
	@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<ProductInPurchase> pInPurchase;

	@OneToMany(mappedBy = "product")
	private List<ProductInCart> pInCart;


	public Product() {
		super();
	}

	public Product(Long id, String name, String info, double uPrice, Category category,
			List<ProductInPurchase> pInPurchase) {
		super();
		this.id = id;
		this.name = name;
		this.info = info;
		this.uPrice = uPrice;
		this.category = category;
		this.pInPurchase = pInPurchase;
	}

	public Product(String name, String info, double uPrice, Category category) {
		super();
		this.name = name;
		this.info = info;
		this.uPrice = uPrice;
		this.category = category;
	}

}
