package com.znjz.class_lei.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Consumer {

    @RabbitListener(queues = "app.class.36")
    public void receiveConfirmMessage(Message message){
        String msg = new String(message.getBody());
        log.info("接受到的队列confirm.queue消息：{}",msg);
    }

    @RabbitListener(queues = "app.class.common2")
    public void receive(Message message){
        String msg = new String(message.getBody());
        log.info("接受到的全局消息2：{}",msg);
    }

    @RabbitListener(queues = "app.class.common")
    public void receivee(Message message){
        String msg = new String(message.getBody());
        log.info("接受到的全局消息1：{}",msg);
    }


    @RabbitListener(queues = "app.user.7")
    public void recei(Message message){
        String msg = new String(message.getBody());
        log.info("user7接受到消息：{}",msg);
    }

    @RabbitListener(queues = "app.user.9")
    public void re(Message message){
        String msg = new String(message.getBody());
        log.info("user9接受到的消息：{}",msg);
    }

}
