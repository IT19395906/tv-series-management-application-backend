package com.tvseries.TvSeriesManagementSystemBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tvseries.TvSeriesManagementSystemBackend.entity.UserRequest;

@Repository
public interface UserRequestRepository extends JpaRepository<UserRequest, Long> {

}
