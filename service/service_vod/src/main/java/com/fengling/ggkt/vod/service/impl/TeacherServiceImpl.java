package com.fengling.ggkt.vod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fengling.ggkt.model.vod.Teacher;
import com.fengling.ggkt.result.Result;

import com.fengling.ggkt.vo.vod.TeacherQueryVo;
import com.fengling.ggkt.vod.mapper.TeacherMapper;
import com.fengling.ggkt.vod.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author fengling
 * @since 2022-07-21
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Override
    public Result findAll() {
        List<Teacher> teachers = baseMapper.selectList(null);
        return Result.ok(teachers);
    }

    @Override
    public Result removeTeacherById(Long id) {
        int i = baseMapper.deleteById(id);
        if (i == 0) {
            System.out.println("cw");
            return Result.fail();
        }
        return Result.ok();
    }

    @Override
    public Result findPage(Long current, Long limit, TeacherQueryVo teacherQueryVo) {
//        IPage<Teacher> page1 = new IPage<>(current, limit);

        Page<Teacher> page = new Page<>(current, limit);

        Page<Teacher> teacherPage1 = baseMapper.selectPage(page, null);
        return Result.ok(teacherPage1);
//        if (teacherQueryVo == null) {
//            Page<Teacher> teacherPage1 = baseMapper.selectPage(page, null);
//            return Result.ok(teacherPage1);
//        } else {
//            String name = teacherQueryVo.getName();
//            Integer level = teacherQueryVo.getLevel();
//            String joinDateBegin = teacherQueryVo.getJoinDateBegin();
//            String joinDateEnd = teacherQueryVo.getJoinDateEnd();
//
//            QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
//            if (!StringUtils.isEmpty(name)) {
//                queryWrapper.like("name", name);
//            }
//            if (!StringUtils.isEmpty(joinDateBegin)) {
//                queryWrapper.ge("join_date", joinDateBegin);
//            }
//            if (!StringUtils.isEmpty(joinDateEnd)) {
//                queryWrapper.le("join_date", joinDateEnd);
//            }
//            if (!StringUtils.isEmpty(level)) {
//                queryWrapper.eq("level", level);
//            }
//
//            Page<Teacher> teacherPage2 = baseMapper.selectPage(page, queryWrapper);
//            return Result.ok(teacherPage2);
//        }

    }

    @Override
    public Result saveTeacher(Teacher teacher) {
        int insert = baseMapper.insert(teacher);
        if (insert == 0) {
            return Result.fail();
        }else {
            return Result.ok();
        }
    }

    @Override
    public Result getTeacherById(Long id) {
        Teacher teacher = baseMapper.selectById(id);
        return Result.ok(teacher);
    }

    @Override
    public Result updateTeacher(Teacher teacher) {
        int i = baseMapper.updateById(teacher);
        if (i == 1) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @Override
    public Result removeBatch(List<Long> idList) {

        int i = baseMapper.deleteBatchIds(idList);
        if (i == 0) {
            return Result.fail();
        } else {
            return Result.ok();
        }
    }
}
