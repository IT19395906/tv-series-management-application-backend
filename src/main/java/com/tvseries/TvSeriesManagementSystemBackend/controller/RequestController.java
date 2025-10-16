package com.tvseries.TvSeriesManagementSystemBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tvseries.TvSeriesManagementSystemBackend.common.CommonResponse;
import com.tvseries.TvSeriesManagementSystemBackend.service.TvSeriesService;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    @Autowired
    private TvSeriesService service;

    @PostMapping("/contact")
    public ResponseEntity<CommonResponse<Void>> addRequest(
            @RequestParam("fname") String fname,
            @RequestParam("lname") String lname,
            @RequestParam("email") String email,
            @RequestParam("contact") String contact,
            @RequestParam("content") String content,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        service.addRequest(fname, lname, email, contact, content, file);
        CommonResponse<Void> response = new CommonResponse<>("Request Sent Successfully", null);
        return ResponseEntity.ok(response);
    }
}
