package com.deadpineapple.core;

import com.deadpineapple.core.email.EmailSender;
import com.deadpineapple.core.email.MailGenerator;

import java.io.IOException;
import java.util.Hashtable;

/**
 * Created by 15256 on 09/05/2016.
 */
public class MailTestMain {
    public static void main(String[] args) throws IOException {
        Hashtable<String,String> values = MailGenerator.getConvertedFileConrespondanceTable("Sofiane","porn.xxx","pornhub.com","https://images.duckduckgo.com/iu/?u=http%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.M8d90d4396de2c488a801c15fd571da1eo0%26pid%3D15.1&f=1");
        MailGenerator mail = new MailGenerator(MailGenerator.FICHIER_CONVERTIE_TEMPLATE,values);
        EmailSender sender = new EmailSender("152565@supinfo.com","test email",mail.generateTheEmail());
    sender.send();
    }
}
