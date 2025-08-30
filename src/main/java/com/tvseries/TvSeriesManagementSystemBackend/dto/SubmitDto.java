package com.tvseries.TvSeriesManagementSystemBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitDto {
    private String title;
    private String description;
    private String language;
    private String category;
    private String quality;
    private String trailer;
    private String releasedDate;
    private int seasons;
    private int episodes;
    private String img;
}
