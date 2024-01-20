package com.htb_kg.ctf.entities;

import com.htb_kg.ctf.enums.Role;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Task {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String name;
    private String description;
    private Integer points;
    @ManyToOne()
    private Level level;
    private Integer userSolves;
    @ManyToOne
    private Category category;
    private String releaseDate;
    private String taskCreator;
    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private FileData downloadFile;

    private String submitFlag;

    @ManyToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<HackerTaskAnswerHistory> history;

    @ManyToMany()
    private List<Hacker> answeredHackers;


}
