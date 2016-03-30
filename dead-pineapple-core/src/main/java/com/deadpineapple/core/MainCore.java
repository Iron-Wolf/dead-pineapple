package com.deadpineapple.core;

import com.deadpineapple.core.email.EmailSender;

/**
 * Created by 15256 on 12/02/2016.
 */
public class MainCore {
    public static void main(String[] args) {
        try {
            ConversionLauncher conversionLauncher = new ConversionLauncher();
            conversionLauncher.start();
        } catch (Exception ex) {
            ex.printStackTrace();
            main(args);
        }
    }
}
