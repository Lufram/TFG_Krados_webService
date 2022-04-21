package com.edix.krados.repository;

import com.edix.krados.model.ProductInPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInPurchaseRepository extends JpaRepository<ProductInPurchase, Long> {
}
