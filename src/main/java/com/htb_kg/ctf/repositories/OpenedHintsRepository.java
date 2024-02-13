package com.htb_kg.ctf.repositories;

import com.htb_kg.ctf.entities.Hacker;
import com.htb_kg.ctf.entities.Hint;
import com.htb_kg.ctf.entities.OpenedHints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface OpenedHintsRepository extends JpaRepository<OpenedHints, Long> {
    Optional<OpenedHints> findByHintIdAndHackerId(Long hintId, Long hackerId);
    List<OpenedHints> findAllByHackerIdAndTaskId(Long hackerId, Long taskId);
    List<OpenedHints> findByHackerAndHintIn(Hacker hacker, Collection<Hint> hints);

}
