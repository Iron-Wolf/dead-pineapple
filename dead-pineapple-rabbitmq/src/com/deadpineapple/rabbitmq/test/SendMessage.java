package com.deadpineapple.rabbitmq.test;

import com.deadpineapple.rabbitmq.RabbitConnection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by 15256 on 27/02/2016.
 */
public class SendMessage {

    public static final String QUEUE_NAME = "hello1";

    public static void main(String[] args) {
        try {
            RabbitConnection rabbit = new RabbitConnection();
            rabbit.declareQueue(QUEUE_NAME);
            String message = "Hello World!";
            rabbit.publishOnQueue(QUEUE_NAME, message);
            System.out.println(" [x] Sent '" + message + "'");
            rabbit.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
