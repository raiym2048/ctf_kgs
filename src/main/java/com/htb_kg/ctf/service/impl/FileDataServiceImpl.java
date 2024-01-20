package com.htb_kg.ctf.service.impl;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.htb_kg.ctf.entities.FileData;
import com.htb_kg.ctf.entities.User;
import com.htb_kg.ctf.exception.NotFoundException;
import com.htb_kg.ctf.mapper.FileMapper;
import com.htb_kg.ctf.repositories.FileRepository;
import com.htb_kg.ctf.repositories.UserRepository;
import com.htb_kg.ctf.service.FileDataService;
import com.htb_kg.ctf.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLConnection;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileDataServiceImpl implements FileDataService {

    private final FileRepository repository;
    private final FileMapper fileMapper;
    private final UserService userService;
    private final UserRepository userRepository;


    @Autowired
    @Qualifier("amazonS3")
    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String AWS3_BUCKET;

    @Value("${aws.s3.bucket.uploaded.folder}")
    private String UPLOADED_FILES_FOLDER;

    @Value("${location.path}")
    private String PATH;


    @Override
    @Transactional
    public FileData uploadFile(MultipartFile file, FileData oldDocument, String token)  {
        if (oldDocument != null) {
            deleteFile(oldDocument);
        }
        User user = userService.getUsernameFromToken(token);
        return save(file, user);
    }

    @Override
    public FileData uploadFile(MultipartFile file, String token)  {
        User user = userService.getUsernameFromToken(token);
        return save(file, user);
    }

    @Override
    public FileData uploadFileToBusiness(MultipartFile file, String token)  {
        User user = userService.getUsernameFromToken(token);
        user.setToBusiness(true);
        user.setBusinessFileSentDate(LocalDateTime.now().toString());
        return saveToBusiness(file, user);
    }

    private FileData save( MultipartFile file, User user) {
        FileData document=new FileData();

        System.out.println(document.toString()+"\n\n\n");
        System.out.println(file.toString()+"\n\n\n");
//        if (document.getId()==null){
//            throw new NotFoundException("id is null");
//        }
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        // Normalize file name
        if (file.getOriginalFilename()!=null){
            fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        }

        // Check if the file's name contains invalid characters
        if (fileName.contains("..")) {
            throw new NotFoundException("Sorry! Filename contains invalid path sequence " + fileName, HttpStatus.NOT_FOUND);
        }

        fileName = validateFileName(fileName);

        System.out.println(document.getName() + " \n " + document.getPath()+" \n\n\n");
        document.setName(fileName);
//        document.set(file.getSize());

        uploadFileToS3Bucket(file, UPLOADED_FILES_FOLDER + document.getName());
        log.info("File with name = {} has successfully uploaded", document.getName());
        repository.save(document);
        String url = PATH+document.getId();

        System.out.println("the url: "+ url);
        document.setPath(url);
        user.setLogo_image(document);
        userRepository.save(user);
        return repository.save(document);
    }
    private FileData saveToBusiness( MultipartFile file, User user) {
        FileData document=new FileData();

        System.out.println(document.toString()+"\n\n\n");
        System.out.println(file.toString()+"\n\n\n");
//        if (document.getId()==null){
//            throw new NotFoundException("id is null");
//        }
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        // Normalize file name
        if (file.getOriginalFilename()!=null){
            fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        }

        // Check if the file's name contains invalid characters
        if (fileName.contains("..")) {
            throw new NotFoundException("Sorry! Filename contains invalid path sequence " + fileName, HttpStatus.NOT_FOUND);
        }

        fileName = validateFileName(fileName);

        System.out.println(document.getName() + " \n " + document.getPath()+" \n\n\n");
        document.setName(fileName);
//        document.set(file.getSize());

        uploadFileToS3Bucket(file, UPLOADED_FILES_FOLDER + document.getName());
        log.info("File with name = {} has successfully uploaded", document.getName());
        repository.save(document);
        String url = PATH+document.getId();

        System.out.println("the url: "+ url);
        document.setPath(url);
        user.setAccessToBusiness(document);
        userRepository.save(user);
        return repository.save(document);
    }

    private void uploadFileToS3Bucket(MultipartFile file, String key) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getInputStream().available());

            if (file.getContentType() != null && !"".equals(file.getContentType())) {
                metadata.setContentType(file.getContentType());
            }

            PutObjectRequest putObjectRequest = new PutObjectRequest(AWS3_BUCKET, key, file.getInputStream(), metadata);
            amazonS3.putObject(putObjectRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void deleteFile(FileData file) {
        try {
            String sourceKey = UPLOADED_FILES_FOLDER + file.getName();
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(AWS3_BUCKET, sourceKey);
            amazonS3.deleteObject(deleteObjectRequest);
            repository.delete(file);

        } catch (Exception e) {
            log.error("Something went wrong:%{}", e.getMessage());
            e.printStackTrace();
        }
    }

    private String validateFileName(String fileName) {
        // Get file name without extension
        String fileNameWithoutExtension = fileName.substring(0, fileName.lastIndexOf("."));
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));

        LocalDateTime time = LocalDateTime.now(Clock.systemUTC());
        Random random = new Random();

        if (fileNameWithoutExtension.contains("_")) {
            String fileNameWithoutDate = fileName.substring(0, fileName.indexOf("_")) + fileExtension;
            fileExtension = fileName.substring(fileName.lastIndexOf("."));
            fileNameWithoutExtension = fileNameWithoutDate.substring(0, fileNameWithoutDate.lastIndexOf("."));
            fileName = fileNameWithoutExtension + "_" + LocalDate.now() + "_" + time.getHour() +
                    "-" + time.getMinute() + "-" + random.nextInt(10) + fileExtension;

            return fileName;
        }

        fileName = fileNameWithoutExtension + "**_" + LocalDate.now() + "_" + time.getHour() +
                "-" + time.getMinute() + "-" + random.nextInt(10) + fileExtension;

        return fileName;
    }

    @Override
    public void downloadFile(Long id, HttpServletResponse http) {
        FileData file = repository.findById(id).orElseThrow();
        // Указание имени и ведра (bucket) файла в Amazon S3
        String bucketName = AWS3_BUCKET;
        String key = UPLOADED_FILES_FOLDER + file.getName();

        try {
            // Получение объекта файла из Amazon S3
            S3Object s3Object = amazonS3.getObject(new GetObjectRequest(bucketName, key));

            // Определение типа MIME файла
            String mimeType = URLConnection.guessContentTypeFromName(file.getName());

            // Установка заголовков ответа
            http.setContentType(mimeType);
            http.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
            http.getOutputStream();
            http.flushBuffer();

            // Создание потока для записи файла в ответ HTTP
            try (OutputStream outputStream = http.getOutputStream()) {
                // Чтение содержимого файла из объекта Amazon S3 и запись в ответ HTTP
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = s3Object.getObjectContent().read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
        } catch (AmazonClientException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getFileById(Long id, HttpServletResponse httpServletResponse) {
        FileData file = repository.findById(id).orElseThrow(()-> new NotFoundException("aefsf",HttpStatus.NOT_FOUND));
        // Указание имени и ведра (bucket) файла в Amazon S3
        String bucketName = AWS3_BUCKET;
        String key = UPLOADED_FILES_FOLDER + file.getName();

        try {
            // Получение объекта файла из Amazon S3
            S3Object s3Object = amazonS3.getObject(new GetObjectRequest(bucketName, key));
            // Определение типа MIME файла
            String mimeType = URLConnection.guessContentTypeFromName(file.getName());

            // Установка заголовков ответа
            httpServletResponse.setContentType(mimeType);
            httpServletResponse.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
            httpServletResponse.flushBuffer();

            // Создание потока для записи файла в ответ HTTP
            try (OutputStream outputStream = httpServletResponse.getOutputStream()) {
                // Чтение содержимого файла из объекта Amazon S3 и запись в ответ HTTP
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = s3Object.getObjectContent().read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
        } catch (AmazonClientException | IOException e) {
            e.printStackTrace();
        }
    }
}
