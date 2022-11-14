package com.fengling.ggkt.vod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fengling.ggkt.model.vod.*;
import com.fengling.ggkt.result.Result;
import com.fengling.ggkt.vo.vod.*;
import com.fengling.ggkt.vod.mapper.*;
import com.fengling.ggkt.vod.service.ChapterService;
import com.fengling.ggkt.vod.service.CourseService;
import com.fengling.ggkt.vod.service.VideoService;
import com.fengling.ggkt.vod.service.VodService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author fengling
 * @since 2022-07-28
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private SubjectMapper subjectMapper;
    @Autowired
    private CourseDescriptionMapper courseDescriptionMapper;
    @Autowired
    private VideoService videoService;
    @Autowired
    private ChapterMapper chapterMapper;
    //因为mapper没有这个
    @Autowired
    private ChapterService chapterService;


    @Override
    public Result findPageCourse(Page<Course> coursePage, CourseQueryVo courseQueryVo) {
        Long subjectId = courseQueryVo.getSubjectId();
        Long subjectParentId = courseQueryVo.getSubjectParentId();
        String title = courseQueryVo.getTitle();
        Long teacherId = courseQueryVo.getTeacherId();

        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like(("title"), title);
        }
        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.eq(("subject_id"), subjectId);
        }
        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.eq(("subject_parent_id"), subjectParentId);
        }
        if (!StringUtils.isEmpty(teacherId)) {
            queryWrapper.eq(("teacher_id"), teacherId);
        }

        Page<Course> pages = baseMapper.selectPage(coursePage, queryWrapper);
        long totalCourse = pages.getTotal();
        long totalPage = pages.getPages();
        List<Course> list = pages.getRecords();
        for (Course item : list) {
            this.getNameById(item);
        }

        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("totalCount", totalCourse);
        hashMap.put("totalPage", totalPage);
        hashMap.put("records", list);
        return Result.ok(hashMap);
    }

    @Override
    public Result saveCourseInfo(CourseFormVo courseFormVo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseFormVo, course);
        baseMapper.insert(course);
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseFormVo.getDescription());
        courseDescription.setId(course.getId());
        courseDescriptionMapper.insert(courseDescription);

        return Result.ok(course.getId());
    }

    @Override
    public Result getCourseInfoById(Long id) {
        Course course = baseMapper.selectById(id);
        if (course == null) {
            return null;
        }
        CourseDescription courseDescription = courseDescriptionMapper.selectById(id);
        CourseFormVo courseFormVo = new CourseFormVo();
        BeanUtils.copyProperties(course,courseFormVo);
        if (courseDescription != null) {
            courseFormVo.setDescription(courseDescription.getDescription());
        }
        return Result.ok(courseFormVo);
    }

    @Override
    public Result updateCourseId(CourseFormVo courseFormVo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseFormVo, course);
        baseMapper.updateById(course);

        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseFormVo.getDescription());
        courseDescription.setId(course.getId());
        courseDescriptionMapper.updateById(courseDescription);

        return Result.ok(courseDescription.getId());
    }

    @Override
    public Result getCoursePublishVo(Long id) {
        CoursePublishVo coursePublishVo = baseMapper.selectCoursePublishVoBy(id);
        return Result.ok(coursePublishVo);
    }

    @Override
    public Result publishCourse(Long id) {
        Course course = baseMapper.selectById(id);
        course.setStatus(1);
        course.setPublishTime(new Date());
        baseMapper.updateById(course);
        return Result.ok();
    }

    @Override
    public Result removeCourseId(Long id) {

        videoService.removeVideoByCourseId(id);
        QueryWrapper<Chapter> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("course_id",id);
        chapterMapper.delete(queryWrapper1);
        courseDescriptionMapper.deleteById(id);
        baseMapper.deleteById(id);
        return Result.ok();
    }








    @Override
    public Map<String, Object> findPage(Page<Course> pageParam, CourseQueryVo courseQueryVo) {
        String title = courseQueryVo.getTitle();//课程名称
        Long subjectId = courseQueryVo.getSubjectId();//二级分类
        Long subjectParentId = courseQueryVo.getSubjectParentId();//一级分类
        Long teacherId = courseQueryVo.getTeacherId();//讲师

        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(title)) {
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(subjectId)) {
            wrapper.eq("subject_id",subjectId);
        }
        if(!StringUtils.isEmpty(subjectParentId)) {
            wrapper.eq("subject_parent_id",subjectParentId);
        }
        if(!StringUtils.isEmpty(teacherId)) {
            wrapper.eq("teacher_id",teacherId);
        }

        Page<Course> pages = baseMapper.selectPage(pageParam, wrapper);


        long totalCount = pages.getTotal();//总记录数
        long totalPage = pages.getPages();//总页数
        long currentPage = pages.getCurrent();//当前页
        long size = pages.getSize();//每页记录数
        List<Course> records = pages.getRecords();
        records.stream().forEach(item -> {
            this.getTeacherAndSubjectName(item);
        });

        Map<String,Object> map = new HashMap<>();
        map.put("totalCount",totalCount);
        map.put("totalPage",totalPage);
        map.put("records",records);
        return map;
    }




    @Override
    public Map<String, Object> getInfoById(Long courseId) {
        Course course = baseMapper.selectById(courseId);
        course.setViewCount(course.getViewCount()+1);
        baseMapper.updateById(course);
        CourseVo courseVo = baseMapper.selectCourseVoById(courseId);
        List<ChapterVo> chapterVoList = chapterService.getTreeList(courseId);

        //课程描述信息
        CourseDescription courseDescription = courseDescriptionMapper.selectById(courseId);
        //课程所属讲师信息
        Teacher teacher = teacherMapper.selectById(course.getTeacherId());

        Map<String,Object> map = new HashMap();
        map.put("courseVo", courseVo);
        map.put("chapterVoList", chapterVoList);
        map.put("description", null != courseDescription ? courseDescription.getDescription() : "");
        map.put("teacher", teacher);
        map.put("isBuy", false);//是否购买
        return map;
    }



    private Course getTeacherAndSubjectName(Course course) {
        //讲师名称
        Long teacherId = course.getTeacherId();
        Teacher teacher = teacherMapper.selectById(teacherId);
        if(teacher != null) {
            course.getParam().put("teacherName",teacher.getName());
        }
        //课程分类名称
        Long subjectParentId = course.getSubjectParentId();
        Subject oneSubject = subjectMapper.selectById(subjectParentId);
        if(oneSubject != null) {
            course.getParam().put("subjectParentTitle",oneSubject.getTitle());
        }
        Long subjectId = course.getSubjectId();
        Subject twoSubject = subjectMapper.selectById(subjectId);
        if(twoSubject != null) {
            course.getParam().put("subjectTitle",twoSubject.getTitle());
        }
        return course;
    }



    @Override
    public List<Course> findlist() {
        List<Course> list = baseMapper.selectList(null);
        list.stream().forEach(item -> {
            this.getTeacherAndSubjectName(item);
        });
        return list;
    }


    private Course getNameById(Course item) {
        Teacher teacher = teacherMapper.selectById(item);
        if (teacher != null) {
            String name = teacher.getName();
            item.getParam().put("teacherName", name);
        }
        Subject subject = subjectMapper.selectById(item.getSubjectParentId());
        if (subject != null) {
            item.getParam().put("subjectParentTitle", subject.getTitle());
        }
        Subject subjectTwo = subjectMapper.selectById(item.getSubjectId());
        if (subject != null) {
            item.getParam().put("subjectTitle", subjectTwo.getTitle());
        }
        return item;
    }
}
