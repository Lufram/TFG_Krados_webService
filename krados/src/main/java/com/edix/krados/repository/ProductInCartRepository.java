package com.edix.krados.repository;

import com.edix.krados.model.ProductInCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductInCartRepository extends JpaRepository<ProductInCart, Long> {

    List<ProductInCart> findByProductAndCart(Long cartId, Long productId);
}
