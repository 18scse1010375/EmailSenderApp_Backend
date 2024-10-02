package com.email.sendEmail.services.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.email.sendEmail.entities.Message;
import com.email.sendEmail.services.EmailService;

import jakarta.mail.BodyPart;
import jakarta.mail.Folder;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.NoSuchProviderException;
import jakarta.mail.Part;
import jakarta.mail.Session;
import jakarta.mail.Store;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {
	
	private JavaMailSender mailSender;
	
	private Logger logger=LoggerFactory.getLogger(EmailServiceImpl.class);
	
	
	@Value("${mail.store.protocol}")
	private String protocol;
	
	@Value("${mail.imaps.host}")
	private String host;
	
	@Value("${mail.imaps.port}")
	private String port;
	
	@Value("${spring.mail.username}")
	private String username;
	
	@Value("${spring.mail.password}")
	private String password;
	
	
	public EmailServiceImpl(JavaMailSender mailSender) {
		this.mailSender=mailSender;
	}
	

	@Override
	public void sendEmail(String to, String subject, String message) {
		// TODO Auto-generated method stub
		
		SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
		
		simpleMailMessage.setTo(to);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(message);
		
		mailSender.send(simpleMailMessage);
		
		logger.info("Email Sent Successfully");
		
		
	}

	@Override
	public void sendEmail(String[] to, String subject, String message) {
		// TODO Auto-generated method stub
		
		SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
		
		simpleMailMessage.setTo(to);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(message);
		
		mailSender.send(simpleMailMessage);
		
	}

	@Override
	public void sendEmailWithHtml(String to, String subject, String htmlContent) {
		// TODO Auto-generated method stub
		
		MimeMessage mimeMessage=mailSender.createMimeMessage();
		
		try {
			  MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true,"UTF-8");
			  
			  helper.setTo(to);
			  helper.setSubject(subject);
			  helper.setText(htmlContent, true);
			  
			  mailSender.send(mimeMessage);
			  
			  logger.info("Email has been sent!!");
				
			
		}catch(MessagingException e) {
			throw new RuntimeException(e);
			
		}
		
	 
		
		
	}

	@Override
	public void sendEmailWithFile(String to, String subject, String message, File file) {
		// TODO Auto-generated method stub
		
		MimeMessage mimeMessage=mailSender.createMimeMessage();
		
		try {
			MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true,"UTF-8");
			
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(message,true);
			
			
			
			FileSystemResource fileSystemResource=new FileSystemResource(file);
			
			helper.addAttachment(fileSystemResource.getFilename(), file );
			
			
			
			mailSender.send(mimeMessage);
			logger.info("Email has been sent!!");
			
		
			
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	
	@Override
	public void sendEmailWithFiles(String to, String subject, String message, MultipartFile[] multipartFiles) {
	    // Create the MimeMessage
	    MimeMessage mimeMessage = mailSender.createMimeMessage();

	    try {
	        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

	        helper.setTo(to);
	        helper.setSubject(subject);
	        helper.setText(message);

	        // Process each input stream
	        for (MultipartFile file : multipartFiles) {
	        	 ByteArrayResource resource = new ByteArrayResource(file.getBytes());
                helper.addAttachment(file.getOriginalFilename(), resource);
            }

	        // Send the email
	        mailSender.send(mimeMessage);
	        logger.info("Email has been sent!!");

	    } catch (MessagingException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    }
	}

	
	@Override
	public void sendEmailWithFile(String to, String subject, String message, InputStream is) {
		// TODO Auto-generated method stub
		
		MimeMessage mimeMessage=mailSender.createMimeMessage();
		
		try {
			MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true,"UTF-8");
			
			helper.setTo(to);
			helper.setSubject(subject);
			
			File file=new File("src/main/resources/email/AccountForm.pdf");
			
			Files.copy(is, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
			
			
			
			
			
			FileSystemResource fileSystemResource=new FileSystemResource(file);
			
			helper.addAttachment(fileSystemResource.getFilename(), file );
			
			helper.setText(message);
			
			mailSender.send(mimeMessage);
			logger.info("Email has been sent!!");
			
		
			
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch(IOException e) {
			throw new RuntimeException();
		}
		 
		
		
		
	}


	@Override
	public List<Message> getInboxMessages()  {
		
		List<Message>list=new ArrayList<>();
		
		Properties configurations=new Properties();
		
		configurations.setProperty("mail.store.protocol", protocol);
		configurations.setProperty("mail.imaps.host", host);
		
		configurations.setProperty("mail.imaps.port", port);
		
		
		
		// TODO Auto-generated method stub
		  Session session=Session.getDefaultInstance(configurations);
		  
		 try {
			Store store= session.getStore();
			
			store.connect(username, password);
			Folder inbox=  store.getFolder("INBOX");
			
			inbox.open(Folder.READ_ONLY);
			
		jakarta.mail.Message[]messages=inbox.getMessages();
		
		
		for(jakarta.mail.Message message : messages      ) {
			
			
			String content;
			try {
				content = getContentFromEmailMessage(message);
				List<String>files=getFilesFromEmail(message);
				
				list.add(new Message(content,files,message.getSubject())  );
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		  
			
		}
			
		
		 }
		 catch (NoSuchProviderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(MessagingException e) {
				e.printStackTrace();
			}
			
			
			
			
		
		 
		 
		  
		return list;
	}


	public List<String> getFilesFromEmail(jakarta.mail.Message message) throws MessagingException, IOException {
		// TODO Auto-generated method stub
		
		List<String>files=new ArrayList<>();
		
		if(message.isMimeType("multipart/*") )
		{
			
			Multipart content=(Multipart)message.getContent();
			
			for(int i=0;i<content.getCount();i++) {
				BodyPart bodyPart=content.getBodyPart(i);
				
				if(Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
				InputStream inputStream=bodyPart.getInputStream();
				
				File file=new File("src/main/resources/email" +bodyPart.getFileName() );
				
				Files.copy(inputStream, file.toPath() , StandardCopyOption.REPLACE_EXISTING  );
				
				files.add(file.getAbsolutePath());
				}
			}
			
			
		}
		
				
		return files;	
				

	}


	public String getContentFromEmailMessage(jakarta.mail.Message message) throws MessagingException, IOException {
		
		if(message.isMimeType("plain/text")  ||  message.isMimeType("text/html") ) {
			String content=(String)message.getContent();
			return content;
		}
		
		else if(message.isMimeType("multipart/*")  ) {
			Multipart part=(Multipart) message.getContent();
			
			for(int i=0;i<part.getCount();i++) {
				BodyPart bodyPart=part.getBodyPart(i);
				
				if(bodyPart.isMimeType("text/plain")) {
					return (String) bodyPart.getContent();
				}
				
			}
			
			
		}
		 
		return "arun";
		
	}


	@Override
	public void sendEmailWithFiles(String to, String subject, String message, InputStream[] inputStreams) {
		// TODO Auto-generated method stub
		
	}


}
