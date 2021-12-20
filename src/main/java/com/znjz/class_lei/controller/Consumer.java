package com.znjz.class_lei.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Consumer {

    @RabbitListener(queues = "text2")
    public void receiveConfirmMessage(Message message){
        String msg = new String(message.getBody());
        log.info("接受到的队列confirm.queue消息：{}",msg);
    }


}
