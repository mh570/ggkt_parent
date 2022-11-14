package com.fengling.ggkt.vod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fengling.ggkt.exception.HiException;
import com.fengling.ggkt.model.vod.Chapter;
import com.fengling.ggkt.model.vod.Video;
import com.fengling.ggkt.result.Result;
import com.fengling.ggkt.vo.vod.ChapterVo;
import com.fengling.ggkt.vo.vod.VideoVo;
import com.fengling.ggkt.vod.mapper.ChapterMapper;
import com.fengling.ggkt.vod.mapper.VideoMapper;
import com.fengling.ggkt.vod.service.ChapterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author fengling
 * @since 2022-07-28
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoMapper videoMapper;

    @Override
    public List getTreeList(Long courseId) {
        List<ChapterVo> finalChapterVoList = new ArrayList<>();
        QueryWrapper<Chapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        List<Chapter> chapterList = baseMapper.selectList(queryWrapper);

        List<VideoVo> videoVoList = new ArrayList<>();
        QueryWrapper<Video> queryWrapperVoid = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        List<Video> videoList = videoMapper.selectList(queryWrapperVoid);

        for (Chapter chapter : chapterList) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);

            for (Video Video : videoList) {
                VideoVo videoVo = new VideoVo();
                if (chapter.getId().equals(Video.getChapterId())) {
                    BeanUtils.copyProperties(Video, videoVo);
                    videoVoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVoList);
            finalChapterVoList.add(chapterVo);
        }

        return finalChapterVoList;
    }

    @Override
    public Result saveChapter(Chapter chapter) {
        baseMapper.insert(chapter);
        return Result.ok();
    }
}
