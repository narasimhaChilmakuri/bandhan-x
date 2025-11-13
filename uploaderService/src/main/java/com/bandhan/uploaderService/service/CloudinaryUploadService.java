package com.bandhan.uploaderService.service;


import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Service
public class CloudinaryUploadService implements UploaderService{

    private final Cloudinary cloudinary;

    @Override
    public String uploadFile(MultipartFile file) {
        try{
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), Map.of());
            String url = uploadResult.get("secure_url").toString();
            log.info("File uploaded successfully: {}", url);
            return url;
        }catch (Exception e){
            log.error("File upload failed: {}", e.getMessage());
            throw new RuntimeException("File upload failed", e);
        }
    }
}
