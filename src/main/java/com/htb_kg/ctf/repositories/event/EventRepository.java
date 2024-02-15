package com.htb_kg.ctf.repositories.event;

import com.htb_kg.ctf.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByEndDateBefore(LocalDateTime dateTime);

    List<Event> findAllByEndDateBefore(LocalDateTime now);
}
