package com.tvseries.TvSeriesManagementSystemBackend.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tvseries.TvSeriesManagementSystemBackend.dto.SearchDto;
import com.tvseries.TvSeriesManagementSystemBackend.dto.SubmitDto;
import com.tvseries.TvSeriesManagementSystemBackend.entity.TvSeries;

public interface TvSeriesService {
    TvSeries add(SubmitDto dto);

    List<TvSeries> get(SearchDto dto);

    Page<TvSeries> getAllSeries(Pageable pageable);

    List<TvSeries> searchByQuery(String keyword);

    Page<TvSeries> searchByQueryPage(String keyword,Pageable pageable);

    TvSeries getById(Long id);

    void deleteById(Long id);

    void update(Long id, SubmitDto dto);

    List<TvSeries> latest10();

    List<String> getAllYears();

    List<TvSeries> getTvSeriesByCategory(String category);

    List<TvSeries> getTvSeriesByLanguage(String language);

    List<TvSeries> getTvSeriesByYear(int year);

    List<TvSeries> getTvSeriesByCollection(String collection);

}
