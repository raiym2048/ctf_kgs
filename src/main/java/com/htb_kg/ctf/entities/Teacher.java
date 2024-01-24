package com.htb_kg.ctf.entities;

import lombok.*;

import jakarta.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "teacher_table")
public class Teacher {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private FileData proof;
}
