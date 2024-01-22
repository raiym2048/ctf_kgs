package com.htb_kg.ctf.repositories.event;

import com.htb_kg.ctf.entities.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventStatusRepository extends JpaRepository<EventStatus, Long> {
    Optional<EventStatus> findByTitle(String title);
}
