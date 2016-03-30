package com.deadpineapple.rabbitmq;

import com.deadpineapple.dal.RabbitMqEntities.FileIsConverted;
import com.deadpineapple.rabbitmq.RabbitReceiver.FileIsConvertedReceiver;
import com.deadpineapple.rabbitmq.RabbitReceiver.FileToConvertReceiver;
import com.deadpineapple.rabbitmq.RabbitReceiver.FileUploadedReceiver;
import com.deadpineapple.rabbitmq.RabbitSender.FileIsConvertedSender;
import com.deadpineapple.rabbitmq.RabbitSender.FileToConvertSender;
import com.deadpineapple.rabbitmq.RabbitSender.FileUploadedSender;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by 15256 on 10/03/2016.
 */
public class RabbitInit {

    private FileIsConvertedReceiver fileIsConvertedReceiver = new FileIsConvertedReceiver();
    private FileToConvertReceiver fileToConvertReceiver = new FileToConvertReceiver();
    private FileUploadedReceiver fileUploadedReceiver = new FileUploadedReceiver();

    private FileIsConvertedSender fileIsConvertedSender = new FileIsConvertedSender();
    private FileToConvertSender fileToConvertSender = new FileToConvertSender();
    private FileUploadedSender fileUploadedSender = new FileUploadedSender();

    public FileIsConvertedReceiver getFileIsConvertedReceiver() {
        return fileIsConvertedReceiver;
    }

    public FileToConvertReceiver getFileToConvertReceiver() {
        return fileToConvertReceiver;
    }

    public FileUploadedReceiver getFileUploadedReceiver() {
        return fileUploadedReceiver;
    }

    public FileIsConvertedSender getFileIsConvertedSender() {
        return fileIsConvertedSender;
    }

    public FileToConvertSender getFileToConvertSender() {
        return fileToConvertSender;
    }

    public FileUploadedSender getFileUploadedSender() {
        return fileUploadedSender;
    }


    public void closeAll() {
        try {
            getFileIsConvertedSender().close();
            getFileToConvertSender().close();
            getFileUploadedSender().close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
