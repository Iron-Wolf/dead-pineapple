package com.deadpineapple.core;

import com.deadpineapple.dal.RabbitMqEntities.FileIsConverted;
import com.deadpineapple.dal.RabbitMqEntities.FileIsUploaded;
import com.deadpineapple.dal.RabbitMqEntities.FileToConvert;
import com.deadpineapple.rabbitmq.RabbitInit;
import com.deadpineapple.rabbitmq.RabbitReceiver.IReceiver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15256 on 18/03/2016.
 */
public class ConversionLauncher {
    private RabbitInit rabbitInit = new RabbitInit();

    private static final int MaximumPartSize = 4 * 1024 * 1024;

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
            List<File> fileParts = splitFile(result.getFileName(), MaximumPartSize);
            for (File file : fileParts) {
                //// TODO: 30/03/2016 : enregistrer en base
                FileToConvert fileToConvert = new FileToConvert();
                fileToConvert.setConvertionType(result.getNewFileType());
                fileToConvert.setFileId(result.getFileId());
                fileToConvert.setFileName(file.getPath());
                rabbitInit.getFileToConvertSender().send(fileToConvert);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //// TODO: 18/03/2016 envoyer le mail de confirmation de payment

    }


    public List<File> splitFile(String filePath, long maxSize)
            throws IOException {

        List<File> files = new ArrayList<File>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String fileName = new File(filePath).getName();
        StringBuffer fileContent = new StringBuffer();
        String line;

        //on crée le premier fichier
        File currentFile = createSplittedFile(fileName, files.size()); //done un nouveau nom au fichier

        while ((line = reader.readLine()) != null) {
            fileContent.append(line);
            if (fileContent.length() >= maxSize) {
                writeFile(currentFile, fileContent.toString()); // on écrit dans le fichier précédent
                files.add(currentFile); // on l'ajoute a la liste
                fileContent = new StringBuffer(); // on remet a zero le buffer
                currentFile = createSplittedFile(fileName, files.size());//on crée le prochain fichier
            }
        }
        writeFile(currentFile, fileContent.toString()); //on vide la fin du fichier ici
        files.add(currentFile);

        return files;
    }


    private static File createSplittedFile(String fileName, int index)
            throws IOException {
        return new File(new File(fileName).getName() + "_" + index);
    }

    private void writeFile(File destFile, String content)
            throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(destFile));
        writer.write(content);
        writer.flush();
        writer.close();
    }


    public void exeFileIsConverted(FileIsConverted result) {
        // // TODO: 18/03/2016 envoyer le mail de confirmation
        // TODO: 18/03/2016 creer le lien de DL
    }
}


