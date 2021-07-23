package com.learnk8s.app;

public interface MessageService {
    int completedJobs();

    void send(String destination, String message);

    int pendingJobs(String queueName);

    boolean isUp();
}
