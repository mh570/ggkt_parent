package com.fengling.ggkt.wechat.service;

import java.util.Map;

public interface MessageService {
    void pushPayMessage(long l);

    String receiveMessage(Map<String, String> param);
}
