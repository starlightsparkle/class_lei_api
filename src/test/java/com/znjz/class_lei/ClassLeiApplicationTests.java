package com.znjz.class_lei;

import com.znjz.class_lei.utils.RabbitUtils;
import com.znjz.class_lei.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ClassLeiApplicationTests {


    @Autowired
    private RabbitAdmin rabbitAdmin;
    @Autowired
    private RabbitUtils rabbitUtils;
    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void createOrBindQueue() {
        rabbitAdmin.declareQueue(new Queue("app.class.common",true));
        rabbitAdmin.declareBinding(new Binding("app.class.common", Binding.DestinationType.QUEUE,"class_lei.exchange",
                "app.class.common",null));
    }


    @Test
    void contextLoads() {
        System.out.println(redisUtil.hasKey("-8352245804906408389"));
    }



}
