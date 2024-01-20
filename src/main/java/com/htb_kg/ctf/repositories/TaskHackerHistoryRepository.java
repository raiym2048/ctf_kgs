package com.htb_kg.ctf.repositories;

import com.htb_kg.ctf.entities.HackerTaskAnswerHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskHackerHistoryRepository extends JpaRepository<HackerTaskAnswerHistory, Long> {
    Optional<HackerTaskAnswerHistory> findHackerTaskAnswerHistoryByTaskIdAndHackerId(Long taskId, Long hackerId);
}
