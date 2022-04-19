package com.edix.krados.repository;

import com.edix.krados.model.Client;
import com.edix.krados.model.ProductInOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInOrderRepository extends JpaRepository<ProductInOrder, Long> {
}
