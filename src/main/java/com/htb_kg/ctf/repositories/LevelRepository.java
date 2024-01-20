package com.htb_kg.ctf.repositories;

import com.htb_kg.ctf.entities.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {
    Optional<Level> findByName(String name);
    void deleteByName(String name);
}
