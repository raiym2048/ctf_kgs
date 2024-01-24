package com.htb_kg.ctf.entities;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String createdDate;
    @OneToMany()
    private List<Hacker>  members;
    private Long teamPoints;
    @ManyToMany()
    private List<Event> pastEvents;

    @OneToOne()
    private Location location;

    @OneToOne()
    private Hacker leader;

}
