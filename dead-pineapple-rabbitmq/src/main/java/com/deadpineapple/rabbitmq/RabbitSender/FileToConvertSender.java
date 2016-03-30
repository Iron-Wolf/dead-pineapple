package com.deadpineapple.rabbitmq.RabbitSender;

import com.deadpineapple.dal.RabbitMqEntities.FileToConvert;

/**
 * Created by 15256 on 10/03/2016.
 */
public class FileToConvertSender extends AbstractRabbitSender<FileToConvert> {
    @Override
    public String getQueueName() {
        return "fileToConvertQueue";
    }
}
