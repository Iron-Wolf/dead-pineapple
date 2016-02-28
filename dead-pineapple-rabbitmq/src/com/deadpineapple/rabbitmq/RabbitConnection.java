package com.deadpineapple.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by 15256 on 28/02/2016.
 */
public class RabbitConnection {

    public static final String FILE_IS_UPLOADED_QUEUE_NAME = "fileisuploaded";
    public static final String FILE_PART_IS_CONVERTED_QUEUE_NAME = "filepartisconverted";
    public static final String FILE_IS_CUTED_QUEUE_NAME = "fileiscuted";


    private Connection connection;
    private Channel channel;

    public RabbitConnection() throws IOException, TimeoutException {
        connection = connect();
        channel = connection.createChannel();
    }

    public Boolean declareQueue(String queueName) {
        try {
            channel.queueDeclare(queueName, true, false, false, null);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void consumeQueue(String queueName, Consumer consumer) throws IOException {
        channel.basicConsume(queueName, true, consumer);
    }

    public void publishOnQueue(String queueName, String message) throws IOException {
        channel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
    }

    public void close() throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }


    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public static Connection connect() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("lzi.space");
        factory.setUsername("rab1");
        factory.setPassword("rab1pass");
        return factory.newConnection();
    }
}
