package com.deadpineapple.rabbitmq.RabbitReceiver;

import com.deadpineapple.dal.RabbitMqEntities.FileIsConverted;
import com.deadpineapple.rabbitmq.RabbitSender.AbstractRabbitSender;

/**
 * Created by 15256 on 10/03/2016.
 */
public class FileIsConvertedReceiver extends AbstractRabbitReceiver<FileIsConverted> {
    @Override
    public String getQueueName() {
        return "fileIsConverted";
    }
}
