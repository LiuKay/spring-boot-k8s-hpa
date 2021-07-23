package com.learnk8s.app.controller;

import com.learnk8s.app.MessageService;
import com.learnk8s.app.model.Ticket;

import org.apache.rocketmq.spring.autoconfigure.RocketMQProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@Controller
public class HelloController {

    @Value("${demo.consumer.topic}")
    private String queueName;

    private final RocketMQProperties rocketMQProperties;
    private final MessageService messageService;

    @Autowired
    public HelloController(RocketMQProperties rocketMQProperties,
                           MessageService messageService) {
        this.rocketMQProperties = rocketMQProperties;
        this.messageService = messageService;
    }

    @GetMapping("/")
    public String home(Model model) {
        int pendingMessages = messageService.pendingJobs(queueName);
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("pendingJobs", pendingMessages);
        model.addAttribute("completedJobs", messageService.completedJobs());
        model.addAttribute("isConnected", messageService.isUp() ? "yes" : "no");
        model.addAttribute("queueName", this.queueName);
        model.addAttribute("consumerName", this.rocketMQProperties.getConsumer().getGroup());
        //TODO:
        model.addAttribute("isStoreEnabled", true);
        model.addAttribute("isWorkerEnabled", true);
        return "home";
    }

    @PostMapping("/submit")
    public String submit(@ModelAttribute Ticket ticket) {
        for (long i = 0; i < ticket.getQuantity(); i++) {
            String id = UUID.randomUUID().toString();
            messageService.send(queueName, id);
        }
        return "success";
    }

    @ResponseBody
    @RequestMapping(value="/metrics", produces="text/plain")
    public String metrics() {
        int totalMessages = messageService.pendingJobs(queueName);
        return "# HELP messages Number of messages in the queueService\n"
                + "# TYPE messages gauge\n"
                + "messages " + totalMessages;
    }

    @RequestMapping(value="/health")
    public ResponseEntity health() {
        HttpStatus status;
        if (messageService.isUp()) {
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(status);
    }


}