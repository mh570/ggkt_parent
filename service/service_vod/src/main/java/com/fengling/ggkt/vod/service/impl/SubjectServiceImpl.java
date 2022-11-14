package com.fengling.ggkt.vod.service.impl;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fengling.ggkt.exception.HiException;
import com.fengling.ggkt.model.vod.Subject;
import com.fengling.ggkt.result.Result;
import com.fengling.ggkt.vo.vod.SubjectEeVo;
import com.fengling.ggkt.vo.vod.SubjectVo;
import com.fengling.ggkt.vod.listener.SubjectListener;
import com.fengling.ggkt.vod.mapper.SubjectMapper;
import com.fengling.ggkt.vod.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author fengling
 * @since 2022-07-25
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Autowired
    private SubjectListener subjectListener;
    @Override
    public Result selectSubjectById(Long id) {
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", id);
        List<Subject> list = baseMapper.selectList(queryWrapper);
        for (Subject subject : list) {
            Long subjectId = subject.getId();


            boolean idChild = this.isChildren(subjectId);
            subject.setHasChildren(idChild);
        }
        return Result.ok(list);
    }

    private boolean isChildren(Long subjectId) {
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", subjectId);
        Integer integer = baseMapper.selectCount(queryWrapper);
        return integer > 0;
    }

    @Override
    public void exportData(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");

            String fileName = URLEncoder.encode("课程分类", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            List<Subject> subjectList = baseMapper.selectList(null);
            List<SubjectEeVo> subjectEeVoList = new ArrayList<>();
            for (Subject subject : subjectList) {
                SubjectEeVo subjectEeVo = new SubjectEeVo();
                BeanUtils.copyProperties(subject, subjectEeVo);
                subjectEeVoList.add(subjectEeVo);
            }


            EasyExcel.write(response.getOutputStream(), SubjectEeVo.class)
                    .sheet("课程分类")
                    .doWrite(subjectEeVoList);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Result importData(MultipartFile filter) {
        try {
            EasyExcel.read(filter.getInputStream(), SubjectEeVo.class,subjectListener)
                    .sheet()
                    .doRead();
//            System.out.println("2222222222222222222222222222222222222222222222222222222222222222222222");
        } catch (IOException e) {
            e.printStackTrace();
            throw new HiException(20001,"cw" + e.getMessage());
        }

        return Result.ok();
    }
}
