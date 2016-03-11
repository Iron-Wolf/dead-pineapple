package com.deadpineapple.transcoder.FfmpegHelper;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;

/**
 * Created by 15256 on 11/03/2016.
 */
public class LaunchConvertion {
    private Process process;
    private String filePath;
    private String fileDestinationPath;

    public LaunchConvertion() {

    }

    public LaunchConvertion(String filePath, String fileDestinationPath) {
        this.filePath = filePath;
        this.fileDestinationPath = fileDestinationPath;
    }

    public Boolean start() throws IOException, InterruptedException {
        process = Runtime.getRuntime().exec(generateFfmpegCommand());
        return process.waitFor() == 0;
    }

    private String generateFfmpegCommand() {
        String cmd = "ffmpeg";

        String globalOptions = " -y -nostats -loglevel 0";
        String inputFileOptions = " ";
        String outputFileOptions = " ";

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

}
