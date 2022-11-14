package com.fengling.ggkt.order.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fengling.ggkt.model.order.OrderInfo;
import com.fengling.ggkt.order.service.OrderDetailService;
import com.fengling.ggkt.order.service.OrderInfoService;
import com.fengling.ggkt.result.Result;
import com.fengling.ggkt.vo.order.OrderInfoQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单表 订单表 前端控制器
 * </p>
 *
 * @author fengling
 * @since 2022-08-01
 */
@RestController
@RequestMapping("/admin/order/orderInfo")
public class OrderInfoController {
    @Autowired
    private OrderInfoService orderInfoService;

    @GetMapping("{page}/{limit}")
    public Result listOrder(@PathVariable("page") Long page,
                            @PathVariable("limit") Long limit,
                            OrderInfoQueryVo orderInfoQueryVo) {
        Page<OrderInfo> infoPage = new Page<>(page, limit);
        return orderInfoService.selectOrderInfoPage(infoPage, orderInfoQueryVo);
    }
}

