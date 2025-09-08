package com.tvseries.TvSeriesManagementSystemBackend.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitDto {
    @NotBlank(message = "Title is required")
    private String title;
    
    @NotBlank(message = "Description is required")
    private String description;
    
    @NotBlank(message = "Language is required")
    private String language;
    
    @NotBlank(message = "Category is required")
    private String category;

    @Size(max = 3, message="Select only 3 tags")
    private List<String> tags;
    
    private String quality;

    private String format;
    
    private String trailer;
    
    @NotNull(message = "ReleasedDate is required")
    private LocalDate releasedDate;
    
    @Min(value=1, message="Seasons must be greater than zero")
    private Integer seasons;
    
    @Min(value=1, message="Episodes must be greater than zero")
    private Integer episodes;

    @Min(value=1, message="IMDB Rate must be greater than zero")
    @Max(value =10, message="IMDB Rate must be less than 10")
    private double imdb;

    @Min(value=1, message="RO must be greater than zero")
    @Max(value =100, message="RO must be less than 100")
    private Integer ro;
    
    private String status;
    
    private String img;
}
