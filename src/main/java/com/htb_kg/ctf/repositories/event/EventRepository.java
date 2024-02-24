package com.htb_kg.ctf.repositories.event;

import com.htb_kg.ctf.entities.Event;
import com.htb_kg.ctf.entities.Hacker;
import com.htb_kg.ctf.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByEndDateBefore(LocalDateTime dateTime);

    List<Event> findAllByEndDateBefore(LocalDateTime now);
    List<Event> findAllByEndDateAfter(LocalDateTime now);


    List<Event> findAllByStartDateBeforeAndEndDateAfter(LocalDateTime now1, LocalDateTime now2);
    List<Event> findAllByStartDateAfter(LocalDateTime now);

    @Query("SELECT e FROM Event e JOIN e.joinedHackers j WHERE j IN :hackers")
    List<Event> findEventsByJoinedHackers(@Param("hackers") List<Hacker> hackers);

    List<Event> findAllEventByJoinedHackersContaining(Hacker hacker);

    Optional<Event> findByChallengesContains(Task task);
}
