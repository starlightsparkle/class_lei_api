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

    private String RKEY ="app.class.";
    private String UserKEY="app.user.";

    @Autowired
    private RabbitAdmin rabbitAdmin;

    /**
     * 创建一个队列
     * @param queueName app.user.1;
     * @param rkey app.class.cid;
     */
    public void createOrBindQueue(String queueName,String rkey) {
        rabbitAdmin.declareBinding(new Binding(UserKEY+queueName, Binding.DestinationType.QUEUE,"class_lei.exchange",
                RKEY+rkey,null));
    }

    /**
     * 删除队列
     * @param queueName
     */
    public void deleteQueue(String queueName){
        rabbitAdmin.deleteQueue(UserKEY+queueName);
    }

    /**
     * 删除路由
     * @param queueName app.user.1;
     * @param rkey app.class.cid;
     */
    public void deleterkey(String queueName,String rkey){
        rabbitAdmin.removeBinding(new Binding(UserKEY+queueName, Binding.DestinationType.QUEUE,"class_lei.exchange",
                RKEY+rkey,null));
    }




}
