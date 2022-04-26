package com.edix.krados.repository;

import com.edix.krados.model.Client;
import com.edix.krados.model.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	public List<Product> findAll();
//	public List<Product> findByContaining(String name);
//	public List<Product> findByCategoryId(int category_id);
}
