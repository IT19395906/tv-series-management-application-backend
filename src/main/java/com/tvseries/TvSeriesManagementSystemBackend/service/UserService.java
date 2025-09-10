package com.tvseries.TvSeriesManagementSystemBackend.service;

import com.tvseries.TvSeriesManagementSystemBackend.dto.RegisterRequest;

public interface UserService {

    void register(RegisterRequest request);
}
