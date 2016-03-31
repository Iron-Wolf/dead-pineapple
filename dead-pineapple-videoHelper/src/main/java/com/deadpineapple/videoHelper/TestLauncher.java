package com.deadpineapple.videoHelper;

import com.deadpineapple.videoHelper.fileEdit.FileSplitter;
import com.deadpineapple.videoHelper.information.VideoInformation;

/**
 * Created by 15256 on 31/03/2016.
 */
public class TestLauncher {
    public static void main(String[] args) {
        FileSplitter vid = new FileSplitter("D:\\serie\\into badlands\\s1\\Into.The.Badlands.S01E01.FASTSUB.VOSTFR.1080p.WEB-DL.HEVC.H265-Yn1D.mkv");
        try {
            vid.splitFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
