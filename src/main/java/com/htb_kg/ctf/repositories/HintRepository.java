package com.htb_kg.ctf.repositories;

import com.htb_kg.ctf.entities.Hint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HintRepository extends JpaRepository<Hint, Long> {

}
