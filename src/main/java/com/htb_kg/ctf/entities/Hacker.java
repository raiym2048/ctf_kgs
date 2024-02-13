package com.htb_kg.ctf.entities;

import lombok.*;

import jakarta.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "hacker_table")
public class Hacker {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;



    private Integer level;

    private Integer points;


    @OneToOne(mappedBy = "hacker")
    private User user;

    @ManyToMany
    private List<Task> favorites;

    @ManyToMany()
    private List<Task> answeredTasks;
}
