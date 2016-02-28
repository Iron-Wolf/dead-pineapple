package com.deadpineapple.rabbitmq.test;

import com.deadpineapple.rabbitmq.RabbitConnection;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by 15256 on 27/02/2016.
 */
public class RecvMessage {
    private final static String QUEUE_NAME = "hello1";

    public static void main(String[] argv)
            throws java.io.IOException,
            java.lang.InterruptedException, TimeoutException {

        RabbitConnection rabbitConnection = new RabbitConnection();

        rabbitConnection.declareQueue(QUEUE_NAME);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(rabbitConnection.getChannel()) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        rabbitConnection.consumeQueue(QUEUE_NAME, consumer);

    }
}