package com.fengling.ggkt.user.controller;


import com.fengling.ggkt.model.user.UserInfo;
import com.fengling.ggkt.user.service.UserInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author fengling
 * @since 2022-08-04
 */
@RestController
@RequestMapping("/admin/user/userInfo")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("inner/getById/{id}")
    public UserInfo getById(@PathVariable("id") Long id) {
        UserInfo userInfo = userInfoService.getById(id);
        return userInfo;
    }
}

