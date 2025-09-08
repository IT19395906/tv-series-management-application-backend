package com.tvseries.TvSeriesManagementSystemBackend.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TvSeries {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String language;

    @Column(nullable = false)
    private String category;

    private String quality;

    private String format;

    private String trailer;

    private String status;

    @Column(nullable = false)
    private LocalDate releasedDate;

    private Integer seasons;

    private Integer episodes;

    private double imdb;

    private Integer ro;

    private String img;

    private String addedDate;

    private String addedBy;

    @ElementCollection
    @CollectionTable(name = "tags", joinColumns = @JoinColumn(name = "tvseries_id"))
    @Column(name = "tag")
    private List<String> tags;
}
