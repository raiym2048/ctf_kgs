package com.htb_kg.ctf.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    @OneToOne(mappedBy = "accessToBusiness")
    private User userBusiness;
    private String name;
    private String type;

    @OneToOne(mappedBy = "logo_image")
    private User user;
    @OneToOne(mappedBy = "downloadFile")
    private Task task;
    //    @Lob
    @Column(name = "image_data")
    private byte[] fileData;

    @OneToOne(mappedBy = "proof")
    private Teacher teacher;

    @Column(name = "path")
    private String path;

}
