package com.learnk8s.app.rocketmq;

import com.learnk8s.app.MessageService;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RocketMQServiceImpl implements MessageService {

    private final RocketMQTemplate rocketMQTemplate;

    @Autowired
    public RocketMQServiceImpl(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    @Override
    public int completedJobs() {
        return -1;
    }

    @Override
    public void send(String destination, String message) {
        rocketMQTemplate.convertAndSend(destination, message);
    }

    @Override
    public int pendingJobs(String queueName) {
        return -1;
    }

    @Override
    public boolean isUp() {
        return true;
    }
}
