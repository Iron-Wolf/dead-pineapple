package com.deadpineapple.core;

import com.deadpineapple.dal.RabbitMqEntities.FileIsConverted;
import com.deadpineapple.dal.RabbitMqEntities.FileIsUploaded;
import com.deadpineapple.dal.RabbitMqEntities.FileToConvert;
import com.deadpineapple.rabbitmq.RabbitInit;
import com.deadpineapple.rabbitmq.RabbitReceiver.IReceiver;
import com.deadpineapple.videoHelper.fileEdit.FileJoiner;
import com.deadpineapple.videoHelper.fileEdit.FileSplitter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15256 on 18/03/2016.
 */
public class ConversionLauncher {
    private RabbitInit rabbitInit = new RabbitInit();

    private static final int MaximumPartSize = 2 * 1024 * 1024;

    public void start() {

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

    public void exeFileIsUploaded(FileIsUploaded result) {
        //// TODO: 18/03/2016 découper le fichier si ca marche
        try {
            FileSplitter splitter = new FileSplitter(result.getFileName());
            List<File> fileParts = splitter.splitFile();

            return;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //// TODO: 18/03/2016 envoyer le mail de confirmation de payment

    }


    public File joinFile(List<File> files) {
        // TODO: 31/03/2016 a faire pas dévellopé
        FileJoiner joiner = new FileJoiner(files, "newName");
        return joiner.joinFiles();
    }

    public void exeFileIsConverted(FileIsConverted result) {
        // // TODO: 18/03/2016 envoyer le mail de confirmation
        // TODO: 18/03/2016 creer le lien de DL
    }
}


