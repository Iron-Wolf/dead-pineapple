package com.deadpineapple.transcoder.FfmpegHelper;

import java.io.IOException;

/**
 * Created by 15256 on 11/03/2016.
 */
public class LaunchConvertion {
    private Process process;
    private String filePath;
    private String fileDestinationPath;
    private ConvertionType convertionType;

    public LaunchConvertion() {

    }

    public LaunchConvertion(String filePath, String fileDestinationPath, ConvertionType convertionType) {
        this.filePath = filePath;
        this.fileDestinationPath = fileDestinationPath;
        this.convertionType = convertionType;
    }

    public Boolean start() throws IOException {
        process = Runtime.getRuntime().exec(generateFfmpegCommand());
        process.getOutputStream();
        return false;
    }

    private String generateFfmpegCommand() {
        String cmd = "ffmpeg";

        String globalOptions = " ";
        String inputFileOptions = " ";
        String outputFileOptions = " ";

        cmd += globalOptions;
        cmd += inputFileOptions;
        cmd += " -i " + getFilePath();
        cmd += " " + outputFileOptions;
        cmd += " " + getFileDestinationPath();
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

    public ConvertionType getConvertionType() {
        return convertionType;
    }

    public void setConvertionType(ConvertionType convertionType) {
        this.convertionType = convertionType;
    }
}
