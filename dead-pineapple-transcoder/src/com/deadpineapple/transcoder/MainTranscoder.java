package com.deadpineapple.transcoder;

import java.io.IOException;

/**
 * Created by 15256 on 12/02/2016.
 */
public class MainTranscoder {
    public static void main(String[] args) throws IOException, InterruptedException {

        Process ffmpeg = Runtime.getRuntime().exec("ffmpeg");
        ffmpeg.getOutputStream();
        ffmpeg.wait();
    }
}
