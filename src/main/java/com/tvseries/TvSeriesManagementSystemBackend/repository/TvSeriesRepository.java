package com.tvseries.TvSeriesManagementSystemBackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tvseries.TvSeriesManagementSystemBackend.entity.TvSeries;

@Repository
public interface TvSeriesRepository extends JpaRepository<TvSeries, Long> {
        @Query("SELECT t FROM TvSeries t WHERE " +
                        "(:title IS NULL OR LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
                        "(:category IS NULL OR LOWER(t.category) LIKE LOWER(CONCAT('%', :category, '%'))) AND " +
                        "(:quality IS NULL OR LOWER(t.quality) LIKE LOWER(CONCAT('%', :quality, '%'))) AND " +
                        "(:releasedDateFrom IS NULL OR t.releasedDate >= :releasedDateFrom) AND " +
                        "(:releasedDateTo IS NULL OR t.releasedDate <= :releasedDateTo) AND " +
                        "(:addedDate IS NULL OR LOWER(t.addedDate) LIKE LOWER(CONCAT('%', :addedDate, '%')))")
        List<TvSeries> search(
                        @Param("title") String title,
                        @Param("category") String category,
                        @Param("quality") String quality,
                        @Param("releasedDateFrom") String releasedDateFrom,
                        @Param("releasedDateTo") String releasedDateTo,
                        @Param("addedDate") String addedDate);
}
