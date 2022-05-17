package com.edix.krados.repository;

import com.edix.krados.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(@Param("username")String username);
    boolean existsByUsername(String username);

    @Transactional
    void deleteByUsername(String username);
}
