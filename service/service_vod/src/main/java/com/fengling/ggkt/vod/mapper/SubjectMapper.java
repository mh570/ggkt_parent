package com.fengling.ggkt.vod.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fengling.ggkt.model.vod.Subject;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 课程科目 Mapper 接口
 * </p>
 *
 * @author fengling
 * @since 2022-07-25
 */
//存储库
@Repository
//或者
@Mapper
public interface SubjectMapper extends BaseMapper<Subject> {

}
