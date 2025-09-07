package com.tvseries.TvSeriesManagementSystemBackend.service.Impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tvseries.TvSeriesManagementSystemBackend.dto.SearchDto;
import com.tvseries.TvSeriesManagementSystemBackend.dto.SubmitDto;
import com.tvseries.TvSeriesManagementSystemBackend.entity.TvSeries;
import com.tvseries.TvSeriesManagementSystemBackend.repository.TvSeriesRepository;
import com.tvseries.TvSeriesManagementSystemBackend.service.TvSeriesService;

import jakarta.persistence.EntityNotFoundException;

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
        series.setFormat(dto.getFormat());
        series.setReleasedDate(dto.getReleasedDate());
        series.setSeasons(dto.getSeasons());
        series.setEpisodes(dto.getEpisodes());
        series.setTrailer(dto.getTrailer());
        series.setImg(dto.getImg());
        series.setTags(dto.getTags());
        series.setStatus(dto.getStatus());
        series.setAddedDate(LocalDate.now().toString());
        series.setAddedBy("Admin");
        return repository.save(series);
    }

    @Override
    public List<TvSeries> get(SearchDto dto) {
        return repository.search(dto.getTitle(), dto.getCategory(), dto.getQuality(), dto.getReleasedDateFrom(),
                dto.getReleasedDateTo(), dto.getAddedDateFrom(), dto.getAddedDateTo());
    }

    @Override
    public Page<TvSeries> getAllSeries(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<TvSeries> searchByQuery(String keyword) {
        String[] keywords = keyword.toLowerCase().split("\\s+");
        Set<TvSeries> result = new HashSet<>();
        for (String part : keywords) {
            List<TvSeries> found = repository.searchByQuery(part);
            result.addAll(found);
        }
        return new ArrayList<>(result);
    }

    @Override
    public TvSeries getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tv Series Not Found With Id" + id));
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Tv Series Not Found With Id" + id);
        }

        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Some Error Occured");
        }
    }

}
