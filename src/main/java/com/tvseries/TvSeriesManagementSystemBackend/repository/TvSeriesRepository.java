package com.tvseries.TvSeriesManagementSystemBackend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tvseries.TvSeriesManagementSystemBackend.entity.TvSeries;

@Repository
public interface TvSeriesRepository extends JpaRepository<TvSeries, Long> {

        @Query("SELECT t FROM TvSeries t WHERE " +
                        "(:title IS NULL OR :title = '' OR LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
                        "(:category IS NULL OR :category = '' OR LOWER(t.category) LIKE LOWER(CONCAT('%', :category, '%'))) AND "
                        +
                        "(:quality IS NULL OR :quality = '' OR LOWER(t.quality) LIKE LOWER(CONCAT('%', :quality, '%'))) AND "
                        +
                        "(:releasedDateFrom IS NULL OR t.releasedDate >= :releasedDateFrom) AND " +
                        "(:releasedDateTo IS NULL OR t.releasedDate <= :releasedDateTo) AND " +
                        "(:addedDateFrom IS NULL OR :addedDateFrom = '' OR t.addedDate >= :addedDateFrom) AND " +
                        "(:addedDateTo IS NULL OR :addedDateTo = '' OR t.addedDate <= :addedDateTo)")
        List<TvSeries> search(
                        @Param("title") String title,
                        @Param("category") String category,
                        @Param("quality") String quality,
                        @Param("releasedDateFrom") LocalDate releasedDateFrom,
                        @Param("releasedDateTo") LocalDate releasedDateTo,
                        @Param("addedDateFrom") String addedDateFrom,
                        @Param("addedDateTo") String addedDateTo);

        @Query("SELECT t FROM TvSeries t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
        List<TvSeries> searchByQuery(@Param("keyword") String keyword);

        @Query("SELECT t FROM TvSeries t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
        Page<TvSeries> searchByQueryPage(@Param("keyword") String keyword, Pageable pageable);

        List<TvSeries> findTop10ByOrderByReleasedDateDesc();

        List<TvSeries> findByCategory(String category);

        List<TvSeries> findByLanguage(String language);

        @Query("SELECT t FROM TvSeries t WHERE FUNCTION('YEAR', t.releasedDate) = :year")
        List<TvSeries> findByYear(@Param("year") int year);

        // List<TvSeries> findByCollection(String collection);

        @Query("SELECT DISTINCT FUNCTION('YEAR', t.releasedDate) FROM TvSeries t ORDER BY FUNCTION('YEAR', t.releasedDate)")
        List<Integer> findDistinctYears();
}
