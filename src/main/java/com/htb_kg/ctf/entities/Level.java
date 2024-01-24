package com.htb_kg.ctf.entities;

import lombok.*;

import jakarta.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Level {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String name;
    @OneToMany()
    private List<Task> tasks;
}
