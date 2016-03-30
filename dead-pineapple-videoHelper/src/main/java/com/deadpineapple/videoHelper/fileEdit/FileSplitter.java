package com.deadpineapple.videoHelper.fileEdit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15256 on 31/03/2016.
 */
public class FileSplitter {
    private String filePath;

    public FileSplitter(String filePath) {
        this.filePath = filePath;
    }

    public List<File> splitFile()
            throws Exception {
        List<File> files = new ArrayList<File>();
        File file = new File(filePath);//File read from Source folder to Split.

        int splitFileIndex = 0;
        String resultPath = splitFileIndex + "_" + file.getName();
        String endTime = "00:00:03";
        String startTime = "00:00:00";
        String ffmpeg = "ffmpeg -v quiet -y -i " + filePath + " -vcodec copy -acodec copy -ss "
                + startTime + " -t " + endTime + " -sn " + resultPath;

        return files;
    }
}
