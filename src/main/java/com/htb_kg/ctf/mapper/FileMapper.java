package com.htb_kg.ctf.mapper;

import com.htb_kg.ctf.dto.file.FileResponse;
import com.htb_kg.ctf.entities.FileData;

public interface FileMapper {
    FileResponse toDto(FileData logoImage);
}
