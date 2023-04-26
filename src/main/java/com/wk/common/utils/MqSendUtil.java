package com.wk.common.utils;

import com.wk.common.dto.MqRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
public class MqSendUtil {

    @Autowired(required = false)
    private RocketMQTemplate rocketMQTemplate;

    public <T> boolean syncSend(MqRequest mqRequest, T t, Consumer<T> successConsumer, Consumer<T> failConsumer) {
        try{
            check(mqRequest);
            SendResult sendResult = rocketMQTemplate.syncSend(mqRequest.getTopic(), MessageBuilder.withPayload(mqRequest.getData()).setHeader("KEYS", mqRequest.getMessageKey()).build());
            log.info("rocketMq发送响应：MsgId:{}，发送状态：{}", sendResult.getMsgId(), sendResult.getSendStatus());
            if(t != null && successConsumer != null) {
                successConsumer.accept(t);
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            log.error("rocketMq发送消息失败", e);
            if(t != null && failConsumer != null) {
                failConsumer.accept(t);
            }
            return false;
        }
    }

    public <T> void asyncSend(MqRequest mqRequest, T t, Consumer<T> successConsumer, Consumer<T> failConsumer) {
        try{
            check(mqRequest);
            rocketMQTemplate.asyncSend(mqRequest.getTopic(),
                    MessageBuilder.withPayload(mqRequest.getData()).setHeader("KEYS", mqRequest.getMessageKey()).build(),
                    new SendCallback() {
                        @Override
                        public void onSuccess(SendResult sendResult) {
                            log.info("rocketMq发送响应：MsgId:{}，发送状态：{}", sendResult.getMsgId(), sendResult.getSendStatus());
                            if(t != null && successConsumer != null) {
                                successConsumer.accept(t);
                            }
                        }

                        @Override
                        public void onException(Throwable throwable) {
                            log.error("rocketMq发送失败", throwable);
                            if(t != null && failConsumer != null) {
                                failConsumer.accept(t);
                            }
                        }
                    });
        }catch (Exception e) {
            e.printStackTrace();
            log.error("rocketMq发送消息失败", e);
            try {
                if (t != null && failConsumer != null) {
                    failConsumer.accept(t);
                }
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void check(MqRequest mqRequest) {
        if (null == mqRequest) {
            throw new RuntimeException("MqRequest不能为空");
        }
        if (StringUtils.isBlank(mqRequest.getTopic())) {
            throw new RuntimeException("mqRequest.getTopic()不能为空");
        }
        if (StringUtils.isBlank(mqRequest.getMessageKey())) {
            throw new RuntimeException("mqRequest.getMessageKey()不能为空");
        }
        if (null == mqRequest.getData() || (mqRequest.getData() instanceof String
                && StringUtils.isBlank((String) mqRequest.getData()))) {
            throw new RuntimeException("mqRequest.getData()不能为空");
        }
    }
}
