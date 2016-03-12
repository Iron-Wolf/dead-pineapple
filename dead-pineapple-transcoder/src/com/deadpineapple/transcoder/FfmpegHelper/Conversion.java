package com.deadpineapple.transcoder.FfmpegHelper;

import com.deadpineapple.dal.constante.Constante;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by 15256 on 11/03/2016.
 */
public class Conversion {


    private Process process;
    private String filePath;
    private String fileDestinationPath;
    private String fileType;
    private Dictionary<String, String> outputFileOptionDictionnary;

    public Conversion() {

    }

    public Conversion(String filePath, String fileDestinationPath, String fileType) {
        this.filePath = filePath;
        this.fileDestinationPath = fileDestinationPath;
        this.fileType = fileType;
        this.outputFileOptionDictionnary = getOutputOptionDictionnary();
    }



    public Boolean start() throws IOException, InterruptedException, FfmpegException {
        process = Runtime.getRuntime().exec(generateFfmpegCommand());
        return process.waitFor() == 0;
    }

    private String generateFfmpegCommand() throws FfmpegException {
        if ( !Arrays.asList(Constante.AcceptedConversionTypes).contains(fileType)){
            throw new FfmpegException("Unvalid type");
        }
        String cmd = "ffmpeg";

        String globalOptions = " -y -nostats -loglevel 0";
        String inputFileOptions = " ";
        String outputFileOptions = " ";
        if (outputFileOptionDictionnary.get(fileType) != null) {
            outputFileOptions += outputFileOptionDictionnary.get(fileType);
        }
        cmd += globalOptions;
        cmd += inputFileOptions;
        cmd += "-i \"" + getFilePath() + "\"";
        cmd += outputFileOptions;
        cmd += "\"" + getFileDestinationPath() + "\"";
        return cmd;
    }

    public Process getProcess() {
        return process;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileDestinationPath() {
        return fileDestinationPath;
    }

    public void setFileDestinationPath(String fileDestinationPath) {
        this.fileDestinationPath = fileDestinationPath;
    }

    Dictionary<String, String> getOutputOptionDictionnary() {
        Dictionary<String, String> dico = new Hashtable<String, String>();
        dico.put(".flv", "-ar 22050 -crf 28 ");
        dico.put(".swf", "-ar 22050 -crf 28 ");
        dico.put(".dv", "-target pal-dv ");
        return dico;
    }

}
