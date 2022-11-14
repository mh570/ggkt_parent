package com.fengling.ggkt.client.course;

import com.fengling.ggkt.model.vod.Course;
import com.fengling.ggkt.model.vod.Teacher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
@FeignClient(value = "service-vod")
public interface CourseFeignClient {

    @GetMapping("/api/vod/course/inner/findByKeyword/{keyword}")
    public List<Course> findByKeyword(@PathVariable String keyword);

    @GetMapping("/api/vod/course/inner/getById/{courseId}")
    Course getById(@PathVariable Long courseId);

    @GetMapping("/admin/vod/teacher/inner/getTeacher/{id}")
    public Teacher getTeacherInfo(@PathVariable Long id);
}
