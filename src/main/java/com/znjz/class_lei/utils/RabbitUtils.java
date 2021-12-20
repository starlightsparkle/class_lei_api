package com.znjz.class_lei.utils;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class RabbitUtils {

    @Autowired
    private RabbitAdmin rabbitAdmin;

    public void createOrBindQueue(String queueName,String rkey) {
        rabbitAdmin.declareQueue(new Queue(queueName,true));
        rabbitAdmin.declareBinding(new Binding(queueName, Binding.DestinationType.QUEUE,"class_lei.exchange",
                rkey,null));
    }


}
