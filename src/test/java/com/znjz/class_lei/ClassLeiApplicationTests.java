package com.znjz.class_lei;

import com.znjz.class_lei.utils.RabbitUtils;
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

    @Test
    public void createOrBindQueue() {
        rabbitAdmin.declareQueue(new Queue("app.class.common",true));
        rabbitAdmin.declareBinding(new Binding("app.class.common", Binding.DestinationType.QUEUE,"class_lei.exchange",
                "app.class.common",null));
    }


    @Test
    void contextLoads() {
        rabbitUtils.deleteQueue("text1");
    }


}
