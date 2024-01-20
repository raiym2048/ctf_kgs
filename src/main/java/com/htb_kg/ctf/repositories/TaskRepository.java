package com.htb_kg.ctf.repositories;

import com.htb_kg.ctf.dto.task.TaskRequest;
import com.htb_kg.ctf.entities.Category;
import com.htb_kg.ctf.entities.FileData;
import com.htb_kg.ctf.entities.Level;
import com.htb_kg.ctf.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Modifying
    @Query("UPDATE Task t SET t.name = :name, t.description = :description, t.points = :points, t.level = :level, t.userSolves = :userSolves, t.category = :category, t.releaseDate = :releaseDate, t.taskCreator = :taskCreator, t.submitFlag = :submitFlag WHERE t.id = :id")
    void updateTaskAttributes(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("description") String description,
            @Param("points") Integer points,
            @Param("level") Level level,
            @Param("userSolves") Integer userSolves,
            @Param("category") Category category,
            @Param("releaseDate") String releaseDate,
            @Param("taskCreator") String taskCreator,
            @Param("submitFlag") String submitFlag
    );}