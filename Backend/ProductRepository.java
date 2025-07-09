package com.expirytracker.repository;

import com.expirytracker.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // ✅ Existing method
    Optional<Product> findByName(String name);

    // ✅ Optional override: for multiple entries with same name
    List<Product> findByNameIgnoreCase(String name);

    // ✅ Allow delete by name
    void deleteByName(String name);
}
