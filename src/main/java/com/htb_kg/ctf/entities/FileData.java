package com.htb_kg.ctf.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

//import javax.persistence.*;

@Entity
@Table(name = "file_data")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;

    //    @Lob
    @Column(name = "image_data")
    private byte[] fileData;


    @Column(name = "path")
    private String path;

}
