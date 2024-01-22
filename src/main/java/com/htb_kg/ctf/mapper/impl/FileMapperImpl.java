package com.htb_kg.ctf.mapper.impl;

import com.htb_kg.ctf.dto.file.FileResponse;
import com.htb_kg.ctf.entities.FileData;
import com.htb_kg.ctf.mapper.FileMapper;
import org.springframework.stereotype.Component;

@Component
public class FileMapperImpl implements FileMapper {
    @Override
    public FileResponse toDto(FileData fileData) {
        FileResponse fileResponse = new FileResponse();
        fileResponse.setType(fileData.getType());
        fileResponse.setPath(fileData.getPath());
        fileResponse.setFileData(fileData.getFileData());
        fileResponse.setName(fileData.getName());
        fileResponse.setId(fileData.getId());

        return fileResponse;
    }
}
