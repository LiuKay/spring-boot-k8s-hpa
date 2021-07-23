package com.learnk8s.app.rocketmq;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(consumerGroup = "${demo.consumer.group}", topic = "${demo.consumer.topic}")
public class StringConsumer implements RocketMQListener<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(StringConsumer.class);

    @Override
    public void onMessage(String message) {
        try {
            LOGGER.info("Processing message: {}", message);
            Thread.sleep(3000);
            LOGGER.info("Processing completed: {}", message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
