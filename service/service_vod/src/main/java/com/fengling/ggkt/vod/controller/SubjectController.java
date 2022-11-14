package com.fengling.ggkt.vod.controller;


import com.fengling.ggkt.result.Result;
import com.fengling.ggkt.vod.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author fengling
 * @since 2022-07-25
 */
@RestController

@RequestMapping("/admin/vod/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping("getChildSubject/{id}")
    public Result getChildSubject(@PathVariable("id") Long id) {
        return subjectService.selectSubjectById(id);
    }
    @GetMapping("exportData")
    public void exportData(HttpServletResponse response){
        subjectService.exportData(response);
    }

    @PostMapping("importData")
    public Result importData(MultipartFile filter) {
        System.out.println("1111111111111111111");
        try {
            return subjectService.importData(filter);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.fail();
        }

    }

}

