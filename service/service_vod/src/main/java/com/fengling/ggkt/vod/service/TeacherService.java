package com.fengling.ggkt.vod.service;

import com.fengling.ggkt.model.vod.Teacher;
import com.fengling.ggkt.result.Result;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fengling.ggkt.vo.vod.TeacherQueryVo;

import java.util.List;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author fengling
 * @since 2022-07-21
 */
public interface TeacherService extends IService<Teacher> {

    Result findAll();

    Result removeTeacherById(Long id);

    Result findPage(Long current, Long limit, TeacherQueryVo teacherQueryVo);

    Result saveTeacher(Teacher teacher);

    Result getTeacherById(Long id);

    Result updateTeacher(Teacher teacher);

    Result removeBatch(List<Long> idList);

}
