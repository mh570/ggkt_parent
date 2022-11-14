package com.fengling.ggkt.vod.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fengling.ggkt.model.vod.VideoVisitor;
import com.fengling.ggkt.result.Result;
import com.fengling.ggkt.vo.vod.VideoVisitorCountVo;
import com.fengling.ggkt.vod.mapper.VideoVisitorMapper;
import com.fengling.ggkt.vod.service.VideoVisitorService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 视频来访者记录表 服务实现类
 * </p>
 *
 * @author fengling
 * @since 2022-07-31
 */
@Service
public class VideoVisitorServiceImpl extends ServiceImpl<VideoVisitorMapper, VideoVisitor> implements VideoVisitorService {

    @Override
    public Result findCount(Long courseId, String startDate, String endDate) {
        List<VideoVisitorCountVo> videoVisitorVoList = baseMapper.findCount(courseId, startDate, endDate);
        Map<String, Object> map = new HashMap<>();
        List<String> dateList =
                videoVisitorVoList.stream().map(VideoVisitorCountVo::getJoinTime).
                        collect(Collectors.toList());
        //代表日期对应数量
        List<Integer> countList = videoVisitorVoList.stream().map(VideoVisitorCountVo::getUserCount)
                .collect(Collectors.toList());

        map.put("xData", dateList);
        map.put("yData", countList);
        return Result.ok(map);
    }
}
