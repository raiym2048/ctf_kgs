package com.htb_kg.ctf.service;

import com.htb_kg.ctf.entities.FileData;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

public interface FileDataService {
    @Transactional
    FileData uploadFile(MultipartFile file, FileData oldDocument, String token);

    FileData uploadFile(MultipartFile file, String token);

    FileData uploadFileToBusiness(MultipartFile file, String token);

    void downloadFile(Long id, HttpServletResponse http);

    void getFileById(Long id, HttpServletResponse httpServletResponse);
}
