package com.edix.krados.repository;

import com.edix.krados.model.Client;
import com.edix.krados.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
