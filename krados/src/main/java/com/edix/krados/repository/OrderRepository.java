package com.edix.krados.repository;

import com.edix.krados.model.Client;
import com.edix.krados.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
