package com.github.joaoh4547.task.notification.mail;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.mail.SimpleEmail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.lang.reflect.InvocationTargetException;

public class MailSender {

    private static final Logger LOG = LoggerFactory.getLogger(MailSender.class);

    private SimpleEmail simpleEmail;


    private void config() {
        simpleEmail = new SimpleEmail();
        simpleEmail.setDebug(true);
        simpleEmail.setHostName("smtp.gmail.com");
        simpleEmail.setSmtpPort(587);
        simpleEmail.setAuthenticator(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("joaohenriquetdj@gmail.com", "kldsjerjwtjwhqpc");
            }
        });
        simpleEmail.setSSLOnConnect(true);
        simpleEmail.setStartTLSEnabled(true);
    }


    public void sendEmail(Mail mail) {
        config();
        try {
            simpleEmail.setFrom("joaohenriquetdj@gmail.com");
            simpleEmail.setSubject(mail.getSubject());
            simpleEmail.setMsg(mail.getBody());
            simpleEmail.addTo(mail.getTo());
            simpleEmail.send();
            LOG.info("Email sent successfully");
        } catch (Exception e) {
            LOG.error("Error sending email", e);
        }
    }


}
