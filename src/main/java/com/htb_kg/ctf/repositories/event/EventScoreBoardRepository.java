package com.htb_kg.ctf.repositories.event;

import com.htb_kg.ctf.entities.Event;
import com.htb_kg.ctf.entities.EventScoreBoard;
import com.htb_kg.ctf.entities.Hacker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventScoreBoardRepository extends JpaRepository<EventScoreBoard, Long> {
    Optional<EventScoreBoard> findByEventAndHacker(Event event, Hacker hacker);

    List<EventScoreBoard> findAllByIdOrderByPointAsc(Long id);
}
