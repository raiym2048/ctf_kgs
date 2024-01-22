package com.htb_kg.ctf.repositories;

import com.htb_kg.ctf.entities.Jeopardy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JeopardyRepository extends JpaRepository<Jeopardy, Long> {
}
