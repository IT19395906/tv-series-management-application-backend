package com.tvseries.TvSeriesManagementSystemBackend.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tvseries.TvSeriesManagementSystemBackend.dto.SearchDto;
import com.tvseries.TvSeriesManagementSystemBackend.dto.SubmitDto;
import com.tvseries.TvSeriesManagementSystemBackend.entity.TvSeries;
import com.tvseries.TvSeriesManagementSystemBackend.service.TvSeriesService;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/tvseries")
@CrossOrigin(origins = "*")
public class TvSeriesController {

    @Autowired
    private TvSeriesService service;

    @GetMapping("")
    public String home() {
        return "controller work";
    }

    @GetMapping("categories")
    public List<String> getAllCategories() {
        return Arrays.asList("Action", "Comedy", "Drama", "Thriller", "Horror", "Adventure", "Crime",
                "Romance", "Documentary", "Sport", "Mystery", "Musical", "History", "Fantasy", "Biography",
                "Animation");
    }

    @GetMapping("languages")
    public List<String> getAllLanguages() {
        return Arrays.asList("English", "Hindi", "Spanish", "French", "German", "Chinese", "Japanese",
                "Korean", "Arabic", "Portuguese", "Russian", "Italian", "Turkish", "Dutch");
    }

    @PostMapping("add")
    public TvSeries add(@RequestBody SubmitDto dto) {
        return service.add(dto);
    }

    @PostMapping("getBySearch")
    public List<TvSeries> get(@RequestBody SearchDto dto) {
        return service.get(dto);
    }

}
