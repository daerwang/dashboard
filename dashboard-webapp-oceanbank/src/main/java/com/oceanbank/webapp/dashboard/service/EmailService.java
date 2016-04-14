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
	
	private final static String HOST_ADDRESS = "OBMAIL01";
	private String from;
	private String to;
	private String subject;
	private String message;

	public EmailService(){}
	
	public EmailService(String from, String to, String subject, String message){
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.message = message;
	}
	
	public void sendEmail() throws MessagingException{
		
		Properties props = System.getProperties();
        props.put("mail.smtp.host", HOST_ADDRESS);
        
        Session session = Session.getInstance(props, null);
        Message msg = new MimeMessage(session);
        
        msg.setFrom(new InternetAddress(from));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
        msg.setSubject(subject);
        msg.setHeader("X-Mailer", "sendhtml");
        msg.setText(message);
        msg.setSentDate(new Date(Calendar.getInstance().getTimeInMillis()));
        
        //msg.setDataHandler(new DataHandler(new ByteArrayDataSource(getStrMessage(), "text/html")));
        
        Transport.send(msg);
        
	}


	public String getFrom() {
		return from;
	}


	public void setFrom(String from) {
		this.from = from;
	}


	public String getTo() {
		return to;
	}


	public void setTo(String to) {
		this.to = to;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
