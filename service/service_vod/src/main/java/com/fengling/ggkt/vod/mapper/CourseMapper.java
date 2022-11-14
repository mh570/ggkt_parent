package com.fengling.ggkt.vod.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fengling.ggkt.model.vod.Course;
import com.fengling.ggkt.vo.vod.CoursePublishVo;
import com.fengling.ggkt.vo.vod.CourseVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author fengling
 * @since 2022-07-28
 */
public interface CourseMapper extends BaseMapper<Course> {

    CoursePublishVo selectCoursePublishVoBy(Long id);

    CourseVo selectCourseVoById(Long courseId);
}
