package com.fengling.ggkt.vod.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fengling.ggkt.model.vod.Course;
import com.fengling.ggkt.result.Result;
import com.fengling.ggkt.vo.vod.CourseFormVo;
import com.fengling.ggkt.vo.vod.CourseQueryVo;
import com.fengling.ggkt.vod.service.CourseService;
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
@RequestMapping("/admin/vod/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("save")
    public Result save(@RequestBody CourseFormVo courseFormVo) {
        return courseService.saveCourseInfo(courseFormVo);
    }

    @GetMapping("{page}/{limit}")
    public Result courseList(@PathVariable("page") Long page,
                             @PathVariable("limit") Long limit,
                             CourseQueryVo courseQueryVo) {
        Page<Course> coursePage = new Page<>(page, limit);
        return courseService.findPageCourse(coursePage, courseQueryVo);
    }

    @GetMapping("get/{id}")
    public Result get(@PathVariable("id") Long id) {
        return courseService.getCourseInfoById(id);
    }

    @PostMapping("update")
    public Result update(@RequestBody CourseFormVo courseFormVo) {
        return courseService.updateCourseId(courseFormVo);
    }

    @GetMapping("getCoursePublishVo/{id}")
    public Result getCoursePublishVo(@PathVariable("id") Long id) {
        return courseService.getCoursePublishVo(id);
    }

    @PutMapping("publishCourse/{id}")
    public Result publishCourse(@PathVariable("id") Long id) {
        return courseService.publishCourse(id);
    }

    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable("id") Long id) {
        return courseService.removeCourseId(id);
    }


    @GetMapping("findAll")
    public Result findAll() {
        List<Course> list = courseService.findlist();
        return Result.ok(list);
    }
}

