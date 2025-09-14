package com.tvseries.TvSeriesManagementSystemBackend.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tvseries.TvSeriesManagementSystemBackend.common.CommonResponse;
import com.tvseries.TvSeriesManagementSystemBackend.dto.SearchDto;
import com.tvseries.TvSeriesManagementSystemBackend.dto.SubmitDto;
import com.tvseries.TvSeriesManagementSystemBackend.entity.TvSeries;
import com.tvseries.TvSeriesManagementSystemBackend.service.TvSeriesService;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

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

    @GetMapping("years")
    public List<String> getAllYears() {
        return service.getAllYears();
    }

    @PostMapping("add")
    public ResponseEntity<CommonResponse<Void>> add(@Valid @RequestBody SubmitDto dto) {
        service.add(dto);
        CommonResponse<Void> response = new CommonResponse<>("Tv Series Uploaded Successfully", null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("getBySearch")
    public ResponseEntity<CommonResponse<List<TvSeries>>> get(@RequestBody SearchDto dto) {
        List<TvSeries> res = service.get(dto);
        if (res.isEmpty()) {
            CommonResponse<List<TvSeries>> response = new CommonResponse<>("Tv Series Not Found", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        CommonResponse<List<TvSeries>> response = new CommonResponse<>("Successfully Found Data", res);
        return ResponseEntity.ok(response);
    }

    @GetMapping("getAll")
    public Page<TvSeries> getAll(Pageable pageable) {
        return service.getAllSeries(pageable);
    }

    @GetMapping("latest")
    public List<TvSeries> latest10() {
        return service.latest10();
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
    public ResponseEntity<CommonResponse<Void>> delete(@PathVariable Long id) {
        service.deleteById(id);
        CommonResponse<Void> response = new CommonResponse<>("Successfully Deleted Tv series", null);
        return ResponseEntity.ok(response);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<CommonResponse<Void>> update(@PathVariable Long id, @Valid @RequestBody SubmitDto dto) {
        service.update(id, dto);
        CommonResponse<Void> response = new CommonResponse<>("TV Series Updated Successfully", null);
        return ResponseEntity.ok(response);
    }

}
