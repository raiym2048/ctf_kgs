package com.htb_kg.ctf.repositories.event;

import com.htb_kg.ctf.entities.EventFormat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventFormatRepository extends JpaRepository<EventFormat, Long> {
    Optional<EventFormat> findByTitle(String title);
}
