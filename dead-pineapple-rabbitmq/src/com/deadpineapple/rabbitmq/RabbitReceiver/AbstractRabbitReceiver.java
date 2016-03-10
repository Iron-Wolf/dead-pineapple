package com.deadpineapple.rabbitmq.RabbitReceiver;

import com.deadpineapple.dal.RabbitMqEntities.FileToConvert;
import com.deadpineapple.rabbitmq.RabbitConnection;
import com.deadpineapple.rabbitmq.Serializer.ObjectByteSerializer;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by 15256 on 10/03/2016.
 */
public abstract class AbstractRabbitReceiver<T> {

    public abstract String getQueueName();

    private RabbitConnection rabbitConnection;

    public RabbitConnection getRabbitConnection() {
        if (rabbitConnection == null) {
            try {
                rabbitConnection = new RabbitConnection();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            rabbitConnection.declareQueue(getQueueName());
        }
        return rabbitConnection;
    }

    public void receiver(final IReceiver<T> receiver) throws IOException {
        getRabbitConnection().declareQueue(getQueueName());
        Consumer consumer = new DefaultConsumer(getRabbitConnection().getChannel()) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                T obj = ObjectByteSerializer.DeSerialyze(body);
                receiver.execute(obj);
            }
        };
        rabbitConnection.consumeQueue(getQueueName(), consumer);
    }


}
