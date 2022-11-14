package com.fengling.ggkt.vod.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fengling.ggkt.model.vod.Subject;
import com.fengling.ggkt.result.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;


/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author fengling
 * @since 2022-07-25
 */
public interface SubjectService extends IService<Subject> {

    Result selectSubjectById(Long id);

    void exportData(HttpServletResponse response);

    Result importData(MultipartFile filter);

}
