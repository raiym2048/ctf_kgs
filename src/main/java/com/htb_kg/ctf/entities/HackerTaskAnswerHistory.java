package com.htb_kg.ctf.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class HackerTaskAnswerHistory {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToMany()
    private List<Hacker> hacker;

    @ManyToMany
    @JoinColumn(name = "task_id")
    private List<Task> task;

    private String time;
}
