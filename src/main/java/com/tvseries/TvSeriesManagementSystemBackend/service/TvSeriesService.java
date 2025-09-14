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

    TvSeries getById(Long id);

    void deleteById(Long id);

    void update(Long id, SubmitDto dto);

    List<TvSeries> latest10();

    List<String> getAllYears();

}
