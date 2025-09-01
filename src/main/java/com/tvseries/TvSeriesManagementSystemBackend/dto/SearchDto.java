package com.tvseries.TvSeriesManagementSystemBackend.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchDto {
    private String title;
    private String category;
    private String quality;
    private LocalDate releasedDateFrom;
    private LocalDate releasedDateTo;
    private String addedDateFrom;
    private String addedDateTo;
}
