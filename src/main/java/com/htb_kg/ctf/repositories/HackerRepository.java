package com.htb_kg.ctf.repositories;

import com.htb_kg.ctf.entities.Hacker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HackerRepository extends JpaRepository<Hacker, Long> {
    List<Hacker> findAllByOrderByPointsAsc();
    List<Hacker> findAllByOrderByPointsDesc();
}
