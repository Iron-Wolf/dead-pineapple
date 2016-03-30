package com.deadpineapple.core.email;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * Created by 15256 on 13/03/2016.
 */
public class EmailSender {
    private String destination;
    private String subject;
    private String htmlContent;
    private String textContent;

    public EmailSender(String destination, String subject, String htmlContent) {
        this.destination = destination;
        this.subject = subject;
        this.htmlContent = htmlContent;
        this.textContent = getTextFromHtml(htmlContent);
    }

    public void send(){
        // Get system properties & Setup mail server
        Properties props = System.getProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "email-smtp.eu-west-1.amazonaws.com");//// TODO: 13/03/2016 faire les changements pour l'envoie

        // Get the default Session subject & auth
        Session session = Session.getDefaultInstance(props,new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("AKIAICQVR7DNANEOASQQ", "AtTn1/ji1PktPCDAzhNI58YxUNLhtG6AQBwIoFom6mc3");
            }
        });

        try{
            // Create a default MimeMessage
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("lucas@zientek.fr","noreply deadpineapple"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(getDestination()));
            message.setSubject(getSubject());

            // set text and html
            message.setText(getTextContent());
            Multipart mp = new MimeMultipart();
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(getHtmlContent(), "text/html");
            mp.addBodyPart(htmlPart);
            message.setContent(mp);

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        }catch (MessagingException mex) {
            mex.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private String getTextFromHtml(String htmlContent) {
        return htmlContent;
    }


    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }
}
