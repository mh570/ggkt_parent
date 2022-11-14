package com.fengling.ggkt.wechat.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fengling.ggkt.exception.HiException;
import com.fengling.ggkt.model.wechat.Menu;
import com.fengling.ggkt.vo.wechat.MenuVo;
import com.fengling.ggkt.wechat.mapper.MenuMapper;
import com.fengling.ggkt.wechat.service.MenuService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 订单明细 订单明细 服务实现类
 * </p>
 *
 * @author fengling
 * @since 2022-08-06
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private WxMpService wxMpService;

    @Override
    public List<Menu> findMenuOneInfo() {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", 0);
        List<Menu> menus = baseMapper.selectList(queryWrapper);
        return menus;
    }

    @Override
    public List<MenuVo> findMenuInfo() {

//        List<MenuVo> menuVoList = new ArrayList<>();
//        List<Menu> menus = baseMapper.selectList(null);
//        List<MenuVo> towMenuList = new ArrayList<>();
//        for (Menu menu : menus) {
//            if (menu.getParentId() == 0) {
//                MenuVo menuVo = new MenuVo();
//                BeanUtils.copyProperties(menu, menuVo);
//
//                if (menu.getParentId() == menu.getId()) {
//                    MenuVo twoMenuVo = new MenuVo();
//                    BeanUtils.copyProperties(menu, twoMenuVo);
//                    towMenuList.add(twoMenuVo);
//                }
//
//                menuVo.setChildren(towMenuList);
//                menuVoList.add(menuVo);
//            }
//        }


        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", 0);
        List<Menu> oneMenuList = baseMapper.selectList(queryWrapper);

        List<MenuVo> oneMenuVoList = new ArrayList<>();

        for (Menu menu : oneMenuList) {

            MenuVo menuVo = new MenuVo();
            BeanUtils.copyProperties(menu, menuVo);

            QueryWrapper<Menu> twoQueryWrapper = new QueryWrapper<>();
            twoQueryWrapper.eq("parent_id", menuVo.getId());
            List<Menu> twoMenuList = baseMapper.selectList(twoQueryWrapper);

            List<MenuVo> twoMenuVoList = new ArrayList<>();

            for (Menu twoMenu : twoMenuList) {
                MenuVo twoMenuVo = new MenuVo();
                BeanUtils.copyProperties(twoMenu, twoMenuVo);
                twoMenuVoList.add(twoMenuVo);
            }

            menuVo.setChildren(twoMenuVoList);
            oneMenuVoList.add(menuVo);
        }
        return oneMenuVoList;
    }

    @Override
    public void removeMenu() {
        try {
            wxMpService.getMenuService().menuDelete();
        } catch (WxErrorException e) {
            e.printStackTrace();
            throw new HiException(20001,"公众号菜单删除失败");
        }
    }

    @Override
    public void syncMenu() {

        List<MenuVo> menuVoList = this.findMenuInfo();
        //封装button里面结构，数组格式
        JSONArray buttonList = new JSONArray();
        for (MenuVo oneMenuVo:menuVoList) {
            //json对象  一级菜单
            JSONObject one = new JSONObject();
            one.put("name",oneMenuVo.getName());
            //json数组   二级菜单
            JSONArray subButton = new JSONArray();

            for (MenuVo twoMenuVo:oneMenuVo.getChildren()) {
                JSONObject view = new JSONObject();
                view.put("type", twoMenuVo.getType());
                if(twoMenuVo.getType().equals("view")) {
                    view.put("name", twoMenuVo.getName());
                    view.put("url", "http://ggkt2.vipgz1.91tunnel.com/#"
                            +twoMenuVo.getUrl());
                } else {
                    view.put("name", twoMenuVo.getName());
                    view.put("key", twoMenuVo.getMeunKey());
                }
                subButton.add(view);
            }
            one.put("sub_button",subButton);
            buttonList.add(one);
        }
        //封装最外层button部分
        JSONObject button = new JSONObject();
        button.put("button",buttonList);

        try {
            String menuId =
                    this.wxMpService.getMenuService().menuCreate(button.toJSONString());
            System.out.println("menuId"+menuId);
        } catch (WxErrorException e) {
            e.printStackTrace();
            throw new HiException(20001,"公众号菜单同步失败");
        }
    }
}
