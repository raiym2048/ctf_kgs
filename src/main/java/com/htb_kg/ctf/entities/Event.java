package com.htb_kg.ctf.entities;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private String key;

    @OneToOne()
    private FileData image;

    @ManyToOne()
    private EventType eventType;

    @ManyToOne
    private EventFormat eventFormat;

    @ManyToOne
    private EventStatus eventStatus;

    @ManyToOne()
    private Location location;

    @ManyToMany()
    private List<Hacker> joinedHackers;

    @ManyToMany
    private List<Team> joinedTeams;

    @ManyToOne()
    private Team winner;

    @OneToMany()
    private List<Task> challenges;



}
