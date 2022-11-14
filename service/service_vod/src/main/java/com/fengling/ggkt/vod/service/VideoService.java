package com.fengling.ggkt.vod.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fengling.ggkt.model.vod.Video;


/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author fengling
 * @since 2022-07-28
 */
public interface VideoService extends IService<Video> {
    //根据课程id删除小节
    void removeVideoByCourseId(Long id);

    //删除小节 删除视频
    void removeVideoById(Long id);

}
