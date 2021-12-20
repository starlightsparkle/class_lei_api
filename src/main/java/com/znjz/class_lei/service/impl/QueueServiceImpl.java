package com.znjz.class_lei.service.impl;


import com.znjz.class_lei.service.QueueServer;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueueServiceImpl implements QueueServer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitAdmin rabbitAdmin;


    @Override
    public void sendMessage(String message, String rk) {
        rabbitTemplate.convertAndSend("class_lei.exchange",rk,message);
    }

    public void sendCommonMessage(String message) {
        sendMessage(message,"app.class.common");
    }



}
