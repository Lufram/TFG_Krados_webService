package com.edix.krados.repository;

import com.edix.krados.model.Category;
import com.edix.krados.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
