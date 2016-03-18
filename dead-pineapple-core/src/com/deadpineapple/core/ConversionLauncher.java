package com.deadpineapple.core;

import com.deadpineapple.dal.RabbitMqEntities.FileIsConverted;
import com.deadpineapple.dal.RabbitMqEntities.FileIsUploaded;
import com.deadpineapple.dal.RabbitMqEntities.FileToConvert;
import com.deadpineapple.rabbitmq.RabbitInit;
import com.deadpineapple.rabbitmq.RabbitReceiver.IReceiver;

import java.io.IOException;

/**
 * Created by 15256 on 18/03/2016.
 */
public class ConversionLauncher
{
    private RabbitInit rabbitInit = new RabbitInit();

    public void start()
    {

        rabbitInit.getFileUploadedReceiver().receiver(new IReceiver<FileIsUploaded>() {
            @Override
            public void execute(FileIsUploaded result) {
                exeFileIsUploaded(result);
            }
        });

        rabbitInit.getFileIsConvertedReceiver().receiver(new IReceiver<FileIsConverted>() {
            @Override
            public void execute(FileIsConverted result) {
                exeFileIsConverted(result);
            }
        });
    }

    public void exeFileIsUploaded(FileIsUploaded result){
        //// TODO: 18/03/2016 découper le fichier si ca marche
        //// TODO: 18/03/2016 envoyer le mail de confirmation de payment
        FileToConvert fileToConvert = new FileToConvert();
        fileToConvert.setConvertionType(result.getNewFileType());
        fileToConvert.setFileId(result.getFileId());
        fileToConvert.setFileName(result.getFileName());
        rabbitInit.getFileToConvertSender().send(fileToConvert);
    }

    public void exeFileIsConverted(FileIsConverted result)
    {
        // // TODO: 18/03/2016 envoyer le mail de confirmation
        // TODO: 18/03/2016 creer le lien de DL
    }
}


