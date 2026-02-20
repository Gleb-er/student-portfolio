package com.example.student_portfolio.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Controller
@RequestMapping("/files")
public class FileDownloadController {

    @GetMapping("/download-template")
    public ResponseEntity<Resource> downloadTemplate() throws FileNotFoundException {
        String filePath = "uploads/templates/article-template.doc";
        File file = new File(filePath);

        if (!file.exists()) {
            filePath = "D:\\локальный ПК\\Downloads\\student-portfolio\\student-portfolio\\uploads\\templates\\article-template.doc";
            file = new File(filePath);

            if (!file.exists()) {
                throw new FileNotFoundException("Файл шаблона статьи не найден");
            }
        }

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=article-template.doc");
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("/download-review")
    public ResponseEntity<Resource> downloadReview() throws FileNotFoundException {
        String filePath = "uploads/templates/review-template.doc";
        File file = new File(filePath);

        if (!file.exists()) {
            filePath = "D:\\локальный ПК\\Downloads\\student-portfolio\\student-portfolio\\uploads\\templates\\review-template.doc";
            file = new File(filePath);

            if (!file.exists()) {
                throw new FileNotFoundException("Файл рецензии не найден");
            }
        }

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=review-template.doc");
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}