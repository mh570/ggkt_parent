package com.fengling.ggkt.vod.controller;


import com.fengling.ggkt.result.Result;
import com.fengling.ggkt.vod.service.VideoVisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 视频来访者记录表 前端控制器
 * </p>
 *
 * @author fengling
 * @since 2022-07-31
 */
@RestController
@RequestMapping("/admin/vod/videoVisitor")
public class VideoVisitorController {
    @Autowired
    private VideoVisitorService videoVisitorService;

    @GetMapping("findCount/{courseId}/{startDate}/{endDate}")
    public Result findCount(@PathVariable("courseId") Long courseId,
                            @PathVariable("startDate") String startDate,
                            @PathVariable("endDate") String endDate) {
        return videoVisitorService.findCount(courseId,startDate,endDate);
    }

}

