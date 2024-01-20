package com.htb_kg.ctf.dto.user;

import com.htb_kg.ctf.dto.file.FileResponse;
import lombok.Data;

@Data
public class BusinessUserResponse {
    private Long id;
    private String email;
    private String creatingDate;
    private String sentDate;
    private FileResponse accessFile;

}
