package com.dev.backend.rest;

import com.dev.backend.service.Impl.ImageUploaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class ImageController {

    private final ImageUploaderService imageUploaderService;

    @PostMapping(path = "/postImages", consumes = "multipart/form-data")
    public ResponseEntity<Map<String, String>> storeImage(@RequestPart("file")MultipartFile imageFile) {

        String imgUrl =  imageUploaderService.uploadFile(imageFile);
        Map<String, String> response = new HashMap<>();
        response.put("location", imgUrl);
        return ResponseEntity.ok(response);
    }
}
