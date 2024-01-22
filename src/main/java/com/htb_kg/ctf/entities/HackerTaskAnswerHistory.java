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

    @ManyToOne()
    private Hacker hacker;

    @ManyToOne
    private Task task;

    private String time;
}
