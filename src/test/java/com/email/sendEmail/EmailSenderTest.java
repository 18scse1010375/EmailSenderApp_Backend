package com.email.sendEmail;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.email.sendEmail.entities.Message;
import com.email.sendEmail.services.EmailService;

import java.io.*;

@SpringBootTest
public class EmailSenderTest {
	
	@Autowired
	private EmailService emailService;
	
//	@Test
//	public void emailSenderTest() {
//		
//		String[]to=new String[] {"arun.singhal@enfuse-solutions.com","singhalarun03@gmail.com","krishbari01@gmail.com"} ;
//		
//		System.out.println("sending email..");
//		
//		emailService.sendEmail(to,"AWS DEMO" , "Just for Testing purpose"  );
//	}
//	
//	@Test
//	
//	public void sendEmailWithHtmlTest() {
//		String html="" + "<h1 style='color : red ; border : 1px solid red;  '> Welcome to Learn Code With Durgesh </h1>" +"" ; 
//		
//		emailService.sendEmailWithHtml("singhalarun03@gmail.com", "Email from springboot application", html );
//	}
	
	
//	@Test
//	public void sendEmailWithFileTest() {
//		emailService.sendEmailWithFile("singhalarun03@gmail.com", "File With Attachment", "Just for sending file with Attachment",new File("C:\\Users\\Arun.Singhal\\Desktop\\document.txt") );
//	}
	
	
	public  MultipartFile convertFileToMultipartFile(File file) throws IOException {
        FileInputStream input = new FileInputStream(file);
        return new MockMultipartFile("file", file.getName(), "text/plain", input);
    }
	
	
	@Test
	public void sendEmailwithFiles() throws IOException {
		InputStream[]inputStream=new InputStream[3];
		
		MultipartFile[]multiPart=new MultipartFile[3];
		
		File file1=new File("C:\\Users\\Arun.Singhal\\Desktop\\Slips\\AccountForm.pdf");
		File file2=new File("C:\\Users\\Arun.Singhal\\Desktop\\Payslip.pdf");
		File file3=new File("C:\\Users\\Arun.Singhal\\Desktop\\document.txt");
		
		
		MultipartFile multipartFile1 = convertFileToMultipartFile(file1);
		MultipartFile multipartFile2 = convertFileToMultipartFile(file2);
		MultipartFile multipartFile3 = convertFileToMultipartFile(file3);
		
		multiPart[0]=multipartFile1;
		multiPart[1]=multipartFile2; 
		multiPart[2]=multipartFile3;
		
		
		
		
	
	
		emailService.sendEmailWithFiles("singhalarun03@gmail.com", "Welcome!", "Testing", multiPart);
	}
	
	
//	@Test
//	public void sendEmailWithFileWithStream() {
//		
//		File file=new File("C:\\Users\\Arun.Singhal\\Desktop\\image.jfif");
//		
//		try {
//			InputStream is=new FileInputStream(file);
//			emailService.sendEmailWithFile("singhalarun03@gmail.com", "Email With File", "This Email Contains file", is);
//			
//			
//		}catch(FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		
//	}
	
	
//	@Test
//	public void getInbox() {
//		List<Message>inboxMessages=emailService.getInboxMessages();
//		
//		inboxMessages.forEach(item->{
//			System.out.println(item.getSubject());
//			System.out.println(item.getContent());
//			System.out.println(item.getFiles());
//			
//			System.out.println("--------------------");
//		});
		
		
		
		
	}
	
	
	


