package com.tvseries.TvSeriesManagementSystemBackend.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tvseries.TvSeriesManagementSystemBackend.dto.SearchDto;
import com.tvseries.TvSeriesManagementSystemBackend.dto.SubmitDto;
import com.tvseries.TvSeriesManagementSystemBackend.entity.TvSeries;
import com.tvseries.TvSeriesManagementSystemBackend.service.TvSeriesService;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Map<String, String>> add(@Valid @RequestBody SubmitDto dto) {
        service.add(dto);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Tv Series Uploaded Successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("getBySearch")
    public List<TvSeries> get(@RequestBody SearchDto dto) {
        return service.get(dto);
    }

    @GetMapping("getAll")
    public Page<TvSeries> getAll(Pageable pageable) {
        return service.getAllSeries(pageable);
    }

    @GetMapping("search")
    public List<TvSeries> searchByQuery(@RequestParam String keyword) {
        return service.searchByQuery(keyword);
    }

    @GetMapping("getById/{id}")
    public ResponseEntity<TvSeries> getById(@PathVariable Long id) {
        TvSeries tvseries = service.getById(id);
        return ResponseEntity.ok(tvseries);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok("Successfully Deleted Tv series");
    }

}
