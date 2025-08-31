package com.tvseries.TvSeriesManagementSystemBackend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    
    private String quality;
    
    private String trailer;
    
    @NotNull(message = "ReleasedDate is required")
    private String releasedDate;
    
    @Min(value=1, message="Seasons must be greater than zero")
    private Integer seasons;
    
    @Min(value=1, message="Episodes must be greater than zero")
    private Integer episodes;
    
    private String status;
    
    private String img;
}
