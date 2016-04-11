package com.oceanbank.webapp.dashboard.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailService {

	
	public static void sendEmail() throws MessagingException{

		String host = "OBMAIL01";
		String from = "mmedina@oceanbank.com";
		String to = "nellbryant@yahoo.com";
		String subject = "test email by nell";
		
		Properties props = System.getProperties();
        props.put("mail.smtp.host", host);
        
        Session session = Session.getInstance(props, null);
        Message msg = new MimeMessage(session);
        
        msg.setFrom(new InternetAddress(from));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
        msg.setSubject(subject);
        msg.setHeader("X-Mailer", "sendhtml");
        msg.setText("Hello There");
        msg.setSentDate(new Date(Calendar.getInstance().getTimeInMillis()));
        
        //msg.setDataHandler(new DataHandler(new ByteArrayDataSource(getStrMessage(), "text/html")));
        
//        if (getCC() != null) {
//            msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(getCC(), false));
//        }
//        if (getBCC() != null) {
//            msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(getBCC(), false));
//        }

        // send the thing off
        Transport.send(msg);
	}
}
