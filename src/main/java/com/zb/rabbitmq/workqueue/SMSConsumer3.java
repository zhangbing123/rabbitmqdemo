package com.zb.rabbitmq.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.zb.rabbitmq.constants.Constants;
import com.zb.rabbitmq.instance.RabbitMQConnection;

import java.io.IOException;

/**
 * 工作队列模式-消费者3
 */
public class SMSConsumer3 {

    public static void main(String[] args) throws IOException {

        Connection connection = RabbitMQConnection.getInstance();

        Channel channel = connection.createChannel();
        channel.queueDeclare(Constants.WORK_QUEUE, false, false, false, null);

        /**
         * 如果不配置Qos，MQ会将所有消息平均分配给所有消费者
         * 如果配置了Qos，消费者确认处理完消息后，再次从MQ中获取1条消息进行处理
         * 可以根据不同消费者的消费能力分配消息进行处理
         */
        channel.basicQos(1);

        channel.basicConsume(Constants.WORK_QUEUE, false, new SMSReciver(channel, "consumer3"));

    }
}
