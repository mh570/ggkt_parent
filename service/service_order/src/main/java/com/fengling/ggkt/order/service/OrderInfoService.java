package com.fengling.ggkt.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fengling.ggkt.model.order.OrderInfo;
import com.fengling.ggkt.result.Result;
import com.fengling.ggkt.vo.order.OrderFormVo;
import com.fengling.ggkt.vo.order.OrderInfoQueryVo;
import com.fengling.ggkt.vo.order.OrderInfoVo;

/**
 * <p>
 * 订单表 订单表 服务类
 * </p>
 *
 * @author fengling
 * @since 2022-08-01
 */
public interface OrderInfoService extends IService<OrderInfo> {

    Result selectOrderInfoPage(Page<OrderInfo> infoPage, OrderInfoQueryVo orderInfoQueryVo);

    void updateOrderStatus(String out_trade_no);

    Long submitOrder(OrderFormVo orderFormVo);

    OrderInfoVo getOrderInfoById(Long id);
}
