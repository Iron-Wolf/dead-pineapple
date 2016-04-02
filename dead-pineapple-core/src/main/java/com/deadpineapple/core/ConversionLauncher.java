package com.deadpineapple.core;

import com.deadpineapple.dal.RabbitMqEntities.FileIsConverted;
import com.deadpineapple.dal.RabbitMqEntities.FileIsUploaded;
import com.deadpineapple.dal.RabbitMqEntities.FileToConvert;
import com.deadpineapple.dal.dao.ConvertedFileDao;
import com.deadpineapple.dal.dao.IConvertedFileDao;
import com.deadpineapple.dal.dao.ISplitFileDao;
import com.deadpineapple.dal.entity.ConvertedFile;
import com.deadpineapple.dal.entity.SplitFile;
import com.deadpineapple.rabbitmq.RabbitInit;
import com.deadpineapple.rabbitmq.RabbitReceiver.IReceiver;
import com.deadpineapple.videoHelper.fileEdit.FileJoiner;
import com.deadpineapple.videoHelper.fileEdit.FileSplitter;

import javax.ejb.EJB;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15256 on 18/03/2016.
 */
public class ConversionLauncher {
    private RabbitInit rabbitInit = new RabbitInit();

    private static final int MaximumPartSize = 2 * 1024 * 1024;

    @EJB
    IConvertedFileDao convertedFileDao;

    @EJB
    ISplitFileDao splitFileDao;

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
        try {
            FileSplitter splitter = new FileSplitter(result.getFileName());
            ConvertedFile mainFile = convertedFileDao.findById(result.getFileId());
            List<File> fileParts = splitter.splitFile();

            for (File fileToConvert : fileParts) {
                //on enregistrer le split en bdd
                SplitFile sfile = new SplitFile();
                sfile.setSize(fileToConvert.length());
                sfile.setConverted(false);
                sfile.setSplitFilePath(fileToConvert.getAbsolutePath());
                sfile = splitFileDao.createFile(sfile);

                //on l'ajoute au main
                mainFile.getSplitFiles().add(sfile);

                //on envoie la requete rabbitmq
                FileToConvert fileToSend =new FileToConvert();
                fileToSend.setFileId(mainFile.getId());
                fileToSend.setSplitFileId(sfile.getId());
                fileToSend.setFileName(sfile.getSplitFilePath());
                fileToSend.setConvertionType(result.getNewFileType());
                fileToSend.setConvertionEncoding(result.getNewEncoding());
                rabbitInit.getFileToConvertSender().send(fileToSend);
            }

            //on enregistre la dépendance vers le fichier
            convertedFileDao.updateFile(mainFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //// TODO: 18/03/2016 envoyer le mail de confirmation de payment

    }


    public File joinFile(List<File> files) throws IOException, InterruptedException {
        // TODO: 31/03/2016 a faire pas dévellopé
        FileJoiner joiner = new FileJoiner(files, "newName");
        return joiner.joinFiles();
    }

    public void exeFileIsConverted(FileIsConverted result) {
        // // TODO: 18/03/2016 envoyer le mail de confirmation
        // TODO: 18/03/2016 creer le lien de DL
    }
}


