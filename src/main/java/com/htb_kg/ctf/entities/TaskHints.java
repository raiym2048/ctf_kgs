package com.htb_kg.ctf.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TaskHints {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "hints_id")
    private Hint hint;

    @ManyToOne
    @JoinColumn(name = "hacker_id")
    private Hacker hacker;

    private Boolean isUsable = true;
    private String usedTime;
}
