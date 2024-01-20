package com.htb_kg.ctf.repositories;

import com.htb_kg.ctf.entities.FileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileData, Long> {
}
