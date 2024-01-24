package com.htb_kg.ctf.controllers;


import com.htb_kg.ctf.service.FileDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class FileController {
    private final FileDataService fileDataService;

    @PostMapping("/user_logo/upload")
    public void downloadFile(@RequestHeader("Authorization") String token,@RequestPart MultipartFile file) throws IOException {
        fileDataService.uploadFile(file, token);
    }
    @GetMapping("/{id}")
    public void getFileById(@PathVariable Long id, HttpServletResponse httpServletResponse){
        fileDataService.getFileById(id, httpServletResponse);
    }
    @GetMapping("/download/{id}")
    public void downloadFileById(@PathVariable Long id, HttpServletResponse httpServletResponse){
        fileDataService.downloadFile(id, httpServletResponse);
    }

    @PostMapping("/toBusiness")
    public void toBusiness(@RequestHeader("Authorization") String token, @RequestPart MultipartFile file){
        fileDataService.uploadFileToBusiness(file, token);
    }

}
