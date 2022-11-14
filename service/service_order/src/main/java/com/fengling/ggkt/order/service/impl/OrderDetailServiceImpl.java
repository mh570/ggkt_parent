package com.fengling.ggkt.order.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fengling.ggkt.model.order.OrderDetail;
import com.fengling.ggkt.model.order.OrderInfo;
import com.fengling.ggkt.order.mapper.OrderDetailMapper;
import com.fengling.ggkt.order.service.OrderDetailService;
import com.fengling.ggkt.result.Result;
import com.fengling.ggkt.vo.order.OrderInfoQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 订单明细 订单明细 服务实现类
 * </p>
 *
 * @author fengling
 * @since 2022-08-01
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {


}
