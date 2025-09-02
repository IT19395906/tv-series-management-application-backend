package com.tvseries.TvSeriesManagementSystemBackend.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommonResponse<T> {
    private String message;
    private T data;
}
