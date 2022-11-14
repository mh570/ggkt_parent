package com.fengling.ggkt.vod.controller;


import com.fengling.ggkt.model.vod.Chapter;
import com.fengling.ggkt.result.Result;
import com.fengling.ggkt.vod.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author fengling
 * @since 2022-07-28
 */
@RestController
@RequestMapping("/admin/vod/chapter")

public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    @GetMapping("getNestedTreeList/{courseId}")
    public Result getTreeList(@PathVariable("courseId") Long courseId) {
        List treeList = chapterService.getTreeList(courseId);
        return Result.ok(treeList);

    }

    @PostMapping("save")
    public Result save(@RequestBody Chapter chapter) {
        return chapterService.saveChapter(chapter);
    }

    @GetMapping("get/{id}")
    public Result get(@PathVariable("id") Long id) {
        return Result.ok(chapterService.getById(id));
    }

    @PostMapping("update")
    public Result update(@RequestBody Chapter chapter) {
        chapterService.updateById(chapter);
        return Result.ok();
    }

    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable("id") Long id) {
        chapterService.removeById(id);
        return Result.ok();
    }



}

