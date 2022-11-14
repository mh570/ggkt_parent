package com.fengling.ggkt.vod.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fengling.ggkt.model.vod.Course;
import com.fengling.ggkt.result.Result;
import com.fengling.ggkt.vo.vod.CourseFormVo;
import com.fengling.ggkt.vo.vod.CourseQueryVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author fengling
 * @since 2022-07-28
 */
public interface CourseService extends IService<Course> {

    Result findPageCourse(Page<Course> coursePage, CourseQueryVo courseQueryVo);

    Result saveCourseInfo(CourseFormVo courseFormVo);

    Result getCourseInfoById(Long id);

    Result updateCourseId(CourseFormVo courseFormVo);

    Result getCoursePublishVo(Long id);

    Result publishCourse(Long id);

    Result removeCourseId(Long id);

    Map<String, Object> findPage(Page<Course> pageParam, CourseQueryVo courseQueryVo);

    Map<String, Object> getInfoById(Long courseId);

    List<Course> findlist();

}
