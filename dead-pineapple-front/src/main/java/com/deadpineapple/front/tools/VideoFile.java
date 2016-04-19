package com.deadpineapple.front.tools;

import com.deadpineapple.dal.entity.ConvertedFile;
import com.deadpineapple.videoHelper.information.VideoInformation;

import javax.persistence.Transient;

/**
 * Created by saziri on 19/04/2016.
 */
public class VideoFile{

    public VideoInformation getVideoInformation() {
        return videoInformation;
    }

    public void setVideoInformation(VideoInformation videoInformation) {
        this.videoInformation = videoInformation;
    }

    public ConvertedFile getConvertedFile() {
        return convertedFile;
    }

    public void setConvertedFile(ConvertedFile convertedFile) {
        this.convertedFile = convertedFile;
    }

    VideoInformation videoInformation;
    ConvertedFile convertedFile;
}
