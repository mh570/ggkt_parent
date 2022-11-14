package com.fengling.ggkt.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fengling.ggkt.client.activity.CouponInfoFeignClient;
import com.fengling.ggkt.client.course.CourseFeignClient;
import com.fengling.ggkt.client.user.UserInfoFeignClient;
import com.fengling.ggkt.exception.HiException;
import com.fengling.ggkt.model.activity.CouponInfo;
import com.fengling.ggkt.model.order.OrderDetail;
import com.fengling.ggkt.model.order.OrderInfo;
import com.fengling.ggkt.model.user.UserInfo;
import com.fengling.ggkt.model.vod.Course;
import com.fengling.ggkt.order.mapper.OrderInfoMapper;
import com.fengling.ggkt.order.service.OrderDetailService;
import com.fengling.ggkt.order.service.OrderInfoService;
import com.fengling.ggkt.result.Result;
import com.fengling.ggkt.utils.AuthContextHolder;
import com.fengling.ggkt.utils.OrderNoUtils;
import com.fengling.ggkt.vo.order.OrderFormVo;
import com.fengling.ggkt.vo.order.OrderInfoQueryVo;
import com.fengling.ggkt.vo.order.OrderInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.config.QuerydslWebConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionService;

/**
 * <p>
 * 订单表 订单表 服务实现类
 * </p>
 *
 * @author fengling
 * @since 2022-08-01
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private CourseFeignClient courseFeignClient;
    @Autowired
    private UserInfoFeignClient userInfoFeignClient;
    @Autowired
    private CouponInfoFeignClient couponInfoFeignClient;


    @Override
    public Result selectOrderInfoPage(Page<OrderInfo> infoPage, OrderInfoQueryVo orderInfoQueryVo) {
        //orderInfoQueryVo获取查询条件
        Long userId = orderInfoQueryVo.getUserId();
        String outTradeNo = orderInfoQueryVo.getOutTradeNo();
        String phone = orderInfoQueryVo.getPhone();
        String createTimeEnd = orderInfoQueryVo.getCreateTimeEnd();
        String createTimeBegin = orderInfoQueryVo.getCreateTimeBegin();
        Integer orderStatus = orderInfoQueryVo.getOrderStatus();

        QueryWrapper<OrderInfo> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(orderStatus)) {
            wrapper.eq("order_status",orderStatus);
        }
        if(!StringUtils.isEmpty(userId)) {
            wrapper.eq("user_id",userId);
        }
        if(!StringUtils.isEmpty(outTradeNo)) {
            wrapper.eq("out_trade_no",outTradeNo);
        }
        if(!StringUtils.isEmpty(phone)) {
            wrapper.eq("phone",phone);
        }
        if(!StringUtils.isEmpty(createTimeBegin)) {
            wrapper.ge("create_time",createTimeBegin);
        }
        if(!StringUtils.isEmpty(createTimeEnd)) {
            wrapper.le("create_time",createTimeEnd);
        }
        //调用实现条件分页查询
        Page<OrderInfo> pages = baseMapper.selectPage(infoPage, wrapper);
        long totalCount = pages.getTotal();
        long pageCount = pages.getPages();
        List<OrderInfo> records = pages.getRecords();
        records.stream().forEach(item -> {
            this.getOrderDetail(item);
        });


        Map<String,Object> map = new HashMap<>();
        map.put("total",totalCount);
        map.put("pageCount",pageCount);
        map.put("records",records);
        return Result.ok(map);
    }

    @Override
    public void updateOrderStatus(String out_trade_no) {

    }

    @Override
    public Long submitOrder(OrderFormVo orderFormVo) {
        Long couponId = orderFormVo.getCouponId();
        Long courseId = orderFormVo.getCourseId();
        //ThreadLocal
        Long userId = AuthContextHolder.getUserId();

        LambdaQueryWrapper<OrderDetail> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OrderDetail::getUserId,userId);
        lambdaQueryWrapper.eq(OrderDetail::getCourseId,courseId);
        OrderDetail one = orderDetailService.getOne(lambdaQueryWrapper);
        if (one != null) {
            return one.getId();
        }
        Course course = courseFeignClient.getById(courseId);
        if (one == null) {
            throw new HiException(20001,"package com.fengling.ggkt.order.service.impl;120");
        }
        UserInfo userInfo = userInfoFeignClient.getById(userId);
        if(userInfo == null) {
            throw new HiException(20001,"用户不存在");
        }
        BigDecimal bigDecimal = new BigDecimal(0);
        if (couponId != null) {
            CouponInfo couponInfoById = couponInfoFeignClient.getById(couponId);
            bigDecimal = couponInfoById.getAmount();
        }
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUserId(userId);
        orderInfo.setNickName(userInfo.getNickName());
        orderInfo.setPhone(userInfo.getPhone());
        orderInfo.setProvince(userInfo.getProvince());
        orderInfo.setOriginAmount(course.getPrice());
        orderInfo.setCouponReduce(bigDecimal);
        orderInfo.setFinalAmount(orderInfo.getOriginAmount().subtract(orderInfo.getCouponReduce()));
        orderInfo.setOutTradeNo(OrderNoUtils.getOrderNo());
        orderInfo.setTradeBody(course.getTitle());
        orderInfo.setOrderStatus("0");
        baseMapper.insert(orderInfo);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(orderInfo.getId());
        orderDetail.setUserId(userId);
        orderDetail.setCourseId(courseId);
        orderDetail.setCourseName(course.getTitle());
        orderDetail.setCover(course.getCover());
        orderDetail.setOriginAmount(course.getPrice());
        orderDetail.setCouponReduce(new BigDecimal(0));
        orderDetail.setFinalAmount(orderDetail.getOriginAmount().subtract(orderDetail.getCouponReduce()));
        orderDetailService.save(orderDetail);
        orderDetailService.save(orderDetail);

        if(null != orderFormVo.getCouponUseId()) {
            couponInfoFeignClient.updateCouponInfoUseStatus(orderFormVo.getCouponUseId(), orderInfo.getId());
        }

        return orderInfo.getId();

    }







    @Override
    public OrderInfoVo getOrderInfoById(Long id) {
        OrderInfo orderInfo = baseMapper.selectById(id);
        OrderDetail orderDetail = orderDetailService.getById(id);
        OrderInfoVo orderInfoVo = new OrderInfoVo();
        BeanUtils.copyProperties(orderInfo,orderInfoVo);
        orderInfoVo.setCourseId(orderDetail.getCourseId());
        orderInfoVo.setCourseName(orderDetail.getCourseName());
        return orderInfoVo;
    }

    //查询订单数据
    private OrderInfo getOrderDetail(OrderInfo orderInfo) {
        Long id = orderInfo.getId();
        OrderDetail orderDetail = orderDetailService.getById(id);
        if(orderDetail != null) {
            String courseName = orderDetail.getCourseName();
            orderInfo.getParam().put("courseName",courseName);
        }
        return orderInfo;
    }
}
