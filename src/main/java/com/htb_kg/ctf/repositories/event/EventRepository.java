package com.htb_kg.ctf.repositories.event;

import com.htb_kg.ctf.entities.Event;
import com.htb_kg.ctf.entities.Hacker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByEndDateBefore(LocalDateTime dateTime);

    List<Event> findAllByEndDateBefore(LocalDateTime now);
    List<Event> findAllByEndDateAfter(LocalDateTime now);


    List<Event> findAllByStartDateBeforeAndEndDateAfter(LocalDateTime now1, LocalDateTime now2);
    List<Event> findAllByStartDateAfter(LocalDateTime now);

    List<Event> findEventsByJoinedHackers(List<Hacker> hackers);
}
