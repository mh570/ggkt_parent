package com.fengling.ggkt.vod.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fengling.ggkt.model.vod.Chapter;
import com.fengling.ggkt.result.Result;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author fengling
 * @since 2022-07-28
 */
public interface ChapterService extends IService<Chapter> {


    List getTreeList(Long courseId);

    Result saveChapter(Chapter chapter);
}
