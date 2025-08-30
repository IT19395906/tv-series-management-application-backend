package com.tvseries.TvSeriesManagementSystemBackend.dto;

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
    private String releasedDateFrom;
    private String releasedDateTo;
    private String addedDate;
}
