package com.tvseries.TvSeriesManagementSystemBackend.service;

import java.util.List;

import com.tvseries.TvSeriesManagementSystemBackend.dto.SearchDto;
import com.tvseries.TvSeriesManagementSystemBackend.dto.SubmitDto;
import com.tvseries.TvSeriesManagementSystemBackend.entity.TvSeries;

public interface TvSeriesService {
TvSeries add(SubmitDto dto);
List<TvSeries> get(SearchDto dto);
    
}
