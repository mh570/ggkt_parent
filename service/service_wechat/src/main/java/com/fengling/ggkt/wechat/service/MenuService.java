package com.fengling.ggkt.wechat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fengling.ggkt.model.wechat.Menu;
import com.fengling.ggkt.vo.wechat.MenuVo;

import java.util.List;

/**
 * <p>
 * 订单明细 订单明细 服务类
 * </p>
 *
 * @author fengling
 * @since 2022-08-06
 */
public interface MenuService extends IService<Menu> {

    List<Menu> findMenuOneInfo();

    List<MenuVo> findMenuInfo();


    void removeMenu();

    void syncMenu();
}
