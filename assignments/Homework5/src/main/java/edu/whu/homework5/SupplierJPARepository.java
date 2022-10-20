package edu.whu.homework5;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierJPARepository extends JpaRepository<Supplier, Long> {

    List<Supplier> findByName(String name);
    void deleteByName(String name);
}
