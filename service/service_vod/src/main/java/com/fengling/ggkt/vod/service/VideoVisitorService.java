package com.fengling.ggkt.vod.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fengling.ggkt.model.vod.VideoVisitor;
import com.fengling.ggkt.result.Result;

/**
 * <p>
 * 视频来访者记录表 服务类
 * </p>
 *
 * @author fengling
 * @since 2022-07-31
 */
public interface VideoVisitorService extends IService<VideoVisitor> {

    Result findCount(Long courseId, String startDate, String endDate);
}
