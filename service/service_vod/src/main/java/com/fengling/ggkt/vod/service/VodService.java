package com.fengling.ggkt.vod.service;

import com.fengling.ggkt.result.Result;

import java.util.Map;

public interface VodService {
    Result upload();

    Result remove(String fileId);

    Map<String, Object> getPlayAuth(Long courseId, Long videoId);
}
