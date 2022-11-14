package com.fengling.ggkt.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fengling.ggkt.model.user.UserInfo;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author fengling
 * @since 2022-08-04
 */
public interface UserInfoService extends IService<UserInfo> {

    UserInfo getUserInfoOpenid(String openId);
}
