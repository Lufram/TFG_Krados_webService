package com.edix.krados.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public double getuPrice() {
		return uPrice;
	}

	public void setuPrice(double uPrice) {
		this.uPrice = uPrice;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
    public String toString() {
    	return "Producto [id=" + id + ", nombre=" + name + ", info=" + info + ", uPrice=" + uPrice + ", categoria="+ category +"]";
    }
}
