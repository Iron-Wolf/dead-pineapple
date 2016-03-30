package com.deadpineapple.videoHelper.fileEdit;

import java.io.File;
import java.util.List;

/**
 * Created by 15256 on 31/03/2016.
 */
public class FileJoiner {
    private List<File> files;
    String fileName;

    public FileJoiner(List<File> files, String fileName) {
        this.files = files;
        this.fileName = fileName;
    }

    public File joinFiles(){
        return new File("");
    }
}
