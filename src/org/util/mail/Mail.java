package org.util.mail;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Mail {
    private String auth;
    private String ttls;
    private String host;
    private String port;
    private String account;
    private String password;

    /***
     * FTPConnection constructor
     * iT initializes a map with properties files
     */
    public Mail() {
        MailProperties mailProperties = new MailProperties("mail.properties");
        Map<String, String> propertiesMap = mailProperties.readProperties();
        auth = propertiesMap.get("mail.smtp.auth");
        ttls = propertiesMap.get("mail.smtp.starttls.enable");
        host = propertiesMap.get("mail.smtp.host");
        port = propertiesMap.get("mail.smtp.port");
        account = propertiesMap.get("gen.user");
        password = propertiesMap.get("gen.password");
    }

    /***
     * Send a email to a subject.
     * <p>
     * This method send a message to an another email.
     * </p>
     * @param email representing the destinatary.
     * @param subject representing the subject
     * @param contextText representing the content of message
     * @return
     */
    public boolean sendEmail(String email, String subject, String contextText) {
        boolean emailSend = false;
        Message message = prepareMessageWithHTMLContent(email, subject, contextText);
        try {
            Transport.send(message);
            emailSend = true;
        } catch (MessagingException e) {
            Logger.getLogger( Mail.class.getName() ).log(Level.SEVERE, null, e);
        }
        return emailSend;
    }

    private Message prepareMessageWithHTMLContent(String emailToSend, String subject, String contentText) {
        Message message = null;
        Session session = prepareSession();
        try{
            message = new MimeMessage(session);
            message.setFrom(new InternetAddress(account));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailToSend));
            message.setSubject(subject);
            message.setContent(contentText, "text/html; charset=utf-8");
            return message;
        } catch (AddressException e) {
            Logger.getLogger( Mail.class.getName() ).log(Level.SEVERE, null, e);
        } catch (MessagingException e) {
            Logger.getLogger( Mail.class.getName() ).log(Level.SEVERE, null, e);
        }
        return message;
    }

    private Session prepareSession() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", auth);
        properties.put("mail.smtp.starttls.enable", ttls);
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(account, password);
            }
        });
        return session;
    }

}
