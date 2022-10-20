package edu.whu.homework5;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodJPARepository extends JpaRepository<Good, Long> {

    List<Good> findByName(String name);

    void deleteByName(String name);
}
