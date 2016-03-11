package com.deadpineapple.transcoder;

import com.deadpineapple.transcoder.FfmpegHelper.LaunchConvertion;

import java.io.IOException;

/**
 * Created by 15256 on 12/02/2016.
 */
public class MainTranscoder {
    public static void main(String[] args) {
        LaunchConvertion conv = new LaunchConvertion("C:\\Users\\15256\\Videos\\01_02-How to Access Your Working Files.mp4", "C:\\Users\\15256\\Videos\\out.avi");
        try {
            Boolean result = conv.start();
            System.out.print(result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
