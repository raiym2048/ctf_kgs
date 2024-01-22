package com.htb_kg.ctf.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String startDate;
    private String endDate;

    private String key;

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