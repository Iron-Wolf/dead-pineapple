package com.deadpineapple.transcoder.FfmpegHelper;

/**
 * Created by 15256 on 11/03/2016.
 */
public class ConvertionType {
    private EncodingType from;
    private EncodingType to;

    public ConvertionType() {
    }

    public ConvertionType(EncodingType from, EncodingType to) {
        this.from = from;
        this.to = to;
    }

    public EncodingType getFrom() {
        return from;
    }

    public void setFrom(EncodingType from) {
        this.from = from;
    }

    public EncodingType getTo() {
        return to;
    }

    public void setTo(EncodingType to) {
        this.to = to;
    }
}
