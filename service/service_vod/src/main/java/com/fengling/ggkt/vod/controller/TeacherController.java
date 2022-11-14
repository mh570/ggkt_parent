package com.fengling.ggkt.vod.controller;


import com.fengling.ggkt.model.vod.Teacher;
import com.fengling.ggkt.result.Result;
import com.fengling.ggkt.vo.vod.TeacherQueryVo;
import com.fengling.ggkt.vod.service.TeacherService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author fengling
 * @since 2022-07-21
 */
//@EnableSwagger2
@RestController

@RequestMapping("/admin/vod/teacher")


public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("findAll")
    public Result findAll() {
        return teacherService.findAll();
    }

    @DeleteMapping("remove/{id}")
    public Result removeTeacher(@PathVariable("id") Long id) {
        return teacherService.removeTeacherById(id);
    }

    @ApiOperation("条件查询分页")
    @PostMapping("/findQueryPage/{current}/{limit}")
    public Result findPage (@PathVariable("current") Long current,
                            @PathVariable("limit") Long limit,
                            @RequestBody(required = false) TeacherQueryVo teacherQueryVo) {
        return teacherService.findPage(current,limit,teacherQueryVo);
    }

    @PostMapping("saveTeacher")
    public Result saveTeacher(@RequestBody Teacher teacher) {
        return teacherService.saveTeacher(teacher);
    }

    @GetMapping("getTeacher/{id}")
    public Result getTeacher(@PathVariable("id") Long id) {
        return teacherService.getTeacherById(id);
    }

    @PostMapping("updateTeacher")
    public Result updateTeacher(@RequestBody Teacher teacher) {
        return teacherService.updateTeacher(teacher);
    }

    @DeleteMapping("removeBatch")
    public Result removeBatch(@RequestBody List<Long> idList){
        return teacherService.removeBatch(idList);
    }

    @GetMapping("inner/getTeacher/{id}")
    public Teacher getTeacherInfo(@PathVariable Long id) {
        Teacher teacher = teacherService.getById(id);
        return teacher;
    }
}

