package com.tvseries.TvSeriesManagementSystemBackend.service.Impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tvseries.TvSeriesManagementSystemBackend.dto.SearchDto;
import com.tvseries.TvSeriesManagementSystemBackend.dto.SubmitDto;
import com.tvseries.TvSeriesManagementSystemBackend.entity.TvSeries;
import com.tvseries.TvSeriesManagementSystemBackend.repository.TvSeriesRepository;
import com.tvseries.TvSeriesManagementSystemBackend.service.TvSeriesService;

@Service
public class TvSeriesServiceImpl implements TvSeriesService {

    @Autowired
    TvSeriesRepository repository;

    @Override
    public TvSeries add(SubmitDto dto) {
        TvSeries series = new TvSeries();
        series.setTitle(dto.getTitle());
        series.setDescription(dto.getDescription());
        series.setCategory(dto.getCategory());
        series.setLanguage(dto.getLanguage());
        series.setQuality(dto.getQuality());
        series.setReleasedDate(dto.getReleasedDate());
        series.setSeasons(dto.getSeasons());
        series.setEpisodes(dto.getEpisodes());
        series.setTrailer(dto.getTrailer());
        series.setImg(dto.getImg());
        series.setAddedDate(LocalDate.now().toString());
        return repository.save(series);
    }

    @Override
    public List<TvSeries> get(SearchDto dto) {
        return repository.search(dto.getTitle(), dto.getCategory(), dto.getQuality(), dto.getReleasedDate(), dto.getAddedDate());
    }

}
