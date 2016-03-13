package com.deadpineapple.core;

import com.deadpineapple.core.email.EmailSender;

/**
 * Created by 15256 on 12/02/2016.
 */
public class MainCore {
    public static void main(String[] args) {
        EmailSender emailSender = new EmailSender("152565@supinfo.com","java send test","<html><body><h1>hello world</h1></body></html>");
        emailSender.send();
        System.out.println("Hello, World!");
    }
}
