package com.htb_kg.ctf.dto.file;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse {

    Long id;
    String name;
    String type;
    byte[] fileData;
    Long userId;
    String path;


}
