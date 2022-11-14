package com.fengling.ggkt.order.service;

import java.util.Map;

public interface WXPayService {
    Map<String, String> queryPayStatus(String orderNo);

    Map<String, Object> createJsapi(String orderNo);
}
