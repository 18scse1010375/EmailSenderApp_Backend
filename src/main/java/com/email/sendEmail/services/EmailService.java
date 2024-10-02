package com.email.sendEmail.services;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.email.sendEmail.entities.Message;

public interface EmailService {
	
	//send email to single person
	public void sendEmail(String to ,  String subject , String message );
	
	//send email to multiple person
	public void sendEmail(String[]to  , String subject  ,String message  ) ;   
	
	
	//send email with html content
	public void sendEmailWithHtml(String to , String subject , String htmlContent  ) ; 
	
	void sendEmailWithFiles(String to, String subject, String message, InputStream[] inputStreams);

	public void sendEmailWithFile(String to , String subject  ,  String message  , File file     );
	
	public void sendEmailWithFile(String to, String subject, String message, InputStream is);
	
	public List<Message>getInboxMessages();

	void sendEmailWithFiles(String to, String subject, String message, MultipartFile[] multipartFiles);

	
	
	
	
	
	
	

}
