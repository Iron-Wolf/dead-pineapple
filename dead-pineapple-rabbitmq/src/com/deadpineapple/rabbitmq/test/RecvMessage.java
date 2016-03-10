package com.deadpineapple.rabbitmq.test;

import com.deadpineapple.dal.RabbitMqEntities.FileIsConverted;
import com.deadpineapple.dal.RabbitMqEntities.FileIsUploaded;
import com.deadpineapple.dal.RabbitMqEntities.FileToConvert;
import com.deadpineapple.rabbitmq.RabbitConnection;
import com.deadpineapple.rabbitmq.RabbitInit;
import com.deadpineapple.rabbitmq.RabbitReceiver.IReceiver;
import com.deadpineapple.rabbitmq.Serializer.ObjectByteSerializer;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by 15256 on 27/02/2016.
 */
public class RecvMessage {

    public static void main(String[] argv)
            throws java.io.IOException,
            java.lang.InterruptedException, TimeoutException {

        RabbitInit rabbitInit = new RabbitInit();
        rabbitInit.getFileUploadedReceiver().receiver(
                new IReceiver<FileIsUploaded>() {
                    @Override
                    public void execute(FileIsUploaded result) {
                        System.out.println(result);
                    }
                }
        );
    }
}