package com.znjz.class_lei.service.impl;


import com.rabbitmq.client.Channel;
import com.znjz.class_lei.service.QueueServer;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class QueueServiceImpl implements QueueServer {
    @Autowired
    SimpleMessageListenerContainer container;


    @Autowired
    private RabbitAdmin rabbitAdmin;


    @Override
    public void createOrBindQueue(String queuename, String routeKeys) {
        rabbitAdmin.declareQueue(new Queue("amqpadmin.queue",true));

    }
}
