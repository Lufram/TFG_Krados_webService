package com.edix.krados.repository;

import com.edix.krados.model.Cart;
import com.edix.krados.model.Product;
import com.edix.krados.model.ProductInCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductInCartRepository extends JpaRepository<ProductInCart, Long> {

    ProductInCart findByCartAndProduct(@Param("cartId") Cart cartId, @Param("productId") Product productId);
}
