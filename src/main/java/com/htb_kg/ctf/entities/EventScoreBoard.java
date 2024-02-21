package com.htb_kg.ctf.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class EventScoreBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Event event;

    @ManyToOne
    private Hacker hacker;

    @ManyToMany
    private List<Task> submittedTasks;

    @ManyToOne
    private Team team;

    private Integer point;
}
