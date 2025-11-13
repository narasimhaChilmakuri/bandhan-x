package com.bandhan.uploaderService.controller;

import com.bandhan.uploaderService.service.UploaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/file")
public class UploaderController {

    private final UploaderService uploaderService;

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file) {

        String url = uploaderService.uploadFile(file);

        return ResponseEntity.ok(url);
    }
}
